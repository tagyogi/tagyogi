package ddc.biz.com.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-22
 * @version 1.0
 * @see 약정담보정보
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-22  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ContrMortMapper {
	
	//약정담보정보 현황 조회
	List<Map<String, Object>> selectContrMortList(HashMap paramMap);
	
}
