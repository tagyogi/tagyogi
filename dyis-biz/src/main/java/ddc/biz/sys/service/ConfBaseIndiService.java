package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * confBaseIndiService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 기준지표관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성 
 *  </pre>
 */
public interface ConfBaseIndiService {
	
	
	//기준지표관리 현황 조회
	public List<Map<String, Object>> selectConfBaseIndiList(HashMap paramMap);
	
	//기준지표관리 상세 조회
	public HashMap selectConfBaseIndi(HashMap paramMap);
	
	//기준지표관리 저장 처리
	public void saveConfBaseIndi(HashMap paramMap);

	public List<Map<String, Object>> selectConfBaseIndiMaxDt(HashMap paramMap);
	
}
