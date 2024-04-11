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

import ddc.biz.sys.repository.ConfGuarPayRateMapper;
import ddc.biz.sys.repository.ConfInvtMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-22
 * @version 1.0
 * @see 선급금약정이자율
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균          최초 생성
 *  2023-10-10  변성균
 *  </pre>
 */
@Service("confGuarPayRateService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ConfGuarPayRateServiceImpl implements ConfGuarPayRateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfGuarPayRateServiceImpl.class);
	
	@Autowired(required = false)
	private ConfGuarPayRateMapper confGuarPayRateMapper;

	//선급금약정이자율 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectConfGuarPayRateList(HashMap paramMap) {
		return confGuarPayRateMapper.selectConfGuarPayRateList(paramMap);
	}
	
	//선급금약정이자율 상세 조회(최종)
	@Transactional(readOnly = true)
	public HashMap selectConfGuarPayRateMaxAppDt(HashMap paramMap) {
		return confGuarPayRateMapper.selectConfGuarPayRateMaxAppDt(paramMap);
	}
	
	//선급금약정이자율 저장 처리
	public void saveConfGuarPayRate(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveConfGuarPayRate setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			
			if("Added".equals((String)setMap.get("STATUS"))){
				confGuarPayRateMapper.insertConfGuarPayRate(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				confGuarPayRateMapper.updateConfGuarPayRate(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				confGuarPayRateMapper.deleteConfGuarPayRate(setMap);
			}
		}
		
	}


    
}
