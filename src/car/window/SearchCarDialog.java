package car.window; // 이 클래스는 car 패키지 내의 window 서브패키지에 속합니다.

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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

import car.controller.CarController;
import car.VO.CarVO;
import common.RentTableModel;
import res.window.RegResDialog;

// 차량 검색 다이얼로그 클래스
public class SearchCarDialog extends JDialog {
    JPanel panelSearch, panelBtn; // 검색 패널, 버튼 패널 객체 선언
    JLabel lCarName; // 차량 번호 라벨 객체 선언
    JTextField tf; // 차량 번호 입력 필드 객체 선언
    JButton btnSearch; // 조회하기 버튼 객체 선언
    JButton btnReg; // 렌터카 등록하기 버튼 객체 선언
    JButton btnModify; // 수정하기 버튼 객체 선언
    JButton btnDelete; // 삭제하기 버튼 객체 선언
    JButton btnResReg; // 렌터카 예약하기 버튼 객체 선언
    JButton btnFavor; //즐겨찾기 버튼 객체 선언

    JTable carTable; // 차량 테이블 객체 선언
    RentTableModel rentTableModel; // 테이블 모델 객체 선언
    String[] columnNames = { "차번호", "차이름", "차색상", "배기량", "차제조사", "즐겨찾기" }; // 테이블 컬럼 이름 배열 선언
    Object[][] carItems; // 테이블 데이터 배열 선언
    String[] LBStar;
    int rowIdx = 0, colIdx = 0; // 선택한 행과 열 인덱스 저장 변수 선언

    CarController carController; // CarController 인터페이스 객체 선언

    // 생성자
    public SearchCarDialog(CarController carController, String str) {
        this.carController = carController; // CarController 객체 초기화
        setTitle(str); // 다이얼로그 창 제목 설정
        init(); // 다이얼로그 초기화 메서드 호출
    }

    // 다이얼로그 초기화 메서드
    private void init() {
        carTable = new JTable(); // 테이블 객체 생성
        // 테이블 행 선택 리스너 등록
        ListSelectionModel rowSel = carTable.getSelectionModel();
        rowSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSel.addListSelectionListener(new ListRowSelectionHandler());

        // 테이블 열 선택 리스너 등록
        ListSelectionModel colSel = carTable.getColumnModel().getSelectionModel();
        colSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        colSel.addListSelectionListener(new ListColSelectionHandler());
        
        panelSearch = new JPanel(); // 검색 패널 객체 생성
        panelBtn = new JPanel(); // 버튼 패널 객체 생성

        lCarName = new JLabel("차번호"); // 차량 번호 라벨 생성
        tf = new JTextField(10); // 차량 번호 입력 필드 생성
        btnSearch = new JButton("조회하기"); // 조회하기 버튼 생성
        btnSearch.addActionListener(new CarBtnHandler()); // 조회하기 버튼에 액션 리스너 등록

        panelSearch.add(lCarName); // 검색 패널에 차량 번호 라벨 추가
        panelSearch.add(tf); // 검색 패널에 차량 번호 입력 필드 추가
        panelSearch.add(btnSearch); // 검색 패널에 조회하기 버튼 추가
        
        // 버튼 생성 및 액션 리스너 등록
        btnReg = new JButton("렌터카등록하기");
        btnModify = new JButton("수정하기");
        btnDelete = new JButton("삭제하기");
        btnResReg = new JButton("렌터카 예약하기");
        btnFavor = new JButton("즐겨찾기 목록");
     
        btnReg.addActionListener(new CarBtnHandler()); // 렌터카 등록 버튼에 차량 버튼 핸들러 등록
        btnModify.addActionListener(new CarBtnHandler()); // 수정 버튼에 차량 버튼 핸들러 등록
        btnDelete.addActionListener(new CarBtnHandler()); // 삭제 버튼에 차량 버튼 핸들러 등록
        btnResReg.addActionListener(new ResBtnHandler()); // 렌터카 예약 버튼에 예약 버튼 핸들러 등록
        btnFavor.addActionListener(new CarBtnHandler()); // 좋아요 버튼에 예약 버튼 핸들러 등록

        // 버튼 패널에 버튼 추가
        panelBtn.add(btnReg);
        panelBtn.add(btnModify);
        panelBtn.add(btnDelete);
        panelBtn.add(btnResReg);
        panelBtn.add(btnFavor);

        // 다이얼로그에 검색 패널, 버튼 패널, 테이블 스크롤 패널 추가
        add(panelSearch, BorderLayout.NORTH);
        add(panelBtn, BorderLayout.SOUTH);
        
		carItems = new String[0][6];
		rentTableModel = new RentTableModel(carItems, columnNames);
		carTable.setModel(rentTableModel);
        add(new JScrollPane(carTable), BorderLayout.CENTER);

        setLocation(200, 100); // 다이얼로그 위치 설정
        setSize(600, 600); // 다이얼로그 크기 설정
        setModal(true); // 다이얼로그가 모달로 표시되도록 설정 (부모 창 위에 항상 표시)
        setVisible(true); // 다이얼로그 표시
    }

