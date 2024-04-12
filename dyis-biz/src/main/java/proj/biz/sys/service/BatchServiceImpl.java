package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.biz.sys.repository.BatchMapper;
import proj.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-03
 * @version 1.0
 * @see 배치관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-03  변성균          최초 생성
 *
 *  </pre>
 */
@Service("batchService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class BatchServiceImpl implements BatchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchServiceImpl.class);
	
	@Autowired(required = false)
	private BatchMapper batchMapper;

	//배치관리 현황 조회
	@Transactional(readOnly = true)
	public List<Object> selectBatchList(HashMap<String, Object> paramMap) {
		return batchMapper.selectBatchList(paramMap);
	}

	//배치관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap<String, Object>selectBatch(HashMap<String, Object> paramMap) {
		return batchMapper.selectBatch(paramMap);
	}

	//배치관리 저장 처리
	public void saveBatch(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> getList = (List<HashMap<String, Object>>) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap<String, Object>) getList.get(i);
			LOGGER.error("saveBatch setMap : " + setMap);
			setMap.put("userNm", SessionUtil .getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				batchMapper.insertBatch(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				batchMapper.updateBatch(setMap);
			}
		}
		
	}
	
	//배치관리 변경 적용 처리 완료 업데이트
	public void updateBatchChgProc(HashMap<String, Object> paramMap) {
		batchMapper.updateBatchChgProc(paramMap);
	}
    
}
