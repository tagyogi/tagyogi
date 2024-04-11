package ddc.biz.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.com.repository.ComMapper;
import ddc.biz.guar.repository.GuarContrMapper;
import ddc.biz.guar.repository.GuarMapper;
import ddc.biz.lend.repository.LendContrMapper;
import ddc.biz.sys.repository.WorkRmvMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-03
 * @version 1.0
 * @see 업무해지처리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-03  변성균          최초 생성
 *
 *  </pre>
 */
@Service("workRmvService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class WorkRmvServiceImpl implements WorkRmvService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkRmvServiceImpl.class);
	
	@Autowired(required = false)
	private WorkRmvMapper workRmvMapper;
	
	@Autowired(required = false)
	private LendContrMapper lendContrMapper;
	
	@Autowired(required = false)
	private GuarContrMapper guarContrMapper;
	
	@Autowired(required = false)
	private GuarMapper guarMapper;
	
	@Autowired(required = false)
	private ComMapper comMapper;
	
	//업무해지 처리
	public HashMap workRmvProc(HashMap paramMap) {
		HashMap resultMap = new HashMap();
		
		try {
			
			resultMap.put("resultCd", 0);
			
			//업무유형 LC 융자약정, GC 보증약정, GM 보증
			String workType = (String)paramMap.get("workType");
			List rmvList = new ArrayList();
			if("LC".equals(workType)) {
				//대상조회
				rmvList = workRmvMapper.selectLendContrWorkRmvTargList(paramMap);
				for(int i = 0; i < rmvList.size(); i++) {
					HashMap getMap = (HashMap) rmvList.get(i);
					getMap.put("userNm", paramMap.get("userNm"));
					
					workRmvMapper.updateLendContrWorkRmv(getMap); //융자약정만기해지
					workRmvMapper.updateLendContrMortWorkRmv(getMap); //융자약정담보해지
					workRmvMapper.updateLendContrSureWorkRmv(getMap); //융자약정연대보증인해지
					
					getMap.put("histReason", "만기해지");
					lendContrMapper.insertLendContrHist(getMap);
				}
				resultMap.put("resultMsg", "융자약정 해지 처리 건수  : " + rmvList.size() );
			}else if("GC".equals(workType)) {
				//대상조회
				rmvList = workRmvMapper.selectGuarContrWorkRmvTargList(paramMap);
				for(int i = 0; i < rmvList.size(); i++) {
					HashMap getMap = (HashMap) rmvList.get(i);
					getMap.put("userNm", paramMap.get("userNm"));
					
					workRmvMapper.updateGuarContrWorkRmv(getMap); //보증약정만기해지
					workRmvMapper.updateGuarContrMortWorkRmv(getMap); //보증약정담보해지
					workRmvMapper.updateGuarContrSureWorkRmv(getMap); //보증약정연대보증인해지
					
					getMap.put("histReason", "만기해지");
					guarContrMapper.insertGuarContrHist(getMap);
				}
				resultMap.put("resultMsg", "보증약정 해지 처리 건수  : " + rmvList.size() );
			}else if("GM".equals(workType)) {
				paramMap.put("cdType", "GR01");
				paramMap.put("cd", paramMap.get("guarType"));
				String guarTypeNm = comMapper.selectCodeDtlNm(paramMap);
				
				rmvList = workRmvMapper.selectGuarWorkRmvTargList(paramMap);
				for(int i = 0; i < rmvList.size(); i++) {
					HashMap getMap = (HashMap) rmvList.get(i);
					getMap.put("userNm", paramMap.get("userNm"));
					
					workRmvMapper.updateGuarWorkRmv(getMap); //보증해지
					
					getMap.put("histReason", "만기해지");
					guarMapper.insertGuarHist(getMap);
				}
				
				resultMap.put("resultMsg", guarTypeNm + " 보증 해지 처리 건수  : " + rmvList.size() );
			}
			  
			
		} catch (Exception e) {
			resultMap.put("resultCd", 9);
			resultMap.put("resultMsg", "처리중 오류가 발생하였습니다.[" + e.toString()+"]");
			LOGGER.error("workRmvProc Exception : " + e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return resultMap;
		
	}
	
    
}
