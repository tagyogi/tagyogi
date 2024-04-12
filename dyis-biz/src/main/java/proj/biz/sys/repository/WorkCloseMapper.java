package proj.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 마감관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface WorkCloseMapper {
	
	//업무마감 현황 조회
	List<Object> selectWorkCloseList(HashMap<String, Object> paramMap);
	
	//업무마감 담당자 조회
	HashMap<String, Object>selectEmp(HashMap<String, Object> paramMap);
	
	//업무마감 전표 관리번호 추출
	String selectCloseToMaxNoDocu(HashMap<String, Object> paramMap);

	//업무마감 전표 처리
	void insertCloseToFiAdocu(HashMap<String, Object> paramMap);
	
	//업무마감 처리 부분
	
	// 출자금 마감
	int updateCloseInvest(HashMap<String, Object> paramMap);
	
	// 출자금반환 마감
	int updateCloseInvestRefund(HashMap<String, Object> paramMap);

	// 예수금/환불 마감
	int updateCloseDep(HashMap<String, Object> paramMap);

	// 보증수수료
	int updateCloseGuar(HashMap<String, Object> paramMap);

	// 보증수수료(미수)
	int updateCloseGuarAfter(HashMap<String, Object> paramMap);

	// 보증수수료(pg결제)
	int updateCloseGuarPg(HashMap<String, Object> paramMap);

	// 보증수수료 환불
	int updateCloseGuarChgRefund(HashMap<String, Object> paramMap);

	// 보증수수료 환불
	int updateCloseGuarRefund(HashMap<String, Object> paramMap);

	// 보증수수료 일부해제환불
	int updateCloseGuarPartRefund(HashMap<String, Object> paramMap);

	// 보증수수료(대충)
	int updateCloseGuarDebt(HashMap<String, Object> paramMap);

	// 보증수수료(해제)
	int updateCloseGuarCan(HashMap<String, Object> paramMap);

	// 보증수수료(일부해제)
	int updateCloseGuarPartCan(HashMap<String, Object> paramMap);

	// 융자지급
	int updateCloseLend(HashMap<String, Object> paramMap);
	
	// 융자이자
	int updateCloseLendInter(HashMap<String, Object> paramMap);
	
	// 융자상환
	int updateCloseLendRepay(HashMap<String, Object> paramMap);

	// 융자상환연체이자
	int updateCloseLendRepayDelay(HashMap<String, Object> paramMap);
	
		
}
