package ddc.web.memb;


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
import ddc.biz.memb.service.MembRateGuarService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membRateGuar Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원보증요율
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membRateGuar")
@Controller("membRateGuarController")
public class MembRateGuarController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembRateGuarController.class);

	/** membRateGuarService */
	@Autowired
	protected MembRateGuarService membRateGuarService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** membService */
	@Autowired
	protected MembService membService;

	/**
	 * 조합원보증요율 현황 조회
	 */
	@RequestMapping(value="/selectMembRateGuarList.do")
	public ModelAndView selectMembRateGuarList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembRateGuarList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membRateGuarService.selectMembRateGuarList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembRateGuarList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembRateGuarList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원보증요율 상세 조회
	 */
	@RequestMapping(value="/selectMembRateGuar.do")
	public ModelAndView selectMembRateGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembRateGuar START");
		JsonResult result = new JsonResult();

		try {
			//조합원정보
			result.add("memb", membService.selectMemb(paramMap));

			//조합원보증요율 정보
			result.add("data", membRateGuarService.selectMembRateGuar(paramMap));

			//조합원보증요율 이력 정보
			result.add("rateHisList", membRateGuarService.selectMembRateGuarList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembRateGuar BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembRateGuar END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원보증요율 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembRateGuar.do")
	public ModelAndView insertMembRateGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertMembRateGuar START");
		JsonResult result = new JsonResult();

		try {
			membRateGuarService.insertMembRateGuar(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertMembRateGuar BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembRateGuar END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원보증요율 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembRateGuar.do")
	public ModelAndView updateMembRateGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMembRateGuar START");
		JsonResult result = new JsonResult();

		try {
			membRateGuarService.updateMembRateGuar(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateMembRateGuar BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembRateGuar END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원보증요율 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembRateGuar.do")
	public ModelAndView deleteMembRateGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembRateGuar START");
		JsonResult result = new JsonResult();

		try {
			membRateGuarService.deleteMembRateGuar(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteMembRateGuar BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembRateGuar END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 신용도 별 요율 조회
	 */
	@RequestMapping(value="/selectGuarRate.do")
	public ModelAndView selectGuarRate(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectGuarRate START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membRateGuarService.selectGuarRate(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectGuarRate BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectGuarRate END :: ");

		return new ModelAndView("jsonView", result);
	}


}
