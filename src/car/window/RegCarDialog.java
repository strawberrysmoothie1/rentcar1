package car.window; // 이 클래스는 car 패키지 내의 window 서브패키지에 속합니다.

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
import car.VO.CarVO;

// 차량 등록 다이얼로그 클래스
public class RegCarDialog extends JDialog {
    JPanel jPanel; // 패널 객체 선언
    JLabel lCarNum, lCarName, lSize, lColor, lMaker; // 라벨 객체 선언
    JTextField tfCarNum, tfCarName, tfSize, tfColor, tfMaker; // 텍스트 필드 객체 선언
    JButton btnCarReg; // 버튼 객체 선언

    CarController carController; // CarController 인터페이스를 구현한 객체 선언

    // 생성자
    public RegCarDialog(CarController carController, String str) {
        this.carController = carController; // CarController 객체 초기화
        setTitle(str); // 다이얼로그 창 제목 설정
        init(); // 다이얼로그 초기화 메서드 호출
    }

    // 다이얼로그 초기화 메서드
    private void init() {
        // 각 라벨과 텍스트 필드 객체 생성
        lCarNum = new JLabel("차량번호");
        lCarName = new JLabel("차량명");
        lSize = new JLabel("배기량");
        lColor = new JLabel("차색상");
        lMaker = new JLabel("차제조사");

        tfCarNum = new JTextField(20);
        tfCarName = new JTextField(20);
        tfSize = new JTextField(20);
        tfColor = new JTextField(20);
        tfMaker = new JTextField(20);

        btnCarReg = new JButton("렌터카등록하기"); // 등록 버튼 생성

        // 등록 버튼에 ActionListener 추가
        btnCarReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 텍스트 필드로부터 입력된 값들을 가져와서 변수에 저장
                String carNum = tfCarNum.getText().trim();
                String carName = tfCarName.getText().trim();
                int carSize = Integer.parseInt(tfSize.getText().trim());
                String carColor = tfColor.getText().trim();
                String carMaker = tfMaker.getText().trim();
                String carFavorites = "☆"; //0=x,  1 = 즐겨찾기

                // CarVO 객체 생성하여 입력된 정보 저장
                CarVO carVO = new CarVO(carNum, carName, carColor, carSize, carMaker, carFavorites);
                
                // CarController를 사용하여 차량 정보 등록
                carController.regCarInfo(carVO);

                // 사용자에게 메시지 표시
                showMessage("차량을 등록했습니다.");

                // 입력 필드 초기화
                tfCarNum.setText("");
                tfCarName.setText("");
                tfSize.setText("");
                tfColor.setText("");
                tfMaker.setText("");
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2)); // 패널 객체 생성 및 레이아웃 설정
        jPanel.add(lCarNum); // 패널에 차량번호 라벨 추가
        jPanel.add(tfCarNum); // 패널에 차량번호 텍스트 필드 추가
        
        jPanel.add(lCarName); // 패널에 차량명 라벨 추가
        jPanel.add(tfCarName); // 패널에 차량명 텍스트 필드 추가
        
        jPanel.add(lSize); // 패널에 배기량 라벨 추가
        jPanel.add(tfSize); // 패널에 배기량 텍스트 필드 추가
        
        jPanel.add(lColor); // 패널에 차색상 라벨 추가
        jPanel.add(tfColor); // 패널에 차색상 텍스트 필드 추가
        
        jPanel.add(lMaker); // 패널에 차제조사 라벨 추가
        jPanel.add(tfMaker); // 패널에 차제조사 텍스트 필드 추가

        // 패널과 버튼을 다이얼로그에 추가
        add(jPanel, BorderLayout.NORTH);
        add(btnCarReg, BorderLayout.SOUTH);

        setLocation(400, 200); // 다이얼로그 위치 설정
        setSize(400, 400); // 다이얼로그 크기 설정
        setModal(true); // 다이얼로그가 모달로 표시되도록 설정 (부모 창 위에 항상 표시)
        setVisible(true); // 다이얼로그 표시
    }

    // 메시지를 표시하는 메서드
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
