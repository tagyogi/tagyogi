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
import ddc.biz.sys.service.ConfGuarRateService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * confGuarRate Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 보증상품요율관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/confGuarRate")
@Controller("confGuarRateController")
public class ConfGuarRateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfGuarRateController.class);

	/** confGuarRateService */
	@Autowired
	protected ConfGuarRateService confGuarRateService;

	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	/**
	 * confGuarRate 현황 조회
	 */
	@RequestMapping(value="/selectConfGuarRateList.do")
	public ModelAndView selectConfGuarRateList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfGuarRateList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", confGuarRateService.selectConfGuarRateList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectConfGuarRateList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfGuarRateList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * confGuarRate 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfGuarRate.do")
	public ModelAndView saveConfGuarRate(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfGuarRate START");
		JsonResult result = new JsonResult();

		try {
			confGuarRateService.saveConfGuarRate(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfGuarRateList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfGuarRate END :: ");

		return new ModelAndView("jsonView", result);
	}



}
