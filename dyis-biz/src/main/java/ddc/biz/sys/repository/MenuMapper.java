package ddc.biz.sys.repository;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;

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
public interface MenuMapper {
	
	/**
	 * <p> 현황 조회</p> 
	 *
	 * @param search 검색
	 * @return 목록
	 */
	public List selectMenuList(HashMap paramMap);
	
	/**
	 * <p> 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 상세
	 */
	public HashMap selectMenu(HashMap paramMap);
	
	
	
	/**
	 * <p> 등록</p>
	 *
	 * @param domain 도메인
	 * @return 수
	 */
	public int insertMenu(HashMap paramMap);

	/**
	 * <p> 수정</p>
	 *
	 * @param domain 도메인
	 * @return 수
	 */
	public int updateMenu(HashMap paramMap);

	/**
	 * <p> 삭제</p>
	 *
	 * @param domain 도메인
	 * @return 수
	 */
	public int deleteMenu(HashMap paramMap);
	
	
	/**
	 * <p> 즐겨찾기 현황 조회</p> 
	 *
	 * @param search 검색
	 * @return 목록
	 */
	public List selectMenuFavLst(String usrId);
	
	
}