package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.sys.repository.WorkCloseMapper;
import ddc.core.config.ConfigProperty;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 마감관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성
 *
 *  </pre>
 */
@Service("workCloseService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class WorkCloseServiceImpl implements WorkCloseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkCloseServiceImpl.class);
	
	private final static String active = ConfigProperty.getString("active"); //서버구분
	
	@Autowired(required = false)
	private WorkCloseMapper workCloseMapper;

	//마감관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectWorkCloseList(HashMap paramMap) {
		return workCloseMapper.selectWorkCloseList(paramMap);
	}
	
	//마감관리 사번조회
	@Transactional(readOnly = true)
	public HashMap selectEmp(HashMap paramMap) {
		return workCloseMapper.selectEmp(paramMap);
	}

	//마감관리 전표 처리
	public String saveWorkClose(HashMap paramMap) {
		
		String resultMsg = "";
		
		LOGGER.debug("paramMap " + paramMap);
		
		HashMap closeMap = (HashMap) paramMap.get("close");
		List getList = (List) closeMap.get("data");
		
		LOGGER.debug("getList" + getList.size());
		
		//운영일경우만 전표처리
		if("prd".equals(active)) {
			for(int i =0; i < getList.size(); i++){
				HashMap<String, Object> setMap = (HashMap) getList.get(i);
				
				LOGGER.debug("saveWorkClose setMap " + setMap);
				
				setMap.put("closeEndDt", paramMap.get("closeEndDt"));
				setMap.put("noEmp", paramMap.get("noEmp"));
				
				setMap.put("cdCompany", paramMap.get("cdCompany"));
				setMap.put("cdDept", paramMap.get("cdDept"));
				
				//관리번호
				setMap.put("noDocu", workCloseMapper.selectCloseToMaxNoDocu(setMap));
				
				//전표처리(차변) 
				setMap.put("noDoline", "1"); //차변
				setMap.put("acctCd", setMap.get("debtCd")); //계정코드 DEBT_CD
				
				//LOGGER.debug("saveWorkClos setMap 차변: " + setMap);
				workCloseMapper.insertCloseToFiAdocu(setMap);
				
				//전표처리(대변) 
				setMap.put("noDoline", "2"); //대변
				setMap.put("acctCd", setMap.get("crdtCd")); //계정코드 CRDT_CD
				
				//LOGGER.debug("saveWorkClos setMap 대변: " + setMap);
				workCloseMapper.insertCloseToFiAdocu(setMap);
				
			}
		}
		
		
		paramMap.put("closeReYn", "N");
		
		int closCnt = 0;
		
		// 출자금 마감
		closCnt = workCloseMapper.updateCloseInvest(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "출자금", closCnt);
		
		// 출자금반환 마감
		closCnt = workCloseMapper.updateCloseInvestRefund(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "출자금반환", closCnt);
		
		// 예수금/환불 마감
		closCnt = workCloseMapper.updateCloseDep(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "예수금/환불", closCnt);
		
		// 보증수수료
		closCnt = workCloseMapper.updateCloseGuar(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료", closCnt);
		
		// 보증수수료(미수)
		closCnt = workCloseMapper.updateCloseGuarAfter(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(미수)", closCnt);
		
		// 보증수수료(pg결제)
		closCnt = workCloseMapper.updateCloseGuarPg(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(pg결제)", closCnt);
		
		// 보증수수료 환불
		closCnt = workCloseMapper.updateCloseGuarChgRefund(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(변경환불)", closCnt);
		
		// 보증수수료 환불
		closCnt = workCloseMapper.updateCloseGuarRefund(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(환불)", closCnt);
		
		// 보증수수료 일부해제환불
		closCnt = workCloseMapper.updateCloseGuarPartRefund(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(일부해제환불)", closCnt);
		
		// 보증수수료(대충)
		closCnt = workCloseMapper.updateCloseGuarDebt(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(대충)", closCnt);
		
		// 보증수수료(해제)
		closCnt = workCloseMapper.updateCloseGuarCan(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(해제)", closCnt);
		
		// 보증수수료(일부해제)
		closCnt = workCloseMapper.updateCloseGuarPartCan(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "보증수수료(일부해제)", closCnt);
		
		// 융자지급
		closCnt = workCloseMapper.updateCloseLend(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "융자지급", closCnt);
		
		// 대출이자
		closCnt = workCloseMapper.updateCloseLendInter(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "대출이자", closCnt);
		
		// 융자상환
		closCnt = workCloseMapper.updateCloseLendRepay(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "융자상환", closCnt);
		
		// 융자상환연체이자
		closCnt = workCloseMapper.updateCloseLendRepayDelay(paramMap);
		if(closCnt > 0) resultMsg = resultMsgSet(resultMsg, "융자상환연체이자", closCnt);
		
		if("".equals(resultMsg)) resultMsg = "처리대상없음";
		
		return resultMsg;
	}
	
	public String resultMsgSet(String resultMsg, String workNm, int closCnt) {
	
		if("".equals(resultMsg)) {
			resultMsg = workNm + "[" + Integer.toString(closCnt) + "]";
		}else {
			resultMsg += ", " + workNm + "[" + Integer.toString(closCnt) + "]";
		}
		
		return resultMsg;
	}
	
	
	//마감관리 원복 처리
	public void saveWorkCloseRevert(HashMap paramMap) {
		// 출자금 마감
		workCloseMapper.updateCloseInvest(paramMap);
		// 출자금반환 마감
		workCloseMapper.updateCloseInvestRefund(paramMap);

		// 예수금/환불 마감
		workCloseMapper.updateCloseDep(paramMap);

		// 보증수수료
		workCloseMapper.updateCloseGuar(paramMap);

		// 보증수수료(미수)
		workCloseMapper.updateCloseGuarAfter(paramMap);

		// 보증수수료(pg결제)
		workCloseMapper.updateCloseGuarPg(paramMap);

		// 보증수수료 환불
		workCloseMapper.updateCloseGuarChgRefund(paramMap);

		// 보증수수료 환불
		workCloseMapper.updateCloseGuarRefund(paramMap);

		// 보증수수료 일부해제환불
		workCloseMapper.updateCloseGuarPartRefund(paramMap);

		// 보증수수료(대충)
		workCloseMapper.updateCloseGuarDebt(paramMap);

		// 보증수수료(해제)
		workCloseMapper.updateCloseGuarCan(paramMap);

		// 보증수수료(일부해제)
		workCloseMapper.updateCloseGuarPartCan(paramMap);

		// 융자지급
		workCloseMapper.updateCloseLend(paramMap);
		// 융자상환
		workCloseMapper.updateCloseLendRepay(paramMap);

		// 융자상환연체이자
		workCloseMapper.updateCloseLendRepayDelay(paramMap);
	}
	



    
}
