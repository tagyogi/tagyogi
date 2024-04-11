package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-04
 * @version 1.0
 * @see 조합가입신청
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-04  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembReqMapper {

	//조합가입신청 현황 조회
	List<Map<String, Object>> selectMembReqList(HashMap paramMap);

	//조합가입신청 상세 조회
	HashMap selectMembReq(HashMap paramMap);

	//조합가입신청 등록 처리
	void insertMembReq(HashMap paramMap);

	//조합가입신청 수정 처리
	void updateMembReq(HashMap paramMap);

	//조합가입신청 삭제 처리
	void deleteMembReq(HashMap paramMap);

	//조합가입신청 등록 이력 처리
	void insertMembReqHist(HashMap paramMap);

	//조합가입신청(상담, 가입, 조합원) 현황 조회(통합)
	List<Map<String, Object>> selectMembIntegList(HashMap paramMap);

	//조합가입신청(상담, 가입, 조합원) 상세 조회(통합)
	//HashMap selectMembInteg(HashMap paramMap);


	//조합원 번호 발번
	int selectMaxMembNo();

	//공제가입상담 여부 조회
	HashMap selectMembCounsel(HashMap paramMap);

	//공제가입신청 조회
	HashMap selectMembJoin(HashMap paramMap);
}
