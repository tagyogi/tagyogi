package proj.biz.com.service;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
//ComService (공통 관리 Service)
 *
 * @author 		: BSG
 * date 		: 2022.07.05
 *
 * modifier 	:
 * modify-date  :
 * description  : 로그인 처리
 */
public interface ComService {
	
	//조합원 정보 조회
    public List<HashMap<String, Object>> selectMembList(HashMap<String, Object> paramMap);
    
    //조합원 정보 상세
    public HashMap<String, Object>selectMemb(HashMap<String, Object> paramMap);
    
    //조합원 정보 상세(사업자번호)
    public HashMap<String, Object>selectMembBizNo(HashMap<String, Object> paramMap);
 	
    //공통코드 현황 조회
    public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> paramMap);
    
    //공통코드 명 조회
    public String selectCodeDtlNm(HashMap<String, Object> paramMap);
  	
    //공통코드 상세 조회
    public HashMap<String, Object> selectCodeDtl(HashMap<String, Object> paramMap);
  	
  	//그리드 정보 조회
    public List<HashMap<String, Object>> selectGridDtlList(HashMap<String, Object> paramMap);
    
    //엑셀 다운로드 현황 조회
    public List<HashMap<String, Object>> selectExcelDownList(HashMap<String, Object> paramMap);
    
    //첨부파일관리번호 추출
    public String selectAtthNo(HashMap<String, Object> paramMap);
    
    //공통 시퀀스번호 추출
    public String selectSeqNo(String bussGbn, String year);
    //즐겨찾기 목록
    public List<HashMap<String, Object>> selectUserMenuFavList(String userId);
    
    //즐겨찾기 등록
    public void insertUserMenuFav(HashMap<String, Object> paramMap);
    
    //기준일 영업일 여부 조회
    public HashMap<String, Object>seletWorkDay(HashMap<String, Object> paramMap);
    
    //모바일 로그인 조합원 조회
    public HashMap<String, Object>selectMembById(HashMap<String, Object> paramMap);
    
    
}