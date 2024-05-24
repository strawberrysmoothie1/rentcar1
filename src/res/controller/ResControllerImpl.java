package res.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.base.AbstractBaseController;
import res.dao.ResDAO;
import res.dao.ResDAOImpl;
import res.VO.ResVO;

public class ResControllerImpl extends AbstractBaseController implements ResController{
	private ResDAO resDAO;
	
	public ResControllerImpl() {
		resDAO = new ResDAOImpl();
		
	}
	@Override
	public List<ResVO> listResInfo(ResVO resVO) {
		List<ResVO> resList = new ArrayList<ResVO>();
		try {
			resList = resDAO.selectResInfo(resVO);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return resList;
		
	}

	@Override
	public void regResInfo(ResVO resVO)  {
		try {
			resDAO.insertResInfo(resVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void modResInfo(ResVO resVO)  {
		try {
			resDAO.updateResInfo(resVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();			
		}
					
	}

	@Override
	public void cancelResInfo(ResVO resVO)  {
		try {
			resDAO.deleteResInfo(resVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	


}
