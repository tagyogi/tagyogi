package ddc.web.sys;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.sys.service.BrdMasService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * brdMas Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 통합게시판관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/brdMas")
@Controller("brdMasController")
public class BrdMasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrdMasController.class); 
	
	/** brdMasService */
	@Autowired
	protected BrdMasService brdMasService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * brdMas 현황 조회 
	 */
	@RequestMapping(value="/selectBrdMasList.do") 
	public ModelAndView selectBrdMasList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectBrdMasList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", brdMasService.selectBrdMasList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectBrdMasList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBrdMasList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * brdMas 상세 조회 
	 */
	@RequestMapping(value="/selectBrdMas.do") 
	public ModelAndView selectBrdMas(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectBrdMas START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("brdMas", brdMasService.selectBrdMas(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectBrdMas BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBrdMas END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * brdMas 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveBrdMas.do") 
	public ModelAndView saveBrdMas(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveBrdMas START");
		JsonResult result = new JsonResult();
		 
		try {
			brdMasService.saveBrdMas(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectBrdMasList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveBrdMas END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
