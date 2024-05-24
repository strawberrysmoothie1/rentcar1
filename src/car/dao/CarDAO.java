package car.dao;

import java.sql.SQLException;
import java.util.List;

import car.VO.CarVO;


public interface CarDAO {
public List<CarVO> selectCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException;
	
	public void insertCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException;
	
	public void updateCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException;
	
	public void deleteCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException;
	
    public void updateFavorite(String carNumber, String newFavor) throws SQLException, ClassNotFoundException;

}
