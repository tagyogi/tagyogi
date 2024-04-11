package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원별융자요
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembRateLendMapper {

	//조합원별융자요 현황 조회
	List<Map<String, Object>> selectMembRateLendList(HashMap paramMap);

	//조합원별융자요 상세 조회
	HashMap selectMembRateLend(HashMap paramMap);

	//조합원별융자요 등록 처리
	void insertMembRateLend(HashMap paramMap);

	//조합원별융자요 수정 처리
	void updateMembRateLend(HashMap paramMap);

	//조합원별융자요 삭제 처리
	void deleteMembRateLend(HashMap paramMap);

	//신용도별 요율 조회(임시)
	HashMap selectLendRate(HashMap paramMap);


}
