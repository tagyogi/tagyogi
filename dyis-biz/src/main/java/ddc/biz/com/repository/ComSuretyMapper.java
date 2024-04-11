package ddc.biz.com.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-09
 * @version 1.0
 * @see 공통보증인
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-09  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface ComSuretyMapper {
	
	//공통보증인 상세 조회
	HashMap selectComSuretyChk(HashMap paramMap);
		
	//공통보증인 등록 처리
	void insertComSurety(HashMap paramMap);
	
	//공통보증인 수정 처리
	void updateComSurety(HashMap paramMap);
	
	//공통보증인 삭제 처리
	void deleteComSurety(HashMap paramMap);
	
		
}
