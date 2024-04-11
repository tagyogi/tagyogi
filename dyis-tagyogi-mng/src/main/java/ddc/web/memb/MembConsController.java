package ddc.web.memb;


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

import ddc.core.common.CommonUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.com.service.FileService;
import ddc.biz.memb.service.MembConsService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membCons Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-04
 * @version 1.0
 * @see 공제가입상담현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-04  김상민1            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membCons")
@Controller("membConsController")
public class MembConsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembConsController.class);

	/** membConsService */
	@Autowired
	protected MembConsService membConsService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/** fileService */
	@Autowired
	protected FileService fileService;
	
	/**
	 * 공제가입상담 현황 조회
	 */
	@RequestMapping(value="/selectMembConsList.do")
	public ModelAndView selectMembConsList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembConsList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membConsService.selectMembConsList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembConsList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembConsList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 공제가입상담 상세 조회
	 */
	@RequestMapping(value="/selectMembCons.do")
	public ModelAndView selectMembCons(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembCons START");
		JsonResult result = new JsonResult();

		try {
			//업체개요 + 신용평가 + 담당자 정보
			HashMap membCons = membConsService.selectMembCons(paramMap);			
			result.add("data", membCons);

			//진행내용(상담내용) 정보
			result.add("consDtlList", membConsService.selectMembConsDtlList(paramMap));
			
			//첨부파일
			paramMap.put("atthNo", membCons.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));

			
		} catch (Exception e) {
			LOGGER.error("selectMembCons BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembCons END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 공제가입상담 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembCons.do")
	public ModelAndView insertMembCons(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembCons START");
		JsonResult result = new JsonResult();

		try {
			//첨부번호 발번 (atthNo)
			fileService.selectMaxFileNo(paramMap);
			
			//업무처리
			membConsService.insertMembCons(paramMap);
			
			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("counsNo"));

			//파일처리
			if(fileList != null) {
				try{
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertMembCons BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembCons END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 공제가입상담 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembCons.do")
	public ModelAndView updateMembCons(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembCons START");
		JsonResult result = new JsonResult();

		try {
			//업무처리
			membConsService.updateMembCons(paramMap);
			
			if(fileList != null){
				try{
					//업무처리 후 파일처리전 참조번호세팅
					paramMap.put("refNo", paramMap.get("counsNo"));
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateMembCons BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembCons END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 공제가입상담 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembCons.do")
	public ModelAndView deleteMembCons(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembCons START");
		JsonResult result = new JsonResult();

		try {
			membConsService.deleteMembCons(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteMembCons BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembCons END :: ");

		return new ModelAndView("jsonView", result);
	}



}
