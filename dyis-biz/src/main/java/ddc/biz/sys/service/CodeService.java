package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * codeService
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 공통코드 관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균          최초 생성 
 *  </pre>
 */
public interface CodeService {
	
	
	//공통코드관리 현황 조회
	public List<Map<String, Object>> selectCodeList(HashMap paramMap);
	
	//공통코드관리 상세 현황 조회
	public List<Map<String, Object>> selectCodeDtlList(HashMap paramMap);
	
	//공통코드관리 저장 처리
	public void saveCode(HashMap paramMap);
	
	
}
