package res.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import UI.ITextField;
import car.controller.CarController;
import common.RentTableModel;
import member.controller.MemberController;
import res.controller.ResController;
import res.VO.ResVO;
import member.VO.MemberVO;

public class UseCarDialog extends JDialog {
	JPanel panelSearch, panelBtn;
	JLabel LResUserID;

	JTextField tfResUserID, tfResUserPW;
	JButton btnUserIDSearch;

	JButton btnResStart;
	JButton btnResReturn;

	JTable resTable;
	RentTableModel rentTableModel;
	String[] columnNames = { "예약번호", "예약차번호", "예약일자", "렌터카이용시작일자", "렌터카반납일자", "예약자아이디", "사용여부" };

	Object[][] resItems = new String[0][7]; // 테이블에 표시될 회원 정보 저장 2차원 배열
	int rowIdx = 0, colIdx = 0; // 테이블 수정 시 선택한 행과 열 인덱스 저장

	ResController resController;
	MemberController memberController;


	public UseCarDialog(ResController resController, MemberController memberController, String str) {
		this.resController = resController;
		this.memberController = memberController;
		setTitle(str);
		init();
	}

	private void init() {
		resTable = new JTable();
		ListSelectionModel rowSel = resTable.getSelectionModel();
		rowSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSel.addListSelectionListener(new ListRowSelectionHandler()); // 테이블 행 클릭 시 이벤트 처리

		ListSelectionModel colSel = resTable.getColumnModel().getSelectionModel();
		colSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		colSel.addListSelectionListener(new ListColSelectionHandler());

		panelSearch = new JPanel();
		panelBtn = new JPanel();

		LResUserID = new JLabel("사용자 정보");
	
		tfResUserID = new ITextField(10, "아이디");
		
		tfResUserPW = new ITextField(10, "비밀번호");
		btnUserIDSearch = new JButton("조회하기");
		btnUserIDSearch.addActionListener(new ResBtnHandler());

		panelSearch.add(LResUserID);
		panelSearch.add(tfResUserID);
		panelSearch.add(tfResUserPW);
		panelSearch.add(btnUserIDSearch);

		btnResStart = new JButton("사용시작");
		btnResReturn = new JButton("차량반납");

		btnResStart.addActionListener(new ResBtnHandler());
		btnResReturn.addActionListener(new ResBtnHandler());

		panelBtn.add(btnResStart);
		panelBtn.add(btnResReturn);

		add(panelSearch, BorderLayout.NORTH);
		add(panelBtn, BorderLayout.SOUTH);

		rentTableModel = new RentTableModel(resItems, columnNames);
		resTable.setModel(rentTableModel);
		add(new JScrollPane(resTable), BorderLayout.CENTER);

		setLocation(200, 100);// 다이얼로그 출력 위치를 정한다.
		setSize(600, 600);
		setModal(true); // 항상 부모창 위에 보이게 한다.
		setVisible(true);
		
		btnUserIDSearch.requestFocusInWindow();
	}

