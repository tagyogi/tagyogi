package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 조합계좌Service
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 조합계좌관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성 
 *  </pre>
 */
public interface KocfcAccService {
	
	
	//조합계좌 현황 조회
	public List<Map<String, Object>> selectKocfcAccList(HashMap paramMap);

	//조합계좌 상세 조회
	public HashMap selectKocfcAcc(HashMap paramMap);

	//조합계좌 저장 처리
	public void saveKocfcAcc(HashMap paramMap);
		
		
}
