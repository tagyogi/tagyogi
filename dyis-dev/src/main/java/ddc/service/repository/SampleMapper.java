package ddc.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 DEV
 * @since 1900.01.01
 * @version 1.0
 * @see jobContent
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  1900.01.01  DEV          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface SampleMapper {
	
	//jobContent 현황 조회
	List<Map<String, Object>> selectSampleList(HashMap paramMap);
	
	//jobContent 상세 조회
	HashMap selectSample(HashMap paramMap);
		
	//jobContent 등록 처리
	void insertSample(HashMap paramMap);
	
	//jobContent 수정 처리
	void updateSample(HashMap paramMap);
	
	//jobContent 삭제 처리
	void deleteSample(HashMap paramMap);
	
		
}
