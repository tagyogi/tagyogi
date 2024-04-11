package ddc.biz.memb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-20
 * @version 1.0
 * @see 보증관련통계
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-07-13  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface MembSuptMapper {

	//조합공헌도 현황 조회
	List<Map<String, Object>> selectContriButeList(HashMap paramMap);

	//(조합원)지역별 현황 현황 조회
	List<Map<String, Object>> selectMembAreaList(HashMap paramMap);

	//(조합원)업력별 현황 현황 조회
	List<Map<String, Object>> selectMembBussList(HashMap paramMap);

	//(조합원)등급별 현황 현황 조회
	List<Map<String, Object>> selectMembGradeList(HashMap paramMap);

	//(보증)연도/장르별 수수료 현황 현황 조회
	List<Map<String, Object>> selectMembGuarList(HashMap paramMap);

	//보증년도 조회(2014~현재년도)
	List<Map<String, Object>> selectGuarYearList(HashMap paramMap);

	//(보증)연도/상품별 수수료 현황 현황 조회
	List<Map<String, Object>> selectMembPrsrList(HashMap paramMap);

	//(조합원)매출액별 현황 현황 조회
	List<Map<String, Object>> selectMembSaleList(HashMap paramMap);

	//(조합원)매출액별 현황 상세 조회
	List<Map<String, Object>> selectMembSale(HashMap paramMap);

	//(조합원)조건별 현황 현황 조회
	List<Map<String, Object>> selectMembTypeList(HashMap paramMap);

	//업무거래현황 조회
	List<Map<String, Object>> selectMembWorkList(HashMap paramMap);

	//업무거래 상세 조회
	HashMap selectMembWork(HashMap paramMap);



}
