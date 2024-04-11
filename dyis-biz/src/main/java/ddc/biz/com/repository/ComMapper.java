package ddc.biz.com.repository;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
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
	public List selectMembList(HashMap paramMap);
	
	/**
	 * <p> 조합원 정보 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMemb(HashMap paramMap);
	
	/**
	 * <p> 조합원 정보 상세 조회(사업자번호)</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMembBizNo(HashMap paramMap);
	
	
	/**
	 * <p> 조합원 정보 상세 조회(인증서)</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMembCert(HashMap paramMap);
	
	/**
	 * <p> 인증서 정보 등록</p> 
	 *
	 */
	public void insertMembCert(HashMap paramMap);
	
	
	/**
	 * <p> 공통코드 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectCodeList(HashMap paramMap);
	
	/**
	 * <p> 공통코드 명 조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public String selectCodeDtlNm(HashMap paramMap);
	
	/**
	 * <p> 공통코드 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public HashMap selectCodeDtl(HashMap paramMap);
	
	
	/**
	 * <p> 공통코드 기타코드조회</p> 
	 *
	 * @param search 검색
	 * @return 
	 */
	public String selectCodeEtcCd(HashMap paramMap);
	
	
	/**
	 * <p> 그리드 정보 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectGridDtlList(HashMap paramMap);
	
	/**
	 * <p> 민원신청 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectWorkReq(HashMap paramMap);
	
	/**
	 * <p> 첨부파일관리번호 추출</p> 
	 *
	 * @param 첨부유형
	 * @return 관리번호
	 */
	public String selectAtthNo(HashMap paramMap);
	
	/**
	 * <p> 시퀀스번호 추출</p> 
	 *
	 * @param 구분
	 * @return 시퀀스번호
	 */
	public String selectSeqNo(HashMap paramMap);
	
	/**
	 * <p> 보증인정보조회 (보증/융자 약정 보증인 관련)</p> 
	 *
	 * @param search 검색
	 * @return 상세
	 */
	public HashMap selectSure(HashMap paramMap);
	
	/**
	 * <p> 즐겨찾기 현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectUserMenuFavList(String userId);
	
	/**
	 * <p> 즐겨찾기 등록</p> 
	 *
	 * @param 
	 * @return 등록
	 */
	public void insertUserMenuFav(HashMap paramMap);
	
	
	
	
	/**
	 * <p> 업무메모 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectUserMemoList(HashMap paramMap);
	
	
	/**
	 * <p> 업무메모 상세 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectUserMemo(HashMap paramMap);
	
	
	/**
	 * <p> 업무메모 등록</p> 
	 *
	 * @param 
	 * @return 등록
	 */
	public void insertUserMemo(HashMap paramMap);
	
	
	/**
	 * <p> 업무메모 수정, 삭제</p> 
	 *
	 * @param 
	 * @return 
	 */
	public void updateUserMemo(HashMap paramMap);
	
	/**
	 * <p> 기준일 영업일 여부 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap seletWorkDay(HashMap paramMap);
	
	/**
	 * <p> 모바일 로그인 조합원 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMembById(HashMap paramMap);
	
	/** ################## 메인 통계 쿼리 시작~!  */
	
	/**
	 * <p> 조합원현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMainStatMemb(HashMap paramMap);
	
	
	/**
	 * <p> 당일보증현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectMainStatGuar(HashMap paramMap);
	
	
	/**
	 * <p> 보증실적현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List<Map<String, Object>> selectMainStatGuarPref(HashMap paramMap);
	
	
	/**
	 * <p> 자본금현황</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public HashMap selectMainStatInvtList(HashMap paramMap);
	
	
	/**
	 * <p> 알림톡 수신자 조회</p> 
	 *
	 * @param search 검색
	 * @return 현황
	 */
	public List selectTalkPersList(HashMap paramMap);
	
}