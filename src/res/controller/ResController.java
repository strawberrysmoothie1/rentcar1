package res.controller;

import java.util.List;

import res.VO.ResVO;


public interface ResController {
	public List<ResVO>  listResInfo(ResVO resVO)  ;   //예약 리스트 조회
	
	public void regResInfo(ResVO resVO) ;
	
	public void modResInfo(ResVO resVO) ;
	
	public void cancelResInfo(ResVO resVO) ;
	

}
