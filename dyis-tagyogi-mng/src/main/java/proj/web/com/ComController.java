package proj.web.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import proj.biz.com.service.ComService;
import proj.biz.memb.service.MembService;
import proj.biz.sys.service.LogProcService;
import proj.core.common.CommonUtil;
import proj.core.domain.JsonResult;
import proj.core.exception.BaseException;
import proj.core.session.SessionUtil;
import proj.web.WebBaseController;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>ComController.java (공통관리 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  : 
 * description  : 공통 관리
 */
@RequestMapping("/com")
@Controller("comController")
public class ComController extends WebBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComController.class);
	
	@Autowired
	private ComService comService;

	
	@Autowired
	private MembService membService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 조합원 정보 조회 (등록 화면내에서 조회)
	 */
	@RequestMapping(value="/selectMembList.do")
	public ModelAndView selectMembList(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("selectMembList START ");
		
		JsonResult result = new JsonResult();
		
		try {
			
			//조합원 정보 조회
			result.put("data", comService.selectMembList(paramMap));
			
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		
		LOGGER.debug("selectMembList END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 조합원 정보 상세조회
	 */
	@RequestMapping(value="/selectMemb.do")
	public ModelAndView selectMemb(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("selectMemb START ");
		
		JsonResult result = new JsonResult();
		
		try {
			
			//조합원 정보 조회
			HashMap<String, Object>membMap = comService.selectMemb(paramMap);
			
			//조합원 신청자 정보
			List<Object> persList = membService.selectCeoPersList(paramMap); 
			
			membMap.put("persList", persList);
			
			result.put("data", membMap);
			
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		
		LOGGER.debug("selectMemb END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 공통코드 조회
	 */
	@RequestMapping(value="/selectCodeList.do")
	public ModelAndView selectCodeList(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("selectCodeList START ");
		
		JsonResult result = new JsonResult();
		
		try {
			
			//공통코드 정보 조회
			paramMap.put("codeList", Arrays.asList(((String) paramMap.get("codeList")).split(":")));
			result.put("codeList", comService.selectCodeList(paramMap));
			
			
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		
		LOGGER.debug("selectCodeList END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 엑셀다운로드 
	 */
	@RequestMapping(value="/excelDown.do")
	public void excelDown(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("excelDown START paramMap " + paramMap);
		
		try {
			
			//엑셀 환경설정
			Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
	        SXSSFSheet objSheet = null;
			SXSSFRow objRow = null;
			SXSSFCell objCell = null; // 셀 생성
			
			// 제목 폰트
			Font fontTitl = workbook.createFont();
			fontTitl.setFontHeightInPoints((short) 12);
			fontTitl.setBoldweight((short) 12);
			fontTitl.setFontName("맑은고딕");
			
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight((short) 10);
			font.setFontName("맑은고딕");
			
			
			// 제목 스타일에 폰트 적용, 정렬
			CellStyle styleTitl = workbook.createCellStyle(); // 타이틀 스타일
			CellStyle styleHd = workbook.createCellStyle(); // 헤더 스타일
			
			styleTitl.setAlignment(CellStyle.ALIGN_CENTER);
			styleTitl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//styleTitl.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			//styleTitl.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //배경색 넣을 경우 필요 
			styleTitl.setBorderTop(CellStyle.BORDER_THIN);
			styleTitl.setBorderBottom(CellStyle.BORDER_THIN);
			styleTitl.setBorderRight(CellStyle.BORDER_THIN);
			styleTitl.setBorderLeft(CellStyle.BORDER_THIN);
			styleTitl.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setRightBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setFont(fontTitl);
			
			
			styleHd.setAlignment(CellStyle.ALIGN_CENTER);
			styleHd.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			styleHd.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //배경색 넣을 경우 필요 
			styleHd.setBorderTop(CellStyle.BORDER_THIN);
			styleHd.setBorderBottom(CellStyle.BORDER_THIN);
			styleHd.setBorderRight(CellStyle.BORDER_THIN);
			styleHd.setBorderLeft(CellStyle.BORDER_THIN);
			styleHd.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setRightBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setFont(font);
			
			//헤더정보 설정
			String excelNm = (String) paramMap.get("excelNm");
			List<HashMap<String, Object>> headList = new ArrayList<>();
			Object gridColsObj = paramMap.get("gridCols");
			List<HashMap<String, Object>> gridCols = (List<HashMap<String, Object>>) gridColsObj;
			
			for(int i = 0; i < gridCols.size(); i++) {
				HashMap<String, Object> colsMap = (HashMap<String, Object>) gridCols.get(i);
				//LOGGER.debug("excelDown colsMap :: " + colsMap );
				String name = (String)colsMap.get("Name");
				String visible = (String)colsMap.get("Visible");
				if(name.equals("delChk") || ("0".equals(visible) && visible != null)) {
					continue;
				}
				headList.add(colsMap);
			}
			//LOGGER.debug("excelDown headList :: " + headList.size() );
			
			
			
			int rowNum = 0;
			//시트 생성
	        objSheet = (SXSSFSheet) workbook.createSheet(excelNm);
	       
	        objRow = (SXSSFRow) objSheet.createRow(rowNum++); //행 추가
	    	for (int x = 0; x < headList.size(); x++) {
	        	HashMap<String, Object> getHead = (HashMap<String, Object>)headList.get(x);
	        	
	        	int sizes = 5000;
	        	if((String)getHead.get("Width") != null) sizes = Integer.parseInt((String)getHead.get("Width")) * 40;
	        	
	        	objSheet.setColumnWidth(x, (short)sizes);
	        	
	        	objCell = (SXSSFCell) objRow.createCell(x);
	    		objCell.setCellValue((String)getHead.get("Header"));
	    		objCell.setCellStyle(styleHd);
	    	}
			
	    	//데이터 조회
			List<HashMap<String, Object>> dataList = comService.selectExcelDownList(paramMap);
			
	    	for (HashMap<String, Object> rowData : dataList) {
	            //row 생성
	        	objRow = (SXSSFRow) objSheet.createRow(rowNum++);
	        	for (int x = 0; x < headList.size(); x++) {
	        		HashMap<String, Object> getHead = (HashMap<String, Object>)headList.get(x);
	        		objCell = (SXSSFCell) objRow.createCell(x);
	        		
	        		
        			//map에 있는 데이터를 한개씩 조회해서 열을 생성한다.
	            	for (String key : rowData.keySet()) {
	            		if(getHead.get("Name").equals(key)){
	            			if(rowData.get(key) != null) {
	            				objCell.setCellValue(rowData.get(key).toString());
	            			}
	                	}
	                }
	            	
	            	//정렬
        			if("Center".equals(getHead.get("Align"))){
        				objCell.setCellStyle(cellStyle(workbook, "center"));
        			}else if("Right".equals(getHead.get("Align"))){
        				objCell.setCellStyle(cellStyle(workbook, "right"));
        			}else{
        				objCell.setCellStyle(cellStyle(workbook, "left"));
        			}
        			
	            	
        			
	        	}
	        }
	    	
	        // 컨텐츠 타입과 파일명 지정
	        response.setContentType("application/vnd.ms-excel");
	        //response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", new String(excelNm.getBytes("KSC5601"), "UTF-8")+"_"+CommonUtil.getCurrentDateDash()));
	        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", URLEncoder.encode(excelNm, "UTF-8")+"_"+ CommonUtil.getCurrentDate()));
	        
	        workbook.write(response.getOutputStream());
	        response.getOutputStream().flush();
	        
	        //이력저장
	        /*
			HttpSession session = request.getSession();
	        Excel excel = new Excel();
	        excel.setExcelName(excelNm);
	        LOGGER.error("excelDown Exception :: " + excel );	
	        excelService.insertExcelDownHst(excel);
	        */
			//LOGGER.debug("excelDown dataList :: " + dataList.size() );
			
		} catch (BaseException | IOException e) {
			LOGGER.error("excelDown BaseException :: " + e.toString() );
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
			//result.setResult(9, "엑셀다운로드 중 오류가 발생했습니다.");
		}
		
		LOGGER.debug("excelDown END :: " );
	}
	
	/**
	 * 엑셀다운로드(json 방식) 
	 */
	@RequestMapping(value="/excelDownToGrid.do")
	public void excelDownToGrid(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("excelDownToGrid START paramMap " + paramMap);
		
		try {
			
			//엑셀 환경설정
			Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
	        SXSSFSheet objSheet = null;
			SXSSFRow objRow = null;
			SXSSFCell objCell = null; // 셀 생성
			
			// 제목 폰트
			Font fontTitl = workbook.createFont();
			fontTitl.setFontHeightInPoints((short) 12);
			fontTitl.setBoldweight((short) 12);
			fontTitl.setFontName("맑은고딕");
			
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight((short) 10);
			font.setFontName("맑은고딕");
			
			
			// 제목 스타일에 폰트 적용, 정렬
			CellStyle styleTitl = workbook.createCellStyle(); // 타이틀 스타일
			CellStyle styleHd = workbook.createCellStyle(); // 헤더 스타일
			
			styleTitl.setAlignment(CellStyle.ALIGN_CENTER);
			styleTitl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//styleTitl.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			//styleTitl.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //배경색 넣을 경우 필요 
			styleTitl.setBorderTop(CellStyle.BORDER_THIN);
			styleTitl.setBorderBottom(CellStyle.BORDER_THIN);
			styleTitl.setBorderRight(CellStyle.BORDER_THIN);
			styleTitl.setBorderLeft(CellStyle.BORDER_THIN);
			styleTitl.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setRightBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleTitl.setFont(fontTitl);
			
			
			styleHd.setAlignment(CellStyle.ALIGN_CENTER);
			styleHd.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			styleHd.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //배경색 넣을 경우 필요 
			styleHd.setBorderTop(CellStyle.BORDER_THIN);
			styleHd.setBorderBottom(CellStyle.BORDER_THIN);
			styleHd.setBorderRight(CellStyle.BORDER_THIN);
			styleHd.setBorderLeft(CellStyle.BORDER_THIN);
			styleHd.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setRightBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleHd.setFont(font);
			
			//헤더정보 설정
			String excelNm = (String) paramMap.get("excelNm");
			List<Object> headList = new ArrayList<>();
			List<Object> gridCols = (List<Object>) paramMap.get("gridCols");
			
			for(int i = 0; i < gridCols.size(); i++) {
				HashMap<String, Object> colsMap = (HashMap<String, Object> ) gridCols.get(i);
				String name = (String) colsMap.get("Name");
				String visible = (String)colsMap.get("Visible");
				if(name.equals("delChk") || ("0".equals(visible) && visible != null)) {
					continue;
				}
				headList.add(colsMap);
			}
			
			int rowNum = 0;
			//시트 생성
	        objSheet = (SXSSFSheet) workbook.createSheet(excelNm);
	       
	        objRow = (SXSSFRow) objSheet.createRow(rowNum++); //행 추가
	    	for (int x = 0; x < headList.size(); x++) {
	        	HashMap<String, Object> getHead = (HashMap<String, Object> )headList.get(x);
	        	
	        	int sizes = 5000;
	        	if((String)getHead.get("Width") != null) sizes = Integer.parseInt((String)getHead.get("Width")) * 40;
	        	
	        	objSheet.setColumnWidth(x, (short)sizes);
	        	
	        	objCell = (SXSSFCell) objRow.createCell(x);
	    		objCell.setCellValue((String)getHead.get("Header"));
	    		objCell.setCellStyle(styleHd);
	    	}
			
	    	//데이터 조회
			//List<HashMap<String, Object>> dataList = comService.selectExcelDownList(paramMap);
	    	List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) paramMap.get("rows");
	    	for (HashMap<String, Object> rowData : dataList) {
	    		//LOGGER.debug("excelDown rowData :: " + rowData );
	            //row 생성
	        	objRow = (SXSSFRow) objSheet.createRow(rowNum++);
	        	for (int x = 0; x < headList.size(); x++) {
	        		HashMap<String, Object>getHead = (HashMap<String, Object>)headList.get(x);
	        		objCell = (SXSSFCell) objRow.createCell(x);
	        		
        			//map에 있는 데이터를 한개씩 조회해서 열을 생성한다.
	            	for (String key : rowData.keySet()) {
	            		if(getHead.get("Name").equals(key)){
	            			objCell.setCellValue(rowData.get(key).toString());
	                	}
	                }
	            	
	            	//정렬
        			if("Center".equals(getHead.get("Align"))){
        				objCell.setCellStyle(cellStyle(workbook, "center"));
        			}else if("Right".equals(getHead.get("Align"))){
        				objCell.setCellStyle(cellStyle(workbook, "right"));
        			}else{
        				objCell.setCellStyle(cellStyle(workbook, "left"));
        			}
	        	}
	        }
	        // 컨텐츠 타입과 파일명 지정
	        response.setContentType("application/vnd.ms-excel");
	        //response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", new String(excelNm.getBytes("KSC5601"), "UTF-8")+"_"+CommonUtil.getCurrentDateDash()));
	        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", URLEncoder.encode(excelNm, "UTF-8")+"_"+ CommonUtil.getCurrentDate()));
	        
	        workbook.write(response.getOutputStream());
	        response.getOutputStream().flush();
	        
	        //이력저장
	        /*
			HttpSession session = request.getSession();
	        Excel excel = new Excel();
	        excel.setExcelName(excelNm);
	        LOGGER.error("excelDown Exception :: " + excel );	
	        excelService.insertExcelDownHst(excel);
	        */
			//LOGGER.debug("excelDown dataList :: " + dataList.size() );
			
		} catch (BaseException | IOException e) {
			LOGGER.error("excelDownToGrid BaseException :: " + e.toString() );
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
			//result.setResult(9, "엑셀다운로드 중 오류가 발생했습니다.");
		}
		
		LOGGER.debug("excelDownToGrid END :: " );
	}
	
	public CellStyle cellStyle(Workbook workbook, String align) {
		CellStyle styleBy = workbook.createCellStyle();
		
		if("center".equals(align)) styleBy.setAlignment(CellStyle.ALIGN_CENTER);
		if("left".equals(align)) styleBy.setAlignment(CellStyle.ALIGN_LEFT);
		if("right".equals(align)) styleBy.setAlignment(CellStyle.ALIGN_RIGHT);
		
		styleBy.setBorderTop(CellStyle.BORDER_THIN);
		styleBy.setBorderBottom(CellStyle.BORDER_THIN);
		styleBy.setBorderRight(CellStyle.BORDER_THIN);
		styleBy.setBorderLeft(CellStyle.BORDER_THIN);
		styleBy.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleBy.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleBy.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleBy.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		return styleBy;
	}
	
	/**
	 * 즐겨찾기 추가
	 */
	@RequestMapping(value="/insertUserMenuFav.do")
	public ModelAndView insertUserMenuFav(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("insertUserMenuFav START ");
		
		JsonResult result = new JsonResult();
		try {
			
			paramMap.put("userId", SessionUtil.getSess("userId"));
			//즐겨찾기 추가
			comService.insertUserMenuFav(paramMap);
		} catch (Exception e) {
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		
		LOGGER.debug("insertUserMenuFav END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	
	
}