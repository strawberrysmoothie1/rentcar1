package res.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import res.VO.ResVO;

public class ModifyRegDialog  extends JDialog{
	JPanel jPanel;
	JLabel lResNum, lResCarNum, lResDate,lUseBeginDate,
	lReturnDate, lResUserId, lResUse;
	JTextField tfResNum,tfResCarNum, tfResDate, tfUseBeginDate,
	tfReturnDate, tfResUserId, tfResUse;
  JButton btnResReg;
  
  ResController resController;
  ResVO resVO;
  ResVO newresVO;
  
  public ModifyRegDialog(ResController resController, ResVO resVO, String str) {
  	this.resController = resController;
  	this.resVO = resVO;
  	setTitle(str);
  	init();
  }

  
  private void init() {
  	lResNum = new JLabel("예약번호");
  	lResCarNum = new JLabel("예약차번호");
  	lResDate= new JLabel("예약날짜");
  	lUseBeginDate = new JLabel("렌터카사용시작일자");
  	lReturnDate = new JLabel("렌터카반납일자");
  	lResUserId = new JLabel("예약자아이디");
  	lResUse = new JLabel("사용여부");
  	 	
  	tfResNum = new JTextField(20);
  	tfResCarNum = new JTextField(20);
  	tfResDate = new JTextField(20);
  	tfUseBeginDate = new JTextField(20);
  	tfReturnDate = new JTextField(20);
  	tfResUserId = new JTextField(20);
  	tfResUse = new JTextField(20);

  	
  	tfResNum.setText(resVO.getResNumber());
  	tfResNum.setEditable(false);
  	tfResCarNum.setText(resVO.getResCarNumber());
  	tfResDate.setText(resVO.getResDate());
  	tfUseBeginDate.setText(resVO.getUseBeginDate());
  	tfReturnDate.setText(resVO.getReturnDate());
  	tfResUserId.setText(resVO.getResUserId());
  	tfResUse.setText(resVO.getResUse());


  	btnResReg=new JButton("예약정보 수정하기");
  	btnResReg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {			
				String resNum = tfResNum.getText().trim();
				String resCarNum = tfResCarNum.getText().trim();
				String resDate = tfResDate.getText().trim();
				String useBeginDate = tfUseBeginDate.getText().trim();
				String returnDate = tfReturnDate.getText().trim();
				String resUserId = tfResUserId.getText().trim();
				String resUse = tfResUse.getText().trim();

							
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
		        
				System.out.println(resNum + ", " + resCarNum + ", " + resDate + ", " +
				useBeginDate + ", " + returnDate + ", " +resUserId + ", "+ resUse);
				
				newresVO = new ResVO(resNum, resCarNum, resDate,
						useBeginDate, returnDate, resUserId, resUse);
				resController.modResInfo(newresVO);
				
				showMessage("예약정보를 수정했습니다.");
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
