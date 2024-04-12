package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * logProcService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-21
 * @version 1.0
 * @see 업무처리로그
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-21  변성균          최초 생성 
 *  </pre>
 */
public interface LogProcService {
	
	//로그인이력 현황 조회
	public List<Object> selectLogLoginList(HashMap<String, Object> paramMap);
	
	//업무로그이력 현황 조회
	public List<Object> selectLogProcList(HashMap<String, Object> paramMap);
	
	//배치로그이력 현황 조회
	public List<Object> selectLogBatchList(HashMap<String, Object> paramMap);
	
	//오류로그 현황 조회
	public List<Object> selectLogErrList(HashMap<String, Object> paramMap);
		
	
	//업무처리로그 상세 조회
	public HashMap<String, Object>selectLogProc(HashMap<String, Object> paramMap);
	
	//업무처리로그 등록 처리(단건)
	public void insertLogProc(HttpServletRequest request, HashMap<String, Object> paramMap);
	
	//배치로그 등록 처리(단건)
	public void insertLogBatch(HashMap<String, Object> paramMap);

	//배치로그 결과 업데이트 처리(단건)
	public void updateLogBatch(HashMap<String, Object> paramMap);

	//업무처리오류로그 등록 처리(단건)
	public void insertLogErr(HttpServletRequest request, HashMap<String, Object> paramMap, String errMsg);
	
	//업무처리오류로그 등록 처리(단건)
	public void insertLogErr(HashMap<String, Object> paramMap, String errMsg);
}
