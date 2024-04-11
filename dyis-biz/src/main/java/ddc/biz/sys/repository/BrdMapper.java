package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.common.PageUtil;
import ddc.core.datacon.DefaultConnMapper;

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
@DefaultConnMapper
@Repository
public interface BrdMapper {
	
	//게시판 현황 조회
	List<Map<String, Object>> selectBrdList(HashMap paramMap);
	
	//게시판 상세 조회
	HashMap selectBrd(HashMap paramMap);
		
	//게시판 등록 처리
	void insertBrd(HashMap paramMap);
	
	//게시판 수정 처리
	void updateBrd(HashMap paramMap);
	
	//게시판 삭제 처리
	void deleteBrd(HashMap paramMap);

	/********************************홈페이지**************************/
	//게시판 현황 조회
	List<Map<String, Object>> selectHomeBrdList(HashMap paramMap);
	
	//게시판 상세 조회
	HashMap selectBrdDtl(HashMap paramMap);
	
	//조회수 증가
	void updateViewCnt(HashMap paramMap);
	
	//총 페이지 수
	int totalPage(HashMap paramMap);

	//총 상단공지 수
	int totalTopCount(HashMap paramMap);

	//질문과답변 - 답변 조회
	HashMap selectBrdAnswer(HashMap paramMap);
	
	//질문과답변 - 답변 등록
	void insertAns(HashMap paramMap);

	//질문과답변 - 답변 수정
	void updateAns(HashMap paramMap);
}
