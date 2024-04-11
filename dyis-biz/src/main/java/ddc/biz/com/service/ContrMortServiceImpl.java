package ddc.biz.com.service;

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

import ddc.biz.com.repository.ContrMortMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-22
 * @version 1.0
 * @see 약정담보정보
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-22  변성균          최초 생성
 *
 *  </pre>
 */
@Service("contrMortService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ContrMortServiceImpl implements ContrMortService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContrMortServiceImpl.class);
	
	@Autowired(required = false)
	private ContrMortMapper contrMortMapper;

	//약정담보정보 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectContrMortList(HashMap paramMap) {
		return contrMortMapper.selectContrMortList(paramMap);
	}
	
}
