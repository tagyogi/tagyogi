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
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membLabel Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 라벨지출력
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
@RequestMapping("/membLabel")
@Controller("membLabelController")
public class MembLabelController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembLabelController.class);


	/** membService */
	@Autowired
	protected MembService membService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/**
	 * 라벨지출력 현황 조회
	 */
	@RequestMapping(value="/selectMembLabelList.do")
	public ModelAndView selectMembLabelList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembLabelList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membService.selectMembList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembLabelList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembLabelList END :: ");

		return new ModelAndView("jsonView", result);
	}

}
