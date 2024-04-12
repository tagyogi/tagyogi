package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * brdService
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
 *  </pre>
 */
public interface BrdService {
	
	
	//게시판관리 현황 조회
	public List<Object> selectBrdList(HashMap<String, Object> paramMap);
	
	//게시판관리 상세 조회
	public HashMap<String, Object>selectBrd(HashMap<String, Object> paramMap);
	
	//게시판관리 등록 처리(단건)
	public void insertBrd(HashMap<String, Object> paramMap);
	
	//게시판관리 수정 처리(단건)
	public void updateBrd(HashMap<String, Object> paramMap);
	
	//게시판관리 삭제 처리(단건)
	public void deleteBrd(HashMap<String, Object> paramMap);
	
	
	/**********************홈페이지***********************/
	//게시판 현황 조회
	public List<Object> selectHomeBrdList(HashMap<String, Object> paramMap);
	
	//게시판 상세 조회
	public HashMap<String, Object>selectBrdDtl(HashMap<String, Object> paramMap);
	
	//총 페이지 수
	public int totalPage(HashMap<String, Object> paramMap);
	
	//총 상단공지 수
	public int totalTopCount(HashMap<String, Object> paramMap);

	//질문과답변 - 답변 조회
	public HashMap<String, Object>selectBrdAnswer(HashMap<String, Object> paramMap);
	
	//질문과답변 - 답변 등록
	public void insertAns(HashMap<String, Object> paramMap);

	//질문과답변 - 답변 수정
	public void updateAns(HashMap<String, Object> paramMap);

		
}
