package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membCredService
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
 *  </pre>
 */
public interface MembCredService {
	
	
	//신용평가 현황 조회
	public List<Map<String, Object>> selectMembCredList(HashMap paramMap);
	
	//신용평가 상세 조회
	public HashMap selectMembCred(HashMap paramMap);
	
	//신용평가 등록 처리(단건)
	public void insertMembCred(HashMap paramMap);
	
	//신용평가 수정 처리(단건)
	public void updateMembCred(HashMap paramMap);
	
	//신용평가 삭제 처리(단건)
	public void deleteMembCred(HashMap paramMap);

	//자체등급 평가지표
	public List<Map<String, Object>> selectInGradeList(HashMap paramMap);
	
	//자체비율 로드
	public List<Map<String, Object>> selectCmgradeList(HashMap paramMap);
	
	//관련조합원
	public List<Map<String, Object>> selectReleList(HashMap paramMap);
	
	//신용평가 상세 조회
	public List<Map<String, Object>> selectCredDtl(HashMap paramMap);

		
		
}
