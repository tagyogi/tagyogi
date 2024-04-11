package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membStatService
 * @author 시스템 개발팀 김상민1
 * @since 2023-04-17
 * @version 1.0
 * @see 조합원현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-17  김상민1          최초 생성
 *  </pre>
 */
public interface MembStatService {

	//조합원현황 장르별 구분 조회
	public List<Map<String, Object>> selectMembStatDesc(HashMap paramMap);

	//조합원현황 현황 조회
	public List<Map<String, Object>> selectMembStatList(HashMap paramMap);

	//콘텐츠공제조합 출자좌수 조회(조합원현황)
	public HashMap selectKocfcInvtNum(HashMap paramMap);

}
