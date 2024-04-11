package ddc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.core.session.SessionUtil;
import ddc.service.repository.LogProcMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-21
 * @version 1.0
 * @see 업무처리로그
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-21  변성균          최초 생성
 *
 *  </pre>
 */
@Service("logProcService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class LogProcServiceImpl implements LogProcService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogProcServiceImpl.class);
	
	@Autowired(required = false)
	private LogProcMapper logProcMapper;

	//업무처리로그 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectLogProcList(HashMap paramMap) {
		return logProcMapper.selectLogProcList(paramMap);
	}

	//업무처리로그 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectLogProc(HashMap paramMap) {
		return logProcMapper.selectLogProc(paramMap);
	}

	//업무처리로그 등록 처리(단건)
	public void insertLogProc(HttpServletRequest request, HashMap paramMap) {
		try {
			
			paramMap.put("userId", SessionUtil.getSess("userId")); //사용자ID
			paramMap.put("procMth", request.getMethod()); //호출메서드
			paramMap.put("procVal", paramMap); //업무처리변수
			paramMap.put("connRef", request.getRequestURI()); //업무처리변수
			paramMap.put("connIp", request.getRemoteAddr()); //업무처리변수
			
			logProcMapper.insertLogProc(paramMap);
		} catch (Exception e) {
			LOGGER.error("insertLogProc Exception : " + e.toString());
		}
	}

    
}
