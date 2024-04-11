package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-31
 * @version 1.0
 * @see 공휴일관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-31  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface HolyMapper {
	
	//공휴일 현황 조회
	List<Map<String, Object>> selectHolyList(HashMap paramMap);
	
	//공휴일 등록 처리
	void insertHoly(HashMap paramMap);
	
	//공휴일 수정 처리
	void updateHoly(HashMap paramMap);
	
	//공휴일 삭제 처리
	void deleteHoly(HashMap paramMap);
	
}
