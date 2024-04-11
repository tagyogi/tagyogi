package ddc.web;


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

import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.MenuService;
import ddc.core.common.FileUploadUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import javax.servlet.http.HttpServletRequest;

/** 
 * sample Controller 클래스
 * @author 시스템 개발팀 DEV
 * @since 1900.01.01
 * @version 1.0
 * @see jobContent
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  1900.01.01  DEV            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/sample")
@Controller("sampleController")
public class SampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class); 
	
	@Autowired
	private FileService fileService;
	
	/**
	 * sample 저장 처리 (리스트형) 멀티 첨부파일 형식
	 */
	@RequestMapping(value="/saveSample.do") 
	public ModelAndView saveSample(@RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("saveSample START paramMap " + paramMap);
		LOGGER.debug("saveSample START fileList " + fileList);
		JsonResult result = new JsonResult();
		 
		try {
			
			//첨부파일 키 발번
			int fileAtthNo = 0;
			
			//신청 등록
			//int rtn = sampleService.saveSample(paramMap);
			
			//첨부파일 등록
			//fileService.fileUpload(fileList, "test", fileAtthNo);
			
			//if(rtn == 0) result.setResult(0, "정상 처리 되었습니다.");
			//else result.setResult(9, "처리중 오류가 발생했습니다.");
			
		} catch (Exception e) {
			LOGGER.error("saveSample BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("saveSample END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
}