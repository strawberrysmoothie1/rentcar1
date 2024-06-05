package res.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import car.VO.CarVO;
import common.base.AbstractBaseController;
import res.dao.ResDAO;
import res.dao.ResDAOImpl;
import res.VO.ResVO;

public class ResControllerImpl extends AbstractBaseController implements ResController{
//	private ResDAO resDAO;
	
	 private static final String SERVER_IP = "localhost";
	   private static final int SERVER_PORT = 0417;
	
	@Override
	public List<ResVO> listResInfo(ResVO resVO) {
		 return sendRequest("listResInfo", resVO);
	}

	@Override
	public void regResInfo(ResVO resVO)  {
		sendRequest("regResInfo", resVO);
	}

	@Override
	public void modResInfo(ResVO resVO)  {
		sendRequest("modResInfo", resVO);		
	}

	@Override
	public void cancelResInfo(ResVO resVO)  {
		sendRequest("cancelResInfo", resVO);

	}
	
	 private List<ResVO> sendRequest(String command, ResVO resVO) {
	        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
	             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
	             
	            out.writeObject(command);
	            out.writeObject(resVO);

	            if (command.equals("listResInfo")) {
	                return (List<ResVO>) in.readObject();
	            }
	            else if (command.equals("regResInfo")) {
	            	 
	            }
	            else if (command.equals("modResInfo")) {
	            	
	            }
	            else if (command.equals("cancelResInfo")) {
	            	
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
