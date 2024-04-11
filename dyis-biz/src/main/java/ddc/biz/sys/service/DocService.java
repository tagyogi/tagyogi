package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * docService
 * @author 시스템 개발팀 변성균
 * @since 2023-09-05
 * @version 1.0
 * @see 서식관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-05  변성균          최초 생성 
 *  </pre>
 */
public interface DocService {
	
	
	//서식관리 현황 조회
	public List<Map<String, Object>> selectDocList(HashMap paramMap);
	
	//서식관리 등록 처리
	public void insertDoc(HashMap paramMap, List<MultipartFile> fileList);
	
	//서식관리 수정 처리
	public void updateDoc(HashMap paramMap, List<MultipartFile> fileList);
	
	//서식관리 삭제 처리
	public void deleteDoc(HashMap paramMap);
		
		
}
