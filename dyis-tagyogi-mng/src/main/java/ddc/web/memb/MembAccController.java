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
import ddc.biz.com.service.ComService;
import ddc.biz.com.service.FileService;
import ddc.biz.memb.service.MembAccService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membAcc Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원계좌
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membAcc")
@Controller("membAccController")
public class MembAccController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembAccController.class);

	/** membAccService */
	@Autowired
	protected MembAccService membAccService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** comService */
	@Autowired
	protected ComService comService;

	/** fileService */
	@Autowired
	protected FileService fileService;


	/**
	 * 조합원계좌신청 현황 조회
	 */
	@RequestMapping(value="/selectMembAccList.do")
	public ModelAndView selectMembAccList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembAccList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membAccService.selectMembAccList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembAccList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembAccList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원계좌신청 상세 조회
	 */
	@RequestMapping(value="/selectMembAcc.do")
	public ModelAndView selectMembAcc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembAcc START");
		JsonResult result = new JsonResult();

		try {
			//조합원정보
			result.add("memb", comService.selectMemb(paramMap));

			//기재변경정보
			HashMap membAcc = membAccService.selectMembAcc(paramMap);
			result.add("data", membAcc);

			//등록된서식 목록
			paramMap.put("atthNo", membAcc.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부


		} catch (Exception e) {
			LOGGER.error("selectMembAcc BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembAcc END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원계좌신청 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembAcc.do")
	public ModelAndView insertMembAcc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembAcc START");
		JsonResult result = new JsonResult();

		try {
			//첨부파일번호 할당
			fileService.selectMaxFileNo(paramMap);

			//업무처리
			membAccService.insertMembAcc(paramMap);

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null){
				//LOGGER.debug("insertMembAcc fileList :: " + paramMap);
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
			LOGGER.error("insertMembAcc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembAcc END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원계좌신청 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembAcc.do")
	public ModelAndView updateMembAcc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembAcc START");
		JsonResult result = new JsonResult();

		try {
			//세션
			paramMap.put("userNm", SessionUtil.getSess("userNm"));

			membAccService.updateMembAcc(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("reqNo"));

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null){
				LOGGER.debug("updateInvt fileList :: " + paramMap);
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
			LOGGER.error("updateMembAcc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembAcc END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원계좌신청 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembAcc.do")
	public ModelAndView deleteMembAcc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembAcc START");
		JsonResult result = new JsonResult();

		try {
			membAccService.deleteMembAcc(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteMembAcc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembAcc END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원계좌관리 현황 조회
	 */
	@RequestMapping(value="/selectMembAccMngList.do")
	public ModelAndView selectMembAccMngList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembAccMngList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membAccService.selectMembAccMngList(paramMap));
			result.put("paramMap", paramMap);

		} catch (Exception e) {
			LOGGER.error("selectMembAccMngList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembAccMngList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원계좌관리 신규등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembAccMng.do")
	public ModelAndView insertMembAccMng(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembAccMng START" + paramMap);
		JsonResult result = new JsonResult();
		LOGGER.debug("insertMembAccMng paramMap paramMap :: " + paramMap );
		try {

			//첨부파일 없어도 첨부파일번호 추출
			fileService.selectMaxFileNo(paramMap);

			membAccService.insertMembAccMng(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("acctNo"));

			//파일처리
			try{
				fileService.fileUpload(paramMap, fileList);
			} catch (Exception e) {
				result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
				logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
			}

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertMembAccMng BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembAccMng END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원계좌관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembAccMng.do")
	public ModelAndView updateMembAccMng(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembAccMng START" + paramMap);
		JsonResult result = new JsonResult();
		LOGGER.debug("updateMembAccMng paramMap paramMap :: " + paramMap );
		try {

			membAccService.updateMembAccMng(paramMap);

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null){
				LOGGER.debug("updateMembAccMng fileList :: " + paramMap);
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
			LOGGER.error("updateMembAccMng BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembAccMng END :: ");

		return new ModelAndView("jsonView", result);
	}


}
