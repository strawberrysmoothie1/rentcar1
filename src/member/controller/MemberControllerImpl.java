package member.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.base.AbstractBaseController;
import member.dao.MemberDAO;
import member.dao.MemberDAOImpl;
import member.VO.MemberVO;

public class MemberControllerImpl extends AbstractBaseController implements MemberController {
	public MemberDAO memberDAO;

	public MemberControllerImpl() {
		memberDAO = new MemberDAOImpl();
	}

	@Override
	public List<MemberVO> listMember(MemberVO memVO) {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			memList = memberDAO.selectMember(memVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return memList;
	}

	@Override
	public void regMember(MemberVO memVO) {
		try {
			memberDAO.insertMember(memVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void modMember(MemberVO memVO) {
		try {
			memberDAO.updateMember(memVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeMember(MemberVO memVO) {
		try {
			memberDAO.deleteMember(memVO);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
