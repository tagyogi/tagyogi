package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membSuspService
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-02
 * @version 1.0
 * @see 업무정지
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-02  김상민1          최초 생성 
 *  </pre>
 */
public interface MembSuspService {
	
	
	//업무정지 현황 조회
	public List<Map<String, Object>> selectMembSuspList(HashMap paramMap);
	
	//업무정지 상세 조회
	public HashMap selectMembSusp(HashMap paramMap);
	
	//업무정지 등록 처리(단건)
	public void insertMembSusp(HashMap paramMap);
	
	//업무정지 수정 처리(단건)
	public void updateMembSusp(HashMap paramMap);
	
	//업무정지 삭제 처리(단건)
	public void deleteMembSusp(HashMap paramMap);
		
		
}
