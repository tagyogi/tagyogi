package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-27
 * @version 1.0
 * @see 기재변경관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-27  김상민          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembDescMapper {

	//기재변경관리 현황 조회
	List<Map<String, Object>> selectMembDescList(HashMap paramMap);

	//기재변경관리 상세 조회
	HashMap selectMembDesc(HashMap paramMap);

	//기재변경관리 등록 처리
	void insertMembDesc(HashMap paramMap);

	//기재변경관리 수정 처리
	void updateMembDesc(HashMap paramMap);

	//기재변경관리 삭제 처리
	void deleteMembDesc(HashMap paramMap);

	//기재변경관리 key 조회(reqNo 채번)
	String selectMembDescReqNo();

	//대표자변경시 해당대표자가 입보한 한도거래약정해제
	void updateGuarContrCeoChg(HashMap paramMap);


}
