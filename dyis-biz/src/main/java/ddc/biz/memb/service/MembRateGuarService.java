package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membRateGuarService
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-03
 * @version 1.0
 * @see 조합원보증요율
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-03  김상민1          최초 생성
 *  </pre>
 */
public interface MembRateGuarService {


	//조합원보증요율 현황 조회
	public List<Map<String, Object>> selectMembRateGuarList(HashMap paramMap);

	//조합원보증요율 상세 조회
	public HashMap selectMembRateGuar(HashMap paramMap);

	//조합원보증요율 등록 처리(단건)
	public void insertMembRateGuar(HashMap paramMap);

	//조합원보증요율 수정 처리(단건)
	public void updateMembRateGuar(HashMap paramMap);

	//조합원보증요율 삭제 처리(단건)
	public void deleteMembRateGuar(HashMap paramMap);

	//신용도 별 요율 조회
	public HashMap selectGuarRate(HashMap paramMap);

}
