package proj.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import proj.biz.sys.repository.DocMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-05
 * @version 1.0
 * @see 서식관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-05  변성균          최초 생성
 *
 *  </pre>
 */
@Service("docService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class DocServiceImpl implements DocService {

	//private static final Logger LOGGER = LoggerFactory.getLogger(DocServiceImpl.class);
	
	@Autowired(required = false)
	private DocMapper docMapper;

	//서식관리 현황 조회
	@Transactional(readOnly = true)
	public List<Object> selectDocList(HashMap<String, Object> paramMap) {
		return docMapper.selectDocList(paramMap);
	}

	//서식관리 등록 처리
	public void insertDoc(HashMap<String, Object> paramMap, List<MultipartFile> fileList) {
		docMapper.insertDoc(paramMap);
		
	}

	//서식관리 수정 처리
	public void updateDoc(HashMap<String, Object> paramMap, List<MultipartFile> fileList) {
		docMapper.updateDoc(paramMap);
	}

	//서식관리 삭제 처리
	public void deleteDoc(HashMap<String, Object> paramMap) {
		docMapper.deleteDoc(paramMap);
		
	}

    
}
