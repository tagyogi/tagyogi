package ddc.service;

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

import ddc.service.repository.SampleMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 DEV
 * @since 1900.01.01
 * @version 1.0
 * @see jobContent
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  1900.01.01  DEV          최초 생성
 *
 *  </pre>
 */
@Service("sampleService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class SampleServiceImpl implements SampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Autowired(required = false)
	private SampleMapper sampleMapper;

	//jobContent 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectSampleList(HashMap paramMap) {
		return sampleMapper.selectSampleList(paramMap);
	}

	//jobContent 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectSample(HashMap paramMap) {
		return sampleMapper.selectSample(paramMap);
	}

	//jobContent 저장 처리 (그리드 일괄처리 전용 미사용시 삭제)
	public void saveSample(HashMap paramMap) {
		List getList = (List) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveSample setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				sampleMapper.insertSample(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				sampleMapper.updateSample(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				sampleMapper.deleteSample(setMap);
			}
		}
		
	}

	//jobContent 등록 처리
	public void insertSample(HashMap paramMap) {
		sampleMapper.insertSample(paramMap);
	}

	//jobContent 수정 처리
	public void updateSample(HashMap paramMap) {
		sampleMapper.updateSample(paramMap);
		
	}

	//jobContent 삭제 처리
	public void deleteSample(HashMap paramMap) {
		sampleMapper.deleteSample(paramMap);
		
	}

    
}
