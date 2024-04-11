package ddc.biz.com.service;

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

import ddc.biz.com.repository.ComConfMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 최진호
 * @since 2023-06-13
 * @version 1.0
 * @see 민원발급
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-13  최진호          최초 생성
 *
 *  </pre>
 */
@Service("comConfService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ComConfServiceImpl implements ComConfService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComConfServiceImpl.class);
	
	@Autowired(required = false)
	private ComConfMapper comConfMapper;

	//민원발급 현황 조회 (출자용)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectComConfInvtList(HashMap paramMap) {
		return comConfMapper.selectComConfInvtList(paramMap);
	}

	//민원발급 현황 조회 (융자용)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectComConfList(HashMap paramMap) {
		return comConfMapper.selectComConfList(paramMap);
	}
	
	//민원발급 현황 조회 (보증용)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectComConfGuarList(HashMap paramMap) {
		return comConfMapper.selectComConfGuarList(paramMap);
	}

	//민원발급 등록 처리(단건)
	public void insertComConf(HashMap paramMap) {
		comConfMapper.insertComConf(paramMap);
	}

	//민원발급 수정 처리(단건)
	public void updateComConf(HashMap paramMap) {
		comConfMapper.updateComConf(paramMap);
		
	}

	//민원발급 삭제 처리(단건)
	public void deleteComConf(HashMap paramMap) {
		comConfMapper.deleteComConf(paramMap);
		
	}
	
	//증명서발급완료처리
	public void updateConfProcStat(HashMap paramMap) {
		comConfMapper.updateConfProcStat(paramMap);
		
	}
	
	//======================================================================
	//홈페이지 민원발급 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectHomeComConfList(HashMap paramMap) {
		return comConfMapper.selectHomeComConfList(paramMap);
	}
	
	//홈페이지 민원발급 현황 - 총 페이지 수
	@Transactional(readOnly = true)
	public int totalPage(HashMap paramMap) {
		return comConfMapper.totalPage(paramMap);
	}
	
	//민원발급 발급일자 저장(단건)
	public void updateComConfPubDt(HashMap paramMap) {
		comConfMapper.updateComConfPubDt(paramMap);
		
	}
	
	//홈페이지 민원발급 삭제 처리(단건)
	public void deleteHomeComConf(HashMap paramMap) {
		comConfMapper.deleteHomeComConf(paramMap);
	}
}
