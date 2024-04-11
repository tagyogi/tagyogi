package ddc.biz.memb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import ddc.biz.com.repository.FileMapper;
import ddc.biz.com.service.FileService;
import ddc.biz.invt.repository.InvtMapper;
import ddc.biz.memb.repository.MembMapper;
import ddc.biz.memb.repository.MembReqMapper;
import ddc.core.common.CommonUtil;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-04
 * @version 1.0
 * @see 조합가입신청
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-04  변성균          최초 생성
 *
 *  </pre>
 */
@Service("membReqService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembReqServiceImpl implements MembReqService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembReqServiceImpl.class);

	@Autowired(required = false)
	private MembReqMapper membReqMapper;

	@Autowired(required = false)
	private MembMapper membMapper;

	@Autowired(required = false)
	private InvtMapper invtMapper;

	@Autowired(required = false)
	private FileMapper fileMapper;


	//조합가입신청 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembReqList(HashMap paramMap) {
		return membReqMapper.selectMembReqList(paramMap);
	}

	//조합가입신청 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembReq(HashMap paramMap) {
		return membReqMapper.selectMembReq(paramMap);
	}

	//조합가입신청 등록 처리
	public void insertMembReq(HashMap<String, Object> paramMap, List<MultipartFile> fileList) throws Exception {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		//paramMap.put("procStat", "11");	//홈페이지 신청 membReqMapper에 procStat NVL로 11로 등록하도록 수정 --> 관리자등록시 문제

		membReqMapper.insertMembReq(paramMap);

		membReqMapper.insertMembReqHist(paramMap);

		//가입완료 (내용 수정시 수정 부분도 같이 처리 )
		if("90".equals((String)paramMap.get("procStat"))) {
			//조합원번호 생성
			paramMap.put("membNo", membReqMapper.selectMaxMembNo());
			paramMap.put("membStat", "10");	//조합원상태(MB08) // 10: 정상, 80: 업무정지, 90: 탈퇴
			paramMap.put("compEmail", paramMap.get("email"));
			paramMap.put("atthType", "MB05");					//조합원 첨부파일번호는 필수로 들어가야함
			fileMapper.selectMaxFileNo(paramMap);	//조합원 첨부번호 발번 (atthNo)

			//조합마스터 등록
 			membMapper.insertMemb(paramMap);

			//조합마스터 이력 등록
			paramMap.put("histReason", "가입완료(조합등록처리)");
			membMapper.insertMembHist(paramMap);

			//조합대표자 등록
			paramMap.put("juminNo", paramMap.get("ceoSsn"));
			paramMap.put("doroPostNo", paramMap.get("ceoPostNo"));
			paramMap.put("ceoStat", "10");	//대표자상태(MB09) //10: 위임, 90: 해임
			paramMap.put("majorYn", "Y");	//주대표여부
			membMapper.insertMembCeo(paramMap);

			//조합담당자 등록
			paramMap.put("persStat", "10");	//담당자상태(MB10) // 10: 재직, 80: 부서이동, 90: 퇴사
			if( !CommonUtil.isNull(paramMap.get("persNm")) || !CommonUtil.isNull(paramMap.get("persCellNo")) ) {	//담당자는 필수값이 아니라 둘다 빈값이 아니면 추가
				membMapper.insertMembPerson(paramMap);
			}

			//조합출자정보 등록
			paramMap.put("investCd", "10");						//출자구분(IV01) // 10: 출자, 11: 추가출자, 20: 배정, 30: 양도양수, 31: 분할, 32: 병합
			paramMap.put("procStat", "10");						//처리상태(IV06) // 10: 접수, 20: 처리중, 21: 처리반려, 30: 처리승인, 40: 처리완료, 50: 환급
			paramMap.put("issueYn", "N");						//발행여부
			paramMap.put("investDt", paramMap.get("reqDt"));	//출자일자
			paramMap.put("atthType", "IV01");					//출자 첨부파일번호는 필수로 들어가야함
			fileMapper.selectMaxFileNo(paramMap);	//출자 첨부번호 발번 (atthNo)

			//paramMap.put("atthNo", null);
			paramMap.put("investNum", paramMap.get("investCnt"));//첨부파일정보 초기화(조합 첨부파일이랑은 별개)
			invtMapper.insertInvt(paramMap);
		}
	}

	//조합가입신청 수정 처리
	public void updateMembReq(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));

		membReqMapper.updateMembReq(paramMap);

		membReqMapper.insertMembReqHist(paramMap);

		//가입완료
		if("90".equals((String)paramMap.get("procStat"))) {
			//조합원번호 생성
			paramMap.put("membNo", membReqMapper.selectMaxMembNo());
			paramMap.put("membStat", "10");	//조합원상태(MB08) // 10: 정상, 80: 업무정지, 90: 탈퇴
			paramMap.put("compEmail", paramMap.get("email"));
			paramMap.put("atthType", "MB05");					//조합원 첨부파일번호는 필수로 들어가야함
			fileMapper.selectMaxFileNo(paramMap);	//조합원 첨부번호 발번 (atthNo)

			//조합마스터 등록
			membMapper.insertMemb(paramMap);

			//조합마스터 이력 등록
			paramMap.put("histReason", "가입완료(조합등록처리)");
			membMapper.insertMembHist(paramMap);

			//조합대표자 등록
			paramMap.put("juminNo", paramMap.get("ceoSsn"));
			paramMap.put("doroPostNo", paramMap.get("ceoPostNo"));
			paramMap.put("ceoStat", "10");	//대표자상태(MB09) //10: 위임, 90: 해임
			paramMap.put("majorYn", "Y");	//주대표여부
			membMapper.insertMembCeo(paramMap);

			//조합담당자 등록
			paramMap.put("persStat", "10");	//담당자상태(MB10) // 10: 재직, 80: 부서이동, 90: 퇴사
			if( !CommonUtil.isNull(paramMap.get("persNm")) || !CommonUtil.isNull(paramMap.get("persCellNo")) ) {	//담당자는 필수값이 아니라 둘다 빈값이 아니면 추가
				membMapper.insertMembPerson(paramMap);
			}

			//조합출자정보 등록
			paramMap.put("investCd", "10");						//출자구분(IV01) // 10: 출자, 11: 추가출자, 20: 배정, 30: 양도양수, 31: 분할, 32: 병합
			paramMap.put("procStat", "10");						//처리상태(IV06) // 10: 접수, 20: 처리중, 21: 처리반려, 30: 처리승인, 40: 처리완료, 50: 환급
			paramMap.put("issueYn", "N");						//발행여부
			paramMap.put("investDt", paramMap.get("reqDt"));	//출자일자
			paramMap.put("atthType", "IV01");					//출자 첨부파일번호는 필수로 들어가야함
			fileMapper.selectMaxFileNo(paramMap);	//출자 첨부번호 발번 (atthNo)
			paramMap.put("investNum", paramMap.get("investCnt")); //출자좌수
			invtMapper.insertInvt(paramMap);
		}
	}

	//조합가입신청 삭제 처리
	public void deleteMembReq(HashMap paramMap) {
		membReqMapper.deleteMembReq(paramMap);

	}


	//조합가입신청 등록 이려 처리
	public void insertMembReqHist(HashMap paramMap) {
		paramMap.put("userNm", SessionUtil.getSess("userNm"));
		membReqMapper.insertMembReqHist(paramMap);
	}



	//조합가입신청(상담, 가입, 조합원) 현황 조회(통합)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembIntegList(HashMap paramMap) {
		return membReqMapper.selectMembIntegList(paramMap);
	}

	//조합가입신청(상담, 가입, 조합원) 상세 조회(통합)
	//@Transactional(readOnly = true)
	//public HashMap selectMembInteg(HashMap paramMap) {
	//	return membReqMapper.selectMembInteg(paramMap);
	//}

	//공제가입상담 여부 조회
	@Override
	public HashMap selectMembCounsel(HashMap paramMap) {
		return membReqMapper.selectMembCounsel(paramMap);
	}

	//공제가입 신청 조회
	@Override
	public HashMap selectMembJoin(HashMap paramMap) {
		return membReqMapper.selectMembJoin(paramMap);
	}


}
