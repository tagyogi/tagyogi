package ddc.web.com;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.common.CommonUtil;
import ddc.core.common.PageUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.sys.service.LogProcService; 
import ddc.biz.com.service.ComConfService;
import ddc.biz.guar.service.GuarListService;
import ddc.biz.guar.service.GuarService;
import ddc.biz.lend.service.LendService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * comConf Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-08
 * @version 1.0
 * @see 민원신청관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-08  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/comConf")
@Controller("comConfController")
public class ComConfController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComConfController.class); 
	
	/** comConfService */
	@Autowired
	protected ComConfService comConfService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	@Autowired(required = false)
	private CommonUtil commonUtil;
	
	/** lendService */
	@Autowired
	protected LendService lendService;
	
	/** guarListService */
	@Autowired
	protected GuarListService guarListService;
	
	
	/**
	 * 민원신청관리 현황 조회 
	 */
	@RequestMapping(value="/selectComConfList.do") 
	public ModelAndView selectComConfList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectComConfList START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("sMembNo", SessionUtil.getSess("membNo"));
			
			result.add("data", comConfService.selectComConfList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectComConfList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("selectComConfList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 민원신청관리 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertComConf.do") 
	public ModelAndView insertComConf(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertComConf START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("membNo", SessionUtil.getSess("membNo"));
			paramMap.put("userNm", SessionUtil.getSess("membNo"));
			
			//처리상태: 신청(10)
			paramMap.put("procStat", 10);
			
			//등록
			comConfService.insertComConf(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
			
			//페이지 새로고침(메뉴코드)
			result.add("menuCd", paramMap.get("menuCd"));
			
			LOGGER.debug("insertHomeComConf result :: " + result);
		} catch (Exception e) {
			LOGGER.error("insertComConf BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("insertComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 민원신청관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateComConf.do") 
	public ModelAndView updateComConf(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateComConf START");
		JsonResult result = new JsonResult();
		
		try {
			comConfService.updateComConf(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateComConf BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updateComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/** ======================================================================*/
	/**
	 * 홈페이지 민원신청관리 현황 조회 (페이징)
	 */
	@RequestMapping(value="/selectHomeComConfList.do") 
	public ModelAndView selectHomeComConfList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectHomeComConfList START" + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("sMembNo", SessionUtil.getSess("membNo"));
			
			LOGGER.debug("selectHomeComConfList SessionUtil! :: " + SessionUtil.getSess("membNo"));
			
			//총 페이지 수
			int totalPage = comConfService.totalPage(paramMap);
			int curPage = Integer.parseInt(commonUtil.nvl(paramMap.get("curPage"), "0"));
			
			//페이징 객체
			PageUtil pageUtil = new PageUtil();
			pageUtil.PageUtil(totalPage, curPage);
			paramMap.put("pageUtil", pageUtil);
			
			//현황 조회
			result.add("pageInfo", pageUtil);
			result.add("data", comConfService.selectHomeComConfList(paramMap));
			
			LOGGER.debug("selectHomeComConfList data! :: " + comConfService.selectHomeComConfList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectHomeComConfList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("selectHomeComConfList END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 홈페이지 민원신청관리 발급날짜 저장 (단건)
	 */
	@RequestMapping(value="/updateComConfPubDt.do") 
	public ModelAndView updateComConfPubDt(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateComConfPubDt START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("membNo", SessionUtil.getSess("membNo"));
			paramMap.put("userNm", SessionUtil.getSess("membNo"));
			
			//발급날짜수정
			comConfService.updateComConfPubDt(paramMap);
			
			result.add("paramMap", paramMap.get("val"));
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
			
			//페이지 새로고침(메뉴코드)
			//result.add("menuCd", paramMap.get("menuCd"));
			
			LOGGER.debug("updateComConfPubDt result :: " + result);
		} catch (Exception e) {
			LOGGER.error("updateComConfPubDt BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updateComConfPubDt END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 홈페이지 민원신청관리 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteHomeComConf.do") 
	public ModelAndView deleteHomeComConf(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteHomeComConf START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("membNo", SessionUtil.getSess("membNo"));
			paramMap.put("userNm", SessionUtil.getSess("membNo"));
			
			//삭제
			comConfService.deleteHomeComConf(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
			
			//페이지 새로고침(메뉴코드)
			result.add("menuCd", paramMap.get("menuCd"));
			
			LOGGER.debug("deleteHomeComConf result :: " + result);
		} catch (Exception e) {
			LOGGER.error("deleteHomeComConf BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("deleteHomeComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	 /**
     * 홈페이지 대출정보 팝업
     */
	@RequestMapping(value="/selectPopLendList.do") 
	public ModelAndView selectPopLendList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectPopLendList START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("sMembNo", SessionUtil.getSess("membNo"));
			
			//대출현황조회
			result.add("data", lendService.selectLendList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectPopLendList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("selectPopLendList END :: ");

		return new ModelAndView("jsonView", result);
	} 
	
	/**
     * 민원발급완료(Ajax)진행중
     */
	@RequestMapping(value="/confIssueAfter.do") 
	public ModelAndView confIssueAfter(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("confIssueAfter START");
		JsonResult result = new JsonResult();
		
		try {
			//세션
			paramMap.put("membNo", SessionUtil.getSess("membNo"));
			paramMap.put("userNm", SessionUtil.getSess("membNo"));
			
			paramMap.put("procStat", 40);

			String ozResult = request.getParameter("oz_args2");
			
			LOGGER.debug("confIssueAfter ozResult!!!!!! :: " + ozResult);
			
			//삭제
			//int rtn = comConfService.deleteHomeComConf(paramMap);
			
			//if(rtn == 0) result.setResult(0, "정상 삭제 처리 되었습니다.");
			//else result.setResult(9, "처리중 오류가 발생했습니다.");
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
			
			//페이지 새로고침(메뉴코드)
			//result.add("menuCd", paramMap.get("menuCd"));
			
			LOGGER.debug("confIssueAfter result :: " + result);
		} catch (Exception e) {
			LOGGER.error("confIssueAfter BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("confIssueAfter END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 기타지원업무(보증증권진위확인)
	 */
	@RequestMapping(value="/selectGuarConfirm.do")
	public ModelAndView selectGuarConfirm(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectGuarConfirm START");
		JsonResult result = new JsonResult();
		
		try {
			result.put("guar", guarListService.selectGuarIssuList(paramMap));  //보증 데이터 확인
			
		} catch (Exception e) {
			LOGGER.error("guarConfirm BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}
		
		LOGGER.debug("guarConfirm END :: ");
		
		return new ModelAndView("jsonView", result);
	}
}
