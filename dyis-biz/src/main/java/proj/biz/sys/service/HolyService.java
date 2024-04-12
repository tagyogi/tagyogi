package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * holyService
 * @author 시스템 개발팀 변성균
 * @since 2023-03-31
 * @version 1.0
 * @see 공휴일관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-31  변성균          최초 생성 
 *  </pre>
 */
public interface HolyService {
	//공휴일관리 현황 조회
	public List<Object> selectHolyList(HashMap<String, Object> paramMap);
	
	//공휴일관리 저장 처리
	public void saveHoly(HashMap<String, Object> paramMap);
	
}
