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

import ddc.biz.sys.repository.ConfInvtMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-22
 * @version 1.0
 * @see 출자좌수액
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균          최초 생성
 *
 *  </pre>
 */
@Service("confInvtService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ConfInvtServiceImpl implements ConfInvtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfInvtServiceImpl.class);
	
	@Autowired(required = false)
	private ConfInvtMapper confInvtMapper;

	//출자좌수액 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectConfInvtList(HashMap paramMap) {
		return confInvtMapper.selectConfInvtList(paramMap);
	}
	
	//출자좌수액 상세 조회(최종)
	@Transactional(readOnly = true)
	public HashMap selectConfInvtMaxAppDt(HashMap paramMap) {
		return confInvtMapper.selectConfInvtMaxAppDt(paramMap);
	}
	
	//출자좌수액 저장 처리
	public void saveConfInvt(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveConfInvt setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			
			if("Added".equals((String)setMap.get("STATUS"))){
				confInvtMapper.insertConfInvt(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				confInvtMapper.updateConfInvt(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				confInvtMapper.deleteConfInvt(setMap);
			}
		}
		
	}


    
}
