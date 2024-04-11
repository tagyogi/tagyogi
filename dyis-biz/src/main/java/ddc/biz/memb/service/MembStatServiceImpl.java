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

import ddc.biz.memb.repository.MembStatMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-17
 * @version 1.0
 * @see 조합원현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-17  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membStatService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembStatServiceImpl implements MembStatService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembStatServiceImpl.class);

	@Autowired(required = false)
	private MembStatMapper membStatMapper;


	//조합원현황 장르별 구분 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembStatDesc(HashMap paramMap) {
		return membStatMapper.selectMembStatDesc(paramMap);
	}

	//조합원현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembStatList(HashMap paramMap) {
		return membStatMapper.selectMembStatList(paramMap);
	}

	//콘텐츠공제조합 출자좌수 조회(조합원현황)
	@Transactional(readOnly = true)
	public HashMap selectKocfcInvtNum(HashMap paramMap) {
		return membStatMapper.selectKocfcInvtNum(paramMap);
	}



}
