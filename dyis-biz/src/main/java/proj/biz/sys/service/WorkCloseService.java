package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * WorkCloseService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 마감관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성 
 *  </pre>
 */
public interface WorkCloseService {
	
	//마감관리 현황 조회
	public List<Object> selectWorkCloseList(HashMap<String, Object> paramMap);
	
	//마감관리 사번조회
	public HashMap<String, Object>selectEmp(HashMap<String, Object> paramMap);
		
	//마감관리 저장 처리
	public String saveWorkClose(HashMap<String, Object> paramMap);
	
	//마감관리 원복 처리
	public void saveWorkCloseRevert(HashMap<String, Object> paramMap);
	
		
}
