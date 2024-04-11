package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.memb.repository.MembAccMapper;
import ddc.biz.memb.repository.MembMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *  2023-05-03  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membAccService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembAccServiceImpl implements MembAccService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembAccServiceImpl.class);

	@Autowired(required = false)
	private MembAccMapper membAccMapper;

	@Autowired(required = false)
	private MembMapper membMapper;

	//조합원계좌 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembAccList(HashMap paramMap) {
		return membAccMapper.selectMembAccList(paramMap);
	}
	
	//조합원계좌 조회(mb_acc)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembAccMngList(HashMap paramMap) {
		return membAccMapper.selectMembAccMngList(paramMap);
	}


	//조합원계좌 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembAcc(HashMap paramMap) {
		return membAccMapper.selectMembAcc(paramMap);
	}


	//조합원계좌 등록 처리
	public void insertMembAcc(HashMap paramMap) {
		//신청등록
		membAccMapper.insertMembAcc(paramMap);
		
	}

	//조합원계좌 수정 처리
	public void updateMembAcc(HashMap paramMap) {
		//계좌정보 수정
		membAccMapper.updateMembAcc(paramMap);

		// 처리완료 일때는 조합원 계좌정보 수정
		String beProcStat =  (String) paramMap.get("beProcStat");
		String procStat	=  (String) paramMap.get("procStat");
		if(!"30".equals(beProcStat) &&  "30".equals(procStat) ) {
			//조합원 이력
			membMapper.insertMembHist(paramMap);

            //조합원 수정(계좌정보 변경)
			paramMap.put("bankChgYn", "Y");
            membMapper.updateMembChg(paramMap);
            
            //등록된 계좌가 없는상태, 승인완료면 조합원계좌관리 계좌등록
            if(membAccMapper.selectMembAccMngCnt(paramMap) == 0) {
            	//첫승인시 사용Y, 이후 해당계좌에대한 관리는 계좌관리에서 수정 가능
            	paramMap.put("useYn","Y");
            	paramMap.put("acctNm",paramMap.get("depoNm"));
            	membAccMapper.insertMembAccMng(paramMap);
            	
            	//조합원계좌관리 이력 등록
        		paramMap.put("histReason", "계좌의뢰신청승인완료");
        		membAccMapper.insertMembAccHist(paramMap);
			}
		}
		
		
	}

	//조합원계좌 삭제 처리
	public void deleteMembAcc(HashMap paramMap) {
		membAccMapper.deleteMembAcc(paramMap);

	}
	
	//조합원계좌관리 신규등록 처리 (단건)
	public void insertMembAccMng(HashMap paramMap) {
		
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		
		membAccMapper.insertMembAccMng(paramMap);
		
		//조합원계좌관리 이력 등록
		paramMap.put("histReason", "계좌신규등록");
		membAccMapper.insertMembAccHist(paramMap);
	}
	
	//조합원계좌관리 수정 처리 (단건)
	public void updateMembAccMng(HashMap paramMap) {

		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		
		membAccMapper.updateMembAccMng(paramMap);
		
		//조합원계좌관리 이력 등록
		paramMap.put("histReason", "계좌수정");
		membAccMapper.insertMembAccHist(paramMap);
	}


}
