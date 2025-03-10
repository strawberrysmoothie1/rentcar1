package res.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import common.RentTableModel;
import res.controller.ResController;
import res.controller.ResControllerImpl;
import res.VO.ResVO;

public class RegResDialog  extends JDialog{
	JPanel jPanel;
	JLabel lResNum, lResCarNum, lResDate,lUseBeginDate,
	lReturnDate, lResUserId, lResUse;
	
	JTextField tfResNum, tfResCarNum, tfResDate, tfUseBeginDate,
	tfReturnDate, tfResUserId, tfResUse;
  JButton btnResReg;
  
  ResController resController;
	JTable rentTable;
	RentTableModel rentTableModel;


	Object[][] memData = null; // 테이블에 표시될 회원 정보 저장 2차원 배열
	int rowIdx = 0, colIdx = 0; // 테이블 수정 시 선택한 행과 열 인덱스 저장

	String carNumber;  //렌터카 조회 화면에서 예약 버튼 클릭 시 넘어온 예약차번호 저장  
	
	public RegResDialog(String carNumber) {
		this(new ResControllerImpl(), carNumber, "렌터카 예약창");
	    String nowTime = getCurrentDate();
	  	init(nowTime);
	}
  
  public RegResDialog(ResController resController, String str) {
  	this.resController = resController;
  	setTitle(str);
    // 현재 시간을 YYYY-MM-DD 형식의 문자열로 저장
    String nowTime = getCurrentDate();
  	init(nowTime);
  }
  
  public RegResDialog(ResController resController, String carNumber, String str) {
  	this.resController = resController;
  	this.carNumber = carNumber;
  	setTitle(str);
    // 현재 시간을 YYYY-MM-DD 형식의 문자열로 저장
    String nowTime = getCurrentDate();
  	init(nowTime);
  }

  // 현재 날짜를 가져와서 YYYY-MM-DD 형식의 문자열로 반환하는 메서드
  private String getCurrentDate() {
      LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 포맷 지정
      return currentDate.format(formatter); // 포맷에 맞게 문자열 반환
  }
  
  private void init(String nowTime) {
	  
	List<ResVO> resList = resController.listResInfo(new ResVO());
	int newResNum = 0;
	int resSize = resList.size();
	if(resSize != 0) {
  	ResVO resVO = resList.get(resSize-1);
  	String ResNum = resVO.getResNumber();
  	newResNum = Integer.parseInt(ResNum);
	}
  	lResNum = new JLabel("예약번호");
  	lResCarNum = new JLabel("예약차번호");
  	lResDate= new JLabel("예약날짜");
  	lUseBeginDate = new JLabel("렌터카사용시작일자");
  	lReturnDate = new JLabel("렌터카반납일자");
  	lResUserId = new JLabel("예약자아이디");
  	
  	
  	tfResNum = new JTextField(String.valueOf(newResNum + 1));
  	tfResNum.setEditable(false);
  	tfResCarNum = new JTextField(20);
  	tfResDate = new JTextField(nowTime);
  	tfResDate.setEditable(false);
  	tfUseBeginDate = new JTextField(20);
  	tfReturnDate = new JTextField(20);
  	tfResUserId = new JTextField(20);
  	
  	if(carNumber != null && carNumber.length() != 0 ) {  //차량 조회 화면에서 예약 시 차번호를 미리 표시합니다.
  		tfResCarNum.setText(carNumber);
  		tfResCarNum.setEditable(false);;
  	}
  	
  	
  	
  	btnResReg=new JButton("등록하기");
  	btnResReg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String resNum = tfResNum.getText().trim();
				String resCarNum = tfResCarNum.getText().trim();
				String resDate = tfResDate.getText().trim();
				String useBeginDate = tfUseBeginDate.getText().trim();
				String returnDate = tfReturnDate.getText().trim();
				String resUserId = tfResUserId.getText().trim();
				String resUse = "대기";
		
			
				String resDate1 = resDate.replace("-", "");
		        int IntResDate = Integer.parseInt(resDate1);
				
				String useBeginDate1 = useBeginDate.replace("-", "");
		        int IntuseBeginDate = Integer.parseInt(useBeginDate1);

				String returnDate1 = returnDate.replace("-", "");
		        int IntreturnDate = Integer.parseInt(returnDate1);

		        
		        if(IntResDate > IntuseBeginDate) {
		        	showMessage("렌터카 사용시작 일자가 오늘 날짜보다 작을 수 없습니다.");
		        	return;
		        } else if(IntuseBeginDate > IntreturnDate) {
		        	showMessage("렌터카 반납 일자가 사용시작 일자보다 작을 수 없습니다.");
		        	return;
		        }
		        
				System.out.println(resDate);
				
				ResVO resVO=new ResVO(resNum, resCarNum, resDate, useBeginDate,
						returnDate, resUserId, resUse);
				resController.regResInfo(resVO);
				System.out.println(resVO.getResDate());
				
				showMessage("새 예약을 등록했습니다.");
				tfResNum.setText("");
				tfResCarNum.setText("");
				tfResDate.setText("");
				tfUseBeginDate.setText("");
				tfReturnDate.setText("");
				tfResUserId.setText("");
				dispose();	
			}
      });
  	
  	
  	jPanel=new JPanel(new GridLayout(0,2));
  	jPanel.add(lResNum);
  	jPanel.add(tfResNum);
  	
  	jPanel.add(lResCarNum);
  	jPanel.add(tfResCarNum);
  	
  	jPanel.add(lResDate);
  	jPanel.add(tfResDate);
  	
  	jPanel.add(lUseBeginDate);
  	jPanel.add(tfUseBeginDate);
  	
  	jPanel.add(lReturnDate);
  	jPanel.add(tfReturnDate);
  	
  	jPanel.add(lResUserId);
  	jPanel.add(tfResUserId);
  	
  	add(jPanel,BorderLayout.NORTH);
  	add(btnResReg,BorderLayout.SOUTH);
  	
      setLocation(300, 200);
      setSize(400,400);
      setModal(true); //항상 부모창 위에 보이게 합니다.
      setVisible(true);
  }
  
  private void showMessage(String msg){
  	JOptionPane.showMessageDialog(this,
  			msg, 
  			"메지지박스",
             JOptionPane.INFORMATION_MESSAGE);
  }
  

}
