package car.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import UI.RoundedButton;
import car.controller.CarController;
import res.VO.ResVO;
import res.controller.ResController;
import res.window.SearchResDialog;

// 차량 예약 일정 다이얼로그 클래스 정의
public class ScheduleCarDialog extends JDialog implements ActionListener {

    CarController carController;
    ResController resController;
    Calendar cal = Calendar.getInstance();
    int year, month;

    JPanel jPanel; // 상단 패널
    JPanel cPanel; // 하단 패널

    JTextField tfResNum; // 텍스트 필드 객체 선언

    JMenu ScheduleMenu;
    JMenuItem menuCarNum, menuResNum, menuID;
    List<ResVO> resList = null;
    ResVO resVO = new ResVO(); // 변경된 부분: 객체 초기화 추가
    JLabel LBsearch;

    RoundedButton buttonBefore = new RoundedButton(14, "Before", 75, 30, Color.BLACK, 170, 170, 170);
    RoundedButton buttonAfter = new RoundedButton(14, "After", 60, 30, Color.BLACK, 170, 170, 170);
    RoundedButton btnSearch;
    JLabel label = new JLabel("00년 0월");

    RoundedButton[] buttons = new RoundedButton[49];
    String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    String searchNum;
    String[] resInfo;

    getCalendar cF; // 클래스 변수로 이동

    // 생성자
    public ScheduleCarDialog(CarController carController, ResController resController, String str) {
        this.resController = resController;
        this.carController = carController;
        setTitle(str); // 다이얼로그 창 제목 설정
        init(); // 다이얼로그 초기화 메서드 호출
    }

