package proj.biz.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.biz.com.repository.ComMapper;
import proj.biz.sys.repository.CodeMapper;
import proj.biz.sys.repository.GridMapper;
import proj.biz.sys.repository.MenuMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;

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
	public List<HashMap<String, Object>> selectMembList(HashMap<String, Object> paramMap) {
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
	public HashMap<String, Object>selectMemb(HashMap<String, Object> paramMap) {
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
	public HashMap<String, Object>selectMembBizNo(HashMap<String, Object> paramMap) {
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
	public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> paramMap) {
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
	public String selectCodeDtlNm(HashMap<String, Object> paramMap) {
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
	public HashMap<String, Object>selectCodeDtl(HashMap<String, Object> paramMap) {
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
	public List<HashMap<String, Object>> selectGridDtlList(HashMap<String, Object> paramMap) {
		return comMapper.selectGridDtlList(paramMap);
	} 
	
	/**
	 * <p>엑셀 다운로드 현황 조회 </p>
	 *
	 * @see ComServiceImpl#selectExcelDownList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<HashMap<String, Object>> selectExcelDownList(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> dataList = new ArrayList<>();
		
		try {
			String excelMth = (String)paramMap.get("excelMth");
			//logger.debug("excelDown excelMth :: " + excelMth );
			
			Object objMapper = null; 
			if(excelMth.toUpperCase().indexOf("MENU") > -1) objMapper = menuMapper; //메뉴현황
			if(excelMth.toUpperCase().indexOf("CODEDTL") > -1) objMapper = codeMapper; //코드상세
			if(excelMth.toUpperCase().indexOf("GRIDDTL") > -1) objMapper = gridMapper; //그리드상세
			
			if(objMapper != null) {
				Method MthService = objMapper.getClass().getMethod(excelMth, new Class[] {HashMap.class});
				dataList = (List<HashMap<String, Object>>) MthService.invoke(objMapper, new Object[] { paramMap });
			}
			//logger.debug("excelDown selectExcelDownList3 :: " + dateList.size());
			
		} catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException  e) {
			logger.error("excelDown BaseException :: " + e.toString() );
		}
		return dataList;
	} 
	
	
	/**
	 * <p>첨부파일관리번호 추출 </p>
	 *
	 * @see ComServiceImpl#selectAtthNo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public String selectAtthNo(HashMap<String, Object> paramMap) {
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
		HashMap<String, Object> setMap = new HashMap<String, Object>();
		setMap.put("bussGbn", bussGbn);
		setMap.put("year", year);
		
		return comMapper.selectSeqNo(setMap);
	} 
	
	/**
	 * <p>즐겨찾기 목록 </p>
	 *
	 * @see ComServiceImpl#selectUserMenuFavList()
	 * @param 
	 * @return 등록
	 */
	@Transactional(readOnly = true)
	public List<HashMap<String, Object>> selectUserMenuFavList(String userId) {
		return comMapper.selectUserMenuFavList(userId);
	} 
	
	/**
	 * <p>즐겨찾기 등록 </p>
	 *
	 * @see ComServiceImpl#insertUserMenuFav()
	 * @param 
	 * @return 등록
	 */
	public void insertUserMenuFav(HashMap<String, Object> paramMap) {
		comMapper.insertUserMenuFav(paramMap);
	} 
	
	/**
	 * <p>기준일 영업일 여부 조회 </p>
	 *
	 * @see ComServiceImpl#seletWorkDay()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object>seletWorkDay(HashMap<String, Object> paramMap) {
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
	public HashMap<String, Object>selectMembById(HashMap<String, Object> paramMap) {
		return comMapper.selectMembById(paramMap);
	} 
	
	
}