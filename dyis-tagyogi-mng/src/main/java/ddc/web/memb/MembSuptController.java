package ddc.web.memb;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.common.CommonUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.com.service.ComService;
import ddc.biz.com.service.ContrMortService;
import ddc.biz.guar.service.GuarContrService;
import ddc.biz.invt.service.InvtBillService;
import ddc.biz.invt.service.InvtDeptService;
import ddc.biz.invt.service.InvtTranService;
import ddc.biz.lend.service.LendContrService;
import ddc.biz.memb.service.MembConsService;
import ddc.biz.memb.service.MembStatService;
import ddc.biz.memb.service.MembSuptService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/**
 * MembSupt Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-20 (통계)
 * @version 1.0
 * @see 조합관련통계
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-20  변성균           최초 생성
 *
 *
 *
 *  </pre>
 */
@RequestMapping("/membSupt")
@Controller("membSuptController")
public class MembSuptController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembSuptController.class);

	/** membSuptService */
	@Autowired
	protected MembSuptService membSuptService;

	/** comService */
	@Autowired
	protected ComService comService;

	/** invtBillService */
	@Autowired
	protected InvtBillService invtBillService;

	/** invtDeptService */
	@Autowired
	protected InvtDeptService invtDeptService;

	/** guarContrService */
	@Autowired
	protected GuarContrService guarContrService;

	/** lendContrService */
	@Autowired
	protected LendContrService lendContrService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;

	/** contrMortService */
	@Autowired
	protected ContrMortService contrMortService;

	/** membConsService */
	@Autowired
	protected MembConsService membConsService;

	/** invtTranService */
	@Autowired
	protected InvtTranService invtTranService;

	/**
	 * 조합공헌도 현황 조회
	 */
	@RequestMapping(value="/selectContriButeList.do")
	public ModelAndView selectContriButeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectContriButeList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectContriButeList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectContriButeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectContriButeList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)지역별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembAreaList.do")
	public ModelAndView selectMembAreaList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembAreaList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembAreaList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembAreaList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembAreaList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)업력별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembBussList.do")
	public ModelAndView selectMembBussList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembBussList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembBussList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembBussList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembBussList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)등급별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembGradeList.do")
	public ModelAndView selectMembGradeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembGradeList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembGradeList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembGradeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembGradeList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (보증)연도/장르별 수수료 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembGuarList.do")
	public ModelAndView selectMembGuarList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembGuarList START");
		JsonResult result = new JsonResult();

		List yearList = membSuptService.selectGuarYearList(paramMap);

		paramMap.put("yearList", yearList);

		try {
			result.add("data", membSuptService.selectMembGuarList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembGuarList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembGuarList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (보증)연도/상품별 수수료 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembPrsrList.do")
	public ModelAndView selectMembPrsrList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembPrsrList START");
		JsonResult result = new JsonResult();

		List yearList = membSuptService.selectGuarYearList(paramMap);

		paramMap.put("yearList", yearList);

		try {
			result.add("data", membSuptService.selectMembPrsrList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembPrsrList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembPrsrList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)매출액별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembSaleList.do")
	public ModelAndView selectMembSaleList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembSaleList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembSaleList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembSaleList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembSaleList END :: ");

		return new ModelAndView("jsonView", result);
	}


	/**
	 * (조합원)매출액별 현황 상세 조회
	 */
	@RequestMapping(value="/selectMembSale.do")
	public ModelAndView selectMembSale(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembSale START" + paramMap);
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembSale(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembSaleList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembSale END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)조건별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembTypeList.do")
	public ModelAndView selectMembTypeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembTypeList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembTypeList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembTypeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembTypeList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)업력별 현황 현황 조회
	 */
	@RequestMapping(value="/selectMembWorkList.do")
	public ModelAndView selectMembWorkList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembWorkList START");
		JsonResult result = new JsonResult();

		try {
			result.add("data", membSuptService.selectMembWorkList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectMembWorkList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembWorkList END :: ");

		return new ModelAndView("jsonView", result);
	}

	/**
	 * (조합원)업무거래현황 상세 조회
	 */
	@RequestMapping(value="/selectMembWork.do")
	public ModelAndView selectMembWork(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembWork START");
		JsonResult result = new JsonResult();

		try {
			//0. 조합원정보
			HashMap membMap = comService.selectMemb(paramMap);
			result.add("memb", membMap);

			//1. 업무거래 정보 조회
			result.add("workDtl", membSuptService.selectMembWork(paramMap));

			//2. 출자금 현황
			result.add("invtBill", invtBillService.selectInvtBillList(paramMap));	//출자증권정보(기존 업무거래상세에 있던 리스트)
			result.add("invtTran", invtTranService.selectInvtTakeList(paramMap));	//출자증권지분취득현황(추가)

			//3. 예수금현황
			result.add("invtDept", invtDeptService.selectInvtDeptList(paramMap));

			//4. 담보현황(보증, 융자)
			result.add("contrMort", contrMortService.selectContrMortList(paramMap));

			//5. 보증약정현황
			paramMap.put("sSerchType", "I");	//order by 때문에 추가
			//paramMap.put("sContrStat", "10");	//기존: 정상만 --> 전체 나오도록 주석처리
			result.add("guarContr", guarContrService.selectGuarContrList(paramMap));

			//6. 대출약정현황
			paramMap.put("sCntYn", "Y");		//갯수조회 필요
			//paramMap.put("sContrStat", "10");	//기존: 정상만 --> 전체 나오도록 주석처리
			result.add("lendContr", lendContrService.selectLendContrList(paramMap));

			//7. 고객관리(진행내용(상담내용) 정보 -- 공제가입관리)
			List<Map<String, Object>> consDtlList = null;
			if(!CommonUtil.isNull(membMap.get("bizNo"))) {
				paramMap.put("bizNo", membMap.get("bizNo"));
				consDtlList = membConsService.selectMembConsDtlList(paramMap);
			}
			result.add("consDtlList", consDtlList);

		} catch (Exception e) {
			LOGGER.error("selectMembWork BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembWork END :: ");

		return new ModelAndView("jsonView", result);
	}


}
