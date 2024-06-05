package res.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import common.base.AbstractBaseDAO;
import res.VO.ResVO;

public class ResDAOImpl extends AbstractBaseDAO implements ResDAO {

    // 예약 정보 조회 메서드
    @Override
    public List<ResVO> selectResInfo(ResVO resVO) throws SQLException, ClassNotFoundException {
        List<ResVO> resList = new ArrayList<ResVO>();
        String _resNumber = resVO.getResNumber();
        if (_resNumber != null && _resNumber.length() != 0) {
            // 특정 예약 번호에 해당하는 정보 조회
            pstmt = conn.prepareStatement("SELECT resNumber, resCarNumber, "
                    + "TO_CHAR(resDate,'YYYY-MM-DD') resDate, "
                    + "TO_CHAR(useBeginDate, 'YYYY-MM-DD') useBeginDate, "
                    + "TO_CHAR(returnDate, 'YYYY-MM-DD') returnDate, "
                    + "resUserId, "
                    + "resUse "
                    + "FROM t_res  WHERE resNumber = ? ORDER BY resNumber");
            pstmt.setString(1, _resNumber);
        } else {
            // 모든 예약 정보 조회
            pstmt = conn.prepareStatement("SELECT resNumber, resCarNumber, "
                    + "TO_CHAR(resDate,'YYYY-MM-DD') resDate, "
                    + "TO_CHAR(useBeginDate, 'YYYY-MM-DD') useBeginDate, "
                    + "TO_CHAR(returnDate, 'YYYY-MM-DD') returnDate, "
                    + "resUserId, "
                    + "resUse "
                    + "FROM t_res  ORDER BY resNumber");
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String resNumber = rs.getString("resNumber");
            String resCarNumber = rs.getString("resCarNumber");
            String resDate = rs.getString("resDate");
            String useBeginDate = rs.getString("useBeginDate");
            String returnDate = rs.getString("returnDate");
            String resUserId = rs.getString("resUserId");
            String resUse = rs.getString("resUse");

            ResVO _resVO = new ResVO();

            _resVO.setResNumber(resNumber);
            _resVO.setResCarNumber(resCarNumber);
            _resVO.setResDate(resDate);
            _resVO.setUseBeginDate(useBeginDate);
            _resVO.setReturnDate(returnDate);
            _resVO.setResUserId(resUserId);
            _resVO.setResUse(resUse);

            resList.add(_resVO);
        } // end while
        rs.close();
        return resList;

    }

    // 예약 정보 삽입 메서드
 // 예약 정보 삽입 메서드
    @Override
    public void insertResInfo(ResVO resVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("INSERT INTO t_res (resNumber, resCarNumber,"
        		+ " resDate, useBeginDate, returnDate, resUserId, resUse) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstmt.setString(1, resVO.getResNumber());
        pstmt.setString(2, resVO.getResCarNumber());
        pstmt.setDate(3, Date.valueOf(resVO.getResDate())); // java.sql.Date 객체로 변환하여 설정
        pstmt.setDate(4, Date.valueOf(resVO.getUseBeginDate())); // java.sql.Date 객체로 변환하여 설정
        pstmt.setDate(5, Date.valueOf(resVO.getReturnDate())); // java.sql.Date 객체로 변환하여 설정
        pstmt.setString(6, resVO.getResUserId());
        pstmt.setString(7, resVO.getResUse());

        pstmt.executeUpdate();
    }


    // 예약 정보 업데이트 메서드
    @Override
    public void updateResInfo(ResVO resVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("UPDATE t_res SET  resCarNumber = ?, resDate = ?,"
        		+ " useBeginDate = ?, returnDate = ?, resUserId = ?, resUse = ? " +
                "WHERE resNumber = ? ");
        pstmt.setString(1, resVO.getResCarNumber());
        pstmt.setString(2, resVO.getResDate());
        pstmt.setString(3, resVO.getUseBeginDate());
        pstmt.setString(4, resVO.getReturnDate());
        pstmt.setString(5, resVO.getResUserId());
        pstmt.setString(6, resVO.getResUse());
        pstmt.setString(7, resVO.getResNumber());

        pstmt.executeUpdate();
    }

    // 예약 정보 삭제 메서드
    @Override
    public void deleteResInfo(ResVO resVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("DELETE FROM t_res WHERE resNumber = ?");
        pstmt.setString(1, resVO.getResNumber());
        pstmt.executeUpdate();
    }

}
