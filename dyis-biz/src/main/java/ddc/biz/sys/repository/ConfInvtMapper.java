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
 * @see 출자좌수액
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ConfInvtMapper {
	
	//출자좌수액 현황 조회
	List<Map<String, Object>> selectConfInvtList(HashMap paramMap);
	
	//출자좌수액 상세 조회(최종)
	HashMap selectConfInvtMaxAppDt(HashMap paramMap);
	
	//출자좌수액 등록 처리
	void insertConfInvt(HashMap paramMap);
	
	//출자좌수액 수정 처리
	void updateConfInvt(HashMap paramMap);
	
	//출자좌수액 삭제 처리
	void deleteConfInvt(HashMap paramMap);
	
		
}
