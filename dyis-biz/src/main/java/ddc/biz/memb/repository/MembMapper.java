package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-03
 * @version 1.0
 * @see memb
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  김상민          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembMapper {

	//조합원관리 현황 조회
	List<Map<String, Object>> selectMembList(HashMap paramMap);

	//조합원관리 상세 조회
	HashMap selectMemb(HashMap paramMap);

	//조합원정보 계정정보 아이디 중복확인(모바일 로그인용 아이디)
	HashMap selectMembUserId(HashMap paramMap);

	//조합원관리 등록 처리
	void insertMemb(HashMap paramMap);

	//조합원관리 수정 처리(관리자)
	void updateMemb(HashMap paramMap);

	//조합원관리 수정 처리(홈페이지)
	void updateMembHome(HashMap membMap);

	//조합원 수정(기재변경)
	void updateMembChg(HashMap paramMap);

	//조합원관리 삭제 처리
	void deleteMemb(HashMap paramMap);

	//조합원관리(조합원번호 채번)
	String selectMembNoSeq();

	//조합원관리 > 대표자현황 조회
	List<Map<String, Object>> selectMembCeoList(HashMap paramMap);

	//조합원 대표자 등록 처리
	void insertMembCeo(HashMap paramMap);

	//조합원 대표자 수정 처리
	void updateMembCeo(HashMap paramMap);

	//조합원 대표자 삭제 처리
	void deleteMembCeo(HashMap paramMap);

	//조합원관리 > 담당자현황 조회
	List<Map<String, Object>> selectMembPersonList(HashMap paramMap);

	//조합원 담당자 등록 처리
	void insertMembPerson(HashMap paramMap);

	//조합원 담당자 수정 처리
	void updateMembPerson(HashMap paramMap);

	//조합원 담당자 삭제 처리
	void deleteMembPerson(HashMap paramMap);

	//조합원 이력 추가
	void insertMembHist(HashMap paramMap);

	//보증약정 출자 유형 업데이트
	void updateGuarContrType(HashMap paramMap);

	//조합원 대표 + 담당자 리스트(보증발급관리 등록,수정에서 사용 - selectBox)
	List<Map<String, Object>> selectCeoPersList(HashMap paramMap);

	int selectMembCnt(HashMap paramMap);

	//조합원 담당자 조회
	HashMap selectMembPers(HashMap paramMap);

}
