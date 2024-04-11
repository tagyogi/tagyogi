package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 보증상품요율관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ConfGuarRateMapper {

	//보증수수료율 현황 조회
	List<Map<String, Object>> selectConfGuarRateList(HashMap paramMap);

	//보증수수료율 상세 조회
	HashMap selectConfGuarRate(HashMap paramMap);

	//보증수수료율 등록 처리
	void insertConfGuarRate(HashMap paramMap);

	//보증수수료율 수정 처리
	void updateConfGuarRate(HashMap paramMap);

	//보증수수료율 삭제 처리
	void deleteConfGuarRate(HashMap paramMap);

	//조합원보증요율 상세 조회
	List<Map<String, Object>> selectGuarRate(HashMap paramMap);


}
