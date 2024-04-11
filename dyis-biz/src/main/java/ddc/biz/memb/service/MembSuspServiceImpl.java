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

import ddc.biz.memb.repository.MembSuspMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-02
 * @version 1.0
 * @see 업무정지
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-02  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membSuspService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembSuspServiceImpl implements MembSuspService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembSuspServiceImpl.class);
	
	@Autowired(required = false)
	private MembSuspMapper membSuspMapper;

	//업무정지 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembSuspList(HashMap paramMap) {
		return membSuspMapper.selectMembSuspList(paramMap);
	}

	//업무정지 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembSusp(HashMap paramMap) {
		return membSuspMapper.selectMembSusp(paramMap);
	}

	//업무정지 등록 처리(단건)
	public void insertMembSusp(HashMap paramMap) {
		membSuspMapper.insertMembSusp(paramMap);
	}

	//업무정지 수정 처리(단건)
	public void updateMembSusp(HashMap paramMap) {
		membSuspMapper.updateMembSusp(paramMap);
		
	}

	//업무정지 삭제 처리(단건)
	public void deleteMembSusp(HashMap paramMap) {
		membSuspMapper.deleteMembSusp(paramMap);
		
	}

    
}
