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

import ddc.core.common.FileUploadUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.sys.service.LogProcService;
import ddc.biz.sys.service.ReportService;
import javax.servlet.http.HttpServletRequest;

/** 
 * report Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-18
 * @version 1.0
 * @see 레포트관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-18  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/report")
@Controller("reportController")
public class ReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class); 
	
	/** reportService */
	@Autowired
	protected ReportService reportService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 레포트관리 현황 조회 
	 */
	@RequestMapping(value="/selectReportList.do") 
	public ModelAndView selectReportList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectReportList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", reportService.selectReportList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectReportList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectReportList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 레포트관리 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertReport.do") 
	public ModelAndView insertReport(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertReport START");
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			//레포트 파일 업로드
			paramMap = FileUploadUtil.uploadRptFiles(fileList, paramMap);
			
			reportService.insertReport(paramMap, fileList);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectReportList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			
			FileUploadUtil.uploadFileRptDel(paramMap); //파일처리 롤백
			
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertReport END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 레포트관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateReport.do") 
	public ModelAndView updateReport(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateReport START");
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			//레포트 파일 업로드
			paramMap = FileUploadUtil.uploadRptFiles(fileList, paramMap);
			
			reportService.updateReport(paramMap, fileList);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectReportList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			FileUploadUtil.uploadFileRptDel(paramMap); //파일처리 롤백
			
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateReport END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 레포트관리 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteReport.do") 
	public ModelAndView deleteReport(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteReport START");
		JsonResult result = new JsonResult();
		
		try {
			reportService.deleteReport(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectReportList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteReport END :: ");

		return new ModelAndView("jsonView", result);
	}
}
