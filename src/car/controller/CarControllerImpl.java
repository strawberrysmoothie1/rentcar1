package car.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import car.dao.CarDAO;
import car.dao.CarDAOImpl;
import car.VO.CarVO;
import common.base.AbstractBaseController;
import member.VO.MemberVO;



public class CarControllerImpl extends AbstractBaseController implements CarController{
//	public CarDAO carDAO;
	
	 private static final String SERVER_IP = "localhost";
	   private static final int SERVER_PORT = 0417;
	
//	public CarControllerImpl() {
//		carDAO = new CarDAOImpl();
//
//	}
	@Override
	public List<CarVO> listCarInfo(CarVO carVO) {
//		List<CarVO> carList = new ArrayList<CarVO>();
//		try {
//			carList = carDAO.selectCarInfo(carVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		return carList;
		 return sendRequest("listCarInfo", carVO);
	}

	

	@Override
	public void regCarInfo(CarVO carVO)  {
//		try {
//			carDAO.insertCarInfo(carVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
		sendRequest("regCarInfo", carVO);
	}

	@Override
	public void modCarInfo(CarVO carVO)  {
//		try {
//			carDAO.updateCarInfo(carVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
		sendRequest("modCarInfo", carVO);
	}

	@Override
	public void removeCarInfo(CarVO carVO) {
//		try {
//			carDAO.deleteCarInfo(carVO);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
		sendRequest("removeCarInfo", carVO);
	}
	

	 private List<CarVO> sendRequest(String command, CarVO carVO) {
	        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
	             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
	             
	            out.writeObject(command);
	            out.writeObject(carVO);

	            if (command.equals("listCarInfo")) {
	                return (List<CarVO>) in.readObject();
	            }
	            else if (command.equals("regCarInfo")) {
	            	
	            }
	            else if (command.equals("modCarInfo")) {
	            	
	            }
	            else if (command.equals("removeCarInfo")) {
	            	
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}

