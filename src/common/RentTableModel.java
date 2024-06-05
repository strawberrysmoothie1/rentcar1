package common;

import javax.swing.table.AbstractTableModel;

// RentTableModel 클래스: AbstractTableModel 클래스를 상속하여 테이블 모델을 구현하는 클래스
public class RentTableModel extends AbstractTableModel {
    
    // 필드 변수 선언
    String[] columnNames; // 테이블의 컬럼 이름 배열
    Object[][] data; // 테이블의 데이터 배열

    // RentTableModel 생성자: 테이블 데이터와 컬럼 이름을 초기화하는 생성자
    public RentTableModel(Object[][] data, String[] columnNames) {
        this.columnNames = columnNames;
        this.data = data;
    }

    // getColumnCount 메서드 오버라이딩: 테이블의 열 개수를 반환하는 메서드
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // getRowCount 메서드 오버라이딩: 테이블의 행 개수를 반환하는 메서드
    @Override
    public int getRowCount() {
        return data.length;
    }

    // getValueAt 메서드 오버라이딩: 지정된 셀의 값을 반환하는 메서드
    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }
    
    // getColumnName 메서드 오버라이딩: 지정된 열의 이름을 반환하는 메서드
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // isCellEditable 메서드 오버라이딩: 특정 셀이 편집 가능한지 여부를 반환하는 메서드
    @Override
    public boolean isCellEditable(int row, int column) {
        // 특정 열에 따라 셀의 편집 가능 여부를 반환
        return columnEditables[column];
    }
    
    // setValueAt 메서드: 지정된 셀의 값을 설정하는 메서드
    public void setValueAt(Object value, int row, int column) {
        // 데이터 배열의 해당 셀에 값을 설정
        data[row][column] = value;
        // 셀이 업데이트되었음을 알림
        fireTableCellUpdated(row, column);
    }

    // columnEditables 배열: 각 열의 편집 가능 여부를 저장하는 배열
    boolean[] columnEditables = new boolean[] {
        false, true, true, true, true, true, true
    };
}
