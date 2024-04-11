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
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ddc.biz.memb.repository.MembCredMapper;
import ddc.biz.sys.repository.ConfBaseIndiMapper;
import ddc.core.common.JsonUtil;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *  2023-04-11  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membCredService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembCredServiceImpl implements MembCredService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembCredServiceImpl.class);
	
	@Autowired(required = false)
	private MembCredMapper membCredMapper;
	
	@Autowired(required = false)
	private ConfBaseIndiMapper confBaseIndiMapper;

	//신용평가 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembCredList(HashMap paramMap) {
		return membCredMapper.selectMembCredList(paramMap);
	}

	//신용평가 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembCred(HashMap paramMap) {
		return membCredMapper.selectMembCred(paramMap);
	}
	
	//신용평가 상세조회
	@Override
	public List<Map<String, Object>> selectCredDtl(HashMap paramMap) {
		return membCredMapper.selectCredDtl(paramMap);
	}


	//신용평가 등록 처리(단건)
	public void insertMembCred(HashMap paramMap) {
		HashMap membFncMap = (HashMap) paramMap.get("membFnc");
		HashMap membNfncMap = (HashMap) paramMap.get("membNfnc");
		HashMap membRprtMap = (HashMap) paramMap.get("membRprt");
		HashMap membReleMap = (HashMap) paramMap.get("membRele");
		String gradeNo = membCredMapper.selectMembCredNo();
		
		paramMap.put("gradeNo", gradeNo);
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		
		membCredMapper.insertMembCred(paramMap);
		
		//재무정보
		List fncList = (List) membFncMap.get("data");
		for(int i=0; i<fncList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) fncList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			setMap.put("gradeNo", gradeNo);
			membCredMapper.insertMembCredIdx(setMap);
		}
		
		//비재무정보
		List nFncList = (List) membNfncMap.get("data");
		for(int i=0; i<nFncList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) nFncList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			setMap.put("gradeNo", gradeNo);
			membCredMapper.insertMembCredIdx(setMap);
		}
		
		//대표자
		List rprtList = (List) membRprtMap.get("data");
		for(int i=0; i<rprtList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) rprtList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			setMap.put("gradeNo", gradeNo);
			membCredMapper.insertMembCredIdx(setMap);
		}
		
		//관련조합원
		List releList = (List) membReleMap.get("data");
		for(int i=0; i<releList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) releList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			setMap.put("gradeNo", gradeNo);
			membCredMapper.insertGradeMember(setMap); 
		}
	}

	//신용평가 수정 처리(단건)
	public void updateMembCred(HashMap paramMap) {
		HashMap membFncMap = (HashMap) paramMap.get("membFnc");
		HashMap membNfncMap = (HashMap) paramMap.get("membNfnc");
		HashMap membRprtMap = (HashMap) paramMap.get("membRprt");
		HashMap membReleMap = (HashMap) paramMap.get("membRele");
		
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		
		membCredMapper.updateMembCred(paramMap);
		
		//재무정보
		List fncList = (List) membFncMap.get("data");
		for(int i=0; i<fncList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) fncList.get(i);
			
			if("Added".equals((String)setMap.get("STATUS"))) {
				membCredMapper.insertMembCredIdx(setMap);
			} else if("Changed".equals((String)setMap.get("STATUS"))) {
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("gradeNo", paramMap.get("gradeNo"));
				membCredMapper.updateMembCredIdx(setMap);
			} else if("Deleted".equals((String)setMap.get("STATUS"))) {}
		}
		
		//비재무정보
		List nFncList = (List) membNfncMap.get("data");
		for(int i=0; i<nFncList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) nFncList.get(i);
			
			if("Added".equals((String)setMap.get("STATUS"))) {
				
			} else if("Changed".equals((String)setMap.get("STATUS"))) {
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("gradeNo", paramMap.get("gradeNo"));
				membCredMapper.updateMembCredIdx(setMap);
			} else if("Deleted".equals((String)setMap.get("STATUS"))) {}
		}
		
		//대표자정보
		List rprtList = (List) membRprtMap.get("data");
		for(int i=0; i<rprtList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) rprtList.get(i);
			
			if("Added".equals((String)setMap.get("STATUS"))) {
				
			} else if("Changed".equals((String)setMap.get("STATUS"))) {
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("gradeNo", paramMap.get("gradeNo"));
				membCredMapper.updateMembCredIdx(setMap);
			} else if("Deleted".equals((String)setMap.get("STATUS"))) {}
		}
		
		//관련조합원정보
		List releList = (List) membReleMap.get("data");
		for(int i=0; i<releList.size(); i++) {
			HashMap<String, Object> setMap = (HashMap) releList.get(i);
			
			if("Added".equals((String)setMap.get("STATUS"))) {
				
			} else if("Changed".equals((String)setMap.get("STATUS"))) {
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("gradeNo", paramMap.get("gradeNo"));
				membCredMapper.updateMembCredIdx(setMap);
			} else if("Deleted".equals((String)setMap.get("STATUS"))) {}
		}
		
	}

	//신용평가 삭제 처리(단건)
	public void deleteMembCred(HashMap paramMap) {
		membCredMapper.deleteMembCred(paramMap);
		
	}

	//자체등급 재무 조회
	@Override
	public List<Map<String, Object>> selectInGradeList(HashMap paramMap) {
		return membCredMapper.selectInGradeList(paramMap);
	}

