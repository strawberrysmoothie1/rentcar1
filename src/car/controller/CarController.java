package car.controller;

import java.io.IOException;
import java.util.List;

import car.exception.CarException;
import car.VO.CarVO;

public interface CarController {
	public List<CarVO> listCarInfo(CarVO carVO) ;  //렌터카 리스트 출력 

	public void regCarInfo(CarVO carVO) ; 
	
	public void modCarInfo(CarVO carVO) ;
	
	public void removeCarInfo(CarVO carVO) ;
	
}
