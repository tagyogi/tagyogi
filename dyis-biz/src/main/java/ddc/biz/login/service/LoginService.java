package ddc.biz.login.service;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>loginService (로그인 처리 Service)</p>
 *
 * @author 		: BSG
 * date 		: 2022.07.05
 *
 * modifier 	:
 * modify-date  :
 * description  : 로그인 처리
 */
public interface LoginService {
	
	/**
     * <p>로그인 정보 조회</p>
     *
     * @return 상세
     */
    public HashMap selectCmEmployee(HashMap paramMap);
    
	/**
	 * <p>로그인 로그 저장</p>
	 *
	 * @param domain 도메인
	 * @return 수
	 */
	public void insertLoginLog(HashMap paramMap);
	
    
}