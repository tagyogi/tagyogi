package ddc.core.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Date;

/**
 * <p>프로젝트명 : 메인홈페이지 운영</p>
 *
 * <p>파일명		: ddc.core.common.ExcelLoadUtil.java</p>
 * <p>설명		: 엑셀 읽어오기 Util</p>
 *
 * @author BSG
 */
public class ExcelLoadUtil {

    /**
     * <p>각 셀별 값을 유형별로 구합니다.</p>
     *
     * @param cell 셀
     * @return 값
     */
    public static String getCellValue(Cell cell) {
        String result = "";
        if (cell != null) {
            switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고 해당 타입에 맞게 가져온다.
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        //result = DateFormatUtil.format(date, "yyyy-MM-dd");
                    } else {
//                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        result = cell.getStringCellValue() + "";
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue() + "";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
                default:
                    break;
            }
        }

        return result;
    }
    
    
}
