package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * confLendService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 융자상품한도관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성 
 *  </pre>
 */
public interface ConfLendService {
	
	
	//대출상품배수관리 현황 조회
	public List<Map<String, Object>> selectConfLendList(HashMap paramMap);
	
	//대출상품배수관리 상세 조회
	public HashMap selectConfLend(HashMap paramMap);
	
	//대출상품배수관리 상세 조회(최종)
	public HashMap selectConfLendMaxAppDt(HashMap paramMap);
		
	//대출상품배수관리 저장 처리
	public void saveConfLend(HashMap paramMap);
	
	
	
}
