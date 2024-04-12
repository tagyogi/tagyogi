package proj.service;

import java.util.HashMap;
import java.util.List;
/**
 * sampleService
 * @author 시스템 개발팀 DEV
 * @since 1900.01.01
 * @version 1.0
 * @see jobContent
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  1900.01.01  DEV          최초 생성 
 *  </pre>
 */
public interface SampleService {
	
	
	//jobContent 현황 조회
	public List<Object> selectSampleList(HashMap<String, Object> paramMap);
	
	//jobContent 상세 조회
	public HashMap<String, Object>selectSample(HashMap<String, Object> paramMap);
	
	//jobContent 저장 처리(그리드 일괄처리용 미사용시 삭제)
	public void saveSample(HashMap<String, Object> paramMap);
	
	//jobContent 등록 처리
	public void insertSample(HashMap<String, Object> paramMap);
	
	//jobContent 수정 처리
	public void updateSample(HashMap<String, Object> paramMap);
	
	//jobContent 삭제 처리
	public void deleteSample(HashMap<String, Object> paramMap);
		
		
}
