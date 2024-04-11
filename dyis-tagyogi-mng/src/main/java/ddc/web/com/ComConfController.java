package ddc.web.com;


import java.net.URLDecoder;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.com.service.ComConfService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/** 
 * comConf Controller 클래스
 * @author 시스템 개발팀 최진호
 * @since 2023-06-13
 * @version 1.0
 * @see 민원발급
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-13  최진호            최초 생성
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
	
	/**
	 * 민원발급 현황 조회 
	 */
	@RequestMapping(value="/selectComConfList.do") 
	public ModelAndView selectComConfList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectComConfList START :: " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			
			String confCd = (String)paramMap.get("sConfCd");
			if("IP".equals(confCd) || "IK".equals(confCd) || "DP".equals(confCd)) {
				result.add("data", comConfService.selectComConfInvtList(paramMap));
			}else if("LP".equals(confCd) || "LR".equals(confCd) || "LI".equals(confCd) || "FL".equals(confCd)){
				result.add("data", comConfService.selectComConfList(paramMap));
			}else if("GR".equals(confCd)) {
				result.add("data", comConfService.selectComConfGuarList(paramMap));
			}
		} catch (Exception e) {
			LOGGER.debug("selectComConfList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectComConfList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 민원발급 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertComConf.do") 
	public ModelAndView insertComConf(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertComConf START");
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			
			comConfService.insertComConf(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.debug("selectComConfList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 민원발급 수정 처리 (단건)
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
			LOGGER.debug("selectComConfList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 민원발급 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteComConf.do") 
	public ModelAndView deleteComConf(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteComConf START");
		JsonResult result = new JsonResult();
		
		try {
			comConfService.deleteComConf(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.debug("selectComConfList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteComConf END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 증명서발급완료처리
	 */
	@RequestMapping(value="/updateConfProcStat.do")
	public ModelAndView updateConfProcStat(HttpServletRequest request, @RequestParam(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateConfProcStat START");
		JsonResult result = new JsonResult();

		try {
			
			String ozResult = request.getParameter("oz_args2");
			LOGGER.debug("updateConfProcStat paramMap1 : " + ozResult);
			if("0".equals(ozResult)) {
				Cookie[] cookies = request.getCookies();
				LOGGER.debug("updateConfProcStat cookies :: " + cookies);
					if(cookies!=null){   
						for(int i=0; i<cookies.length; i++){  
						LOGGER.debug("updateConfProcStat cookies : " + cookies[i].getName() + " / " + cookies[i].getValue());
							//if("req_nm".equals(cookies[i].getName()) || "purpose".equals(cookies[i].getName()) ) {
								//paramMap.put(cookies[i].getName(), URLDecoder.decode(cookies[i].getValue(), "UTF-8") );
							//}else {
								paramMap.put(cookies[i].getName(), cookies[i].getValue());
							//}
	            		}
	            	}
					
					paramMap.put("procStat", "40");
	            	LOGGER.debug("updateConfProcStat paramMap : " + paramMap);
	            	
	            	//증명서발급완료처리
	            	comConfService.updateConfProcStat(paramMap);
	        	}

			//업무로그처리
			//logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.debug("updateConfProcStat BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateConfProcStat END :: ");

		return new ModelAndView("jsonView", result);
	}
}
