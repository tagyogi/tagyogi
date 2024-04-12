package proj.web;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import proj.core.config.ConfigProperty;
import proj.core.domain.JsonResult;
import proj.service.DevDbService;
import proj.util.DateUtil;
import proj.util.FileUploadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * 메인정보  Controller 클래스
 * @author 공통 서비스 개발팀 BSG
 * @since 2022.02
 * @version 1.0
 * @see 개발용 
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2022.02  BSG            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@Controller
public class DevDbController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DevDbController.class);
	
	/** 개발경로 */
	protected final static String DEV_SRC  = ConfigProperty.getString("Globals.dev.src"); //
	protected final static String DB_TYPE  = ConfigProperty.getString("dbType"); //
	public static final String SEPERATOR = File.separator;
    
	/** devDbService */
	@Autowired
	protected DevDbService devDbService;
	
	/**
	 * ################################# 개발 데이터베이스 초기설정 #############################################################################
	 */
	/**
	 * 개발 데이터베이스 초기설정
	 */
	@RequestMapping(value="/dev/devDbInit.do")
	public ModelAndView devDbInit(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		try{
			
			//DB정보관리 테이블 존재 확인
			paramMap.put("dbType", DB_TYPE);
			paramMap.put("sTblId", "DEV_DB_MAS");
			HashMap<String, Object>getTbl = devDbService.selectTblInfo(paramMap);
			
			if(getTbl != null) {
				josnView.addObject("resultCode", 9);
				josnView.addObject("resultMsg", "데이터베이스 정보가 존재합니다 (초기화처리불가)");
			}else {
				//개발데이터베이스 생성
				devDbService.createDevDbInit(paramMap);
				
			}
			
			josnView.addObject("resultCode", 0);
		}catch(Exception e){
			LOGGER.debug("selectDevDbMasList Exception " + e.getMessage());
			josnView.addObject("resultCode", 9);
			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
			
		}
		LOGGER.debug("selectDevDbMasList END");
		return josnView;
	}
	
	/**
	 * ################################# 개발 데이터베이스 영역 #############################################################################
	 * ################################# 개발 데이터베이스 영역 #############################################################################
	 * ################################# 개발 데이터베이스 영역 #############################################################################
	 */
	/**
	 * 개발 데이터베이스 현황 화면
	 */
	@RequestMapping(value="/dev/devDbListPage.do")
	public ModelAndView devDbListPage(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("menuPath", request.getParameter("menuPath") == null?"990103":request.getParameter("menuPath")); //메뉴경로
		
		LOGGER.debug("devDbListPage END");
		return mav;		
	}
	
	/** 
	 * 개발 데이터베이스 현황 조회
	 */ 
	@RequestMapping(value="/dev/selectDevDbMasList.do")
	public ModelAndView selectDevDbMasList(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		try{
			
			//목록조회
			josnView.addObject("rows", devDbService.selectDevDbMasList(paramMap));
			josnView.addObject("resultCode", 0);
		}catch(Exception e){
			LOGGER.debug("selectDevDbMasList Exception " + e.getMessage());
			josnView.addObject("resultCode", 9);
			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
			
		}
		LOGGER.debug("selectDevDbMasList END");
		return josnView;
		
	}
	
	/**
	 * 개발 데이터베이스 상세 현황
	 */
	@RequestMapping(value="/dev/selectDevDbDtlList.do")
	public ModelAndView selectDevDbDtlList(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		LOGGER.debug("selectDevDbDtlList START");		
		try{
			//목록조회
			josnView.addObject("rows", devDbService.selectDevDbDtlList(paramMap));
			josnView.addObject("resultCode", 0);
		}catch(Exception e){
			LOGGER.debug("selectDevDbMasList Exception " + e.getMessage());
			josnView.addObject("resultCode", 9);
			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
		}
		
		LOGGER.debug("selectDevDbDtlList END");
		return josnView;
	}
	
	/**
	 * 개발 데이터베이스 정보 등록 처리
	 */
	@RequestMapping(value="/dev/saveDevDbMasProc.do")
	public ModelAndView saveDevDbMasProc(@RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		LOGGER.debug("saveDevDbMasProc START paramMap " + paramMap);		
		try{
			
			//처리
			devDbService.saveDevDbMasProc(paramMap); 
			
			josnView.addObject("resultCode", 0);
			josnView.addObject("resultMsg", "정상 처리되었습니다.");//정상처리되었습니다.
 		}catch(Exception ex){
 			LOGGER.debug("saveDevDbMasProc Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
 		}
		LOGGER.debug("saveDevDbMasProc END");
		return josnView;		
	}
	
	/**
	 * 개발 데이터베이스 상세 정보 등록 처리
	 */
	@RequestMapping(value="/dev/saveDevDbDtlProc.do")
	public ModelAndView saveDevDbDtlProc(@RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		LOGGER.debug("saveDevDbDtlProc START paramMap " + paramMap);		
		try{
			
			//처리
			devDbService.saveDevDbDtlProc(paramMap); 
			
			josnView.addObject("resultCode", 0);
			josnView.addObject("resultMsg", "정상 처리되었습니다.");//정상처리되었습니다.
 		}catch(Exception ex){
 			LOGGER.debug("saveDevDbDtlProc Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
 			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
 		}
		LOGGER.debug("saveDevDbDtlProc END");
		return josnView;		
	}
	
	
	/**
	 * 개발 데이터베이스 삭제 처리
	 */
	@RequestMapping(value="/dev/deleteDevDbProc.do")
	public ModelAndView deleteDevDbProc(@RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		LOGGER.debug("deleteDevDbProc START paramMap " + paramMap);		
		try{
			
			//처리
			devDbService.deleteDevDbProc(paramMap); 
			
			josnView.addObject("resultCode", 0);
			josnView.addObject("resultMsg", "삭제처리되었습니다.");//삭제처리되었습니다.
 		}catch(Exception ex){
 			LOGGER.debug("deleteDevDbProc Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
 			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
 		}
		LOGGER.debug("deleteDevDbProc END");
		return josnView;		
	}
	
	/**
	 * 개발 테이블 필드 정보 초기화
	 */
	@RequestMapping(value="/dev/insertColsInitProc.do")
	public ModelAndView insertColsInitProc(@RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		LOGGER.debug("insertColsInitProc START paramMap " + paramMap);		
		try{
			
			//필드 정보 삭제 
			devDbService.insertColsInitProc(paramMap);
			
			josnView.addObject("resultCode", 0);
			josnView.addObject("resultMsg", "정상처리되었습니다.");//정상처리되었습니다.
 		}catch(Exception ex){
 			LOGGER.debug("insertColsInitProc Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
 			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
 		}
		
		LOGGER.debug("insertColsInitProc END");
		return josnView;		
	}
	
	/**
	 * 개발 데이터베이스 생성
	 */
	@RequestMapping(value="/dev/createTblProc.do")
	public ModelAndView createTblProc(@RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		LOGGER.debug("createTblProc START : " + paramMap);		
		try{
			
			//테이블 정보 조회
	    	HashMap<String, Object>masMap = devDbService.selectDevDbMasInfo(paramMap);
	    	
	    	//테이블 필드 정보 조회
	    	List<Object> dtlList = devDbService.selectDevDbDtlList(paramMap);
	    	
	    	//테이블 이전 필드 정보 조회
	    	List<Object> befDtlList = devDbService.selectBefTblColList(paramMap);
	    	
	    	//테이블 생성 쿼리문 추출
	    	HashMap<String, Object>getSql = createSql(DB_TYPE, masMap, dtlList, befDtlList);
	    	getSql.put("tblAt", paramMap.get("tblAt")); //생성여부
	    	getSql.put("tblBakAt", paramMap.get("tblBakAt")); //백업테이블생성여부
	    	
	    	LOGGER.debug("createTblProc getSql : " + getSql);	
	    	if((Integer)getSql.get("resultCode") == 0) {
	    		//처리
				devDbService.createTblProc(getSql); 
				josnView.addObject("resultCode", 0);
				josnView.addObject("resultMsg", "정상 처리되었습니다.");//정상처리되었습니다.
	    	}else {
	    		josnView.addObject("resultCode", getSql.get("resultCode")); 
				josnView.addObject("resultMsg", "테이블 쿼리문 생성중 오류가 발생했습니다.");//테이블 쿼리문 생성중 오류가 발생했습니다.
	    	}
 		}catch(Exception ex){
 			LOGGER.debug("createTblProc Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
 			josnView.addObject("resultMsg", "오류가 발생하였습니다.");//오류가 발생하였습니다..
 		}
		LOGGER.debug("createTblProc END");
		return josnView;		
	}
	

    // 테이블 생성 명령어 구현
    public HashMap<String, Object>createSql(String DEV_DB_TYPE, HashMap<String, Object>masMap, List<Object> dtlList, List<Object> befDtlList) throws Exception {
    	HashMap<String, Object>setMap = new HashMap<String, Object>();
    	
    	String dropBakPkSql = ""; //백업 테이블 pk 삭제
    	String dropBakTblSql = ""; //백업 테이블 삭제
    	String copyTblSql = ""; //테이블 백업 복사
    	
    	String dropPkSql = ""; //기존 테이블 PK 삭제
    	String dropTblSql = ""; //기존 테이블 삭제
    	
    	String tblSql = ""; //신규 테이블 생성
    	String unqSql = ""; //신규테이블 INDEX 생성
    	String pkSql = ""; //신규테이블 PK 생성
    	
    	String comtSql = ""; //코멘트 쿼리
    	
    	String pkcolNm = ""; //PK 필드
    	
    	String insertSql = ""; //백업테이블 insert 쿼리
    	//LOGGER.debug("createSql masMap : " + masMap);
    	//LOGGER.debug("createSql dtlList : " + dtlList);
    	
    	setMap.put("resultCode", 0);
    	if("oracle".equals(DEV_DB_TYPE)) {
    		dropBakPkSql = "ALTER TABLE " + masMap.get("tblNm") + "_BAK DROP PRIMARY KEY CASCADE";
    		dropBakTblSql = "DROP TABLE " + masMap.get("tblNm") + "_BAK CASCADE CONSTRAINTS";
    		
    		copyTblSql = "CREATE TABLE " + masMap.get("tblNm") + "_BAK AS SELECT * FROM " + masMap.get("tblNm"); //테이블 백업 복사
    		
    		dropPkSql = "ALTER TABLE " + masMap.get("tblNm") + " DROP PRIMARY KEY CASCADE";
    		dropTblSql = "DROP TABLE " + masMap.get("tblNm") + " CASCADE CONSTRAINTS";
    		
    		tblSql = "CREATE TABLE " + masMap.get("tblNm") + "(";
    		
    		comtSql = "COMMENT ON TABLE " + masMap.get("tblNm") + " IS '" + masMap.get("tblCmt") + "'; "; //테이블 코멘트
    		
    		//필드 for문
    		for(int i = 0; i < dtlList.size(); i++) {
    			HashMap<String, Object>setDtlMap = (HashMap<String, Object>) dtlList.get(i);
    			//LOGGER.debug("createSql setDtlMap : " + setDtlMap);
    			
    			String colType = (String) setDtlMap.get("colType"); //필드 유형
    			String colLen = (String)setDtlMap.get("colLen");
    			if(colLen == null) colLen = "100";
    			
    			if("DATE".equals(colType) || "CLOB".equals(colType)) {
    				tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + " ";
    			}else if("NUMBER".equals(colType)) {
    				tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + "(" + setDtlMap.get("colLen") + ")";
    			}else {
    				tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + "(" + setDtlMap.get("colLen") + " BYTE) ";
    			}
    			
    			//pk 추출
    			if("1".equals(setDtlMap.get("pkAt"))) {
    				
    				if("".equals(pkcolNm)) pkcolNm = (String)setDtlMap.get("colNm") ; //PK 필드 추출
    				else pkcolNm += ", "+(String)setDtlMap.get("colNm") ; //PK 필드 추출
    			}
    			
    			if(!"".equals(setDtlMap.get("defVal")) && setDtlMap.get("defVal") != null) {
    				if("NUMBER".equals(colType)) {
    					tblSql += " DEFAULT " + setDtlMap.get("defVal"); //기본값
    				}else {
    					tblSql += " DEFAULT '" + setDtlMap.get("defVal") + "'"; //기본값	
    				}
    			}
    			
    			if("1".equals(setDtlMap.get("nullAt"))) tblSql += " NOT NULL"; //NOT NULL 여부
    			
    			comtSql += "COMMENT ON COLUMN " + masMap.get("tblNm") + "." + setDtlMap.get("colNm") + " IS '" + setDtlMap.get("colCmt") + "'"; //코멘트
    			
    			if(i != dtlList.size()-1) {
    				tblSql += ","; //마지막이 아닐경우 콤마 추가
    				comtSql += "; "; //마지막이 아닐경우 콤마 추가
    			}
    			
    		}
    		
    		tblSql += ")";
    		
    		LOGGER.debug("createSql pkcolNm : " + pkcolNm);
    		
    		if(!"".equals(pkcolNm)) {
    			unqSql = "CREATE UNIQUE INDEX IDX_" + masMap.get("tblNm") + "_PK ON " + masMap.get("tblNm") + "(" + pkcolNm + ")"; //신규테이블 INDEX 생성
            	pkSql = "ALTER TABLE " + masMap.get("tblNm") + " ADD ( CONSTRAINT IDX_" + masMap.get("tblNm") +"_PK PRIMARY KEY ( " + pkcolNm + ") USING INDEX IDX_" + masMap.get("tblNm") + "_PK ENABLE VALIDATE)"; //신규테이블 PK 생성
    		}
    		
    		//백업 필드 for문
    		String befCols = "";
    		for(int i = 0; i < befDtlList.size(); i++) {
    			HashMap<String, Object>setBefMap = (HashMap<String, Object>) befDtlList.get(i);
    			
    			if("".equals(befCols)) befCols = (String)setBefMap.get("columnName");
    			else befCols += "," +(String)setBefMap.get("columnName");
    			
    		}
    		insertSql = "INSERT INTO " + masMap.get("tblNm") + " ( " + befCols + " ) ( SELECT " + befCols + " FROM " + masMap.get("tblNm") + "_BAK )";
        	 
    	}else if("mysql".equals(DEV_DB_TYPE)) {
    		/* MYSQL 적용시 검토 필요*/
    		
    		dropBakPkSql = "ALTER TABLE " + masMap.get("tblNm") + "_BAK DROP PRIMARY KEY CASCADE";
    		dropBakTblSql = "DROP TABLE " + masMap.get("tblNm") + "_BAK CASCADE CONSTRAINTS";
    		
    		copyTblSql = "CREATE TABLE " + masMap.get("tblNm") + "_BAK AS SELECT * FROM " + masMap.get("tblNm"); //테이블 백업 복사
    		
    		dropPkSql = "ALTER TABLE " + masMap.get("tblNm") + " DROP PRIMARY KEY CASCADE";
    		dropTblSql = "DROP TABLE " + masMap.get("tblNm") + " CASCADE CONSTRAINTS";
    		
    		tblSql = "CREATE TABLE " + masMap.get("tblNm") + "(";
    		
    		comtSql = "COMMENT ON TABLE " + masMap.get("tblNm") + " IS '" + masMap.get("tblCmt") + "'; "; //테이블 코멘트
    		for(int i = 0; i < dtlList.size(); i++) {
    			HashMap<String, Object>setDtlMap = (HashMap<String, Object>) dtlList.get(i);
    			//LOGGER.debug("createSql setDtlMap : " + setDtlMap);
    			
    			String colType = (String) setDtlMap.get("colType"); //필드 유형
    			if("DATE".equals(colType) || "CLOB".equals(colType)) {
    				tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + " ";
    			}else if("NUMBER".equals(colType)) {
    				String colPre = (String)setDtlMap.get("colPre");
    				if(!"".equals(colPre) && colPre != null) {
    					tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + "(" + setDtlMap.get("colLen") + ", " + colPre + ")";	
    				}else {
    					tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + "(" + setDtlMap.get("colLen") + ")";
    				}
    				
    			}else {
    				tblSql += setDtlMap.get("colNm") + " " + setDtlMap.get("colType") + "(" + setDtlMap.get("colLen") + " BYTE) ";
    			}
    			
    			//pk 추출
    			if("1".equals(setDtlMap.get("pkAt"))) {
    				if("".equals(pkcolNm)) pkcolNm = (String)setDtlMap.get("colNm") ; //PK 필드 추출
    				else pkcolNm += ", "+(String)setDtlMap.get("colNm") ; //PK 필드 추출
    			}
    			
    			if(setDtlMap.get("defVal") != null) tblSql += " DEFAULT '" + setDtlMap.get("defVal") + "'"; //기본값
    			
    			if("1".equals(setDtlMap.get("nullAt"))) tblSql += " NOT NULL"; //NOT NULL 여부
    			
    			comtSql += "COMMENT ON COLUMN " + masMap.get("tblNm") + "." + setDtlMap.get("colNm") + " IS '" + setDtlMap.get("colCmt") + "' "; //코멘트
    			
    			if(i != dtlList.size()-1) {
    				tblSql += ","; //마지막이 아닐경우 콤마 추가
    				comtSql += "; "; //마지막이 아닐경우 콤마 추가
    			}
    		}
    		
    		tblSql += ")";
    		LOGGER.debug("createSql pkcolNm : " + pkcolNm);
    		if(!"".equals(pkcolNm)) {
    			unqSql = "CREATE UNIQUE INDEX IDX_" + masMap.get("tblNm") + "_PK ON " + masMap.get("tblNm") + "(" + pkcolNm + ")"; //신규테이블 INDEX 생성
            	pkSql = "ALTER TABLE " + masMap.get("tblNm") + " ADD ( CONSTRAINT IDX_" + masMap.get("tblNm") +"_PK PRIMARY KEY ( " + pkcolNm + ") USING INDEX IDX_" + masMap.get("tblNm") + "_PK ENABLE VALIDATE)"; //신규테이블 PK 생성
    		}
    		
    		//백업 필드 for문
    		String befCols = "";
    		for(int i = 0; i < befDtlList.size(); i++) {
    			HashMap<String, Object>setBefMap = (HashMap<String, Object>) befDtlList.get(i);
    			
    			if("".equals(befCols)) befCols = (String)setBefMap.get("columnName");
    			else befCols += "," +(String)setBefMap.get("columnName");
    			
    		}
    		insertSql = "INSERT INTO " + masMap.get("tblNm") + " ( " + befCols + " ) ( SELECT " + befCols + " FROM " + masMap.get("tblNm") + "_BAK )";
    		
    	}else {
    		setMap.put("resultCode", 9);
    	} 
    	
    	setMap.put("dropBakPkSql", dropBakPkSql);
    	setMap.put("dropBakTblSql", dropBakTblSql);
    	
    	setMap.put("copyTblSql", copyTblSql);
    	
    	setMap.put("dropPkSql", dropPkSql);
    	setMap.put("dropTblSql", dropTblSql);
    	
    	setMap.put("tblSql", tblSql);
    	setMap.put("unqSql", unqSql);
    	setMap.put("pkSql", pkSql);
    	
    	setMap.put("comtSql", comtSql);
    	
    	setMap.put("insertSql", insertSql);
    	
    	return setMap;
    	
    }
	
    
    
    /**
	 * 데이터베이스 데이터 export
	 */
	@RequestMapping(value="/dev/exportTblExcel.do")
	public void exportTblExcel(HttpServletResponse response, HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
		
		LOGGER.debug("exportTblExcel START : " + paramMap);		
		try{
			
			String excelNm = paramMap.get("tblNm")+"_export_";
			
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
			CellStyle styleBy = workbook.createCellStyle(); // 바디 스타일
			
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
			
			styleHd.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
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
			
			styleBy.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleBy.setBorderTop(CellStyle.BORDER_THIN);
			styleBy.setBorderBottom(CellStyle.BORDER_THIN);
			styleBy.setBorderRight(CellStyle.BORDER_THIN);
			styleBy.setBorderLeft(CellStyle.BORDER_THIN);
			styleBy.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleBy.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleBy.setRightBorderColor(IndexedColors.BLACK.getIndex());
			styleBy.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			
			//초기세팅 끝
			
			//시트 생성
	        objSheet = (SXSSFSheet) workbook.createSheet(excelNm);
	        
			int rowNum = 0;
			int cellNum = 0;
			//String[] sHeads = null;
			//헤더 생성
			List<Object> colsList = devDbService.selectBefTblColList(paramMap);
			objRow = (SXSSFRow) objSheet.createRow(rowNum++);
    		
			for (int x = 0; x < colsList.size(); x++) {
				HashMap<String, Object>getMap = (HashMap<String, Object>)colsList.get(x);
				
	        	objSheet.setColumnWidth(x, 3000);
	    		objCell = (SXSSFCell) objRow.createCell(x);
	    		objCell.setCellValue((String)getMap.get("columnName"));
	    		objCell.setCellStyle(styleHd);
	        }
			
			//데이터
			List<HashMap<String, Object>> excelList = devDbService.selectTblList(paramMap);
			for (Map<String, Object> data : excelList) {
	        	cellNum = 0;
	            //row 생성
	        	objRow = (SXSSFRow) objSheet.createRow(rowNum++);
	        	for( String key : data.keySet() ){
	        		objCell = (SXSSFCell) objRow.createCell(cellNum);
	        		objCell.setCellValue((String)data.get(key));
	            	objCell.setCellStyle(styleBy);
	            	
	            	cellNum++;
	            }
	        }
	        
			// 컨텐츠 타입과 파일명 지정
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", URLEncoder.encode(excelNm, "UTF-8")+"_"+DateUtil.getToday()));
	        
	        workbook.write(response.getOutputStream());
	        response.getOutputStream().flush();
	        
		}catch(Exception ex) {
			LOGGER.error("exportTblExcel Exception :: " + ex.getMessage() );	
		}
			
		LOGGER.debug("exportTblExcel END :: " );
	}
	
	
	/** 데이터베이스 데이터 import   */
	@RequestMapping(value="/dev/importTblExcel.do", method=RequestMethod.POST)
	public ModelAndView importTblExcel(HttpServletRequest request) throws Exception {
		JsonResult result = new JsonResult();
			
	   	LOGGER.debug("importTblExcel START" );
   	 
	   	try{
	   		HashMap<String, Object> setMap = new HashMap<String, Object>();
	   		
	   		HashMap<String, Object> getFileMap = FileUploadUtil.uploadExcelFiles(request, "dev");
	   		setMap.put("tblNm", (String)request.getParameter("tblNm"));
	   		
	   		List<Object> dataList = new ArrayList<>();
	    	List<Object> colsList = new ArrayList<>();
	    	
	    	List<Object> colList = devDbService.selectBefTblColList(setMap); //컬럼 목록
	    	
	    	//파일 읽기
			FileInputStream file = new FileInputStream((String)getFileMap.get("filePath")+(String)getFileMap.get("fileSaveNm"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			//시트 첫번째
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows(); //행수
			//LOGGER.debug("GradResultExcelUpload START rows : " + rows);
			int init = 0;
			for(int i = 1; i < rows; i++){
				//행읽기
	            XSSFRow row = sheet.getRow(i);
	            //LOGGER.debug("GradResultExcelUpload START row : " + row);
	            HashMap<String, Object>getMap = new HashMap<String, Object>(); 
	            if(row != null){
	            	//int cells = row.getPhysicalNumberOfCells();
	            	//LOGGER.debug("GradResultExcelUpload START cells cnt: " + cells);
	            	for(int x = 0; x < colList.size(); x++){
	            		//셀 읽기
	                    XSSFCell cell = row.getCell(x);
	                    if(cell != null) {
	                    	cell.setCellType(cell.CELL_TYPE_STRING);
	                        getMap.put("col" + (x+1), cell.getStringCellValue());
	                    }else {
	                    	getMap.put("col" + (x+1), null);
	                    }
	                    
	                    if(init == 0) {
	                    	colsList.add("col" + (x+1));
	                    }
	            	}
	            	dataList.add(getMap);
	            	
	            }
	            init++;
	            
	            
	            setMap.put("dataList", dataList);
	            setMap.put("colsList", colsList);
	    		LOGGER.debug("GradResultUploadProc i : " + i );
	            try {
	            	//등록처리 
		            devDbService.importTblProc(setMap); 
		            	
	            }catch(Exception e){
	            	LOGGER.debug("importTblExcel importTblProc  i = " + i + " Exception : "+ e.getMessage());
	            	
	            }
	            
	            dataList = new ArrayList<>(); //초기화
			}
			
	   		
	   		//devDbService.importTblProc(getFileMap); 
	   		result.add("resultCode", 0);
	   		
	   	}catch(Exception e){
	   		result.add("resultCode", -1);
	   		LOGGER.debug("importTblExcel Exception : " + e.getMessage());
	   	}
   	
		LOGGER.debug("importTblExcel END");
		
		return new ModelAndView("jsonView", result);
   }
	
}