//	//외부등급 조회
//	@SuppressWarnings("unchecked")
//	@Override
//	public HashMap selectGetGradeOut(HashMap paramMap) {
//		HashMap gradeMap = new HashMap();
//		
//		try {
//			//외부등급 순번채번
//			paramMap.put("grade_no", membCredMapper.getGradeOutSeq(paramMap));
//			
//			//외부등급 등록
//			HashMap kedCdMap = membCredMapper.getKedCd(paramMap);
//			
//			if(kedCdMap == null) {
//				//외부등급 입력 (외부등급 없을경우)
//				membCredMapper.insertGradeOutN(paramMap);
//				
//				gradeMap = membCredMapper.selectGetGradeOut(paramMap);
//				gradeMap.put("fil_cd01", "");
//				gradeMap.put("fil_cd02", "");
//				gradeMap.put("fil_cd03", "");
//				gradeMap.put("fil_cd04", "");
//				gradeMap.put("fil_cd05", "");
//				gradeMap.put("fil_cd06", "");
//				gradeMap.put("fil_cd07", "");
//				gradeMap.put("fil_cd08", "");
//				gradeMap.put("fil_cd09", "");
//			} else {
//				paramMap.putAll(JsonUtil.toLowerMap(kedCdMap));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getYmd(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.selectGetGradeOut(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getFinVal(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getYearCnt(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getGamsa(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getEtc1(paramMap)));
//				paramMap.putAll(JsonUtil.toLowerMap(membCredMapper.getEtc2(paramMap)));
//				
//				// 외부등급 입력(외부등급 있을경우)
//				membCredMapper.insertGradeOutY(paramMap);
//				
//				// 외부등급 조회
//				gradeMap = membCredMapper.selectGetGradeOut(paramMap);
//				
//				// 필터링- 필터링해당('':선택, 1:해당없음(신용평가 적립), 2:해당함(신용평가 부적합))
//				String outGrade = ""; 				//외부등급
//				String gamsa = "";					//감사의견
//				int finVal08 = 0;	//자기자본(자본총계)
//				String sonSil = "";					//최근3년연속영업손실
//				int finVal10 = 0;	//매출액
//				int finVal09 = 0;	//장기차입금
//				int finVal06 = 0;	//단기차입금
//				int iza = 0;			//차입금이자
//				String chenap = "";					//연체여부
//				String yenche = "";					//국세지방체납여부
//				String budo = "";					//부도여부
//				
//				if("D".equals(outGrade)) {
//					gradeMap.put("fil_cd01", "2");
//				} else {
//					gradeMap.put("fil_cd01", "1");
//				}
//				
//				if((!"NS".equals(gamsa)) && (!"UQ".equals(gamsa)) && (!"".equals(gamsa))) {
//					gradeMap.put("fil_cd02", "2");
//				} else {
//					gradeMap.put("fil_cd02", "1");
//				}
//				
//				if(finVal08 < 0) {
//					gradeMap.put("fil_cd03", "2");
//				} else {
//					gradeMap.put("fil_cd03", "1");
//				}
//				
//				if("Y".equals(sonSil)) {
//					gradeMap.put("fil_cd04", "2");
//				} else {
//					gradeMap.put("fil_cd04", "1");
//				}
//				
//				if(((finVal09 + finVal06) - finVal10) > 0) {
//					gradeMap.put("fil_cd05", "2");
//				} else {
//					gradeMap.put("fil_cd05", "1");
//				}
//				
//				if((iza - (finVal10 / 10)) > 0) {
//					gradeMap.put("fil_cd06", "2");
//				} else {
//					gradeMap.put("fil_cd06", "1");
//				}
//				
//				if("Y".equals(chenap) || "Y".equals(yenche)) {
//					gradeMap.put("fil_cd07", "2");
//				} else {
//					gradeMap.put("fil_cd07", "1");
//				}
//				
//				if("Y".equals(budo)) {
//					gradeMap.put("fil_cd08", "2");
//				} else {
//					gradeMap.put("fil_cd08", "1");
//				}
//				
//				//업력
//				gradeMap.put("year_cnt", paramMap.get("year_cnt"));
//			}
//		} catch(Exception e) {
//			LOGGER.error("selectGetGradeOut Exception : " + e.toString());
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//		}
//		return gradeMap;
//	}

	//자체비율로드
	@Override
	public List<Map<String, Object>> selectCmgradeList(HashMap paramMap) {
		return membCredMapper.selectCmgradeList(paramMap);
	}
	
	
	//관련조합원 조회
	@Override
	public List<Map<String, Object>> selectReleList(HashMap paramMap) {
		return membCredMapper.selectReleList(paramMap);
	}


    
}
