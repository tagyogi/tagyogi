package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membAccService
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원계좌
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1          최초 생성 
 *  </pre>
 */
public interface MembAccService {
	
	
	//조합원계좌신청 현황 조회
	public List<Map<String, Object>> selectMembAccList(HashMap paramMap);
	
	//조합원계좌 조회(mb_acc)
	public List<Map<String, Object>> selectMembAccMngList(HashMap paramMap);
	
	//조합원계좌 상세 조회
	public HashMap selectMembAcc(HashMap paramMap);
	
	//조합원계좌 등록 처리
	public void insertMembAcc(HashMap paramMap);
	
	//조합원계좌 수정 처리
	public void updateMembAcc(HashMap paramMap);
	
	//조합원계좌 삭제 처리
	public void deleteMembAcc(HashMap paramMap);
	
	//조합원계좌관리 신규등록 처리
	public void insertMembAccMng(HashMap paramMap);
	
	//조합원계좌관리 수정 처리 (단건)
	public void updateMembAccMng(HashMap paramMap);
		
		
}
