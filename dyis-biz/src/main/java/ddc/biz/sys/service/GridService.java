package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * gridService
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 현황화면그리드옵션관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균          최초 생성 
 *  </pre>
 */
public interface GridService {
	
	
	//그리드관리 현황 조회
	public List<Map<String, Object>> selectGridList(HashMap paramMap);
	
	//그리드관리 상세 현황 조회
	public List<Map<String, Object>> selectGridDtlList(HashMap paramMap);
		
	//그리드관리 저장 처리
	public void saveGrid(HashMap paramMap);
	
	//그리드관리 열 저장 처리
	public void saveGridCols(HashMap paramMap);
	
	//그리드관리 열 초기 생성
	public void createGridCols(HashMap paramMap);
		
	
			
}