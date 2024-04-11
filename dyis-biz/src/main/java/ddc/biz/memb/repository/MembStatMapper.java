package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-17
 * @version 1.0
 * @see 조합원현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-17  김상민1          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembStatMapper {

	//조합원현황 장르별 구분 조회
	List<Map<String, Object>> selectMembStatDesc(HashMap paramMap);

	//조합원현황 현황 조회
	List<Map<String, Object>> selectMembStatList(HashMap paramMap);

	//콘텐츠공제조합 출자좌수 조회(조합원현황)
	HashMap selectKocfcInvtNum(HashMap paramMap);

}
