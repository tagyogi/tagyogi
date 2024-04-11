package ddc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 공통서비스 개발팀 bsg
 * @since 2022.02
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2022.02  bsg          최초 생성 
 *  </pre>
 */
public interface DevDbService {
	
	// 개발 데이터베이스 초기 생성
	public void createDevDbInit(Map<String, Object> pMap) throws Exception;
		
	// 테이블 정보 조회
	public HashMap selectTblInfo(Map<String, Object> pMap) throws Exception;
    
	// 개발 데이터베이스 현황
	public List<HashMap> selectDevDbMasList(Map<String, Object> pMap) throws Exception;
    
	// 개발 데이터베이스 정보 상세
	public HashMap selectDevDbMasInfo(Map<String, Object> pMap) throws Exception;
    
    // 개발 데이터베이스 상세 현황
	public List<HashMap> selectDevDbDtlList(Map<String, Object> pMap) throws Exception;
    
    // 개발 데이터베이스  정보 등록 처리
	public void saveDevDbMasProc(Map<String, Object> pMap) throws Exception;
    
    // 개발 데이터베이스  상세 정보 등록 처리
	public void saveDevDbDtlProc(Map<String, Object> pMap) throws Exception;
    
    // 개발 데이터베이스  삭제 처리
	public void deleteDevDbProc(Map<String, Object> pMap) throws Exception;
    
    
    // 테이블 이전 필드 정보 조회
	public List<HashMap> selectBefTblColList(Map<String, Object> pMap) throws Exception;
    
    // 개발 데이터베이스 생성 처리
	public void createTblProc(Map<String, Object> pMap) throws Exception;
    
    // 테이블 데이터 export
	public List<HashMap> selectTblList(Map<String, Object> pMap) throws Exception;
    
    // 테이블 데이터 import
	public void importTblProc(Map<String, Object> pMap) throws Exception;
    
    // 테이블 필드 정보 초기화 등록
	public void insertColsInitProc(Map<String, Object> pMap) throws Exception;
    
	
}
