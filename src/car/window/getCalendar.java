package car.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import UI.RoundedButton; // RoundedButton import

import res.VO.ResVO;
import res.controller.ResController;

public class getCalendar extends JPanel {
    Calendar cal;
    int year, month;
    RoundedButton[] buttons;
    ResController resController;
    List<ResVO> resList;
    ScheduleCarDialog parentDialog;
    String[][] getBtnResNum;  // 배열 선언 위치 변경

    public getCalendar(ResController resController, List<ResVO> resList, ScheduleCarDialog parentDialog) {
        this.resController = resController;
        this.resList = resList;
        this.parentDialog = parentDialog;
        cal = Calendar.getInstance();

        setLayout(new GridLayout(7, 7, 5, 5));

        buttons = new RoundedButton[49];
        String[] dayNames = {"S", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            add(dayLabel);
        }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new RoundedButton(24, "", 100, 50, Color.BLACK, 200, 200, 200);
            add(buttons[i]);
            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 24));
            buttons[i].setPreferredSize(new Dimension(20, 15));
            if (i % 7 == 0) buttons[i].setForeground(Color.RED);
            if (i % 7 == 6) buttons[i].setForeground(Color.BLUE);
        }

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        getBtnResNum = new String[resList.size()][cal.getActualMaximum(Calendar.DATE) + 1]; // 초기화 위치 이동
        calSet();
    }

    public String getCalText() {
        return year + "년 " + month + "월";
    }

    public void calSet() {
        for (int i = 7; i < buttons.length; i++) {
            buttons[i].setText("");
        }

        cal.set(year, month - 1, 1);
        int firstDay = cal.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++) {
            buttons[6 + firstDay + i].setText(String.valueOf(i));
        }

        // 배열 초기화 위치 이동
        getBtnResNum = new String[resList.size()][cal.getActualMaximum(Calendar.DATE) + 1];

        int i = 0;
        for (ResVO res : resList) {
            String[] parts1 = res.getUseBeginDate().split("-");
            String[] parts2 = res.getReturnDate().split("-");
            int startYear = Integer.parseInt(parts1[0]);
            int startMonth = Integer.parseInt(parts1[1]);
            int startDay = Integer.parseInt(parts1[2]);
            int endYear = Integer.parseInt(parts2[0]);
            int endMonth = Integer.parseInt(parts2[1]);
            int endDay = Integer.parseInt(parts2[2]);

            if ((year > startYear || (year == startYear && month >= startMonth)) &&
                (year < endYear || (year == endYear && month <= endMonth))) {

                int start = (year == startYear && month == startMonth) ? startDay : 1;
                int end = (year == endYear && month == endMonth) ? endDay : cal.getActualMaximum(Calendar.DATE);

                for (int day = start; day <= end; day++) {
                    int buttonIndex = 6 + firstDay + day;
                    String buttonText = buttons[buttonIndex].getText();

                    if (!buttonText.contains("(" + res.getResNumber() + ")")) {
                        if (buttonText.contains("\n")) {
                            buttonText = buttonText + " (" + res.getResNumber();
                        } else {
                            buttonText = buttonText + "\n(" + res.getResNumber();
                        }

                        buttons[buttonIndex].setText(buttonText + ")");
                    }
                    getBtnResNum[i][day] = res.getResNumber();
                   // System.out.println(getBtnResNum[i][day]);
                }
            }
            i++;
        }
    }

    public void allInit(int gap) {
        for (int i = 7; i < buttons.length; i++) {
            buttons[i].setText("");
        }
        month += gap;
        if (month <= 0) {
            year--;
            month = 12;
        } else if (month >= 13) {
            year++;
            month = 1;
        }
        calSet();
    }

    public void setYearMonth(int year, int month) {
        this.year = year;
        this.month = month;
        calSet();
    }

    public void setButtons(RoundedButton[] buttons) {
        this.buttons = buttons;
    }

    public String[][] getBtnResNum() {
        return getBtnResNum;
    }
}
