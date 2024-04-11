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
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.sys.service.KakaoService;
import javax.servlet.http.HttpServletRequest;

/** 
 * kakao Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-07
 * @version 1.0
 * @see 카카오채널관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-07  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/kakao")
@Controller("kakaoController")
public class KakaoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KakaoController.class); 
	
	/** kakaoService */
	@Autowired
	protected KakaoService kakaoService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 카카오채널전송 현황 조회 
	 */
	@RequestMapping(value="/selectKakaoSndList.do") 
	public ModelAndView selectKakaoSndList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectKakaoSndList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", kakaoService.selectKakaoSndList(paramMap));
			
			result.add("titlList", kakaoService.selectKakaoTitlList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectKakaoSndList Exception :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectKakaoSndList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 카카오채널관리 현황 조회 
	 */
	@RequestMapping(value="/selectKakaoList.do") 
	public ModelAndView selectKakaoList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectKakaoList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", kakaoService.selectKakaoList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectKakaoList Exception :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectKakaoList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 카카오채널관리 등록 처리
	 */
	@RequestMapping(value="/insertKakao.do") 
	public ModelAndView insertKakao(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertKakao START paramMap " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			kakaoService.insertKakao(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertKakao Exception :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertKakao END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 카카오채널관리 수정 처리
	 */
	@RequestMapping(value="/updateKakao.do") 
	public ModelAndView updateKakao(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateKakao START paramMap " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			kakaoService.updateKakao(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateKakao Exception :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateKakao END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 카카오채널관리 삭제 처리 
	 */
	@RequestMapping(value="/deleteKakao.do") 
	public ModelAndView deleteKakao(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteKakao START");
		JsonResult result = new JsonResult();
		
		try {
			kakaoService.deleteKakao(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteKakao Exception :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteKakao END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 카카오채널 전송 처리
	 * 
	 * 조합원 번호, 전송 msg_seq 정보 필요
	 * 대상 구분 필요(협의) - 대표자 또는 담당자 
	 * membNo
	 * refNo
	 * msgSeq
	 * 
	 */
	@RequestMapping(value="/sendKakao.do") 
	public ModelAndView sendKakao(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("sendKakao START paramMap :" + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			HashMap resultMap = kakaoService.sendKakao(paramMap);
			
			//logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("sendKakao Exception :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("sendKakao END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	
}
