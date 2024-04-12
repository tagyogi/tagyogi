package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * reportService
 * @author 시스템 개발팀 변성균
 * @since 2023-05-18
 * @version 1.0
 * @see 레포트관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-18  변성균          최초 생성 
 *  </pre>
 */
public interface ReportService {
	
	
	//레포트관리 현황 조회
	public List<Object> selectReportList(HashMap<String, Object> paramMap);
	
	//레포트관리 등록 처리(단건)
	public void insertReport(HashMap<String, Object> paramMap, List<MultipartFile> fileList);
	
	//레포트관리 수정 처리(단건)
	public void updateReport(HashMap<String, Object> paramMap, List<MultipartFile> fileList);
	
	//레포트관리 삭제 처리(단건)
	public void deleteReport(HashMap<String, Object> paramMap);
		
		
}
