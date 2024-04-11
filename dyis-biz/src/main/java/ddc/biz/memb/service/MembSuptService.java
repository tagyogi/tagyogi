package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * membSuptService
 * @author 시스템 개발팀 변성균
 * @since 2023-09-20
 * @version 1.0
 * @see 조합원관련통계
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-07-13  변성균          최초 생성
 *  </pre>
 */
public interface MembSuptService {


	//조합공헌도 현황 조회
	public List<Map<String, Object>> selectContriButeList(HashMap paramMap);

	//(조합원)지역별 현황 현황 조회
	public List<Map<String, Object>> selectMembAreaList(HashMap paramMap);

	//(조합원)업력별 현황 현황 조회
	public List<Map<String, Object>> selectMembBussList(HashMap paramMap);

	//(조합원)등급별 현황 현황 조회
	public List<Map<String, Object>> selectMembGradeList(HashMap paramMap);

	//(보증)연도/장르별 수수료 현황 현황 조회
	public List<Map<String, Object>> selectMembGuarList(HashMap paramMap);

	//보증년도 조회(2014~현재년도)
	public List<Map<String, Object>> selectGuarYearList(HashMap paramMap);


	//(보증)연도/상품별 수수료 현황 현황 조회
	public List<Map<String, Object>> selectMembPrsrList(HashMap paramMap);


	//(조합원)매출액별 현황 현황 조회
	public List<Map<String, Object>> selectMembSaleList(HashMap paramMap);

	//(조합원)매출액별 현황 상세 조회
	public List<Map<String, Object>> selectMembSale(HashMap paramMap);

	//(조합원)조건별 현황 현황 조회
	public List<Map<String, Object>> selectMembTypeList(HashMap paramMap);


	//업무거래현황
	public List<Map<String, Object>> selectMembWorkList(HashMap paramMap);

	//업무거래 상세 조회
	public HashMap selectMembWork(HashMap paramMap);

}
