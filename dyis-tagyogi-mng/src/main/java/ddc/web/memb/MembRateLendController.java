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
import ddc.biz.memb.service.MembRateLendService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membRateLend Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원별융자이율
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
@RequestMapping("/membRateLend")
@Controller("membRateLendController")
public class MembRateLendController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembRateLendController.class);

	/** membRateLendService */
	@Autowired
	protected MembRateLendService membRateLendService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** membService */
	@Autowired
	protected MembService membService;

	/**
	 * 조합원별융자이율 현황 조회
	 */
	@RequestMapping(value="/selectMembRateLendList.do")
	public ModelAndView selectMembRateLendList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembRateLendList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membRateLendService.selectMembRateLendList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembRateLendList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembRateLendList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원별융자이율 상세 조회
	 */
	@RequestMapping(value="/selectMembRateLend.do")
	public ModelAndView selectMembRateLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembRateLend START");
		JsonResult result = new JsonResult();

		try {
			//조합원정보
			result.add("memb", membService.selectMemb(paramMap));

			//조합원융자이율 정보
			result.add("data", membRateLendService.selectMembRateLend(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembRateLendList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembRateLend END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원별융자이율 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembRateLend.do")
	public ModelAndView insertMembRateLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertMembRateLend START");
		JsonResult result = new JsonResult();

		try {
			membRateLendService.insertMembRateLend(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembRateLendList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembRateLend END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원별융자이율 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembRateLend.do")
	public ModelAndView updateMembRateLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMembRateLend START");
		JsonResult result = new JsonResult();

		try {
			membRateLendService.updateMembRateLend(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembRateLendList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembRateLend END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원별융자이율 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembRateLend.do")
	public ModelAndView deleteMembRateLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembRateLend START");
		JsonResult result = new JsonResult();

		try {
			membRateLendService.deleteMembRateLend(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembRateLendList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembRateLend END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 신용도 별 이율 조회(임시)
	 */
	@RequestMapping(value="/selectLendRate.do")
	public ModelAndView selectLendRate(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectLendRate START" + paramMap);
		JsonResult result = new JsonResult();

		try {
			result.add("data", membRateLendService.selectLendRate(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectLendRate BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectLendRate END :: ");

		return new ModelAndView("jsonView", result);
	}
}