	private void loadTableData(List<ResVO> resList) {
		if (resList != null && resList.size() != 0) {
			resItems = new String[resList.size()][7];
			for (int i = 0; i < resList.size(); i++) {
				ResVO resVO = resList.get(i);
				resItems[i][0] = resVO.getResNumber();
				resItems[i][1] = resVO.getResCarNumber();
				resItems[i][2] = resVO.getResDate();
				resItems[i][3] = resVO.getUseBeginDate();
				resItems[i][4] = resVO.getReturnDate();
				resItems[i][5] = resVO.getResUserId();
				resItems[i][6] = resVO.getResUse();
			}

			rentTableModel = new RentTableModel(resItems, columnNames);
			resTable.setModel(rentTableModel);
		} else {
			showMessage("조회한 정보가 없습니다.");
			resItems = new Object[10][10];
			rentTableModel = new RentTableModel(resItems, columnNames);
			resTable.setModel(rentTableModel);
		}
	}

	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "메지지박스", JOptionPane.INFORMATION_MESSAGE);
	}

	class ResBtnHandler implements ActionListener {
		String STnowTime = getCurrentDate().replace("-", "");
        int nowTime = Integer.parseInt(STnowTime);
        
		String resNumber = null, resCarNumber = null, resDate = null, 
				useBeginDate = null, returnDate = null, resUserId = null, resUse = null;
		List<ResVO> resList = null;
		String password =null, resUser=null, resUserPW=null;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnUserIDSearch) {
				resUser = tfResUserID.getText().trim();
				resUserPW = tfResUserPW.getText().trim();
				List<ResVO> filteredList = new ArrayList<>();	
				List<ResVO> resList = resController.listResInfo(new ResVO());
				List<MemberVO> memList = memberController.listMember(new MemberVO());
				for (MemberVO member : memList) {
                    if (resUser.equals(member.getMemId())) {
                        password = member.getMemPassword();
                        break;
                    }
                }
				//회원 검색창에 이름을 입력한 경우와 입력하지 않은 경우를 처리하는 조건문
				if (resUser != null && resUserPW != null) {
					if (resUserPW.equals(password)) {														
						for (ResVO res : resList) {
							if (resUser.equals(res.getResUserId())) {
								filteredList.add(res);
							}
						}
						if (!filteredList.isEmpty()) {
							loadTableData(filteredList);
						}
						else if (resUser.equals("아이디") && resUserPW.equals("비밀번호")){
							loadTableData(resList);
						}
					}else {
						showMessage("조회한 정보가 없습니다.");
					}
				}
				return;


			} else if (e.getSource() == btnResStart) {
				resNumber = (String) resItems[rowIdx][0];
				resCarNumber = (String) resItems[rowIdx][1];
				resDate = (String) resItems[rowIdx][2];
				useBeginDate = (String) resItems[rowIdx][3];
				returnDate = (String) resItems[rowIdx][4];
				resUserId = (String) resItems[rowIdx][5];
				resUse = (String) resItems[rowIdx][6];				
				
				String useBeginDate1 = useBeginDate.replace("-", "");
		        int IntuseBeginDate = Integer.parseInt(useBeginDate1);
		        
		        String returnDate1 = returnDate.replace("-", "");
		        int IntreturnDate = Integer.parseInt(returnDate1);
		        
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate LCnowTime = LocalDate.parse(getCurrentDate(), formatter);
		        LocalDate LCreservationDate = LocalDate.parse(returnDate, formatter);
		        // 날짜 차이 계산
		        long daysBetween = ChronoUnit.DAYS.between(LCnowTime, LCreservationDate);
		        
				if(nowTime >= IntuseBeginDate && nowTime <= IntreturnDate) {
					if(resUse.equals("사용중")) {
						showMessage("이미 사용중 입니다.");
						return;
					}
					showMessage("차량을 사용합니다\n"
							+ "현재일: "+ getCurrentDate() +"\n"
							+ "반납일: "+ returnDate + "\n"
							+ "남은 날짜: " + daysBetween + "일\n"
							+ "차량을 늦지 않게 반납해 주세요.\n"
							+ "반납일 이내에 반납하지 않으면 추가 비용이 발생합니다."
							);
					 if (resUse.equals("대기")) {
						 resUse = "사용중";
		                }	
					 ResVO resVO = new ResVO(resNumber, resCarNumber, resDate,
								useBeginDate, returnDate, resUserId, resUse);
					resController.modResInfo(resVO);
				} else if (nowTime > IntreturnDate){
					showMessage("차량 렌트기간이 지났습니다.");
				} else {
					showMessage("아직 차량 렌트기간이 아닙니다.");
				}
				
			} else if (e.getSource() == btnResReturn) {
				resNumber = (String) resItems[rowIdx][0];
				resCarNumber = (String) resItems[rowIdx][1];
				resDate = (String) resItems[rowIdx][2];
				useBeginDate = (String) resItems[rowIdx][3];
				returnDate = (String) resItems[rowIdx][4];
				resUserId = (String) resItems[rowIdx][5];
				resUse = (String) resItems[rowIdx][6];								
		        
		        String returnDate1 = returnDate.replace("-", "");
		        int IntreturnDate = Integer.parseInt(returnDate1);
		        
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate LCnowTime = LocalDate.parse(getCurrentDate(), formatter);
		        LocalDate LCreservationDate = LocalDate.parse(returnDate, formatter);
		        // 날짜 차이 계산
		        long daysBetween = ChronoUnit.DAYS.between(LCnowTime, LCreservationDate);
		        
				if(nowTime <= IntreturnDate) {
					if(resUse.equals("대기")) {
						showMessage("이미 반납 했습니다.");
						return;
					}
					showMessage("차량을 정상적으로 반납 했습니다\n"
							+ "현재일: "+ getCurrentDate() +"\n"
							+ "반납일: "+ returnDate + "\n"
							+ "남은 날짜: " + daysBetween + "일\n"
							);
					 if (resUse.equals("사용중")) {
						 resUse = "대기";
		                }	
					 ResVO resVO = new ResVO(resNumber, resCarNumber, resDate,
								useBeginDate, returnDate, resUserId, resUse);
					//resController.modResInfo(resVO);
				} else if (nowTime > IntreturnDate){
					showMessage("차량 렌트기간이 "+ -daysBetween+"일 지났습니다.\n"
							+ "추가비용이 발생합니다\n"
							+"추가비용: "+ daysBetween*-15000 +"원");
				} else {
					showMessage("아직 차량 렌트기간이 아닙니다.");
				}
				
				
			}
			String resUser2 = tfResUserID.getText().trim();
			List<ResVO> resList = resController.listResInfo(new ResVO());
			ResVO resVO = new ResVO();
			resList = resController.listResInfo(resVO);
			List<ResVO> filteredList = new ArrayList<>();
			
			for (ResVO res : resList) {
				if (resUser2.equals(res.getResUserId())) {
					filteredList.add(res);
				}
			}
			
			loadTableData(filteredList);

		} // end actionPerformedb

	}// end MemberBtnHandler

	// 테이블의 행 클릭 시 이벤트 처리
	class ListRowSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				rowIdx = lsm.getMinSelectionIndex();
				System.out.println(rowIdx + " 번째 행이 선택됨...");
//				System.out.println(memData[rowIdx][colIdx]);
			}
		}
	}

	class ListColSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			colIdx = lsm.getMinSelectionIndex(); // 클릭한 열 인덱스를 얻습니다.
			if (!e.getValueIsAdjusting()) {
				System.out.println(rowIdx + " 번째 행, " + colIdx + "열 선택됨...");
//			System.out.println(memData[rowIdx][colIdx]);
			}
		}

	}
	  // 현재 날짜를 가져와서 YYYY-MM-DD 형식의 문자열로 반환하는 메서드
	  private String getCurrentDate() {
	      LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 포맷 지정
	      return currentDate.format(formatter); // 포맷에 맞게 문자열 반환
	  }
	  
}
