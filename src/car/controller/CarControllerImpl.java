package car.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import car.dao.CarDAO;
import car.dao.CarDAOImpl;
import car.VO.CarVO;
import common.base.AbstractBaseController;
import member.VO.MemberVO;



public class CarControllerImpl extends AbstractBaseController implements CarController{
	public CarDAO carDAO;
	
	public CarControllerImpl() {
		carDAO = new CarDAOImpl();

	}
	@Override
	public List<CarVO> listCarInfo(CarVO carVO) {
		List<CarVO> carList = new ArrayList<CarVO>();
		try {
			carList = carDAO.selectCarInfo(carVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return carList;
	}

	

	@Override
	public void regCarInfo(CarVO carVO)  {
		try {
			carDAO.insertCarInfo(carVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void modCarInfo(CarVO carVO)  {
		try {
			carDAO.updateCarInfo(carVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCarInfo(CarVO carVO) {
		try {
			carDAO.deleteCarInfo(carVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateFavorite(String carNumber, String newFavor) {
	    try {
	        // 즐겨찾기 정보를 업데이트하는 DAO 메서드 호출
	        carDAO.updateFavorite(carNumber, newFavor);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	

}

