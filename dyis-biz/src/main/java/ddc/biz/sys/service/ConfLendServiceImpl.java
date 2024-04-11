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

import ddc.biz.sys.repository.ConfLendMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 융자상품한도관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성
 *
 *  </pre>
 */
@Service("confLendService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ConfLendServiceImpl implements ConfLendService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfLendServiceImpl.class);
	
	@Autowired(required = false)
	private ConfLendMapper confLendMapper;

	//융자상품배수관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectConfLendList(HashMap paramMap) {
		return confLendMapper.selectConfLendList(paramMap);
	}

	//융자상품배수관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectConfLend(HashMap paramMap) {
		return confLendMapper.selectConfLend(paramMap);
	}

	//대출상품배수관리 상세 조회(최종)
	@Transactional(readOnly = true)
	public HashMap selectConfLendMaxAppDt(HashMap paramMap) {
		return confLendMapper.selectConfLendMaxAppDt(paramMap);
	}
	
			
	//융자상품배수관리 저장 처리
	public void saveConfLend(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));

			LOGGER.error("saveConfLend setMap : " + setMap);
			if("Added".equals((String)setMap.get("STATUS"))){
				HashMap getMap = confLendMapper.selectConfLend(setMap);
				if(getMap == null) {
					confLendMapper.insertConfLend(setMap);
				}
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				confLendMapper.updateConfLend(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				confLendMapper.deleteConfLend(setMap);
			}
		}
		
	}

}
