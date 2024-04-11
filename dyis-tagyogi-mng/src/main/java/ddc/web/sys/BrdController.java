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
import ddc.core.common.PageUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.BrdMasService;
import ddc.biz.sys.service.BrdService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * brd Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 게시판관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/brd")
@Controller("brdController")
public class BrdController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrdController.class); 
	
	/** brdService */
	@Autowired
	protected BrdService brdService;
	
	/** brdMasService */
	@Autowired
	protected BrdMasService brdMasService;
	
	/** fileService 파일첨부 */
	@Autowired
	private FileService fileService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * brd 현황 조회 
	 */
	@RequestMapping(value="/selectBrdList.do") 
	public ModelAndView selectBrdList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectBrdList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", brdService.selectBrdList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectBrdList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBrdList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * brd 상세 조회 
	 */
	@RequestMapping(value="/selectBrd.do") 
	public ModelAndView selectBrd(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectBrd START");
		JsonResult result = new JsonResult();
		
		try {
			//상세
			HashMap getMap = brdService.selectBrd(paramMap);
			result.add("data", getMap);
			
			int brdNo = Integer.parseInt((String) paramMap.get("brdNo"));
			if(brdNo == 6) {//질문과답변
				result.add("answer", brdService.selectBrdAnswer(paramMap));
			}
			
			//첨부파일
			paramMap.put("atthNo", getMap.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));
			
			//게시판정보
			result.add("brdMas", brdMasService.selectBrdMas(paramMap));
			
			
		} catch (Exception e) {
			LOGGER.error("selectBrdList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBrd END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * brd 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertBrd.do") 
	public ModelAndView insertBrd(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertBrd START paramMap " + paramMap);
		JsonResult result = new JsonResult();
		
		try {
			
			//첨부파일 있을 경우 첨부번호 조회 (atthNo)
			//if(fileList != null)	fileService.selectMaxFileNo(paramMap);
			
			
			//첨부파일이 없어도 첨부번호 조회해옴
			fileService.selectMaxFileNo(paramMap);
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			brdService.insertBrd(paramMap);
			
			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("brdNo"));
			LOGGER.debug("insertBrd brdNo :: " + paramMap.get("brdNo"));

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
			LOGGER.error("insertBrd BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertBrd END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * brd 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateBrd.do") 
	public ModelAndView updateBrd(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateBrd START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			brdService.updateBrd(paramMap);
			
			result.add("brdNo", paramMap.get("brdNo"));
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateBrd BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateBrd END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * brd 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteBrd.do") 
	public ModelAndView deleteBrd(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteBrd START");
		JsonResult result = new JsonResult();
		
		try {
			String atthNo = String.valueOf(paramMap.get("atthNo")); 
			if(!"null".equals(atthNo)) {
				//파일삭제
				fileService.deleteFileMaster(paramMap);
			}
			
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			brdService.deleteBrd(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteBrd BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteBrd END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 질문과답변 - 답변 등록
	 */
	@RequestMapping(value="/insertAns.do") 
	public ModelAndView insertAns(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertAns START" + paramMap );
		JsonResult result = new JsonResult();
		
		try {
			 if(paramMap.get("ansCont").equals("") || paramMap.get("ansCont") == null) {
				 result.add("msg", "답변 등록에 실패하였습니다.");
			 }else {
				paramMap.put("userNm", SessionUtil.getSess("userNm"));
				paramMap.put("userId", SessionUtil.getSess("userId"));
				paramMap.put("deptNm", SessionUtil.getSess("deptNm"));
				
				brdService.insertAns(paramMap);
				result.add("msg", "답변이 등록되었습니다");
			 }
			
		} catch (Exception e) {
			LOGGER.error("insertAns BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}
		LOGGER.debug("insertAns END :: ");
		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 질문과답변 - 답변 수정
	 */
	@RequestMapping(value="/updateAns.do") 
	public ModelAndView updateAns(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateAns START" + paramMap );
		JsonResult result = new JsonResult();
		
		try {
			if(paramMap.get("ansCont").equals("") || paramMap.get("ansCont") == null) {
				result.add("msg", "답변 등록에 실패하였습니다.");
			}else {
				paramMap.put("userNm", SessionUtil.getSess("userNm"));
				paramMap.put("userId", SessionUtil.getSess("userId"));
				paramMap.put("deptNm", SessionUtil.getSess("deptNm"));
				
				brdService.updateAns(paramMap);
				result.add("msg", "답변이 등록되었습니다");
			}
			
		} catch (Exception e) {
			LOGGER.error("updateAns BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}
		LOGGER.debug("updateAns END :: ");
		return new ModelAndView("jsonView", result);
	}
}
