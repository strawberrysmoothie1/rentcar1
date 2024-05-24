package main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import car.controller.CarController;
import car.controller.CarControllerImpl;
import car.window.FavorCarDialog;
import car.window.RegCarDialog;
import car.window.SearchCarDialog;
import common.base.AbstractBaseWindow;
import member.controller.MemberController;
import member.controller.MemberControllerImpl;
import member.exception.MemberException;
import member.window.RegMemDialog;
import member.window.SearchMemDialog;
import res.controller.ResController;
import res.controller.ResControllerImpl;
import res.window.RegResDialog;
import res.window.SearchResDialog;

// 메인 윈도우 클래스
public class RentMainWindow extends AbstractBaseWindow {
    JFrame frame; // 프레임
    JMenuBar menuBar; // 메뉴 바
    JMenu carMenu, memberMenu, resMenu, helpMenu; // 메인 메뉴 항목
    // 서브 메뉴 항목
    JMenuItem carMenu11, carMenu12, carMenu13, carMenu14;
    JMenuItem memMenu21, memMenu22, memMenu23, memMenu24;
    JMenuItem resMenu31, resMenu32, resMenu33, resMenu34;
    JMenuItem helpMenu41;
    JPanel jPanel;
    JLabel lCarName;
    JTextField tf;
    JButton btnSearch;
    JComboBox comboBox; // 검색 조건 설정 콤보 박스

    // 컨트롤러 객체들
    MemberController memberController;
    CarController carController;
    ResController resController;

    // 생성자
    public RentMainWindow() {
        frame = new JFrame("렌트카 조회/예약 시스템");
        menuBar = new JMenuBar();
        // 메인 메뉴 항목 객체 생성
        carMenu = new JMenu("차량 관리");
        memberMenu = new JMenu("회원 관리");
        resMenu = new JMenu("예약 관리");
        helpMenu = new JMenu("도움말");
    }

