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
import ddc.biz.sys.service.KocfcService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * kocfc Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-29
 * @version 1.0
 * @see 조합관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-29  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/kocfc")
@Controller("kocfcController")
public class KocfcController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KocfcController.class); 
	
	/** kocfcService */
	@Autowired
	protected KocfcService kocfcService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * kocfc 현황 조회 
	 */
	@RequestMapping(value="/selectKocfcList.do") 
	public ModelAndView selectKocfcList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectKocfcList START");
		JsonResult result = new JsonResult();
		
		try {
			
			result.add("data", kocfcService.selectKocfcList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectKocfcList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectKocfcList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * kocfc 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveKocfc.do") 
	public ModelAndView saveKocfc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveKocfc START");
		JsonResult result = new JsonResult();
		 
		try {
			kocfcService.saveKocfc(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectKocfcList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveKocfc END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
