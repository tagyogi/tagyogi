package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kocfcService
 * @author 시스템 개발팀 변성균
 * @since 2023-03-29
 * @version 1.0
 * @see 조합관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-29  변성균          최초 생성 
 *  </pre>
 */
public interface KocfcService {
	
	
	//조합정보 현황 조회
	public List<Map<String, Object>> selectKocfcList(HashMap paramMap);
	
	//조합정보 상세 조회
	public HashMap selectKocfc();
		
	//조합정보 저장 처리
	public void saveKocfc(HashMap paramMap);
		
		
}
