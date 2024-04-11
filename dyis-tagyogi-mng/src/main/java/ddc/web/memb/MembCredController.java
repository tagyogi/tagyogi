package ddc.web.memb;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ddc.biz.com.service.ComService;
import ddc.biz.com.service.FileService;
import ddc.biz.memb.service.MembCredService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.ConfBaseIndiService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membCred Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-11
 * @version 1.0
 * @see 신용평가관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-11  김상민1            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membCred")
@Controller("membCredController")
public class MembCredController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembCredController.class);

	/** membCredService */
	@Autowired
	protected MembCredService membCredService;

	/** membService */
	@Autowired
	protected MembService membService;

	/**confBaseIndiService */
	@Autowired
	protected ConfBaseIndiService confBaseIndiService;

	/** fileService */
	@Autowired
	protected FileService fileService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** comService */
	@Autowired
	protected ComService comService;

	/**
	 * 신용평가 현황 조회
	 */
	@RequestMapping(value="/selectMembCredList.do")
	public ModelAndView selectMembCredList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembCredList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membCredService.selectMembCredList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembCredList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembCredList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 신용평가 상세 조회
	 */
	@RequestMapping(value="/selectMembCred.do")
	public ModelAndView selectMembCred(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembCred START");
		JsonResult result = new JsonResult();

		try {
			//조합원정보
			result.add("memb", comService.selectMemb(paramMap));	//첨부파일번호때문에 membService -> comService호출로 변경

			//재무정보, 필터링 값 조회
			HashMap fncInfo = membCredService.selectMembCred(paramMap);
			result.add("fncInfo", fncInfo);

			paramMap.put("gradeNo", fncInfo.get("gradeNo"));

			//재무지표 상세 조회
			paramMap.put("idxCd", "01");
			result.add("fncList", membCredService.selectCredDtl(paramMap));

			//비재무지표 상세 조회
			paramMap.put("idxCd", "02");
			result.add("nFncList", membCredService.selectCredDtl(paramMap));

			//대표자 상세 조회
			paramMap.put("idxCd", "03");
			result.add("rprtList", membCredService.selectCredDtl(paramMap));

			//관련조합원 상세조회
			result.add("releList", membCredService.selectReleList(paramMap));

			//자체비율
			result.add("selfList", membCredService.selectCmgradeList(paramMap));

			paramMap.put("atthNo", fncInfo.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부

		} catch (Exception e) {
			LOGGER.error("selectMembCred BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembCred END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 자체등급 평가 지표 참조값 조회 (등록 화면)
	 */
	@RequestMapping(value="/selectCredInfoList.do")
	public ModelAndView selectCredInfoList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectCredInfoList START");
		JsonResult result = new JsonResult();

		try {

			//재무 평가 항목
			paramMap.put("idxCd", "01");
			result.add("fncList", confBaseIndiService.selectConfBaseIndiMaxDt(paramMap));

			//비재무 평가 항목
			paramMap.put("idxCd", "02");
			result.add("nFncList", confBaseIndiService.selectConfBaseIndiMaxDt(paramMap));

			//대표자 평가 항목
			paramMap.put("idxCd", "03");
			result.add("rprtList", confBaseIndiService.selectConfBaseIndiMaxDt(paramMap));

			//관련조합원
			result.add("releList", membCredService.selectReleList(paramMap));

			//자체비율
			result.add("selfList", membCredService.selectCmgradeList(paramMap));


		} catch (Exception e) {
			LOGGER.error("selectCredInfoList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectCredInfoList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 신용평가 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembCred.do")
	public ModelAndView insertMembCred(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembCred START");
		JsonResult result = new JsonResult();

		try {
			LOGGER.debug("insertMembCred paramMap :: " + paramMap);
			//첨부파일 있을 경우 첨부번호 조회 (atthNo)
			fileService.selectMaxFileNo(paramMap);

			membCredService.insertMembCred(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("gradeNo"));

			//파일처리
			if(fileList != null){
				try{
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}

		} catch (Exception e) {
			LOGGER.error("selectMembCredList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembCred END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 신용평가 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembCred.do")
	public ModelAndView updateMembCred(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembCred START");
		JsonResult result = new JsonResult();

		try {
			membCredService.updateMembCred(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("gradeNo"));

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null){

				LOGGER.debug("updateMembCred fileList :: " + paramMap);
				try{
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}

			}else { //단순정보수정
				fileService.updateFile(paramMap, fileList);
			}

		} catch (Exception e) {
			LOGGER.error("updateMembCred BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembCred END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 신용평가 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembCred.do")
	public ModelAndView deleteMembCred(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembCred START");
		JsonResult result = new JsonResult();

		try {
			membCredService.deleteMembCred(paramMap);

		} catch (Exception e) {
			LOGGER.error("selectMembCredList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembCred END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 자체비율로드
	 */
	@RequestMapping(value="/selectCmgradeList.do")
	public ModelAndView selectCmgradeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectCmgradeList START");
		JsonResult result = new JsonResult();

		try {
			List<Map<String, Object>> list = membCredService.selectCmgradeList(paramMap);
			result.add("data", list);
		} catch (Exception e) {
			LOGGER.error("selectCmgradeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectRprtList END :: ");

		return new ModelAndView("jsonView", result);
	}
}
