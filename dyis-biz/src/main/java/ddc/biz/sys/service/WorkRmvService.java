package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WorkRmvService
 * @author 시스템 개발팀 변성균
 * @since 2023-08-03
 * @version 1.0
 * @see 업무해지처리 
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-03  변성균          최초 생성 
 *  </pre>
 */
public interface WorkRmvService {
	
	//업무해지 처리
	public HashMap workRmvProc(HashMap paramMap);
			
}
