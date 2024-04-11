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
import ddc.biz.memb.service.MembDescService;
import ddc.biz.memb.service.MembService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * membDesc Controller 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-27
 * @version 1.0
 * @see 기재변경관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-27  김상민            최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membDesc")
@Controller("membDescController")
public class MembDescController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembDescController.class);

	/** membDescService */
	@Autowired
	protected MembDescService membDescService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** membService */
	@Autowired
	protected MembService membService;

	/** fileService */
	@Autowired
	protected FileService fileService;

	/**
	 * 기재변경관리 현황 조회
	 */
	@RequestMapping(value="/selectMembDescList.do")
	public ModelAndView selectMembDescList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembDescList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membDescService.selectMembDescList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembDescList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembDescList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 기재변경관리 상세 조회
	 */
	@RequestMapping(value="/selectMembDesc.do")
	public ModelAndView selectMembDesc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembDesc START");
		JsonResult result = new JsonResult();

		try {
			//조합원정보
			result.add("memb", membService.selectMemb(paramMap));

			//기재변경정보
			HashMap membDesc = membDescService.selectMembDesc(paramMap);
			result.add("data", membDesc);

			//등록된서식 목록
			paramMap.put("atthNo", membDesc.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부

			//조합원 기재변경 이력 정보
			result.add("membDescHisList", membDescService.selectMembDescList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembDesc BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembDesc END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 기재변경관리 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembDesc.do")
	public ModelAndView insertMembDesc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMembDesc START");
		JsonResult result = new JsonResult();

		try {
			//첨부번호 발번 (atthNo)
			fileService.selectMaxFileNo(paramMap);

			membDescService.insertMembDesc(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("reqNo"));

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
			LOGGER.error("insertMembDesc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembDesc END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * 기재변경관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembDesc.do")
	public ModelAndView updateMembDesc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateMembDesc START");
		JsonResult result = new JsonResult();

		try {

			membDescService.updateMembDesc(paramMap);

			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("reqNo"));

			//파일처리
			//파일자체가 삭제되고 등록한게 아니라면 일반정보변경은 paramMap으로 수정해야함 이런경우는 fileList가 null임
			if(fileList != null){
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
			LOGGER.error("updateMembDesc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembDesc END :: ");

		return new ModelAndView("jsonView", result);
	}

}
