package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mngReqService
 * @author 시스템 개발팀 변성균
 * @since 2023-12-11
 * @version 1.0
 * @see 보안요청관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-12-11  변성균          최초 생성 
 *  </pre>
 */
public interface MngReqService {
	
	
	//보안요청관리 현황 조회
	public List<Map<String, Object>> selectMngReqList(HashMap paramMap);
	
	//보안요청관리 상세 조회
	public HashMap selectMngReq(HashMap paramMap);
	
	//보안요청관리 등록 처리
	public void insertMngReq(HashMap paramMap);
	
	//보안요청관리 수정 처리(코멘트)
	public void updateMngReqComent(HashMap paramMap);
	
	//보안요청관리 수정 처리(처리)
	public void updateMngReqProc(HashMap paramMap);

	//보안요청관리 삭제 처리
	public void deleteMngReq(HashMap paramMap);
		
		
}
