package ddc.biz.sys.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ddc.core.common.PageUtil;
import ddc.core.datacon.DefaultConnMapper;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 업무해지 처리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균          최초 생성
 *
 *  </pre>
 */
@DefaultConnMapper
@Repository
public interface WorkRmvMapper {
	
	//업무해지 보증약정 현황 조회
	List<Map<String, Object>> selectGuarContrWorkRmvTargList(HashMap paramMap);
	
	//업무해지 보증약정 처리
	void updateGuarContrWorkRmv(HashMap paramMap);
	
	//업무해지 보증약정담보 처리
	void updateGuarContrMortWorkRmv(HashMap paramMap);
	
	//업무해지 보증연대보증 처리
	void updateGuarContrSureWorkRmv(HashMap paramMap);
	
	//업무해지 보증 현황 조회
	List<Map<String, Object>> selectGuarWorkRmvTargList(HashMap paramMap);
	
	//업무해지 보증 처리
	void updateGuarWorkRmv(HashMap paramMap);
	
	
	//업무해지 융자약정 현황 조회
	List<Map<String, Object>> selectLendContrWorkRmvTargList(HashMap paramMap);
	
	//업무해지 융자약정 처리
	void updateLendContrWorkRmv(HashMap paramMap);
	
	//업무해지 융자약정담보 처리
	void updateLendContrMortWorkRmv(HashMap paramMap);
	
	//업무해지 융자약정연대보증 처리
	void updateLendContrSureWorkRmv(HashMap paramMap);
	
}
