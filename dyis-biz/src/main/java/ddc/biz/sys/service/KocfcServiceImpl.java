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

import ddc.biz.sys.repository.KocfcMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-29
 * @version 1.0
 * @see 조합관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-29  변성균          최초 생성
 *
 *  </pre>
 */
@Service("kocfcService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class KocfcServiceImpl implements KocfcService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KocfcServiceImpl.class);
	
	@Autowired(required = false)
	private KocfcMapper kocfcMapper;

	//조합정보 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectKocfcList(HashMap paramMap) {
		return kocfcMapper.selectKocfcList(paramMap);
	}

	//조합정보 현황 조회
	@Transactional(readOnly = true)
	public HashMap selectKocfc() {
		return kocfcMapper.selectKocfc();
	}

		
	//조합정보 저장 처리
	public void saveKocfc(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			
			LOGGER.error("saveKocfc setMap : " + setMap);
			
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				kocfcMapper.insertKocfc(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				if("1".equals((String)setMap.get("delChk"))){
					kocfcMapper.deleteKocfc(setMap);
				}else {
					kocfcMapper.updateKocfc(setMap);
				}
			}
		}
	}

}
