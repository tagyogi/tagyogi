package ddc.web.com;


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
import ddc.biz.com.service.ContrMortService;
import ddc.biz.guar.service.GuarContrService;
import ddc.biz.guar.service.GuarMortService;
import ddc.biz.lend.service.LendContrService;
import ddc.biz.lend.service.LendMortService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * 약정담보 Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-22
 * @version 1.0
 * @see 약정담보정보
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-22  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/contrMort")
@Controller("contrMortController")
public class ContrMortController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContrMortController.class); 
	
	/** contrMortService */
	@Autowired
	protected ContrMortService contrMortService;
	
	/** MembService */
	@Autowired
	protected MembService membService;
	
	/** GuarContrService */
	@Autowired
	protected GuarContrService guarContrService;
	
	/** GuarMortService */
	@Autowired
	protected GuarMortService guarMortService;
	
	/** LendContrService */
	@Autowired
	protected LendContrService lendContrService;
	

	/** LendMortService */
	@Autowired
	protected LendMortService lendMortService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	
	/**
	 * 약정담보정보 현황 조회 
	 */
	@RequestMapping(value="/selectContrMortList.do") 
	public ModelAndView selectContrMortList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectContrMortList START " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			
			result.add("data", contrMortService.selectContrMortList(paramMap));
		} catch (Exception e) {
			LOGGER.error("selectContrMortList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectContrMortList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 약정담보정보 상세 조회
	 */
	@RequestMapping(value="/selectContrMort.do") 
	public ModelAndView selectContrMort(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectContrMort START" + paramMap);
		JsonResult result = new JsonResult();
		
		try {

			result.add("memb", membService.selectMemb(paramMap)); //조합원정보
			if("GR".equals(paramMap.get("mortType"))) {
				result.add("contr", guarContrService.selectGuarContr(paramMap));
				result.add("mort", guarMortService.selectGuarMort(paramMap));
				
			}else if("LD".equals(paramMap.get("mortType"))) {
				result.add("contr", lendContrService.selectLendContr(paramMap));
				result.add("mort", lendMortService.selectLendMort(paramMap));
			}
			
		} catch (Exception e) {
			LOGGER.error("selectContrMort BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectContrMort END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 담보 삭제 (단건)
	 */
	@RequestMapping(value="/deleteMort.do") 
	public ModelAndView deleteMort(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMort START");
		JsonResult result = new JsonResult();
		
		try {
			
			if("GR".equals(paramMap.get("mortType"))) {
				guarMortService.deleteGuarMort(paramMap);
			}else if("LD".equals(paramMap.get("mortType"))) {
				lendMortService.deleteLendMort(paramMap);
			}
			
		} catch (Exception e) {
			LOGGER.error("deleteMort BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMort END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