    // 테이블 데이터 로드 메서드
    private void loadTableData(List<CarVO> carList) { 	
        if (carList != null && carList.size() != 0) {
            carItems = new Object[carList.size()][6]; // 데이터 배열 초기화
            LBStar = new String[carList.size()];
            // 차량 정보를 데이터 배열에 저장
            for (int i = 0; i < carList.size(); i++) {
                CarVO carVO = carList.get(i);
                
                carItems[i][0] = carVO.getCarNumber();
                carItems[i][1] = carVO.getCarName();
                carItems[i][2] = carVO.getCarColor();
                carItems[i][3] = Integer.toString(carVO.getCarSize());
                carItems[i][4] = carVO.getCarMaker();
                carItems[i][5] = carVO.getCarFavorites();
            }

            rentTableModel = new RentTableModel(carItems, columnNames); // 테이블 모델 객체 생성
            carTable.setModel(rentTableModel); // 테이블 모델 설정
        } else {
            message("조회한 정보가 없습니다."); // 메시지 표시
            carItems = new Object[10][10]; // 빈 데이터 배열 생성
            rentTableModel = new RentTableModel(carItems, columnNames); // 테이블 모델 객체 생성
            carTable.setModel(rentTableModel); // 테이블 모델 설정
        }
    }

    // 메시지를 표시하는 메서드
    private void message(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지박스", JOptionPane.INFORMATION_MESSAGE);
    }

    
    // 차량등록 버튼 핸들러 클래스
    class CarBtnHandler implements ActionListener {
        String carNumber = null, carName = null, carColor = null, carMaker = null, carFavorites = null;
        int carSize = 0; 
        List<CarVO> carList = null;

        @Override
        public void actionPerformed(ActionEvent e) {
        	if (e.getSource() == btnSearch) {
                // 조회하기 버튼이 클릭된 경우
                String carNumber = tf.getText().trim(); // 입력된 차량 번호 가져오기
                carList = new ArrayList<CarVO>();
                CarVO carVO = new CarVO(); 
                
                // 차량 검색창에 차번호를 입력한 경우와 입력하지 않은 경우를 처리하는 조건문
                if (carNumber != null && carNumber.length() != 0) {
                    carVO.setCarNumber(carNumber);
                    List<CarVO> carList = carController.listCarInfo(carVO);
                    if (carList != null && carList.size() != 0) {
                        loadTableData(carList); // 테이블에 조회된 차량 정보 표시
                    } else {
                        loadTableData(null); // 조회된 정보가 없는 경우 테이블 초기화
                    }
                } else {
                    // 모든 차량 정보 조회
                    carList = carController.listCarInfo(carVO);
                    loadTableData(carList); // 테이블에 모든 차량 정보 표시
                }
                return;

            } else if (e.getSource() == btnFavor)  {
                // 즐겨찾기 버튼이 클릭된 경우
                carList = new ArrayList<CarVO>();
                CarVO carVO = new CarVO();                             
                carList = carController.listCarInfo(carVO);
                // 즐겨찾기 차량만 필터링
                List<CarVO> favoriteCars = new ArrayList<>();
                for (CarVO car : carList) {
                    if ("★".equals(car.getCarFavorites())) {
                        favoriteCars.add(car);
                    }
                }
                loadTableData(favoriteCars); // 테이블에 모든 차량 정보 표시
                
                return;
            } else if (e.getSource() == btnDelete) {
                carNumber = (String) carItems[rowIdx][0];
                carName = (String) carItems[rowIdx][1];
                carColor = (String) carItems[rowIdx][2];
                carSize = Integer.parseInt((String) carItems[rowIdx][3]);
                carMaker = (String) carItems[rowIdx][4];
                carFavorites = (String) carItems[rowIdx][5];
                CarVO carVO = new CarVO(carNumber, carName, carColor, carSize, carMaker, carFavorites);

                carController.removeCarInfo(carVO);

            } else if (e.getSource() == btnModify) {
                carNumber = (String) carItems[rowIdx][0];
                carName = (String) carItems[rowIdx][1];
                carColor = (String) carItems[rowIdx][2];
                carSize = Integer.parseInt((String) carItems[rowIdx][3]);
                carMaker = (String) carItems[rowIdx][4];
                carFavorites = (String) carItems[rowIdx][5];
                CarVO carVO = new CarVO(carNumber, carName, carColor, carSize, carMaker, carFavorites);

                new ModifyCarDialog(carController, carVO, "차량 수정창");
            } 
            else if(e.getSource() == btnReg) {            	
                new RegCarDialog(carController, "차량 등록창");
                return;
            }

            List<CarVO> carList = new ArrayList<CarVO>();
            CarVO carVO = new CarVO();
            carList = carController.listCarInfo(carVO);
            loadTableData(carList);

        } // end actionPerformed

    }// end CarBtnHandler
    
    // 예약 버튼 핸들러 클래스
    class ResBtnHandler implements ActionListener {
        // 필드 변수 선언
        String carNumber = null, carName = null, carColor = null, carMaker = null, carFavorites = null;
        int carSize = 0;

        // actionPerformed 메서드 오버라이딩: 액션 이벤트 처리
        @Override
        public void actionPerformed(ActionEvent e) {
            // 이벤트 소스가 btnResReg인 경우에만 실행
            if(e.getSource() == btnResReg) {
                // 선택된 행의 차번호 가져오기
            	carNumber = tf.getText().trim();
                // 콘솔에 차번호 출력
                System.out.println("차번호 : " + carNumber);                
                new RegResDialog(carNumber);
            }         
        }     
    }
    // 테이블의 행 클릭 시 이벤트 처리
    class ListRowSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                rowIdx = lsm.getMinSelectionIndex();
                System.out.println(rowIdx + " 번째 행이 선택됨...");
//              System.out.println(memData[rowIdx][colIdx]);
            }
        }
    }
    // 테이블의 열 클릭 시 이벤트 처리
    class ListColSelectionHandler implements ListSelectionListener {

        @Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			colIdx = lsm.getMinSelectionIndex();
			if (!e.getValueIsAdjusting()) {
				System.out.println(rowIdx + " 번째 행, " + colIdx + "열 선택됨...");
//			System.out.println(carData[rowIdx][colIdx]);
			}
		}       
	}
}
