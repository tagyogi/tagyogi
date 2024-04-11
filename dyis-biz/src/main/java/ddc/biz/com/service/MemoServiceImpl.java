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

import ddc.biz.com.repository.MemoMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 메모(개인)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성
 *
 *  </pre>
 */
@Service("memoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MemoServiceImpl implements MemoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemoServiceImpl.class);
	
	@Autowired(required = false)
	private MemoMapper memoMapper;

	//memo 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMemoList(HashMap paramMap) {
		return memoMapper.selectMemoList(paramMap);
	}

	//memo 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMemo(HashMap paramMap) {
		return memoMapper.selectMemo(paramMap);
	}

	//memo 등록 처리(단건)
	public void insertMemo(HashMap paramMap) {
		memoMapper.insertMemo(paramMap);
	}

	//memo 수정 처리(단건)
	public void updateMemo(HashMap paramMap) {
		memoMapper.updateMemo(paramMap);
	}

	//memo 삭제 처리(단건)
	public void deleteMemo(HashMap paramMap) {
		memoMapper.deleteMemo(paramMap);
		
	}

    
}
