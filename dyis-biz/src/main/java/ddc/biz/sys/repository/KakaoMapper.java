package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface KakaoMapper {
	
	//카카오채널전송 현황 조회
	List<Map<String, Object>> selectKakaoSndList(HashMap paramMap);
	
	//카카오채널관리 제목 그룹 현황
	List<Map<String, Object>> selectKakaoTitlList(HashMap paramMap);
	
	//카카오채널관리 현황 조회
	List<Map<String, Object>> selectKakaoList(HashMap paramMap);
	
	//카카오채널관리 현황 조회
	HashMap selectKakao(HashMap paramMap);
		
	//카카오채널관리 등록 처리
	void insertKakao(HashMap paramMap);
	
	//카카오채널관리 수정 처리
	void updateKakao(HashMap paramMap);
	
	//카카오채널관리 삭제 처리
	void deleteKakao(HashMap paramMap);
	
	//카카오채널관리 로그 등록 처리
	void insertKakaoLog(HashMap paramMap);
		
	//카카오채널관리 전송 등록
	void insertBizMsg(HashMap paramMap);
		
}
