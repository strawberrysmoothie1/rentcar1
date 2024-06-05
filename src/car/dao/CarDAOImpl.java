package car.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import car.VO.CarVO;
import common.base.AbstractBaseDAO;

public class CarDAOImpl extends AbstractBaseDAO implements CarDAO {

    // 차량 정보 조회 메서드
    @Override
    public List<CarVO> selectCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException {
        List<CarVO> carList = new ArrayList<CarVO>();
        String _carNumber = carVO.getCarNumber();
        // 입력된 차량 번호에 따라 SQL 쿼리를 동적으로 생성
        if (_carNumber != null && _carNumber.length() != 0) {
            pstmt = conn.prepareStatement("SELECT * FROM t_Car WHERE carNumber = ? ORDER BY carNumber");
            pstmt.setString(1, _carNumber);
        } else {
            pstmt = conn.prepareStatement("SELECT * FROM t_Car ORDER BY carNumber");
        }

        ResultSet rs = pstmt.executeQuery();
        // 조회 결과를 CarVO 객체에 매핑하여 리스트에 추가
        while (rs.next()) {
            String carNumber = rs.getString("carNumber");
            String carName = rs.getString("carName");
            String carColor = rs.getString("carColor");
            int carSize = rs.getInt("carSize");
            String carMaker = rs.getString("carMaker");
            String carFavorites = rs.getString("carFavorites");
            CarVO _carVO = new CarVO();

            _carVO.setCarNumber(carNumber);
            _carVO.setCarName(carName);
            _carVO.setCarColor(carColor);
            _carVO.setCarSize(carSize);
            _carVO.setCarMaker(carMaker);
            _carVO.setCarFavorites(carFavorites);
            carList.add(_carVO);
        } // end while

        rs.close();
        return carList;
    }

    // 차량 정보 등록 메서드
    @Override
    public void insertCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("INSERT INTO t_Car (carNumber, carName, carColor, carSize, carMaker, carFavorites) VALUES (?, ?, ?, ?, ?, ?)");
        pstmt.setString(1, carVO.getCarNumber());
        pstmt.setString(2, carVO.getCarName());
        pstmt.setString(3, carVO.getCarColor());
        pstmt.setInt(4, carVO.getCarSize());
        pstmt.setString(5, carVO.getCarMaker());
        pstmt.setString(6, carVO.getCarFavorites());
        pstmt.executeUpdate();
    }

    // 차량 정보 수정 메서드
    @Override
    public void updateCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("UPDATE t_Car SET carName = ?, carColor = ?, carSize = ?, carMaker = ?, carFavorites = ? WHERE carNumber = ?");
        pstmt.setString(1, carVO.getCarName());
        pstmt.setString(2, carVO.getCarColor());
        pstmt.setInt(3, carVO.getCarSize());
        pstmt.setString(4, carVO.getCarMaker());
        pstmt.setString(5, carVO.getCarFavorites());
        pstmt.setString(6, carVO.getCarNumber());
        pstmt.executeUpdate();
    }

    // 차량 정보 삭제 메서드
    @Override
    public void deleteCarInfo(CarVO carVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("DELETE FROM t_Car WHERE carNumber = ?");
        pstmt.setString(1, carVO.getCarNumber());
        pstmt.executeUpdate();
    }

}
