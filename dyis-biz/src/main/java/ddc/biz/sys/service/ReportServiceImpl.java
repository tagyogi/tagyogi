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
import org.springframework.web.multipart.MultipartFile;

import ddc.biz.sys.repository.ReportMapper;
import ddc.core.common.FileUploadUtil;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-18
 * @version 1.0
 * @see 레포트관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-18  변성균          최초 생성
 *
 *  </pre>
 */
@Service("reportService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired(required = false)
	private ReportMapper reportMapper;

	//레포트관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectReportList(HashMap paramMap) {
		return reportMapper.selectReportList(paramMap);
	}

	//레포트관리 등록 처리(단건)
	public void insertReport(HashMap paramMap, List<MultipartFile> fileList) {
		reportMapper.insertReport(paramMap);
	}

	//레포트관리 수정 처리(단건)
	public void updateReport(HashMap paramMap, List<MultipartFile> fileList) {
		reportMapper.updateReport(paramMap);
	}

	//레포트관리 삭제 처리(단건)
	public void deleteReport(HashMap paramMap) {
		reportMapper.deleteReport(paramMap);
	}

    
}
