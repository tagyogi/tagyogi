package ddc.web.sys;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.sys.service.GridService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * grid Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 현황화면그리드옵션관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/grid")
@Controller("gridController")
public class GridController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GridController.class); 
	
	/** gridService */
	@Autowired
	protected GridService gridService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * grid 현황 조회 
	 */
	@RequestMapping(value="/selectGridList.do") 
	public ModelAndView selectGridList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectGridList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", gridService.selectGridList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectGridDtlList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectGridList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * grid 상세 현황 조회 
	 */
	@RequestMapping(value="/selectGridDtlList.do") 
	public ModelAndView selectGridDtlList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectGridDtlList START paramMap " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", gridService.selectGridDtlList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectGridDtlList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectGridDtlList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * grid 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveGrid.do") 
	public ModelAndView saveGrid(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveGrid START");
		JsonResult result = new JsonResult();
		
		try {
			gridService.saveGrid(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveGrid END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * grid 열 저장 처리 
	 */
	@RequestMapping(value="/saveGridCols.do") 
	public ModelAndView saveGridCols(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveGridCols START");
		JsonResult result = new JsonResult();
		
		try {
			
			gridService.saveGridCols(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveGridCols END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * grid 열 초기 생성
	 */
	@RequestMapping(value="/createGridCols.do") 
	public ModelAndView createGridCols(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("createGridCols START : " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			
			gridService.createGridCols(paramMap);
			
		} catch (Exception e) {
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("createGridCols END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
