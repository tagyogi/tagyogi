package ddc.biz.login.repository;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>LoginMapper (로그처리 Mapper)</p>
 *
 * @author 		: BSG
 * date 		: 2023.01.04
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@DefaultConnMapper
@Repository
public interface LoginMapper {
	
	/**
	 * <p> 로그인 정보 조회</p> 
	 *
	 * @param search 검색
	 * @return 상세
	 */
	public HashMap selectCmEmployee(HashMap paramMap);
	
	/**
	 * <p> 로그인 로그 처리</p>
	 *
	 * @param domain 도메인
	 * @return 수
	 */
	public void insertLoginLog(HashMap paramMap);

}