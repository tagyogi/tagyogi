package ddc.biz.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
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
    public List selectMembList(HashMap paramMap);
    
    //조합원 정보 상세
    public HashMap selectMemb(HashMap paramMap);
    
    //조합원 정보 상세(사업자번호)
    public HashMap selectMembBizNo(HashMap paramMap);
 	
    //공통코드 현황 조회
    public List selectCodeList(HashMap paramMap);
    
    //공통코드 명 조회
    public String selectCodeDtlNm(HashMap paramMap);
  	
    //공통코드 상세 조회
    public HashMap selectCodeDtl(HashMap paramMap);
  	
  	//그리드 정보 조회
    public List selectGridDtlList(HashMap paramMap);
    
    //엑셀 다운로드 현황 조회
    public List<Map<String, Object>> selectExcelDownList(HashMap paramMap);
    
    //민원신청조회
    public HashMap selectWorkReq(HashMap paramMap);
    
    //첨부파일관리번호 추출
    public String selectAtthNo(HashMap paramMap);
    
    //공통 시퀀스번호 추출
    public String selectSeqNo(String bussGbn, String year);
    
    //보증인정보조회 (보증/융자 약정 보증인 관련)
    public HashMap selectSure(HashMap paramMap);
    
    //즐겨찾기 목록
    public List selectUserMenuFavList(String userId);
    
    //즐겨찾기 등록
    public void insertUserMenuFav(HashMap paramMap);
    
    //공인인증서 조회
    public HashMap selectMembCert(HashMap paramMap);
    
    //공인인증서 등록
    public void insertMembCert(HashMap paramMap);
    
    //업무메모 조회
    public List selectUserMemoList(HashMap paramMap);
    
    //업무메모상세
    public HashMap selectUserMemo(HashMap paramMap);
    
    //업무메모 등록
    public void insertUserMemo(HashMap paramMap);
    
    //업무메모 수정, 삭제
    public void updateUserMemo(HashMap paramMap);
    
    //기준일 영업일 여부 조회
    public HashMap seletWorkDay(HashMap paramMap);
    
    //모바일 로그인 조합원 조회
    public HashMap selectMembById(HashMap paramMap);
    
    //(메인통계) 조합원현황
    public HashMap selectMainStatMemb(HashMap paramMap);
    
    //(메인통계) 당일보증현황
    public List selectMainStatGuar(HashMap paramMap);
    
    //(메인통계) 보증실적현황
    public List selectMainStatGuarPref(HashMap paramMap);

    //(메인통계) 자본금현황
    public HashMap selectMainStatInvtList(HashMap paramMap);
    
    //알림톡수신자 현황 조회
    public List selectTalkPersList(HashMap paramMap);
    
}