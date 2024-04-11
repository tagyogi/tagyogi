package ddc.biz.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * comSuretyService
 * @author 시스템 개발팀 변성균
 * @since 2023-06-09
 * @version 1.0
 * @see 공통보증인
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-09  변성균          최초 생성 
 *  </pre>
 */
public interface ComSuretyService {
	
	//공통보증인 여부 조회
	public HashMap selectComSuretyChk(HashMap paramMap);
	
	//공통보증인 등록 처리(단건)
	public void insertComSurety(HashMap paramMap);
	
	//공통보증인 수정 처리(단건)
	public void updateComSurety(HashMap paramMap);
	
	//공통보증인 삭제 처리(단건)
	public void deleteComSurety(HashMap paramMap);
		
		
}