    // 다이얼로그 초기화 메서드
    private void init() {
        resList = resController.listResInfo(new ResVO()); // resList 초기화
        cF = new getCalendar(resController, resList, this); // resList 전달하여 초기화

        // 상단 패널의 위치와 크기 설정
        jPanel = new JPanel(null);
        jPanel.setBackground(Color.WHITE); // 배경색 설정
        jPanel.setPreferredSize(new Dimension(550, 30)); // 패널 크기 설정

        // 하단 패널의 위치와 크기 설정
        cPanel = new JPanel(new BorderLayout()); // BorderLayout 사용
        cPanel.setBackground(Color.WHITE); // 배경색 설정
        cPanel.setPreferredSize(new Dimension(550, 500)); // 패널 크기 설정

        LBsearch = new JLabel("예약 번호"); // 콤보 박스 생성
        LBsearch.setBounds(112, 2, 80, 25); // 콤보 박스 위치와 크기 설정가
        jPanel.add(LBsearch); // 패널에 콤보 박스 추가
        btnSearch = new RoundedButton(13, "조회하기", 100, 20, Color.WHITE, 255, 50, 50);
        btnSearch.setBounds(380, 2, 80, 25);
        jPanel.add(btnSearch);

        tfResNum = new JTextField(10);
        tfResNum.setBounds(170, 2, 200, 25); // 텍스트 필드 위치와 크기 설정
        jPanel.add(tfResNum); // 패널에 차량번호 텍스트 필드 추가

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(buttonBefore);
        panel1.add(label);
        panel1.add(buttonAfter);

        Font font = new Font("SansSerif", Font.BOLD, 20);
        buttonAfter.setFont(font);
        buttonBefore.setFont(font);
        label.setFont(font);

        label.setText(cF.getCalText());

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(7, 7, 5, 5));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new RoundedButton(13, "", 55, 40, Color.BLACK, 200, 200, 200);
            panel2.add(buttons[i]);

            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 24));

            if (i < 7) buttons[i].setText(dayNames[i]);

            if (i % 7 == 0) buttons[i].setForeground(Color.RED);
            if (i % 7 == 6) buttons[i].setForeground(Color.BLUE);

            buttons[i].addActionListener(this); // 버튼에 액션 리스너 추가
        }

        cF.setButtons(buttons);
        cF.calSet();

        cPanel.add(panel1, BorderLayout.NORTH);
        cPanel.add(panel2, BorderLayout.CENTER);

        add(jPanel, BorderLayout.NORTH);
        add(cPanel);

        buttonAfter.addActionListener(this);
        buttonBefore.addActionListener(this);
        btnSearch.addActionListener(this);

        setLocation(225, 150); // 다이얼로그 위치 설정
        setSize(550, 500); // 다이얼로그 크기 설정
        setModal(true); // 다이얼로그가 모달로 표시되도록 설정 (부모 창 위에 항상 표시)
        setVisible(true); // 다이얼로그 표시
    }

    // 메시지를 표시하는 메서드
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지박스", JOptionPane.INFORMATION_MESSAGE);
    }

    // 특정 날짜에 대한 예약 번호를 표시하는 메서드 추가
    public void showReservationsForDate(String date, String[][] getBtnResNum, int day) {
        // JDialog 생성
        JDialog dialog = new JDialog(this, date + "일 예약 번호", true);
        dialog.setLayout(null);  // 레이아웃을 null로 설정하여 절대 위치 사용 가능

        int resNumArraySize = getBtnResNum.length;
        int legnth = 1;
        int yOffset = 20;  // 초기 y 좌표 오프셋
        resInfo = new String[7];
        for (int j = 0; j < resNumArraySize; j++) {
            if (getBtnResNum[j][day] != null) {
                String resNum = getBtnResNum[j][day];
                RoundedButton resButton = new RoundedButton(12, "예약 번호: "+resNum, 100, 20, Color.BLACK, 200, 200, 200);
                resButton.setBounds(50, yOffset, 200, 30);  // 버튼 위치와 크기 설정
                legnth ++;
                resButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	String STresNum = String.valueOf(resNum);
                    	resVO.setResNumber(STresNum);
    					List<ResVO> resList = resController.listResInfo(resVO);
    						ResVO resVO = resList.get(resList.size()-1);
    					resInfo[0] = resVO.getResNumber();
    					resInfo[1] = resVO.getResCarNumber();
    					resInfo[2] = resVO.getResDate();
    					resInfo[3] = resVO.getUseBeginDate();
    					resInfo[4] = resVO.getReturnDate();
    					resInfo[5] = resVO.getResUserId();
    					resInfo[6] = resVO.getResUse();
					
    					showMessage("예약 번호: " + resInfo[0]+
    							"\n사용자     : " + resInfo[5] +
    							"\n차량 번호: " + resInfo[1] +
    							"\n등록 날짜: " + resInfo[2] + 
    							"\n\n시작 날짜: " + resInfo[3] +
    							"\n반납 날짜: " + resInfo[4]);

                    }
                });
                dialog.add(resButton);
                yOffset += 40;  // 다음 버튼의 y 좌표 오프셋을 증가시켜 버튼들이 아래로 쌓이게 함
            }
        }
        
        int not =30+(legnth*40);        
        if(not == 70) {
        	 dialog.setSize(300, 100);
         	JLabel noReservationLabel = new JLabel("예약이 없습니다.");
            noReservationLabel.setBounds(98, 16, 150, 30);  // 라벨 위치와 크기 설정
            dialog.add(noReservationLabel);
        }else {
        	 dialog.setSize(300, 30+(legnth*40));
        }
        
        dialog.setLocationRelativeTo(this);  // 다이얼로그를 부모 컴포넌트의 중앙에 위치시킴
        dialog.setVisible(true);  // 다이얼로그 표시
    }
    // 액션 이벤트 처리 메서드
    @Override
    public void actionPerformed(ActionEvent e) {
        int gap = 0;
        if (e.getSource() == buttonAfter) { // 1달 후
            gap = 1;
            cF.allInit(gap);
        } else if (e.getSource() == buttonBefore) { // 1달 전
            gap = -1;
            cF.allInit(gap);
        } else if (e.getSource() == btnSearch) {
            String STresNum = tfResNum.getText().trim();
            ResVO foundRes = null;

            for (ResVO res : resList) {
                if (res.getResNumber().equals(STresNum)) {
                    foundRes = res;
                    break;
                }
            }
            if (foundRes != null) {
                int startYear = Integer.parseInt(foundRes.getUseBeginDate().split("-")[0]);
                int startMonth = Integer.parseInt(foundRes.getUseBeginDate().split("-")[1]);
                cF.setYearMonth(startYear, startMonth);
                showMessage(
                        "예약자   : " + foundRes.getResUserId() +
                                "\n렌트 차량: " + foundRes.getResCarNumber() +
                                "\n렌트 기간: " + foundRes.getUseBeginDate() + " ~ " + foundRes.getReturnDate()
                );
            } else {
                showMessage("예약 번호를 찾을 수 없습니다.");
            }
        } else {
            // 날짜 버튼 클릭 처리
            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource() == buttons[i]) {
                    String buttonText = buttons[i].getText();
                    if (!buttonText.isEmpty() && !Arrays.asList(dayNames).contains(buttonText)) {
                        // 버튼 텍스트에서 예약번호 추출
                        String[] buttonTextLines = buttonText.split("\n");
                        String dayText = buttonTextLines[0];

                        try {
                            int day = Integer.parseInt(dayText);

                            // 예약번호를 저장할 리스트 생성
                            List<String> reservationList = new ArrayList<>();

                            // getBtnResNum의 크기 (i 값의 최대값)
                            String[][] getBtnResNum = (String[][]) cF.getBtnResNum();
                            int resNumArraySize = getBtnResNum.length;
                            System.out.println(day + "일");
                            
                            // 생성된 배열을 이용하여 다이얼로그 표시
                           showReservationsForDate(dayText, getBtnResNum, day);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        label.setText(cF.getCalText()); // 년월 글자 갱신
    }

}
