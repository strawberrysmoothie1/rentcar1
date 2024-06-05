package member.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.base.AbstractBaseController;
import member.dao.MemberDAO;
import member.dao.MemberDAOImpl;
import member.VO.MemberVO;

public class MemberControllerImpl extends AbstractBaseController implements MemberController {
	private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 0417;
    
//	public MemberDAO memberDAO;
//	public MemberControllerImpl() {
//		memberDAO = new MemberDAOImpl();
//	}

	@Override
	public List<MemberVO> listMember(MemberVO memVO) {
//		List<MemberVO> memList = new ArrayList<MemberVO>();
//		try {
//			memList = memberDAO.selectMember(memVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		return memList;
		return sendRequest("listMember", memVO);
	}

	@Override
	public void regMember(MemberVO memVO) {
//		try {
//			memberDAO.insertMember(memVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
		sendRequest("regMember", memVO);
	}

	@Override
	public void modMember(MemberVO memVO) {
//		try {
//			memberDAO.updateMember(memVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
        sendRequest("modMember", memVO);
	}

	@Override
	public void removeMember(MemberVO memVO) {
//		try {
//			memberDAO.deleteMember(memVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
		sendRequest("removeMember", memVO);
	}

    private List<MemberVO> sendRequest(String command, MemberVO memVO) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(command);
            out.writeObject(memVO);

            if (command.equals("listMember")) {
                return (List<MemberVO>) in.readObject();
            }  
            else if (command.equals("regMember")) {
            	
            }
            else if (command.equals("modMember")) {
            	
            }
            else if (command.equals("removeMember")) {
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
