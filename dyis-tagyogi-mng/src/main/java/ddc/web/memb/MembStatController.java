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
import ddc.biz.memb.service.MembStatService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membStat Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-17
 * @version 1.0
 * @see 조합원현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-17  김상민1            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membStat")
@Controller("membStatController")
public class MembStatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembStatController.class);

	/** membStatService */
	@Autowired
	protected MembStatService membStatService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 조합원현황 장르별 구분 조회
	 */
	@RequestMapping(value="/selectMembStatDesc.do")
	public ModelAndView selectMembStatDesc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembStatDesc START");
		JsonResult result = new JsonResult();

		try {
			//조합원 장르별 현황
			result.add("data", membStatService.selectMembStatDesc(paramMap));

			//콘텐츠공제조합 출자좌수 조회(조합원현황)
			result.add("kocfc", membStatService.selectKocfcInvtNum(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembStatDesc BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembStatDesc END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원현황 현황 조회
	 */
	@RequestMapping(value="/selectMembStatList.do")
	public ModelAndView selectMembStatList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembStatList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membStatService.selectMembStatList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembStatList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembStatList END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	

}
