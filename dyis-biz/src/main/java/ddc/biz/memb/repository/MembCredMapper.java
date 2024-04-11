package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-11
 * @version 1.0
 * @see 신용평가관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-11  김상민1          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembCredMapper {
	
	//신용평가 현황 조회
	List<Map<String, Object>> selectMembCredList(HashMap paramMap);
	
	//신용평가 상세 조회
	HashMap selectMembCred(HashMap paramMap);
		
	//신용평가 등록 처리
	void insertMembCred(HashMap paramMap);
	
	//신용평가 수정 처리
	void updateMembCred(HashMap paramMap);
	
	//신용평가 삭제 처리
	void deleteMembCred(HashMap paramMap);
	
	//자체등급 재무 조회
	List<Map<String, Object>> selectInGradeList(HashMap paramMap);

	//자체비율 로드 조회
	List<Map<String, Object>> selectCmgradeList(HashMap paramMap);
	
	//신용평가 등록
	void insertMembCredIdx(HashMap setMap);

	//신용평가 수정
	void updateMembCredIdx(HashMap<String, Object> setMap);

	//신용평가 관련조합원 등록
	void insertGradeMember(HashMap<String, Object> setMap);

	//gradeNo 채번
	String selectMembCredNo();
	
	//관련조합원 조회
	List<Map<String, Object>> selectReleList(HashMap paramMap);
	
	//신용평가(등급 정보) 상세 조회
	List<Map<String, Object>> selectCredDtl(HashMap paramMap);

		
}
