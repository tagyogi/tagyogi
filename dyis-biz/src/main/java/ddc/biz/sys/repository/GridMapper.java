package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 현황화면그리드옵션관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface GridMapper {
	
	//그리드 현황 조회
	List<Map<String, Object>> selectGridList(HashMap paramMap);
	
	//그리드 상세 조회
	List<Map<String, Object>> selectGridDtlList(HashMap paramMap);
		
	//그리드 등록 처리
	void insertGrid(HashMap paramMap);
	
	//그리드 수정 처리
	void updateGrid(HashMap paramMap);
	
	//그리드 삭제 처리
	void deleteGrid(HashMap paramMap);

	//그리드 열 등록 처리
	void insertGridCols(HashMap paramMap);
	
	//그리드 열 수정 처리
	void updateGridCols(HashMap paramMap);
	
	//그리드 열 삭제 처리
	void deleteGridCols(HashMap paramMap);

	//그리드 열 초기 생성
	void createGridCols(HashMap paramMap);

}
