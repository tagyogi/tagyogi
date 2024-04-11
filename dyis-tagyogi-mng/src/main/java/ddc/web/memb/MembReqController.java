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
import ddc.biz.com.service.FileService;
import ddc.biz.invt.service.InvtService;
import ddc.biz.memb.service.MembConsService;
import ddc.biz.memb.service.MembReqService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.ConfInvtService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membReq Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-04
 * @version 1.0
 * @see 공제가입등록
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
@RequestMapping("/membReq")
@Controller("membReqController")
public class MembReqController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembReqController.class);

	/** membReqService */
	@Autowired
	protected MembReqService membReqService;

	/** membService */
	@Autowired
	protected MembService membService;

	/** membConsService */
	@Autowired
	protected MembConsService membConsService;


	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** FileService */
	@Autowired
	protected FileService fileService;


	/** confInvtService */
	@Autowired
	protected ConfInvtService confInvtService;

	/**
	 * 공제가입등록 현황 조회
	 */
	@RequestMapping(value="/selectMembReqList.do")
	public ModelAndView selectMembReqList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembReqList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membReqService.selectMembReqList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembReqList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembReqList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 공제가입등록 상세 조회
	 */
	@RequestMapping(value="/selectMembReq.do")
	public ModelAndView selectMembReq(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembReq START paramMap : " + paramMap);
		JsonResult result = new JsonResult();

		try {
			//공제가입등록 정보
			HashMap membReqInfo = membReqService.selectMembReq(paramMap);
			result.add("data", membReqInfo);

			//좌당지분액 정보
			paramMap.put("appDt", String.valueOf(membReqInfo.get("reqDt")));
			result.add("invtData", confInvtService.selectConfInvtMaxAppDt(paramMap));

			//등록된서식 목록
			paramMap.put("atthNo", membReqInfo.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부

		} catch (Exception e) {
			LOGGER.error("selectMembReq BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembReq END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 공제가입등록 등록 처리
	 */
	@RequestMapping(value="/insertMembReq.do")
	public ModelAndView insertMembReq(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembReq START");
		JsonResult result = new JsonResult();

		try {
			//첨부번호 발번 (atthNo)
			fileService.selectMaxFileNo(paramMap);

			LOGGER.debug("공제가입등록 :: " + paramMap);

			//업무처리전 첨부파일 저장양식(출자첨부로 변환 방지)
			String atthType = CommonUtil.toString(paramMap.get("atthType"));
			String atthNo = CommonUtil.toString(paramMap.get("atthNo"));

			//업무처리
			membReqService.insertMembReq(paramMap, fileList);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("bizNo"));
			paramMap.put("atthType", atthType);
			paramMap.put("atthNo", atthNo);
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
			LOGGER.error("insertMembReq BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembReq END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 공제가입등록 수정 처리
	 */
	@RequestMapping(value="/updateMembReq.do")
	public ModelAndView updateMembReq(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembReq START");
		JsonResult result = new JsonResult();

		try {

			//업무처리전 첨부파일 저장양식(출자첨부로 변환 방지)
			String atthType = CommonUtil.toString(paramMap.get("atthType"));
			String atthNo = CommonUtil.toString(paramMap.get("atthNo"));

			//업무 처리
			membReqService.updateMembReq(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("bizNo"));
			paramMap.put("atthType", atthType);
			paramMap.put("atthNo", atthNo);
			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null) {
				LOGGER.debug("updateMembReq fileList :: " + paramMap);
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
			LOGGER.error("updateMembReq BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembReq END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 공제가입등록 삭제 처리
	 */
	@RequestMapping(value="/deleteMembReq.do")
	public ModelAndView deleteMembReq(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembReq START");
		JsonResult result = new JsonResult();

		try {
			membReqService.deleteMembReq(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteMembRe BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembReq END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 기등록업체 현황 조회(통합)
	 */
	@RequestMapping(value="/selectMembIntegList.do")
	public ModelAndView selectMembIntegList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembIntegList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membReqService.selectMembIntegList(paramMap));


		} catch (Exception e) {
			LOGGER.error("selectMembIntegList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembIntegList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 기등록업체 상세 조회
	 */
	@RequestMapping(value="/selectMembInteg.do")
	public ModelAndView selectMembInteg(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembInteg START paramMap " + paramMap);
		JsonResult result = new JsonResult();

		try {

			if("M".equals((String)paramMap.get("regType"))) { //조합원
				paramMap.put("membNo", paramMap.get("membKey"));
				result.add("data", membService.selectMemb(paramMap));

			}else if("R".equals((String)paramMap.get("regType"))) { //가입신청
				paramMap.put("bizNo", paramMap.get("membKey"));
				result.add("data", membReqService.selectMembReq(paramMap));

			}else if("S".equals((String)paramMap.get("regType"))) { //상담
				paramMap.put("counsNo", paramMap.get("membKey"));
				result.add("data", membConsService.selectMembCons(paramMap));
			}

		} catch (Exception e) {
			LOGGER.error("selectMembInteg BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembInteg END :: ");

		return new ModelAndView("jsonView", result);
	}


}
