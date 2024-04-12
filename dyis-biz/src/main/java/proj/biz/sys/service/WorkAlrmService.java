package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * workAlrmService
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
 *  </pre>
 */
public interface WorkAlrmService {
	
	
	//알람대상관리 현황 조회
	public List<Object> selectWorkAlrmList(HashMap<String, Object> paramMap);
	
	//알람대상관리 상세 조회
	public HashMap<String, Object>selectWorkAlrm(HashMap<String, Object> paramMap);
	
	//알람대상관리 저장 처리(그리드 일괄처리용 미사용시 삭제)
	public void saveWorkAlrm(HashMap<String, Object> paramMap);
	
	//알람확인처리
	public void updateWorkAlrmEmplChk(HashMap<String, Object> paramMap);
		
}
