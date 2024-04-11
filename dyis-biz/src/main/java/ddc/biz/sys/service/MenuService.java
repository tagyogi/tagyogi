package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MenuService (메뉴관리 Service)</p>
 *
 * @author 		: BSG
 * date 		: 2022.07.05
 *
 * modifier 	:
 * modify-date  :
 * description  : 메뉴관리관리
 */
public interface MenuService {
	
	//메뉴관리 현황 조회
    public List selectMenuList(HashMap paramMap);
    
    //메뉴관리 상세 조회
    public HashMap selectMenu(HashMap paramMap);
    
    //메뉴관리 저장
	public void saveMenu(HashMap paramMap);
	
    
	//즐겨찾기 현황 조회
    public List selectMenuFavLst(String usrId);
    
    
}