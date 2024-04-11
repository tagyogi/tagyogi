package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membConsService
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-04
 * @version 1.0
 * @see 공제가입상담현황
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-04  김상민1          최초 생성
 *  </pre>
 */
public interface MembConsService {


	//공제가입상담 현황 조회
	public List<Map<String, Object>> selectMembConsList(HashMap paramMap);

	//공제가입상담 상세 조회
	public HashMap selectMembCons(HashMap paramMap);

	//공제가입상담 등록 처리(단건)
	public void insertMembCons(HashMap paramMap);

	//공제가입상담 수정 처리(단건)
	public void updateMembCons(HashMap paramMap);

	//공제가입상담 삭제 처리(단건)
	public void deleteMembCons(HashMap paramMap);

	//상담내용 리스트 조회
	public List<Map<String, Object>> selectMembConsDtlList(HashMap paramMap);

	//공제가입상담(상담번호 채번)
	public String selectMembConsNo();


}
