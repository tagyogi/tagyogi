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
import ddc.core.config.ConfigProperty;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.com.service.FileService;
import ddc.biz.memb.service.MembAccService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * memb Controller 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-03
 * @version 1.0
 * @see memb
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  김상민            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/memb")
@Controller("membController")
public class MembController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembController.class);

	/** membService */
	@Autowired
	protected MembService membService;

	/** mmbAccService */
	@Autowired
	protected MembAccService membAccService;

	/** FileService */
	@Autowired
	protected FileService fileService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/**
	 * 조합원관리 현황 조회
	 */
	@RequestMapping(value="/selectMembList.do")
	public ModelAndView selectMembList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membService.selectMembList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원관리 상세 조회
	 */
	@RequestMapping(value="/selectMemb.do")
	public ModelAndView selectMemb(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMemb START");
		JsonResult result = new JsonResult();

		try {
			//기본정보 조회
			HashMap membData = membService.selectMemb(paramMap);
			result.add("data", membData);
			//대표자 정보
			result.add("ceoList", membService.selectMembCeoList(paramMap));
			//담당자 정보
			result.add("personList", membService.selectMembPersonList(paramMap));
			//계좌이력
			result.add("bankHistList", membAccService.selectMembAccMngList(paramMap));
			//첨부파일
			paramMap.put("atthNo", membData.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부
			
		} catch (Exception e) {
			LOGGER.error("selectMemb BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMemb END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원 대표자 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveMembCeo.do")
	public ModelAndView saveMembCeo(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveMembCeo START");
		JsonResult result = new JsonResult();

		try {
			membService.saveMembCeo(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);

		} catch (Exception e) {
			LOGGER.error("saveMembCeo BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveMembCeo END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원 담당자 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveMembPerson.do")
	public ModelAndView saveMembPerson(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveMembPerson START");
		JsonResult result = new JsonResult();

		try {
			membService.saveMembPerson(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);

		} catch (Exception e) {
			LOGGER.error("saveMembPerson BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveMembPerson END :: ");

		return new ModelAndView("jsonView", result);
	}



	/**
	 * 조합원관리 페이지정보 전체 수정 처리 (기본정보, 대표자정보, 담당자정보)
	 */
	@RequestMapping(value="/updateMemb.do")
	public ModelAndView updateMemb(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMemb START");
		JsonResult result = new JsonResult();

		try {

			//업무처리
			membService.updateMemb(paramMap);

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			HashMap membMap = (HashMap) paramMap.get("memb");
			if(fileList != null){
				try{
					//업무처리 후 파일처리전 참조번호세팅
					paramMap.put("refNo", membMap.get("membNo"));
					LOGGER.debug("membMap :: " + membMap);
					fileService.fileUpload(membMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, membMap, e.toString()); //에러처리로그
				}
			}

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);

		} catch (Exception e) {
			LOGGER.error("updateMemb BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMemb END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원관리 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMemb.do")
	public ModelAndView deleteMemb(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMemb START");
		JsonResult result = new JsonResult();

		try {
			membService.deleteMemb(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);

		} catch (Exception e) {
			LOGGER.error("deleteMemb BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMemb END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 조합원관리 계좌변경이력 조회(계좌변경 팝업) : /selectMemb에서 파라미터로 조작하려하였으나 참조하는곳이 많아 별도로 분리(대표,담당자 정보는 필요없음)
	 */
	@RequestMapping(value="/selectMembBankHistList.do")
	public ModelAndView selectMembBankHistList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembBankHistList START");
		JsonResult result = new JsonResult();

		try {
			//기본정보 조회
			result.add("data", membService.selectMemb(paramMap));
			//계좌이력
			result.add("bankHistList", membAccService.selectMembAccList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembBankHistList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembBankHistList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 조합원관리 인감파일 조회
	 */
	@RequestMapping(value="/selectMembSeal.do")
	public ModelAndView selectMembSeal(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembSeal START");
		JsonResult result = new JsonResult();

		try {
			//첨부파일
			result.add("data", fileService.selectFile(paramMap)); //파일첨부

		} catch (Exception e) {
			LOGGER.error("selectMembSeal BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembSeal END :: ");

		return new ModelAndView("jsonView", result);
	}



	/**
	 * 보증약정 출자 유형 업데이트
	 */
	@RequestMapping(value="/updateGuarContrType.do")
	public ModelAndView updateGuarContrType(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateGuarContrType START");
		JsonResult result = new JsonResult();

		try {
			//보증약정 출자 유형 업데이트
			membService.updateGuarContrType(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);

		} catch (Exception e) {
			LOGGER.error("updateGuarContrType BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateGuarContrType END :: ");

		return new ModelAndView("jsonView", result);
	}





}
