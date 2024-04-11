package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membRateLendService
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
 *  </pre>
 */
public interface MembRateLendService {


	//조합원별융자요 현황 조회
	public List<Map<String, Object>> selectMembRateLendList(HashMap paramMap);

	//조합원별융자요 상세 조회
	public HashMap selectMembRateLend(HashMap paramMap);

	//조합원별융자요 저장 처리
	public void saveMembRateLend(HashMap paramMap);

	//조합원별융자요 등록 처리(단건)
	public void insertMembRateLend(HashMap paramMap);

	//조합원별융자요 수정 처리(단건)
	public void updateMembRateLend(HashMap paramMap);

	//조합원별융자요 삭제 처리(단건)
	public void deleteMembRateLend(HashMap paramMap);

	//신용도별 요율 조회(임시)
	public HashMap selectLendRate(HashMap paramMap);
}
