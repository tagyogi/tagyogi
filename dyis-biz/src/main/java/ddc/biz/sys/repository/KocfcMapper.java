package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-29
 * @version 1.0
 * @see 조합관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-29  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface KocfcMapper {
	
	//조합정보 현황 조회
	List<Map<String, Object>> selectKocfcList(HashMap paramMap);

	//조합정보 상세 조회
	HashMap selectKocfc();
	
	//조합정보 등록 처리
	void insertKocfc(HashMap paramMap);
	
	//조합정보 수정 처리
	void updateKocfc(HashMap paramMap);
	
	//조합정보 삭제 처리
	void deleteKocfc(HashMap paramMap);
	
		
}
