package ddc.biz.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * comConfService
 * @author 시스템 개발팀 최진호
 * @since 2023-06-13
 * @version 1.0
 * @see 민원발급
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-13  최진호          최초 생성 
 *  </pre>
 */
public interface ComConfService {
	
	//민원발급 현황 조회 (출자용)
	public List<Map<String, Object>> selectComConfInvtList(HashMap paramMap);
		
	//민원발급 현황 조회 (융자용)
	public List<Map<String, Object>> selectComConfList(HashMap paramMap);
	
	//민원발급 현황 조회 (보증용)
	public List<Map<String, Object>> selectComConfGuarList(HashMap paramMap);
	
	//민원발급 등록 처리(단건)
	public void insertComConf(HashMap paramMap);
	
	//민원발급 수정 처리(단건)
	public void updateComConf(HashMap paramMap);
	
	//민원발급 삭제 처리(단건)
	public void deleteComConf(HashMap paramMap);
	
	//증명서발급완료처리
	public void updateConfProcStat(HashMap paramMap);

	//==================================================
	//홈페이지 민원발급 현황 조회
	public List<Map<String, Object>> selectHomeComConfList(HashMap paramMap);

	//홈페이지 민원발급 현황 - 총 페이지 수
	public int totalPage(HashMap paramMap);
	
	//민원발급 발급일자저장 처리(단건)
	public void updateComConfPubDt(HashMap paramMap);

	//홈페이지 민원발급 삭제 처리(단건)
	public void deleteHomeComConf(HashMap paramMap);
}
