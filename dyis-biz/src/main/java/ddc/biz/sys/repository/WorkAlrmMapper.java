package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-10-11
 * @version 1.0
 * @see 알람대상관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-10-11  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface WorkAlrmMapper {
	
	//알람대상관리 현황 조회
	List<Map<String, Object>> selectWorkAlrmList(HashMap paramMap);
	
	//알람대상관리 상세 조회
	HashMap selectWorkAlrm(HashMap paramMap);
		
	//알람대상관리 등록 처리
	void insertWorkAlrm(HashMap paramMap);
	
	//알람대상관리 수정 처리
	void updateWorkAlrm(HashMap paramMap);
	
	//알람대상관리 삭제 처리
	void deleteWorkAlrm(HashMap paramMap);
	
	//알림여부 사용자 일괄 업데이트
	void updateWorkAlrmEmpl();
	
	//알림여부 사용자 확인 처리
	void updateWorkAlrmEmplChk(HashMap paramMap);
	
	
		
}
