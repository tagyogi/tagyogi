package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-04
 * @version 1.0
 * @see 공제가입상담현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-04  김상민1          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembConsMapper {

	//공제가입상담현황 현황 조회
	List<Map<String, Object>> selectMembConsList(HashMap paramMap);

	//공제가입상담현황 상세 조회
	HashMap selectMembCons(HashMap paramMap);

	//공제가입상담현황 등록 처리
	void insertMembCons(HashMap paramMap);

	//공제가입상담현황 수정 처리
	void updateMembCons(HashMap paramMap);

	//공제가입상담현황 삭제 처리
	void deleteMembCons(HashMap paramMap);

	//상담내용 리스트 조회
	List<Map<String, Object>> selectMembConsDtlList(HashMap paramMap);

	//상담 진행내용 등록 처리
	void insertMembConsDtl(HashMap setMap);

	//상담 진행내용 수정 처리
	void updateMembConsDtl(HashMap paramMap);

	//상담 진행내용 삭제 처리
	void deleteMembConsDtl(HashMap paramMap);

	//공제가입상담(상담번호 채번)
	String selectMembConsNo();

}
