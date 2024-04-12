package proj.biz.sys.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 통합게시판관리
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
public interface BrdMasMapper {
	
	//통합게시판 현황 조회
	List<Object> selectBrdMasList(HashMap<String, Object> paramMap);
	
	//통합게시판 상세 조회
	HashMap<String, Object>selectBrdMas(HashMap<String, Object> paramMap);
		
	//통합게시판 등록 처리
	void insertBrdMas(HashMap<String, Object> paramMap);
	
	//통합게시판 수정 처리
	void updateBrdMas(HashMap<String, Object> paramMap);
	
}
