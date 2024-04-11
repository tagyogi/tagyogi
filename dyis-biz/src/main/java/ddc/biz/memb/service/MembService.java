package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membService
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
 *  </pre>
 */
public interface MembService {

	//조합원관리 현황 조회
	public List<Map<String, Object>> selectMembList(HashMap paramMap);

	//조합원관리 상세 조회
	public HashMap selectMemb(HashMap paramMap);

	//조합원관리 등록 처리(단건)
	public void insertMemb(HashMap paramMap);

	//조합원관리 수정 처리(단건)
	public void updateMemb(HashMap paramMap);

	//조합원관리 삭제 처리(단건)
	public void deleteMemb(HashMap paramMap);

	//조합원관리 > 대표자현황 조회
	public List<Map<String, Object>> selectMembCeoList(HashMap paramMap);

	//조합원 대표자 저장 처리
	public void saveMembCeo(HashMap paramMap);

	//조합원관리 > 담당자현황 조회
	public List<Map<String, Object>> selectMembPersonList(HashMap paramMap);

	//조합원 담당자 저장 처리
	public void saveMembPerson(HashMap paramMap);

	//조합원 이력 추가
	public void insertMembHist(HashMap paramMap);

	//보증약정 출자 유형 업데이트
	public void updateGuarContrType(HashMap paramMap);

	//조합원 대표 + 담당자 리스트(보증발급관리 등록,수정에서 사용 - selectBox)
	public List<Map<String, Object>> selectCeoPersList(HashMap paramMap);

	//조합원 조회 건수 확인
	public int selectMembCnt(HashMap paramMap);

	//조합원정보 계정정보 아이디 중복확인(모바일 로그인용 아이디)
	public HashMap selectMembUserId(HashMap paramMap);

	//담당자 정보 조회
	public HashMap selectMembPers(HashMap paramMap);



}
