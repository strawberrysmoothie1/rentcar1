package car.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import car.controller.CarController;
import car.controller.CarControllerImpl;
import res.controller.ResController;
import car.VO.CarVO;

public class FavorCarDialog extends JDialog {

    JPanel jPanel; // 패널 객체 선언
    JLabel lCarNum, lCarFavor; // 라벨 객체 선언
    JTextField tfCarNum, tfCarFavor; // 텍스트 필드 객체 선언
    JButton btnCarFavor; // 버튼 객체 선언
    String carNumber, btnName, FavorLBName, carFavorites;

    CarController carController; // CarController 인터페이스를 구현한 객체 선언

    // 생성자
	
    public FavorCarDialog(CarController carController, String carNumber, String carFavorites, String str) {
        this.carController =carController;
    	this.carNumber = carNumber;
        this.carFavorites = carFavorites;
        setTitle(str); // 다이얼로그 창 제목 설정
        init(); // 다이얼로그 초기화 메서드 호출
    }


	// 다이얼로그 초기화 메서드
    private void init() {
        // 각 라벨과 텍스트 필드 객체 생성
    	System.out.println("차번호: "+carNumber+"즐겨찾기: "+carFavorites);
    	
        lCarNum = new JLabel("차량번호");
        lCarFavor = new JLabel("즐겨찾기 여부");

        tfCarNum = new JTextField(20);
        tfCarFavor = new JTextField(20);
        btnName = "즐겨찾기 업데이트";
        FavorLBName = "";     
        tfCarFavor.setText(carNumber);
        tfCarFavor.setEditable(false);
        if (carNumber != null && carNumber.length() != 0) {
        	tfCarNum.setText(carNumber);
        	tfCarNum.setEditable(false);
        	FavorLBName = "즐겨찾기 미등록 중";
        	tfCarFavor.setText(FavorLBName);
            tfCarFavor.setEditable(false);
            btnName = "즐겨찾기 등록";
        	if (carFavorites.equals("★")) {
        	btnName="즐겨찾기 해제";
        	FavorLBName = "즐겨찾기 등록 중";
        	tfCarFavor.setText(FavorLBName);
            tfCarFavor.setEditable(false);
        	}
        }


        btnCarFavor = new JButton(btnName); // 등록 버튼 생성

        // 등록 버튼에 ActionListener 추가
        btnCarFavor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 텍스트 필드로부터 입력된 값들을 가져와서 변수에 저장
            	carNumber = tfCarNum.getText().trim();
                String newFavor = "☆"; // 새로운 즐겨찾기 값 설정
                
                if (carFavorites.equals("☆")) {
                	newFavor = "★";
                }
                	
                // 즐겨찾기 값 변경을 위한 작업 수행
                carController.updateFavorite(carNumber, newFavor);
                              
                // 사용자에게 메시지 표시
                showMessage("즐겨찾기가 업데이트 됐습니다.");
                
             // 다이얼로그를 닫음
                dispose();
            }
        });               

        jPanel = new JPanel(new GridLayout(0, 2)); // 패널 객체 생성 및 레이아웃 설정
        jPanel.add(lCarNum); // 패널에 차량번호 라벨 추가
        jPanel.add(tfCarNum); // 패널에 차량번호 텍스트 필드 추가
        
        jPanel.add(lCarFavor); // 패널에 차량번호 라벨 추가
        jPanel.add(tfCarFavor); // 패널에 차량번호 텍스트 필드 추가        
        // 패널과 버튼을 다이얼로그에 추가
        add(jPanel, BorderLayout.NORTH);
        add(btnCarFavor, BorderLayout.SOUTH);

        setLocation(400, 300); // 다이얼로그 위치 설정
        setSize(400, 200); // 다이얼로그 크기 설정
        setModal(true); // 다이얼로그가 모달로 표시되도록 설정 (부모 창 위에 항상 표시)
        setVisible(true); // 다이얼로그 표시
    }

    // 메시지를 표시하는 메서드
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
