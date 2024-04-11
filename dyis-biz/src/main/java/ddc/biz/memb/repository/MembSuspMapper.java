package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-02
 * @version 1.0
 * @see 업무정지
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-02  김상민1          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembSuspMapper {
	
	//업무정지 현황 조회
	List<Map<String, Object>> selectMembSuspList(HashMap paramMap);
	
	//업무정지 상세 조회
	HashMap selectMembSusp(HashMap paramMap);
		
	//업무정지 등록 처리
	void insertMembSusp(HashMap paramMap);
	
	//업무정지 수정 처리
	void updateMembSusp(HashMap paramMap);
	
	//업무정지 삭제 처리
	void deleteMembSusp(HashMap paramMap);
	
		
}
