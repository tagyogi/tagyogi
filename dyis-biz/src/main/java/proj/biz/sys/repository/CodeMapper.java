package proj.biz.sys.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 공통코드 관리
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
public interface CodeMapper {
	
	//공통코드 현황 조회
	List<Object> selectCodeList(HashMap<String, Object> paramMap);
	
	//공통코드 상세 현황 조회
	List<Object> selectCodeDtlList(HashMap<String, Object> paramMap);
	
	//공통코드 등록 처리
	void insertCode(HashMap<String, Object> paramMap);
	
	//공통코드 수정 처리
	void updateCode(HashMap<String, Object> paramMap);
	
	//공통코드 삭제 처리
	void deleteCode(HashMap<String, Object> paramMap);
	
}
