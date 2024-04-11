package ddc.biz.com.service;

import org.apache.commons.jexl.util.introspection.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.com.repository.ComMapper;
import ddc.biz.login.repository.LoginMapper;
import ddc.biz.sys.repository.CodeMapper;
import ddc.biz.sys.repository.GridMapper;
import ddc.biz.sys.repository.KocfcMapper;
import ddc.biz.sys.repository.MenuMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

/**
 *<p>ComServiceImpl </p>
 *
 * @author 	: 변성균 
 * date 	: 2022.06.24
 * modifier :
 * modify-date :
 * description : 공통관리 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ComServiceImpl implements ComService {

	private static final Logger logger = LoggerFactory.getLogger(ComServiceImpl.class);
	
	@Autowired
	private ComMapper comMapper;

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private KocfcMapper kocfcMapper;
	
	@Autowired
	private CodeMapper codeMapper;
	
	@Autowired
	private GridMapper gridMapper;
	
	
	
	/**
	 * <p>조합원 정보 조회 </p>
	 *
	 * @see ComServiceImpl#selectMembList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectMembList(HashMap paramMap) {
		return comMapper.selectMembList(paramMap);
	} 
	
	/**
	 * <p>조합원 정보 상세 조회 </p>
	 *
	 * @see ComServiceImpl#selectMemb()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectMemb(HashMap paramMap) {
		return comMapper.selectMemb(paramMap);
	} 
	
	/**
	 * <p>조합원 정보 상세 조회(사업자번호) </p>
	 *
	 * @see ComServiceImpl#selectMemb()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectMembBizNo(HashMap paramMap) {
		return comMapper.selectMembBizNo(paramMap);
	} 
	
	/**
	 * <p>공통코드 조회 </p>
	 *
	 * @see ComServiceImpl#selectCodeList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectCodeList(HashMap paramMap) {
		return comMapper.selectCodeList(paramMap);
	} 
	
	/**
	 * <p>공통코드 명 조회</p>
	 *
	 * @see ComServiceImpl#selectCodeDtlNm()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public String selectCodeDtlNm(HashMap paramMap) {
		return comMapper.selectCodeDtlNm(paramMap);
	} 
	
	/**
	 * <p>공통코드 상세 조회</p>
	 *
	 * @see ComServiceImpl#selectCodeDtl()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectCodeDtl(HashMap paramMap) {
		return comMapper.selectCodeDtl(paramMap);
	} 
	
	
	
	/**
	 * <p>그리드 정보 조회 </p>
	 *
	 * @see ComServiceImpl#selectGridDtlList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectGridDtlList(HashMap paramMap) {
		return comMapper.selectGridDtlList(paramMap);
	} 
	
	/**
	 * <p>엑셀 다운로드 현황 조회 </p>
	 *
	 * @see ComServiceImpl#selectExcelDownList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectExcelDownList(HashMap paramMap) {
		List dataList = new ArrayList();
		
		try {
			String excelMth = (String)paramMap.get("excelMth");
			//logger.debug("excelDown excelMth :: " + excelMth );
			
			Object objMapper = null; 
			if(excelMth.toUpperCase().indexOf("MENU") > -1) objMapper = menuMapper; //메뉴현황
			if(excelMth.toUpperCase().indexOf("KOCFC") > -1) objMapper = kocfcMapper; //조합현황
			if(excelMth.toUpperCase().indexOf("CODEDTL") > -1) objMapper = codeMapper; //코드상세
			if(excelMth.toUpperCase().indexOf("GRIDDTL") > -1) objMapper = gridMapper; //그리드상세
			
			if(objMapper != null) {
				Method MthService = objMapper.getClass().getMethod(excelMth, new Class[] {HashMap.class});
				dataList = (List) MthService.invoke(objMapper, new Object[] { paramMap });
			}
			//logger.debug("excelDown selectExcelDownList3 :: " + dateList.size());
			
		} catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException  e) {
			logger.error("excelDown BaseException :: " + e.toString() );
		}
		return dataList;
	} 
	
	
	/**
	 * <p>민원신청조회 </p>
	 *
	 * @see ComServiceImpl#selectWorkReq()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectWorkReq(HashMap paramMap) {
		return comMapper.selectWorkReq(paramMap);
	} 
	
	/**
	 * <p>첨부파일관리번호 추출 </p>
	 *
	 * @see ComServiceImpl#selectAtthNo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public String selectAtthNo(HashMap paramMap) {
		return comMapper.selectAtthNo(paramMap);
	} 
	
	
	/**
	 * <p>시퀀스번호 추출 </p>
	 *
	 * @see ComServiceImpl#selectSeqNo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public String selectSeqNo(String bussGbn, String year) {
		HashMap setMap = new HashMap();
		setMap.put("bussGbn", bussGbn);
		setMap.put("year", year);
		
		return comMapper.selectSeqNo(setMap);
	} 
	
	/**
	 * <p>보증인정보조회 (보증/융자 약정 보증인 관련) </p>
	 *
	 * @see ComServiceImpl#selectSure()
	 * @param domain 도메인
	 * @return 상세
	 */
	@Transactional(readOnly = true)
	public HashMap selectSure(HashMap paramMap) {
		return comMapper.selectSure(paramMap);
	} 
	
	
	/**
	 * <p>즐겨찾기 목록 </p>
	 *
	 * @see ComServiceImpl#selectUserMenuFavList()
	 * @param 
	 * @return 등록
	 */
	@Transactional(readOnly = true)
	public List selectUserMenuFavList(String userId) {
		return comMapper.selectUserMenuFavList(userId);
	} 
	
	/**
	 * <p>즐겨찾기 등록 </p>
	 *
	 * @see ComServiceImpl#insertUserMenuFav()
	 * @param 
	 * @return 등록
	 */
	public void insertUserMenuFav(HashMap paramMap) {
		comMapper.insertUserMenuFav(paramMap);
	} 
	
	/**
	 * <p>인증서 조회 </p>
	 *
	 * @see ComServiceImpl#selectMembCert()
	 * @param 
	 * @return 등록
	 */
	@Transactional(readOnly = true)
	public HashMap selectMembCert(HashMap paramMap) {
		return comMapper.selectMembCert(paramMap);
	} 
	
	/**
	 * <p>인증서 등록 </p>
	 *
	 * @see ComServiceImpl#insertMembCert()
	 * @param 
	 * @return 등록
	 */
	public void insertMembCert(HashMap paramMap) {
		comMapper.insertMembCert(paramMap);
	} 
	
	/**
	 * <p>업무메모 조회 </p>
	 *
	 * @see ComServiceImpl#selectUserMemo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectUserMemoList(HashMap paramMap) {
		return comMapper.selectUserMemoList(paramMap);
	} 
	
	/**
	 * <p>업무메모 상세 조회 </p>
	 *
	 * @see ComServiceImpl#selectUserMemo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectUserMemo(HashMap paramMap) {
		return comMapper.selectUserMemo(paramMap);
	} 
	
	/**
	 * <p>업무메모 등록 </p>
	 *
	 * @see ComServiceImpl#insertUserMemo()
	 * @param 
	 * @return 등록
	 */
	public void insertUserMemo(HashMap paramMap) {
		comMapper.insertUserMemo(paramMap);
	}
	
	/**
	 * <p>업무메모 수정, 삭제 </p>
	 *
	 * @see ComServiceImpl#updateUserMemo()
	 * @param 
	 * @return 등록
	 */
	public void updateUserMemo(HashMap paramMap) {
		comMapper.updateUserMemo(paramMap);
	}
	
	
	/**
	 * <p>기준일 영업일 여부 조회 </p>
	 *
	 * @see ComServiceImpl#seletWorkDay()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap seletWorkDay(HashMap paramMap) {
		return comMapper.seletWorkDay(paramMap);
	}

	/**
	 * <p>모바일 로그인 조합원 조회 </p>
	 *
	 * @see ComServiceImpl#selectMembById()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectMembById(HashMap paramMap) {
		return comMapper.selectMembById(paramMap);
	} 
	
	
	/** ################## 메인 통계 쿼리 시작~!  */
	
	/**
	 * <p>조합원현황 </p>
	 *
	 * @see ComServiceImpl#selectMainStatMemb()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectMainStatMemb(HashMap paramMap) {
		return comMapper.selectMainStatMemb(paramMap);
	} 
	
	/**
	 * <p>당일보증현황 </p>
	 *
	 * @see ComServiceImpl#selectMainStatGuar()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectMainStatGuar(HashMap paramMap) {
		return comMapper.selectMainStatGuar(paramMap);
	} 
	
	/**
	 * <p>보증실적현황 </p>
	 *
	 * @see ComServiceImpl#selectMainStatGuarPref()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMainStatGuarPref(HashMap paramMap) {
		return comMapper.selectMainStatGuarPref(paramMap);
	} 
	
	/**
	 * <p>자본금현황 </p>
	 *
	 * @see ComServiceImpl#selectMainStatInvtList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectMainStatInvtList(HashMap paramMap) {
		return comMapper.selectMainStatInvtList(paramMap);
	} 
	

	/**
	 * <p>알림톡수신자 조회 </p>
	 *
	 * @see ComServiceImpl#selectTalkPersList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectTalkPersList(HashMap paramMap) {
		return comMapper.selectTalkPersList(paramMap);
	} 
	
}