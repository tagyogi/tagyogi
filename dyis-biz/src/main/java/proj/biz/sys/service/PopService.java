package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * popService
 * @author 시스템 개발팀 윤가영1
 * @since 2023-11-01
 * @version 1.0
 * @see 팝업
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-11-01  윤가영1          최초 생성 
 *  </pre>
 */
public interface PopService {
	
	
	//팝업 현황 조회
	public List<Object> selectPopList(HashMap<String, Object> paramMap);
	
	//팝업 상세 조회
	public HashMap<String, Object>selectPop(HashMap<String, Object> paramMap);
	
	//팝업 등록 처리
	public void insertPop(HashMap<String, Object> paramMap);
	
	//팝업 수정 처리
	public void updatePop(HashMap<String, Object> paramMap);
		
		
}
