package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import UI.GetImage;
import UI.RoundedButton;
import car.controller.CarController;
import car.controller.CarControllerImpl;
import car.window.ScheduleCarDialog;
import car.window.SearchCarDialog;
import member.controller.MemberController;
import member.controller.MemberControllerImpl;
import member.exception.MemberException;
import member.window.SearchMemDialog;
import res.controller.ResController;
import res.controller.ResControllerImpl;
import res.window.SearchResDialog;
import res.window.UseCarDialog;

public class RentMainWindow extends JFrame {
    JFrame frame; // 프레임

    RoundedButton carBtn, memberBtn, resBtn, helpBtn, scheduleBtn, resUseBtn; // 메인 메뉴 항목

    JPanel jPanel;

    // 컨트롤러 객체들
    MemberController memberController;
    CarController carController;
    ResController resController;

    // 생성자
    public RentMainWindow() {
        frame = new JFrame("렌트카 조회/예약 시스템");

        // 메인 메뉴 항목 객체 생성
        carBtn = new RoundedButton(18, "차량 관리", 250, 40, Color.BLACK, 200,200,200);
        memberBtn = new RoundedButton(18, "회원 관리", 250, 40, Color.BLACK, 200,200,200);
        resBtn = new RoundedButton(18, "예약 관리", 250, 40, Color.BLACK, 200,200,200);
        helpBtn = new RoundedButton(18, "도움말", 250, 40, Color.BLACK, 200,200,200);
        scheduleBtn = new RoundedButton(18, "예약 스케줄", 250, 40, Color.BLACK, 200,200,200);
        resUseBtn = new RoundedButton(18, "차량 사용", 250, 40, Color.WHITE, 255,30, 30);
    }

    // 윈도우 시작 메소드
    public void startFrame() throws MemberException, IOException {
        jPanel = new JPanel(); // 패널 생성

        jPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 그리드 레이아웃 설정

        // 패널에 버튼 추가
        jPanel.add(carBtn);
        jPanel.add(memberBtn);
        jPanel.add(resBtn);
        jPanel.add(scheduleBtn);
        jPanel.add(resUseBtn);
        jPanel.add(helpBtn);

        carBtn.addActionListener(new CarHandler());
        memberBtn.addActionListener(new MemberHandler());
        resBtn.addActionListener(new ResHandler());
        scheduleBtn.addActionListener(new CarHandler());
        resUseBtn.addActionListener(new ResHandler());
        helpBtn.addActionListener(new HelpHandler());
        
        GetImage getImage = new GetImage("CarIMG.png", 600, 200);

        // GetImage 객체를 사용하여 이미지 아이콘 가져오기
        ImageIcon imageIcon = getImage.getImage();

     // 패널을 생성하여 버튼과 이미지를 같은 패널에 추가
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 이미지를 표시할 레이블 생성
        JLabel imageLabel = new JLabel(imageIcon);

        // 이미지를 표시할 레이블을 패널의 상단에 추가
        mainPanel.add(imageLabel, BorderLayout.NORTH);

        // 버튼 패널을 생성하여 버튼들을 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(jPanel);

        // 버튼 패널을 패널의 중앙에 추가
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Frame에 패널 추가
        Container con = frame.getContentPane();
        con.add(mainPanel);

        frame.setLocation(200, 100);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 컨트롤러 생성
        memberController = new MemberControllerImpl();
        carController = new CarControllerImpl();
        resController = new ResControllerImpl();
    }

    // 회원 메뉴 이벤트 핸들러
    class MemberHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if (e.getSource() == memberBtn) {
                new SearchMemDialog(memberController, "회원 등록창"); // 회원 등록 다이얼로그 생성
            }
        }
    }

    // 차량 메뉴 이벤트 핸들러
    class CarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if (e.getSource() == carBtn) {
                new SearchCarDialog(carController, "차량 등록창"); // 차량 등록 다이얼로그 생성
            } else if (e.getSource() == scheduleBtn) {
                new ScheduleCarDialog(carController, resController, "차량 시간표"); // 차량 등록 다이얼로그 생성
            }
        }
    }

    // 예약 메뉴 이벤트 핸들러
    class ResHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 예약 기능 구현 실습
            if (e.getSource() == resBtn) {
                new SearchResDialog(resController, "예약 등록창"); // 예약 등록 다이얼로그 생성
            } else if (e.getSource() == resUseBtn) {
                new UseCarDialog(resController, memberController, "사용 관리"); // 차량 등록 다이얼로그 생성
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
        JLabel jbver = new JLabel(" 버전 1.0");
        JLabel jbdate = new JLabel("       2023.03.11");
        JLabel jbauthor = new JLabel(" 제작 : 웹 자바");

        d.setLayout(new FlowLayout());
        d.add(jbver);
        d.add(jbdate);
        d.add(jbauthor);

        d.setLocation(400, 230);
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
