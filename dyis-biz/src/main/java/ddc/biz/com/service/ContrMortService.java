package ddc.biz.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * contrMortService
 * @author 시스템 개발팀 변성균
 * @since 2023-06-22
 * @version 1.0
 * @see 약정담보정보
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-22  변성균          최초 생성 
 *  </pre>
 */
public interface ContrMortService {
	
	
	//약정담보정보 현황 조회
	public List<Map<String, Object>> selectContrMortList(HashMap paramMap);
	
	
		
		
	
}
