package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membDescService
 * @author 시스템 개발팀 김상민
 * @since 2023-04-27
 * @version 1.0
 * @see 기재변경관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-27  김상민          최초 생성
 *  </pre>
 */
public interface MembDescService {

	//기재변경관리 현황 조회
	public List<Map<String, Object>> selectMembDescList(HashMap paramMap);

	//기재변경관리 상세 조회
	public HashMap selectMembDesc(HashMap paramMap);

	//기재변경관리 등록 처리(단건)
	public void insertMembDesc(HashMap paramMap);

	//기재변경관리 수정 처리(단건)
	public void updateMembDesc(HashMap paramMap);

}
