package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * membReqService
 * @author 시스템 개발팀 변성균
 * @since 2023-08-04
 * @version 1.0
 * @see 조합가입신청
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-04  변성균          최초 생성
 *  </pre>
 */
public interface MembReqService {


	//조합가입신청 현황 조회
	public List<Map<String, Object>> selectMembReqList(HashMap paramMap);

	//조합가입신청 상세 조회
	public HashMap selectMembReq(HashMap paramMap);

	//조합가입신청 등록 처리
	public void insertMembReq(HashMap<String, Object> paramMap, List<MultipartFile> fileList) throws Exception;

	//조합가입신청 수정 처리
	public void updateMembReq(HashMap paramMap);

	//조합가입신청 삭제 처리
	public void deleteMembReq(HashMap paramMap);

	//조합가입신청 등록 이력 처리
	public void insertMembReqHist(HashMap paramMap);

	//조합현황 정보 (상담, 가입, 조합원) 현황 조회 (통합)
	public List<Map<String, Object>> selectMembIntegList(HashMap paramMap);

	//조합가입신청 상세 조회(통합)
	//public HashMap selectMembInteg(HashMap paramMap);

	//공제가입 상담 여부 조회
	public HashMap selectMembCounsel(HashMap paramMap);

	//공제가입 신청 조회
	public HashMap selectMembJoin(HashMap paramMap);




}
