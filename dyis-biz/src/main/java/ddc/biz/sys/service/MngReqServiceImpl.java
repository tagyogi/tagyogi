package ddc.biz.sys.service;

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

import ddc.biz.sys.repository.MngReqMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-12-11
 * @version 1.0
 * @see 보안요청관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-12-11  변성균          최초 생성
 *
 *  </pre>
 */
@Service("mngReqService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MngReqServiceImpl implements MngReqService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MngReqServiceImpl.class);
	
	@Autowired(required = false)
	private MngReqMapper mngReqMapper;

	//보안요청관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMngReqList(HashMap paramMap) {
		return mngReqMapper.selectMngReqList(paramMap);
	}

	//보안요청관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMngReq(HashMap paramMap) {
		return mngReqMapper.selectMngReq(paramMap);
	}

	//보안요청관리 등록 처리
	public void insertMngReq(HashMap paramMap) {
		mngReqMapper.insertMngReq(paramMap);
	}

	//보안요청관리 수정 처리(코멘트)
	public void updateMngReqComent(HashMap paramMap) {
		mngReqMapper.updateMngReqComent(paramMap);
	}

	//보안요청관리 수정 처리(처리)
	public void updateMngReqProc(HashMap paramMap) {
		mngReqMapper.updateMngReqProc(paramMap);
	}

	
	//보안요청관리 삭제 처리
	public void deleteMngReq(HashMap paramMap) {
		mngReqMapper.deleteMngReq(paramMap);
		
	}

    
}
