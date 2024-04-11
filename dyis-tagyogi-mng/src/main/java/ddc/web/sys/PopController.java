package ddc.web.sys;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.sys.service.PopService;
import javax.servlet.http.HttpServletRequest;

/** 
 * pop Controller 클래스
 * @author 시스템 개발팀 윤가영1
 * @since 2023-11-01
 * @version 1.0
 * @see 팝업
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-11-01  윤가영1            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/pop")
@Controller("popController")
public class PopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PopController.class); 
	
	/** popService */
	@Autowired
	protected PopService popService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/** FileService */
	@Autowired
	protected FileService fileService;
	
	/**
	 * 팝업 현황 조회 
	 */
	@RequestMapping(value="/selectPopList.do") 
	public ModelAndView selectPopList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectPopList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", popService.selectPopList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectPopList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectPopList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 팝업 상세 조회 
	 */
	@RequestMapping(value="/selectPop.do") 
	public ModelAndView selectPop(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectPop START");
		JsonResult result = new JsonResult();
		
		try {
			
			//상세
			HashMap getMap = popService.selectPop(paramMap);
			result.add("data", getMap);
			
			//첨부파일
			paramMap.put("atthNo", getMap.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectPop BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectPop END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 팝업 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertPop.do") 
	public ModelAndView insertPop(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertPop START" + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userNm", SessionUtil.getSess("userNm")); //세션
			
			//첨부파일 없어도 첨부파일번호 추출
			fileService.selectMaxFileNo(paramMap);
			
			popService.insertPop(paramMap);
			
			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("seq"));
			
			//파일처리
			if(fileList != null) {
				try{
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}

			result.add("paramMap", paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertPop BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertPop END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 팝업 수정 처리 (단건)
	 */
	@RequestMapping(value="/updatePop.do") 
	public ModelAndView updatePop(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updatePop START");
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			popService.updatePop(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updatePop BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updatePop END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
