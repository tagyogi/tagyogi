package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembAccMapper {
	
	//조합원계좌신청 현황 조회
	List<Map<String, Object>> selectMembAccList(HashMap paramMap);
	
	//조합원계좌 현황 조회(mb_acc)
	List<Map<String, Object>> selectMembAccMngList(HashMap paramMap);
	
	//조합원계좌 상세 조회
	HashMap selectMembAcc(HashMap paramMap);
		
	//조합원계좌신청 등록 처리
	void insertMembAcc(HashMap paramMap);
	
	//조합원계좌신청 수정 처리
	void updateMembAcc(HashMap paramMap);
	
	//조합원계좌신청 삭제 처리
	void deleteMembAcc(HashMap paramMap);
	
	//조합원계좌관리 계좌등록 처리
	void insertMembAccMng(HashMap paramMap);
	
	//조합원계좌 이력 등록 처리
	void insertMembAccHist(HashMap paramMap);
	
	//조합원계좌관리 계좌등록 카운트
	int selectMembAccMngCnt(HashMap paramMap);
	
	//조합원계좌관리 수정 처리
	void updateMembAccMng(HashMap paramMap);
			
}
