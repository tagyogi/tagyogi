package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.sys.repository.UnitTestCaseMapper;
import ddc.biz.sys.repository.UnitTestMapper;
import ddc.biz.sys.repository.UnitTestReqMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-08
 * @version 1.0
 * @see 검수관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-08  변성균          최초 생성
 *
 *  </pre>
 */
@Service("unitTestService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class UnitTestServiceImpl implements UnitTestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnitTestServiceImpl.class);
	
	@Autowired(required = false)
	private UnitTestMapper unitTestMapper;

	@Autowired(required = false)
	private UnitTestCaseMapper unitTestCaseMapper;

	@Autowired(required = false)
	private UnitTestReqMapper unitTestReqMapper;


	//검수관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectUnitTestList(HashMap paramMap) {
		return unitTestMapper.selectUnitTestList(paramMap);
	}

	//검수관리 저장 처리
	public void saveUnitTest(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveUnitTest setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				unitTestMapper.insertUnitTest(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				unitTestMapper.updateUnitTest(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				unitTestMapper.deleteUnitTest(setMap);
			}
		}
		
	}

	//단위테스트현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectUnitTestCaseList(HashMap paramMap) {
		return unitTestCaseMapper.selectUnitTestCaseList(paramMap);
	}


	//단위테스트현황 저장 처리
	public void saveUnitTestCase(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveUnitTestCase setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				unitTestCaseMapper.insertUnitTestCase(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				unitTestCaseMapper.updateUnitTestCase(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				unitTestCaseMapper.deleteUnitTestCase(setMap);
			}
		}
		
	}
    
	//단위별요청사항 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectUnitTestReqList(HashMap paramMap) {
		return unitTestReqMapper.selectUnitTestReqList(paramMap);
	}

	//단위별요청사항 저장 처리
	public void saveUnitTestReq(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveUnitTestReq setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				unitTestReqMapper.insertUnitTestReq(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				unitTestReqMapper.updateUnitTestReq(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				unitTestReqMapper.deleteUnitTestReq(setMap);
			}
		}
		
	}

	
}
