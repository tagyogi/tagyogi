package proj.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 직원관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface EmplMapper {
	
	//직원 현황 조회
	List<Object> selectEmplList(HashMap<String, Object> paramMap);
	
	//직원 상세 조회
	HashMap<String, Object>selectEmpl(HashMap<String, Object> paramMap);
		
	//직원 등록 처리
	void insertEmpl(HashMap<String, Object> paramMap);
	
	//직원 수정 처리
	void updateEmpl(HashMap<String, Object> paramMap);
	
	//직원정보수정 - 마이페이지(개인정보수정-단건)
	void updateMyInfo(HashMap<String, Object> paramMap);
	
	//직원 삭제 처리
	void deleteEmpl(HashMap<String, Object> paramMap);
	
		
}
