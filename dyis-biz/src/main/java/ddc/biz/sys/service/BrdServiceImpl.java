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

import ddc.biz.sys.repository.BrdMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 게시판관리
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
@Service("brdService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class BrdServiceImpl implements BrdService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrdServiceImpl.class);
	
	@Autowired(required = false)
	private BrdMapper brdMapper;

	//게시판관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectBrdList(HashMap paramMap) {
		return brdMapper.selectBrdList(paramMap);
	}

	//게시판관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectBrd(HashMap paramMap) {
		return brdMapper.selectBrd(paramMap);
	}

	//게시판관리 등록 처리(단건)
	public void insertBrd(HashMap paramMap) {
		brdMapper.insertBrd(paramMap);
	}

	//게시판관리 수정 처리(단건)
	public void updateBrd(HashMap paramMap) {
		brdMapper.updateBrd(paramMap);
	}

	//게시판관리 삭제 처리(단건)
	public void deleteBrd(HashMap paramMap) {
		brdMapper.deleteBrd(paramMap);
		
	}
	
	/**********************************홈페이지******************************/
	//게시판 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectHomeBrdList(HashMap paramMap) {
		return brdMapper.selectHomeBrdList(paramMap);
	}
	
	//게시판 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectBrdDtl(HashMap paramMap) {
		
		//조회수 증가
		brdMapper.updateViewCnt(paramMap);
		
		return brdMapper.selectBrdDtl(paramMap);
	}
	
	//총 페이지 수
	public int totalPage(HashMap paramMap) {
		return brdMapper.totalPage(paramMap);
	}
	
	//총 상단공지 수
	public int totalTopCount(HashMap paramMap) {
		return brdMapper.totalTopCount(paramMap);
	}
	
	//질문과답변 - 답변 조회
	@Transactional(readOnly = true)
	public HashMap selectBrdAnswer(HashMap paramMap) {
		return brdMapper.selectBrdAnswer(paramMap);
	}
	
	//질문과답변 - 답변 등록
	@Transactional(readOnly = true)
	public void insertAns(HashMap paramMap) {
		brdMapper.insertAns(paramMap);
	}	
    
	//질문과답변 - 답변 수정
	@Transactional(readOnly = true)
	public void updateAns(HashMap paramMap) {
		brdMapper.updateAns(paramMap);
		
	}
}
