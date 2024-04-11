package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ddc.biz.memb.repository.MembSuptMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
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
 *
 *  </pre>
 */
@Service("membSuptService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembSuptServiceImpl implements MembSuptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembSuptServiceImpl.class);

	@Autowired(required = false)
	private MembSuptMapper membSuptMapper;

	//조합공헌도 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectContriButeList(HashMap paramMap) {
		return membSuptMapper.selectContriButeList(paramMap);
	}

	//(조합원)지역별 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembAreaList(HashMap paramMap) {
		return membSuptMapper.selectMembAreaList(paramMap);
	}

	//(조합원)업력별 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembBussList(HashMap paramMap) {
		return membSuptMapper.selectMembBussList(paramMap);
	}

	//(조합원)등급별 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembGradeList(HashMap paramMap) {
		return membSuptMapper.selectMembGradeList(paramMap);
	}

	//(보증)연도/장르별 수수료 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembGuarList(HashMap paramMap) {
		return membSuptMapper.selectMembGuarList(paramMap);
	}

	//보증년도 조회(2014~현재년도)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectGuarYearList(HashMap paramMap) {
		return membSuptMapper.selectGuarYearList(paramMap);
	}

	//(보증)연도/상품별 수수료 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembPrsrList(HashMap paramMap) {
		return membSuptMapper.selectMembPrsrList(paramMap);
	}

	//(조합원)매출액별 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembSaleList(HashMap paramMap) {
		return membSuptMapper.selectMembSaleList(paramMap);
	}

	//(조합원)매출액별 현황 상세 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembSale(HashMap paramMap) {
		return membSuptMapper.selectMembSale(paramMap);
	}

	//(조합원)조건별 현황 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembTypeList(HashMap paramMap) {
		return membSuptMapper.selectMembTypeList(paramMap);
	}

	//업무거래현황
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembWorkList(HashMap paramMap) {
		return membSuptMapper.selectMembWorkList(paramMap);
	}

	//업무거래 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembWork(HashMap paramMap) {
		return membSuptMapper.selectMembWork(paramMap);
	}

}
