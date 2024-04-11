package ddc.biz.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * memoService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 메모(개인)
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성 
 *  </pre>
 */
public interface MemoService {
	
	
	//memo 현황 조회
	public List<Map<String, Object>> selectMemoList(HashMap paramMap);
	
	//memo 상세 조회
	public HashMap selectMemo(HashMap paramMap);
	
	//memo 등록 처리(단건)
	public void insertMemo(HashMap paramMap);
	
	//memo 수정 처리(단건)
	public void updateMemo(HashMap paramMap);
	
	//memo 삭제 처리(단건)
	public void deleteMemo(HashMap paramMap);
		
		
}