    // 윈도우 시작 메소드
    protected void startFrame() throws MemberException, IOException {
        frame.setJMenuBar(menuBar); // Frame에 메뉴바를 설정
        menuBar.add(memberMenu); // 메뉴바에 "회원 관리" 항목 추가
        // 회원 관리 메뉴 관련 서브 메뉴 항목 추가
        memberMenu.add(memMenu21 = new JMenuItem("회원 등록"));
        memberMenu.add(memMenu22 = new JMenuItem("회원 조회"));
        memberMenu.addSeparator(); // 분리선 추가
        memberMenu.add(memMenu23 = new JMenuItem("회원 수정"));
        memberMenu.add(memMenu24 = new JMenuItem("회원 삭제"));

        menuBar.add(carMenu); // 메뉴바에 "차량 관리" 항목 추가
        // 차량 관리 메뉴 관련 서브 메뉴 항목 추가
        carMenu.add(carMenu11 = new JMenuItem("차량 등록"));
        carMenu.add(carMenu12 = new JMenuItem("차량 조회"));
        carMenu.addSeparator(); // 분리선 추가
        carMenu.add(carMenu13 = new JMenuItem("차량 수정"));
        carMenu.add(carMenu14 = new JMenuItem("차량 삭제"));

        menuBar.add(resMenu); // 메뉴바에 "예약 관리" 항목 추가
        // 예약 관리 메뉴 관련 서브 메뉴 항목 추가
        resMenu.add(resMenu31 = new JMenuItem("예약 등록"));
        resMenu.add(resMenu32 = new JMenuItem("예약 조회"));
        resMenu.addSeparator(); // 분리선 추가
        resMenu.add(resMenu33 = new JMenuItem("예약 수정"));
        resMenu.add(resMenu34 = new JMenuItem("예약 취소"));

        menuBar.add(helpMenu); // 메뉴바에 "도움말" 항목 추가
        helpMenu.add(helpMenu41 = new JMenuItem("버전"));

        jPanel = new JPanel(); // 패널 생성
        lCarName = new JLabel("차번호"); // 레이블 생성
        tf = new JTextField(10); // 텍스트 필드 생성
        comboBox = new JComboBox(); // 콤보 박스 생성
        comboBox.addItem("차번호");
        comboBox.addItem("차이름");
        comboBox.addItem("차색상");
        comboBox.addItem("배기량");
        comboBox.addItem("제조사");

        btnSearch = new JButton("차량 조회하기"); // 버튼 생성
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchCarDialog(carController, "차 정보 조회"); // 차량 정보 조회 다이얼로그 생성
            }
        });

         
        jPanel.add(lCarName); // 패널에 레이블 추가
        jPanel.add(tf); // 패널에 텍스트 필드 추가
        jPanel.add(comboBox); // 패널에 콤보 박스 추가
        jPanel.add(btnSearch); // 패널에 버튼 추가

        Container con = frame.getContentPane(); // contentPane을 가져옴
        con.add(jPanel, "North"); // 패널을 Frame의 상단에 추가

        frame.setLocation(200, 100); // 위치 설정
        frame.setSize(800, 600); // 크기 설정
        frame.setVisible(true); // 보이기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 설정

        // 메뉴 항목 선택 이벤트와 이벤트 리스너를 연결
        carMenu11.addActionListener(new CarHandler());
        carMenu12.addActionListener(new CarHandler());
        carMenu13.addActionListener(new CarHandler());
        carMenu14.addActionListener(new CarHandler());

        memMenu21.addActionListener(new MemberHandler());
        memMenu22.addActionListener(new MemberHandler());
        memMenu23.addActionListener(new MemberHandler());
        memMenu24.addActionListener(new MemberHandler());

        resMenu31.addActionListener(new ResHandler());
        resMenu32.addActionListener(new ResHandler());
        resMenu33.addActionListener(new ResHandler());
        resMenu34.addActionListener(new ResHandler());

        helpMenu41.addActionListener(new HelpHandler());

        // 컨트롤러 생성
        memberController = new MemberControllerImpl();
        carController = new CarControllerImpl();
        resController = new ResControllerImpl();
    }

    // 회원 메뉴 이벤트 핸들러
    class MemberHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println(e.getActionCommand());
                if (e.getSource() == memMenu21) {
                    new RegMemDialog(memberController, "회원 등록창"); // 회원 등록 다이얼로그 생성
                } else if (e.getSource() == memMenu22) {
                    new SearchMemDialog(memberController, "회원 조회창"); // 회원 조회 다이얼로그 생성
                } else if (e.getSource() == memMenu23) {
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 차량 메뉴 이벤트 핸들러
    class CarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if (e.getSource() == carMenu11) {
                new RegCarDialog(carController, "차량 등록창"); // 차량 등록 다이얼로그 생성
            } else if (e.getSource() == carMenu12) {
                new SearchCarDialog(carController, "차량 조회창"); // 차량 조회 다이얼로그 생성
            } else if (e.getSource() == carMenu13) {
               
            }
        }
    }

    // 예약 메뉴 이벤트 핸들러
    class ResHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 예약 기능 구현 실습
            if (e.getSource() == resMenu31) {
                new RegResDialog(resController, "예약 등록창"); // 예약 등록 다이얼로그 생성
            } else if (e.getSource() == resMenu32) {
                new SearchResDialog(resController, "예약 조회창"); // 예약 조회 다이얼로그 생성
            } else if (e.getSource() == carMenu13) {
            }
        }
    }

    // 도움말 메뉴 이벤트 핸들러
    class HelpHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            version(); // 버전 메소드 호출
        }
    }

    // 버전 메소드
    public void version() {
        // 버전 관리 다이얼로그 생성
        final JDialog d = new JDialog(this, "버전 관리");
        JLabel jbver = new JLabel("       버전 1.0");
        JLabel jbdate = new JLabel("       2023.03.11");
        JLabel jbauthor = new JLabel("       제작 : 웹 자바");

        d.setLayout(new FlowLayout());
        d.add(jbver);
        d.add(jbdate);
        d.add(jbauthor);

        d.setLocation(250, 230);
        d.setSize(200, 100);
        d.setVisible(true);

        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose(); // 다이얼로그 종료
                d.setVisible(false); // 다이얼로그 안 보이게 설정
            }
        });
    }

    // 메인 메소드
    public static void main(String[] args) {
        RentMainWindow test = new RentMainWindow();
        try {
            test.startFrame();
        } catch (MemberException | IOException e) {
            e.printStackTrace();
        }
    }
}
