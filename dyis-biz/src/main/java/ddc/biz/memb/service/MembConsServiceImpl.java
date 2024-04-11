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

import ddc.biz.memb.repository.MembConsMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-04
 * @version 1.0
 * @see 공제가입상담현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-04  김상민1          최초 생성
 *
 *  </pre>
 */
@Service("membConsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembConsServiceImpl implements MembConsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembConsServiceImpl.class);

	@Autowired(required = false)
	private MembConsMapper membConsMapper;

	//공제가입상담 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembConsList(HashMap paramMap) {
		return membConsMapper.selectMembConsList(paramMap);
	}

	//공제가입상담 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembCons(HashMap paramMap) {
		return membConsMapper.selectMembCons(paramMap);
	}

	//공제가입상담 등록 처리(단건)
	public void insertMembCons(HashMap paramMap) {
		HashMap membConsDtlMap = (HashMap) paramMap.get("membConsDtl");

		//1. counsNo 채번
		String counsNo = membConsMapper.selectMembConsNo();

		//2. 공제가입상담(업체정보 + 신용평가 + 담당자) 저장
		paramMap.put("counsNo", counsNo);
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membConsMapper.insertMembCons(paramMap);

		//3. 공제가입상담(진행내용) 저장
		List dtlList = (List) membConsDtlMap.get("data");
		for(int i =0; i < dtlList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) dtlList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			setMap.put("counsNo", counsNo);
			membConsMapper.insertMembConsDtl(setMap);
		}
	}

	//공제가입상담 수정 처리(단건)
	public void updateMembCons(HashMap paramMap) {
		HashMap membConsDtlMap = (HashMap) paramMap.get("membConsDtl");

		//1. 공제가입상담(업체정보 + 신용평가 + 담당자) 저장
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membConsMapper.updateMembCons(paramMap);

		//2. 공제가입상담(진행내용) 저장
		List dtlList = (List) membConsDtlMap.get("data");

		for(int i =0; i < dtlList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) dtlList.get(i);
			setMap.put("userNm", SessionUtil.getSess("userNm"));

			if("Added".equals((String)setMap.get("STATUS"))){
				setMap.put("counsNo", paramMap.get("counsNo"));
				membConsMapper.insertMembConsDtl(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				membConsMapper.updateMembConsDtl(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				membConsMapper.deleteMembConsDtl(setMap);
			}
		}


	}

	//공제가입상담 삭제 처리(단건)
	public void deleteMembCons(HashMap paramMap) {
		membConsMapper.deleteMembCons(paramMap);
	}


	//상담내용 리스트 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembConsDtlList(HashMap paramMap) {
		return membConsMapper.selectMembConsDtlList(paramMap);
	}

	//공제가입상담(상담번호 채번)
	@Transactional(readOnly = true)
	public String selectMembConsNo() {
		return membConsMapper.selectMembConsNo();
	}


}
