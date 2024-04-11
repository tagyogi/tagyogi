package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.sys.repository.LogProcMapper;
import ddc.biz.sys.repository.WorkAlrmMapper;
import ddc.core.config.ConfigProperty;
import ddc.core.session.SessionUtil;

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
	
	/** 시스템명 */
    private final static String service = ConfigProperty.getString("service");
    
	@Autowired(required = false)
	private LogProcMapper logProcMapper;

	@Autowired(required = false)
	private WorkAlrmMapper workAlrmMapper;

	//로그인이력 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectLogLoginList(HashMap paramMap) {
		return logProcMapper.selectLogLoginList(paramMap);
	}
	
	//업무로그이력 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectLogProcList(HashMap paramMap) {
		return logProcMapper.selectLogProcList(paramMap);
	}
	
	//배치로그이력 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectLogBatchList(HashMap paramMap) {
		return logProcMapper.selectLogBatchList(paramMap);
	}
	
	//오류로그 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectLogErrList(HashMap paramMap) {
		return logProcMapper.selectLogErrList(paramMap);
	}

	
	//업무처리로그 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectLogProc(HashMap paramMap) {
		return logProcMapper.selectLogProc(paramMap);
	}

	 
	//업무처리로그 등록 처리(단건)
	public void insertLogProc(HttpServletRequest request, HashMap paramMap) {
		try {
			
			HashMap logMap = new HashMap();
			String mth = request.getMethod(); //호출메서드
			String uri = request.getRequestURI();  //업무처리변수
			if("admin".equals(service)) {
				logMap.put("userNm", SessionUtil.getSess("userNm")); //사용자ID
			}else {
				logMap.put("userNm", SessionUtil.getSess("membNo")); //사용자ID
			}
			
			logMap.put("procMth", mth); //호출메서드
			logMap.put("procVal", paramMap.toString()); //업무처리변수
			logMap.put("connRef", uri); //업무처리변수
			
			String getIp = request.getHeader("X-Forwarded-For");
			if(getIp == null) getIp = request.getRemoteAddr();
			
			logMap.put("connIp", getIp); //업무처리변수
			LOGGER.debug("insertLogProc logMap : " + logMap);
			logProcMapper.insertLogProc(logMap);
			
			//민원알람처리
			HashMap alrmMap = new HashMap();
			alrmMap.put("alrmMth", uri);
			HashMap getAlrm = workAlrmMapper.selectWorkAlrm(alrmMap);
			
			if(getAlrm != null) {
				workAlrmMapper.updateWorkAlrmEmpl();
			}
			
		} catch (Exception e) {
			LOGGER.error("insertLogProc Exception : " + e.toString());
		}
	}
    
	//배치로그 등록 처리(단건)
	public void insertLogBatch(HashMap paramMap) {
		try {
			
			LOGGER.debug("insertLogBatch paramMap : " + paramMap);
			logProcMapper.insertLogBatch(paramMap);
		} catch (Exception e) {
			LOGGER.error("insertLogProc Exception : " + e.toString());
		}
	}
	
	//배치로그 결과 업데이트 처리(단건)
	public void updateLogBatch(HashMap paramMap) {
		try {
			
			LOGGER.debug("updateLogBatch paramMap : " + paramMap);
			logProcMapper.updateLogBatch(paramMap);
		} catch (Exception e) {
			LOGGER.error("updateLogBatch Exception : " + e.toString());
		}
	}

	//업무처리오류로그
	public void insertLogErr(HttpServletRequest request, HashMap paramMap, String errMsg) {
		try {
			HashMap logMap = new HashMap();
			
			if("admin".equals(service)) {
				logMap.put("userNm", SessionUtil.getSess("userNm")); //사용자ID
			}else {
				logMap.put("userNm", SessionUtil.getSess("membNo")); //사용자ID
			}
			
			logMap.put("errMth", request.getMethod()); //호출메서드
			logMap.put("errVal", paramMap.toString()); //업무처리변수
			logMap.put("errMsg", errMsg); //에러메세지
			logMap.put("connRef", request.getRequestURI()); //업무처리변수
			
			String getIp = request.getHeader("X-Forwarded-For");
			if(getIp == null) getIp = request.getRemoteAddr();
			
			logMap.put("connIp", getIp); //업무처리변수
			LOGGER.debug("insertLogErr logMap : " + logMap);
			logProcMapper.insertLogErr(logMap);
			
		} catch (Exception e) {
			LOGGER.error("insertLogErr Exception : " + e.toString());
		}
	}

	
	//업무처리오류로그
	public void insertLogErr(HashMap paramMap, String errMsg) {
		try {
			String getIp = "";
			HashMap logMap = new HashMap();
			
			logMap.put("userNm", "batch"); //사용자ID
			logMap.put("errVal", paramMap.toString()); //업무처리변수
			logMap.put("errMsg", errMsg); //에러메세지
			logMap.put("errMth", paramMap.get("procNm")); //호출메서드
			logMap.put("connRef", ""); //업무처리변수
			getIp = "";
			
			logMap.put("connIp", getIp); //업무처리변수
			LOGGER.debug("insertLogErr logMap : " + logMap);
			logProcMapper.insertLogErr(logMap);
			
		} catch (Exception e) {
			LOGGER.error("insertLogErr Exception : " + e.toString());
		}
	}
	
	
}
