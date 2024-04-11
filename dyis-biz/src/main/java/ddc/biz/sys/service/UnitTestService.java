package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * unitTestService
 * @author 시스템 개발팀 변성균
 * @since 2023-08-08
 * @version 1.0
 * @see 검수관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-08  변성균          최초 생성 
 *  </pre>
 */
public interface UnitTestService {
	
	
	//검수관리 현황 조회
	public List<Map<String, Object>> selectUnitTestList(HashMap paramMap);
	
	//검수관리 저장 처리
	public void saveUnitTest(HashMap paramMap);
	
	//단위테스트현황 현황 조회
	public List<Map<String, Object>> selectUnitTestCaseList(HashMap paramMap);
	
	//단위테스트현황 저장 처리
	public void saveUnitTestCase(HashMap paramMap);
	
	//단위별요청사항 현황 조회
	public List<Map<String, Object>> selectUnitTestReqList(HashMap paramMap);
	
	//단위별요청사항 저장 처리
	public void saveUnitTestReq(HashMap paramMap);
	
}
