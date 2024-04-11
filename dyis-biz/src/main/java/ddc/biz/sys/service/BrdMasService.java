package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * brdMasService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 통합게시판관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성 
 *  </pre>
 */
public interface BrdMasService {
	
	
	//통합게시판관리 현황 조회
	public List<Map<String, Object>> selectBrdMasList(HashMap paramMap);
	
	//통합게시판관리 상세 조회
	public HashMap selectBrdMas(HashMap paramMap);
	
	//통합게시판관리 저장 처리
	public void saveBrdMas(HashMap paramMap);
	
	//통합게시판관리 등록 처리(단건)
	public void insertBrdMas(HashMap paramMap);
	
	//통합게시판관리 수정 처리(단건)
	public void updateBrdMas(HashMap paramMap);
	
}
