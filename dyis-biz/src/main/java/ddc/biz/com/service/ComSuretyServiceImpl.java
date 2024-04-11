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

import ddc.biz.com.repository.ComSuretyMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-09
 * @version 1.0
 * @see 공통보증인
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-09  변성균          최초 생성
 *
 *  </pre>
 */
@Service("comSuretyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ComSuretyServiceImpl implements ComSuretyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComSuretyServiceImpl.class);
	
	@Autowired(required = false)
	private ComSuretyMapper comSuretyMapper;

	//공통보증인 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectComSuretyChk(HashMap paramMap) {
		return comSuretyMapper.selectComSuretyChk(paramMap);
	}

	//공통보증인 등록 처리(단건)
	public void insertComSurety(HashMap paramMap) {
		comSuretyMapper.insertComSurety(paramMap);
	}

	//공통보증인 수정 처리(단건)
	public void updateComSurety(HashMap paramMap) {
		comSuretyMapper.updateComSurety(paramMap);
		
	}

	//공통보증인 삭제 처리(단건)
	public void deleteComSurety(HashMap paramMap) {
		comSuretyMapper.deleteComSurety(paramMap);
	}

    
}
