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

import ddc.biz.sys.repository.WorkAlrmMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-10-11
 * @version 1.0
 * @see 알람대상관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-10-11  변성균          최초 생성
 *
 *  </pre>
 */
@Service("workAlrmService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class WorkAlrmServiceImpl implements WorkAlrmService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkAlrmServiceImpl.class);
	
	@Autowired(required = false)
	private WorkAlrmMapper workAlrmMapper;

	//알람대상관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectWorkAlrmList(HashMap paramMap) {
		return workAlrmMapper.selectWorkAlrmList(paramMap);
	}

	//알람대상관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectWorkAlrm(HashMap paramMap) {
		return workAlrmMapper.selectWorkAlrm(paramMap);
	}

	//알람대상관리 저장 처리 (그리드 일괄처리 전용 미사용시 삭제)
	public void saveWorkAlrm(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveWorkAlrm setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				workAlrmMapper.insertWorkAlrm(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				workAlrmMapper.updateWorkAlrm(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				workAlrmMapper.deleteWorkAlrm(setMap);
			}
		}
		
	}
	
	//알람확인 처리
	public void updateWorkAlrmEmplChk(HashMap paramMap) {
		workAlrmMapper.updateWorkAlrmEmplChk(paramMap);
	}
	

}
