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

import ddc.biz.sys.repository.EmplMapper;
import ddc.core.crypto.CryptoUtil;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 직원관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성
 *
 *  </pre>
 */
@Service("emplService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class EmplServiceImpl implements EmplService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmplServiceImpl.class);
	
	@Autowired(required = false)
	private EmplMapper emplMapper;

	//직원관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectEmplList(HashMap paramMap) {
		return emplMapper.selectEmplList(paramMap);
	}

	//직원관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectEmpl(HashMap paramMap) {
		return emplMapper.selectEmpl(paramMap);
	} 

	//직원관리 저장 처리
	public void saveEmpl(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			
			setMap.put("regNm", SessionUtil .getSess("userNm"));
			String userPwNew = (String)setMap.get("userPwNew");
			if(!"".equals(userPwNew) && userPwNew != null) {
				LOGGER.error("saveEmpl userPwNew chg : ");
				setMap.put("userPwEnc", CryptoUtil.setHashEnc((String)setMap.get("userId")+(String)setMap.get("userPwNew")));
			}
			
			if("Added".equals((String)setMap.get("STATUS"))){
				emplMapper.insertEmpl(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				emplMapper.updateEmpl(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				emplMapper.deleteEmpl(setMap);
			}
		}
	}

	//직원관리 수정 처리(단건)
	public void updateEmpl(HashMap paramMap) {
		emplMapper.updateEmpl(paramMap);
		
	}
	
	//직원정보수정 - 마이페이지(개인정보수정-단건)
	public void updateMyInfo(HashMap paramMap) {
		String userPwNew = (String)paramMap.get("userPwNew");
		paramMap.put("regNm", SessionUtil .getSess("userNm"));
		
		if(!"".equals(userPwNew) && userPwNew != null) {
			LOGGER.error("updateMyInfo userPwNew chg : ");
			paramMap.put("userPwEnc", CryptoUtil.setHashEnc((String)paramMap.get("userId")+(String)paramMap.get("userPwNew")));
		}
		
		LOGGER.debug("updateMyInfo param :: " + paramMap);
		
		emplMapper.updateMyInfo(paramMap);
		
	}

    
}
