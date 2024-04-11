package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Map<String, Object>> selectHolyList(HashMap paramMap);
	
	//공휴일관리 저장 처리
	public void saveHoly(HashMap paramMap);
	
}
