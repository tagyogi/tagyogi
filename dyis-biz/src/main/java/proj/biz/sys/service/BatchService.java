package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * batchService
 * @author 시스템 개발팀 변성균
 * @since 2023-08-03
 * @version 1.0
 * @see 배치관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-03  변성균          최초 생성 
 *  </pre>
 */
public interface BatchService {
	
	//배치관리 현황 조회
	public List<Object> selectBatchList(HashMap<String, Object> paramMap);
	
	//배치관리 상세 조회
	public HashMap<String, Object>selectBatch(HashMap<String, Object> paramMap);
	
	//배치관리 저장 처리
	public void saveBatch(HashMap<String, Object> paramMap);
	
	//배치관리 변경 적용 처리완료 업데이트
	public void updateBatchChgProc(HashMap<String, Object> paramMap);
			
}
