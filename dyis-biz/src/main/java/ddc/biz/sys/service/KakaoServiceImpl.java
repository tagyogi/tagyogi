package ddc.biz.sys.service;

import java.util.ArrayList;
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

import ddc.biz.guar.repository.GuarMapper;
import ddc.biz.memb.repository.MembMapper;
import ddc.biz.sys.repository.KakaoMapper;
import ddc.core.common.CommonUtil;
import ddc.core.config.ConfigProperty;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-07
 * @version 1.0
 * @see 카카오채널관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-07  변성균          최초 생성
 *
 *  </pre>
 */
@Service("kakaoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class KakaoServiceImpl implements KakaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KakaoServiceImpl.class);
	
	private final static String kakaoKey = ConfigProperty.getString("kakao.key"); //발시자key
	
	@Autowired(required = false)
	private KakaoMapper kakaoMapper;

	@Autowired(required = false)
	private MembMapper membMapper;

	@Autowired(required = false)
	private GuarMapper guarMapper;

	//카카오채널전송 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectKakaoSndList(HashMap paramMap) {
		return kakaoMapper.selectKakaoSndList(paramMap);
	}
	
	//카카오채널관리 메시지 그룹 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectKakaoTitlList(HashMap paramMap) {
		return kakaoMapper.selectKakaoTitlList(paramMap);
	}
	
	//카카오채널관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectKakaoList(HashMap paramMap) {
		return kakaoMapper.selectKakaoList(paramMap);
	}
	
	//카카오채널관리 상세
	@Transactional(readOnly = true)
	public HashMap selectKakao(HashMap paramMap) {
		return kakaoMapper.selectKakao(paramMap);
	}


	//카카오채널관리 등록 처리
	public void insertKakao(HashMap paramMap){
		kakaoMapper.insertKakao(paramMap);
	}

	//카카오채널관리 수정 처리
	public void updateKakao(HashMap paramMap) {
		kakaoMapper.updateKakao(paramMap);
	}

	//카카오채널관리 삭제 처리
	public void deleteKakao(HashMap paramMap) {
		kakaoMapper.deleteKakao(paramMap);
	}
	
	//카카오채널관리 전송 처리
	/**
	 * 필요변수
	 * msgSeq 전송메세지순번
	 * param  치환 변수값
	 * refNo  참조번호
	 * telNo  특정대상번호
	 * 
	 */
	public HashMap sendKakao(HashMap paramMap) {
		HashMap resultMap = new HashMap();
		
		String procType = CommonUtil.nvl((String)paramMap.get("procType"), "");
		if("BATCH".equals(procType)) {
			paramMap.put("userNm", "배치");
		}else {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
		}
	
		//메세지 정보조회
		HashMap kakaoMsg = kakaoMapper.selectKakao(paramMap);
		LOGGER.debug("sendKakao kakaoMsg sendKakao:3 ");
		String workType = (String)kakaoMsg.get("workType"); //업무유형
		String msgCont = (String)kakaoMsg.get("msgCont"); //내용
		String param = (String)paramMap.get("param");
		String[] params = param.split(":");
		
		//데이터 조회해서 가져올경우
		String refVal = (String)kakaoMsg.get("refVal"); //참조변수
		String[] refVals = refVal.split(":"); 
		
		//내용 변수 치환
		for(int i = 0; i < params.length; i++) {
			msgCont = msgCont.replaceAll("param"+(i+1), params[i]);
		}
		
		String telNo = (String)paramMap.get("telNo"); //특정연락처
		String telNm = (String)paramMap.get("telNm"); //특정연락처명
		
		LOGGER.debug("sendKakao kakaoMsg telNo: " + telNo + " / telNm : " +  telNm);
		
		if(telNo != null && telNm != null ) {
			String[] telNos = telNo.split(":");
			String[] telNms = telNm.split(":");
			
			//메세지 전송
			for(int i = 0; i < telNos.length; i++) {
				//
				LOGGER.debug("sendKakao kakaoMsg telNos: " + telNos[i]);
				
				if(telNos[i].indexOf("010") == 0) {
					paramMap.put("sndCont", msgCont);
					paramMap.put("sndTelNo", telNos[i]);
					paramMap.put("sndTelNm", telNms[i]);
					paramMap.put("bizCd", kakaoMsg.get("bizCd"));
					
					//로그 등록 및 cmid 추출
					kakaoMapper.insertKakaoLog(paramMap);
					
					LOGGER.debug("sendKakao paramMap : " + paramMap);
					
					//전송 처리 등록 
					paramMap.put("senderKey", kakaoKey); //발신자key
					kakaoMapper.insertBizMsg(paramMap);
				}
			}
		}else {
			
		}
		
		return resultMap;
	}
	
	
}
