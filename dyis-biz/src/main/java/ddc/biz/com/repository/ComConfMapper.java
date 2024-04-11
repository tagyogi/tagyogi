package ddc.biz.com.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ComConfMapper {
	
	
	//민원발급 현황 조회(출자용)
	List<Map<String, Object>> selectComConfInvtList(HashMap paramMap);
		
	//민원발급 현황 조회(융자용)
	List<Map<String, Object>> selectComConfList(HashMap paramMap);
	
	//민원발급 현황 조회(보증용)
	List<Map<String, Object>> selectComConfGuarList(HashMap paramMap);
	
	//민원발급 등록 처리
	void insertComConf(HashMap paramMap);
	
	//민원발급 수정 처리
	void updateComConf(HashMap paramMap);
	
	//민원발급 삭제 처리
	void deleteComConf(HashMap paramMap);
	
	//증명서발급완료처리
	void updateConfProcStat(HashMap paramMap);

	//==============================================================
	//홈페이지 민원발급 현황 조회
	List<Map<String, Object>> selectHomeComConfList(HashMap paramMap);

	//홈페이지 민원발급 현황 - 총 페이지 수
	int totalPage(HashMap paramMap);
	
	//민원발급 발급일자 저장
	void updateComConfPubDt(HashMap paramMap);
	
	//홈페이지 민원발급 삭제 처리
	void deleteHomeComConf(HashMap paramMap);
		
}
