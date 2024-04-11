package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * confGuarPayRateService
 * @author 시스템 개발팀 변성균
 * @since 2023-05-22
 * @version 1.0
 * @see 선급금약정이자율
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균          최초 생성 
 *  </pre>
 */
public interface ConfGuarPayRateService {
	
	
	//선급금약정이자율 현황 조회
	public List<Map<String, Object>> selectConfGuarPayRateList(HashMap paramMap);
	
	//선급금약정이자율 상세 조회 (최종)
	public HashMap selectConfGuarPayRateMaxAppDt(HashMap paramMap);
		
	//선급금약정이자율 저장 처리
	public void saveConfGuarPayRate(HashMap paramMap);
		
		
}
