package proj.biz.com.repository;

import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>ComMapper (공통관리 Mapper)</p>
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
public interface ComMapper {
	
	/**
	 * <p> 조합원 정보 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List<HashMap<String, Object>> selectMembList(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 조합원 정보 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap<String, Object>selectMemb(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 조합원 정보 상세 조회(사업자번호)</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap<String, Object>selectMembBizNo(HashMap<String, Object> paramMap);
	
	
	/**
	 * <p> 조합원 정보 상세 조회(인증서)</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap<String, Object>selectMembCert(HashMap<String, Object> paramMap);
	
	
	/**
	 * <p> 공통코드 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 공통코드 명 조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public String selectCodeDtlNm(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 공통코드 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public HashMap<String, Object>selectCodeDtl(HashMap<String, Object> paramMap);
	
	
	/**
	 * <p> 공통코드 기타코드조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public String selectCodeEtcCd(HashMap<String, Object> paramMap);
	
	
	/**
	 * <p> 그리드 정보 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List<HashMap<String, Object>> selectGridDtlList(HashMap<String, Object> paramMap);
	
	
	/**
	 * <p> 첨부파일관리번호 추출</p> 
	 *
	 * @param 첨부유형
	 * @return 관리번호
	 */
	public String selectAtthNo(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 시퀀스번호 추출</p> 
	 *
	 * @param 구분
	 * @return 시퀀스번호
	 */
	public String selectSeqNo(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 즐겨찾기 현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List<HashMap<String, Object>> selectUserMenuFavList(String userId);
	
	/**
	 * <p> 즐겨찾기 등록</p> 
	 *
	 * @param 
	 * @return 등록
	 */
	public void insertUserMenuFav(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 기준일 영업일 여부 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap<String, Object>seletWorkDay(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 모바일 로그인 조합원 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap<String, Object>selectMembById(HashMap<String, Object> paramMap);
	
}