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

import ddc.biz.sys.repository.PopMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 윤가영1
 * @since 2023-11-01
 * @version 1.0
 * @see 팝업
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-11-01  윤가영1          최초 생성
 *
 *  </pre>
 */
@Service("popService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class PopServiceImpl implements PopService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PopServiceImpl.class);
	
	@Autowired(required = false)
	private PopMapper popMapper;

	//팝업 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectPopList(HashMap paramMap) {
		return popMapper.selectPopList(paramMap);
	}

	//팝업 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectPop(HashMap paramMap) {
		return popMapper.selectPop(paramMap);
	}

	//팝업 등록 처리
	public void insertPop(HashMap paramMap) {
		popMapper.insertPop(paramMap);
	}

	//팝업 수정 처리
	public void updatePop(HashMap paramMap) {
		popMapper.updatePop(paramMap);
		
	}

    
}
