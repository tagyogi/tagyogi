package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * emplService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 직원관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성 
 *  </pre>
 */
public interface EmplService {
	
	
	//직원관리 현황 조회
	public List<Map<String, Object>> selectEmplList(HashMap paramMap);
	
	//직원관리 상세 조회
	public HashMap selectEmpl(HashMap paramMap);
	
	//직원관리 저장 처리
	public void saveEmpl(HashMap paramMap);
	
	//직원관리 수정 처리(단건)
	public void updateEmpl(HashMap paramMap);
	
	//직원정보수정 - 마이페이지(개인정보수정-단건)
	public void updateMyInfo(HashMap paramMap);
		
		
}
