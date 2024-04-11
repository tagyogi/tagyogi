package ddc.service.repository;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MenuMapper (메뉴관리 Mapper)</p>
 *
 * @author 		: BSG
 * date 		: 2022.07.05
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@DefaultConnMapper
@Repository
public interface DevDbMapper {
	
	/**
     * ######################################## 개발 데이터베이스 초기설정 영역 ################################################# 
     */
	//  테이블 정보 조회
    public HashMap selectTblInfo(Map<String, Object> pMap);
    
    public void insertDevDbMasInit(); //기존 테이블 정보 등록
    
    public void insertDevDbDtlInit(Map<String, Object> pMap); //기존 컬럼 정보 등록
	
	/**
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     */
	
    
    
	//  개발 데이터베이스 현황
    public List selectDevDbMasList(Map<String, Object> pMap);
    
    //  개발 데이터베이스 상세 정보
    public HashMap selectDevDbMasInfo(Map<String, Object> pMap);
    
    //  개발 데이터베이스 상세 현황
    public List selectDevDbDtlList(Map<String, Object> pMap);
    
    
	// DEV_DB_MAS 신규 등록
    public void insertDevDbMas(Map<String, Object> pMap);
    
    // DEV_DB_MAS 변경
    public void updateDevDbMas(Map<String, Object> pMap);
	
    // DEV_DB_MAS 삭제
    public void deleteDevDbMas(Map<String, Object> pMap);
	
    
    // DEV_DB_DTL 일괄삭제
    public void deleteDevDbDtlAll(Map<String, Object> pMap);
    
    // DEV_DB_DTL 등록
    public void insertDevDbDtl(Map<String, Object> pMap);
    
 	// DEV_DB_DTL 수정
    public void updateDevDbDtl(Map<String, Object> pMap);
    
    // 이전 테이블 필드 정보 추출
    public List selectBefTblColList(Map<String, Object> pMap);
    
    // 테이블 생성
    public void createDbsql(Map<String, Object> pMap);
    
    /**
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     */
    
 	// 테이블 데이터 추출
    public List selectTblList(Map<String, Object> pMap);
    
    //테이블 데이터 등록
    public void insertTblProc(Map<String, Object> pMap);
    
}