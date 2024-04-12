package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.biz.sys.repository.GridMapper;
import proj.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 현황화면그리드옵션관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균          최초 생성
 *
 *  </pre>
 */
@Service("gridService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class GridServiceImpl implements GridService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GridServiceImpl.class);
	
	@Autowired(required = false)
	private GridMapper gridMapper;

	//그리드관리 현황 조회
	@Transactional(readOnly = true)
	public List<Object> selectGridList(HashMap<String, Object> paramMap) {
		return gridMapper.selectGridList(paramMap);
	}

	//그리드관리 상세 조회
	@Transactional(readOnly = true)
	public List<Object> selectGridDtlList(HashMap<String, Object> paramMap) {
		return gridMapper.selectGridDtlList(paramMap);
	}

	//그리드관리 저장 처리
	public void saveGrid(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> getList = (List<HashMap<String, Object>>) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap<String, Object>) getList.get(i);
			
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				gridMapper.insertGrid(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				gridMapper.updateGrid(setMap);
			}
		}
	}
	
	
	//그리드관리 열 저장 처리
	public void saveGridCols(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> getList = (List<HashMap<String, Object>>) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap<String, Object>) getList.get(i);
			
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			
			LOGGER.debug("saveGridCols : " + setMap);
			
			if("Added".equals((String)setMap.get("STATUS"))){
				gridMapper.insertGridCols(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				gridMapper.updateGridCols(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				gridMapper.deleteGridCols(setMap);
			}
		}
	}
	
	//그리드관리 열 초기 생성
	public void createGridCols(HashMap<String, Object> paramMap) {
		gridMapper.createGridCols(paramMap);
	}
	
	
}
