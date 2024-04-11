package ddc.biz.memb.service;

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

import ddc.biz.memb.repository.MembRateLendMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원별융자요
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membRateLendService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembRateLendServiceImpl implements MembRateLendService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembRateLendServiceImpl.class);

	@Autowired(required = false)
	private MembRateLendMapper membRateLendMapper;

	//조합원별융자요 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembRateLendList(HashMap paramMap) {
		return membRateLendMapper.selectMembRateLendList(paramMap);
	}

	//조합원별융자요 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembRateLend(HashMap paramMap) {
		return membRateLendMapper.selectMembRateLend(paramMap);
	}

	//조합원별융자요 저장 처리
	public void saveMembRateLend(HashMap paramMap) {
		List getList = (List) paramMap.get("data");

		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.error("saveMembRateLend setMap : " + setMap);
			setMap.put("userId", SessionUtil .getSess("userId"));
			if("Added".equals((String)setMap.get("STATUS"))){
				membRateLendMapper.insertMembRateLend(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				membRateLendMapper.updateMembRateLend(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				membRateLendMapper.deleteMembRateLend(setMap);
			}
		}
	}

	//조합원별융자요 등록 처리(단건)
	public void insertMembRateLend(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membRateLendMapper.insertMembRateLend(paramMap);
	}

	//조합원별융자요 수정 처리(단건)
	public void updateMembRateLend(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membRateLendMapper.updateMembRateLend(paramMap);
	}

	//조합원별융자요 삭제 처리(단건)
	public void deleteMembRateLend(HashMap paramMap) {
		membRateLendMapper.deleteMembRateLend(paramMap);

	}

	//신용도별 요율 조회(임시)
	@Transactional(readOnly = true)
	public HashMap selectLendRate(HashMap paramMap) {
		return membRateLendMapper.selectLendRate(paramMap);
	}


}
