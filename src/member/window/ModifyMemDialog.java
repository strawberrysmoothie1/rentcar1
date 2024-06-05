package member.window;

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


import member.controller.MemberController;
import member.VO.MemberVO;

public class ModifyMemDialog  extends JDialog{
	JPanel jPanel;
	JLabel lId,lName,lPassword,lAddress,lPhoneNum;
	JTextField tfId,tfName,tfPassword,tfAddress,tfPhoneNum ;
    JButton btnMemModify;
  
    MemberController memberController;
    MemberVO memVO;
    MemberVO newMemVO;
  
  public ModifyMemDialog(MemberController memberController, MemberVO memVO, String str) {
  	this.memberController = memberController;
  	this.memVO = memVO;
  	setTitle(str);
  	init();
  }
  

private void init() {
  	lId = new JLabel("아이디");
  	lPassword = new JLabel("비밀번호");
  	lName= new JLabel("이름");
  	lAddress = new JLabel("주소");
  	lPhoneNum = new JLabel("전화번호");
  	
  	
  	tfId=new JTextField(20);
  	tfPassword=new JTextField(20);
  	tfName=new JTextField(20);
  	tfAddress=new JTextField(20);
  	tfPhoneNum=new JTextField(20);
  	
  	tfId.setText(memVO.getMemId());
  	tfId.setEditable(false);
	tfPassword.setText(memVO.getMemPassword());
	tfName.setText(memVO.getMemName());
	tfAddress.setText(memVO.getMemAddress());
	tfPhoneNum.setText(memVO.getMemPhoneNum());
  	
  	btnMemModify=new JButton("회원정보 수정하기");
  	
 	 	btnMemModify.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String id=tfId.getText().trim();
				String password=tfPassword.getText().trim();
				String  name=tfName.getText().trim();
				String address=tfAddress.getText().trim();
				String phoneNum=tfPhoneNum.getText().trim();
				newMemVO=new MemberVO(id, password, name, address, phoneNum);
				
				memberController.modMember(newMemVO);
				
				showMessage("회원정보를 수정했습니다.");				
				dispose();
				
			}
      });
  	
  	
  	jPanel=new JPanel(new GridLayout(0,2));
  	jPanel.add(lId);
  	jPanel.add(tfId);
  	
  	jPanel.add(lPassword);
  	jPanel.add(tfPassword);
  	
  	jPanel.add(lName);
  	jPanel.add(tfName);
  	
  	jPanel.add(lAddress);
  	jPanel.add(tfAddress);
  	
  	jPanel.add(lPhoneNum);
  	jPanel.add(tfPhoneNum);
  	
  	add(jPanel,BorderLayout.NORTH);
  	add(btnMemModify,BorderLayout.SOUTH);
  	
      setLocation(300, 300);
      setSize(400,200);
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
