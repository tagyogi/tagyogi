package proj.web.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.com.service.FileService;
import proj.biz.sys.service.BrdService;
import proj.core.common.CommonUtil;
import proj.core.common.PageUtil;
import proj.core.config.ConfigProperty;
import proj.core.domain.JsonResult;

import proj.web.WebBaseController;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 프로젝트명	: 동양정보서비스 홈페이지 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>IndexController.java (인덱스 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2023.06.21
 *
 * modifier 	: 게시판
 * modify-date  : 
 * description  : 홈페이지
 */
@RequestMapping("/brd")
@Controller("brdController")
public class BrdController extends WebBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrdController.class);
	
	//도메인
	protected final static String SERVER_URL  = ConfigProperty.getString("domain"); //
	
	@Autowired
	private BrdService brdService;
	
	/** fileService 파일첨부 */
	@Autowired
	private FileService fileService;
	
	@Autowired(required = false)
	private CommonUtil commonUtil;
	
    /**
	 * 게시판 리스트 조회
	 */
	@RequestMapping(value="/selectBrdList.do")
	public ModelAndView selectBrdList(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("selectBrdList START " + paramMap);
		
		JsonResult result = new JsonResult();
		
		try {
			PageUtil pageUtil = new PageUtil();
			
			int totalPage = brdService.totalPage(paramMap);
			int curPage = Integer.parseInt(commonUtil.nvl(paramMap.get("curPage"), "0"));
			int brdNo = Integer.parseInt((String) paramMap.get("brdNo"));
			
			if(brdNo == 3) {
				int totalTopCount = brdService.totalTopCount(paramMap);
				pageUtil.PageUtil(totalPage, curPage, (9-totalTopCount));//갤러리게시판 페이지당 게시물 9개
			}else {
				pageUtil.PageUtil(totalPage, curPage);
			}
			paramMap.put("pageUtil", pageUtil);
			
			result.add("pageInfo", pageUtil);
			result.add("tableInfo", brdService.selectHomeBrdList(paramMap));
			result.add("brdNo", brdNo);
			
		} catch (Exception e) {
			result.setResult(99, "조회중 오류가 발생했습니다.");
		}
		
		LOGGER.debug("brdHome END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 게시판 상세 조회 
	 */
	@RequestMapping(value="/selectBrdDtl.do") 
	public ModelAndView selectBrdDtl(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectBrdDtl START" + paramMap );
		JsonResult result = new JsonResult();
		
		try {
			//상세
			HashMap<String, Object>getMap = brdService.selectBrdDtl(paramMap);
			result.add("data", getMap);
			result.add("brdRef", paramMap.get("brdRef"));
			LOGGER.debug("selectBrdDtl brdRef:: " + paramMap.get("brdRef"));
			
			//질문과답변(답변)
			int brdNo = Integer.parseInt((String) paramMap.get("brdNo"));
			if(brdNo == 6) {
				result.add("answer", brdService.selectBrdAnswer(paramMap));
			}
			
			//첨부파일
			paramMap.put("atthNo", getMap.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));
			
			
		} catch (Exception e) {
			LOGGER.error("selectBrd BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("selectBrd END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 질문과답변 - 질문 등록
	 */
	@RequestMapping(value="/insertQna.do") 
	public ModelAndView insertQna(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("insertQna START" + paramMap );
		JsonResult result = new JsonResult();
		
		try {
			//핸드폰 하이픈 제거
			paramMap.put("regTelNo", paramMap.get("regTelNo").toString().replaceAll("[^0-9]",""));
		
			brdService.insertBrd(paramMap);
			
			result.add("msg", "질문이 등록되었습니다");
			
		} catch (Exception e) {
			LOGGER.error("insertQna BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("insertQna END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 질문과답변 - 답변 등록
	 */
	@RequestMapping(value="/insertAns.do") 
	public ModelAndView insertAns(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("insertAns START" + paramMap );
		JsonResult result = new JsonResult();
		
		try {
			 if(paramMap.get("ansCont").equals("") || paramMap.get("ansCont") == null) {
				 result.add("msg", "답변 등록에 실패하였습니다.");
			 }else {
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
	
    
}