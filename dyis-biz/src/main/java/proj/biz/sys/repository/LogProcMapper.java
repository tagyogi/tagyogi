package proj.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-21
 * @version 1.0
 * @see 업무처리로그
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-21  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface LogProcMapper {
	
	//로그인이력 현황 조회
	List<Object> selectLogLoginList(HashMap<String, Object> paramMap);	
	
	//업무처리로그 현황 조회
	List<Object> selectLogProcList(HashMap<String, Object> paramMap);
	
	//배치로그이력 현황 조회
	List<Object> selectLogBatchList(HashMap<String, Object> paramMap);	
	
	//오류로그이력 현황 조회
	List<Object> selectLogErrList(HashMap<String, Object> paramMap);	
	
	//업무처리로그 상세 조회
	HashMap<String, Object>selectLogProc(HashMap<String, Object> paramMap);
		
	//업무처리로그 등록 처리
	void insertLogProc(HashMap<String, Object> paramMap);
	
	//배치로그 등록 처리
	void insertLogBatch(HashMap<String, Object> paramMap);

	//배치로그 결과 업데이트 처리
	void updateLogBatch(HashMap<String, Object> paramMap);

	//업무처리오류로그 등록 처리
	void insertLogErr(HashMap<String, Object> paramMap);

	
	
}
