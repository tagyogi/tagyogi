package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kakaoService
 * @author 시스템 개발팀 변성균
 * @since 2023-09-07
 * @version 1.0
 * @see 카카오채널관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-07  변성균          최초 생성 
 *  </pre>
 */
public interface KakaoService {

	
	
	//카카오채널전송 현황 조회
	public List<Map<String, Object>> selectKakaoSndList(HashMap paramMap);

	//카카오채널관리 메시지 그룹 현황 조회
	public List<Map<String, Object>> selectKakaoTitlList(HashMap paramMap);
	
	//카카오채널관리 현황 조회
	public List<Map<String, Object>> selectKakaoList(HashMap paramMap);
	
	//카카오채널관리 상세
	public HashMap selectKakao(HashMap paramMap);
		
	//카카오채널관리 등록 처리
	public void insertKakao(HashMap paramMap);
	
	//카카오채널관리 수정 처리
	public void updateKakao(HashMap paramMap);
	
	//카카오채널관리 삭제 처리
	public void deleteKakao(HashMap paramMap);
		
	//카카오채널 전송 처리
	public HashMap sendKakao(HashMap paramMap);
		
	
}
