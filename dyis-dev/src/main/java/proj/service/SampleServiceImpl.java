package proj.service;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.core.session.SessionUtil;
import proj.service.repository.SampleMapper;

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
	public List<Object> selectSampleList(HashMap<String, Object> paramMap) {
		return sampleMapper.selectSampleList(paramMap);
	}

	//jobContent 상세 조회
	@Transactional(readOnly = true)
	public HashMap<String, Object>selectSample(HashMap<String, Object> paramMap) {
		return sampleMapper.selectSample(paramMap);
	}

	//jobContent 저장 처리 (그리드 일괄처리 전용 미사용시 삭제)
	public void saveSample(HashMap<String, Object> paramMap) {
		List<Object> getList = (List<Object>) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap<String, Object>) getList.get(i);
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
	public void insertSample(HashMap<String, Object> paramMap) {
		sampleMapper.insertSample(paramMap);
	}

	//jobContent 수정 처리
	public void updateSample(HashMap<String, Object> paramMap) {
		sampleMapper.updateSample(paramMap);
		
	}

	//jobContent 삭제 처리
	public void deleteSample(HashMap<String, Object> paramMap) {
		sampleMapper.deleteSample(paramMap);
		
	}

    
}
