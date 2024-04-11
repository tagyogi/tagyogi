package ddc.biz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * confGuarRateService
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 보증상품요율관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균          최초 생성
 *  </pre>
 */
public interface ConfGuarRateService {


	//보증수수료율관리 현황 조회
	public List<Map<String, Object>> selectConfGuarRateList(HashMap paramMap);

	//보증수수료율관리 저장 처리
	public void saveConfGuarRate(HashMap paramMap);


}
