package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * emplGrantService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 권한관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성 
 *  </pre>
 */
public interface EmplGrantService {
	
	
	//직원권한관리 현황 조회
	public List<Object> selectEmplGrantList(HashMap<String, Object> paramMap);
	
	//직원권한관리 저장 처리
	public void saveEmplGrant(HashMap<String, Object> paramMap);
		
}
