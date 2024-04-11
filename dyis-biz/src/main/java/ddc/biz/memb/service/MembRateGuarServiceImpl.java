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

import ddc.biz.memb.repository.MembRateGuarMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원보증요율
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
@Service("membRateGuarService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembRateGuarServiceImpl implements MembRateGuarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembRateGuarServiceImpl.class);

	@Autowired(required = false)
	private MembRateGuarMapper membRateGuarMapper;

	//조합원보증요율 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembRateGuarList(HashMap paramMap) {
		return membRateGuarMapper.selectMembRateGuarList(paramMap);
	}

	//조합원보증요율 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembRateGuar(HashMap paramMap) {
		return membRateGuarMapper.selectMembRateGuar(paramMap);
	}

	//조합원보증요율 등록 처리(단건)
	public void insertMembRateGuar(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membRateGuarMapper.insertMembRateGuar(paramMap);
	}

	//조합원보증요율 수정 처리(단건)
	public void updateMembRateGuar(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membRateGuarMapper.updateMembRateGuar(paramMap);

	}

	//조합원보증요율 삭제 처리(단건)
	public void deleteMembRateGuar(HashMap paramMap) {
		membRateGuarMapper.deleteMembRateGuar(paramMap);
	}

	//신용도 별 요율 조회
	@Transactional(readOnly = true)
	public HashMap selectGuarRate(HashMap paramMap) {
		return membRateGuarMapper.selectGuarRate(paramMap);
	}

}
