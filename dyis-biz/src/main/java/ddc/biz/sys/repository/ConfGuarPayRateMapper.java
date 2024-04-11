package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-22
 * @version 1.0
 * @see 선급금약정이자율
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균          최초 생성
 *  2023-10-10
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ConfGuarPayRateMapper {
	
	//선급금약정이자율 현황 조회
	List<Map<String, Object>> selectConfGuarPayRateList(HashMap paramMap);
	
	//선급금약정이자율 상세 조회(최종)
	HashMap selectConfGuarPayRateMaxAppDt(HashMap paramMap);
	
	//선급금약정이자율 등록 처리
	void insertConfGuarPayRate(HashMap paramMap);
	
	//선급금약정이자율 수정 처리
	void updateConfGuarPayRate(HashMap paramMap);
	
	//선급금약정이자율 삭제 처리
	void deleteConfGuarPayRate(HashMap paramMap);
	
		
}
