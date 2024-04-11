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

import ddc.biz.memb.repository.MembAccMapper;
import ddc.biz.memb.repository.MembMapper;
import ddc.core.common.CommonUtil;
import ddc.core.crypto.CryptoUtil;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-03
 * @version 1.0
 * @see memb
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  김상민          최초 생성
 *
 *  </pre>
 */
@Service("membService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembServiceImpl implements MembService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembServiceImpl.class);

	@Autowired(required = false)
	private MembMapper membMapper;

	@Autowired(required = false)
	private MembAccMapper membAccMapper;


	//조합원관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembList(HashMap paramMap) {
		return membMapper.selectMembList(paramMap);
	}

	//조합원관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMemb(HashMap paramMap) {
		return membMapper.selectMemb(paramMap);
	}

	//조합원정보 계정정보 아이디 중복확인(모바일 로그인용 아이디)
	@Transactional(readOnly = true)
	public HashMap selectMembUserId(HashMap paramMap) {
		return membMapper.selectMembUserId(paramMap);
	}

	//조합원관리 등록 처리(단건)
	public void insertMemb(HashMap paramMap) {
		membMapper.insertMemb(paramMap);
	}

	//조합원관리 수정 처리(단건)
	public void updateMemb(HashMap paramMap) {

		HashMap membMap = (HashMap) paramMap.get("memb");
		HashMap membCeoMap = (HashMap) paramMap.get("membCeo");
		HashMap membPersonMap = (HashMap) paramMap.get("membPerson");

		//1. 업데이트전 데이터 이력 추가(as-is 기준)
		if(("H").equals(membMap.get("gubun"))) { // 홈페이지인 경우
			membMap.put("userNm", membMap.get("membNm"));
		} else {
			membMap.put("userNm", SessionUtil.getSess("userNm"));
		}

		//조합원 히스토리 쌓는방식 통일을 위해 update 후에 넣는 방식으로 수정(2023-12-07)
		//membMapper.insertMembHist(membMap);

		//변환컬럼셋팅
		//보조장르
		String[] bussCdsArr = CommonUtil.toStrings(membMap.get("subBussCds"));
		String subBussCds = "";
		if(bussCdsArr != null) {
			for(int i=0; i<bussCdsArr.length; i++) {
				subBussCds += bussCdsArr[i] + (i==bussCdsArr.length-1 ? "" : ",");
			}
			membMap.put("subBussCds", subBussCds);
		}


		if(!(("H").equals(membMap.get("gubun")))) { //홈페이지가 아닌경우 (관리자)
			//법인등록번호(필수값x)
			String lawNo = String.valueOf(membMap.get("lawNo1"))+String.valueOf(membMap.get("lawNo2"));
			membMap.put("lawNo", lawNo);

			//사업자번호(필수값)
			String bizNo = String.valueOf(membMap.get("bizNo1"))+String.valueOf(membMap.get("bizNo2"))+String.valueOf(membMap.get("bizNo3"));
			membMap.put("bizNo", bizNo);
		}

		//알게된사유
		String[] knowCdssArr = CommonUtil.toStrings(membMap.get("knowCd"));
		String knowCds = "";
		if(knowCdssArr != null) {
			for(int i=0; i<knowCdssArr.length; i++) {
				knowCds += knowCdssArr[i] + (i==knowCdssArr.length-1 ? "" : ",");
			}
			membMap.put("knowCds", knowCds);
		}

		//조기경보 수정
        String membCretop = CommonUtil.nvl(membMap.get("membCretop"), "");
        if(!"".equals(membCretop)){
            //cretop 1 관심, 2 관찰1, 3 관찰2, 4 관찰3, 5 부도, 6 폐업, 7 휴업
            if(Integer.parseInt(membCretop) == 6) {
                //paramMap.put("membStat", "90");
            	membMap.put("membStat", "80");	//폐업시 탈퇴 아닌 업무중지
            } else if(Integer.parseInt(membCretop) > 3 && Integer.parseInt(membCretop) <= 5) {
            	membMap.put("membStat", "80");
            }
        }

		//2. 계좌정보 수정시 계좌수정 이력 추가 => 조합원계좌관리 메뉴 신규추가로 계좌관련 수정은 신규메뉴에서 처리
		/*HashMap beforeMemb = membMapper.selectMemb(membMap);
		String bankCd = CommonUtil.nvl(membMap.get("bankCd"),"");  // 변경은행
		String acctNo = CommonUtil.nvl(membMap.get("acctNo"),"");	// 변경계좌번호
		String depoNm = CommonUtil.nvl(membMap.get("depoNm"),"");	// 변경예금주
		String acctNm = CommonUtil.nvl(membMap.get("depoNm"),"");	// 변경예금주(계좌관리 계좌정보)
		if(!bankCd.equals(CommonUtil.nvl(beforeMemb.get("bankCd"),"")) || !acctNo.equals(CommonUtil.nvl(beforeMemb.get("acctNo"),"")) || !depoNm.equals(CommonUtil.nvl(beforeMemb.get("depoNm"),"")) ) {
			//membAccMapper.insertMembAcc(membMap);	//계좌이력 추가

			membMap.put("useYn","Y");
			membAccMapper.insertMembAccMng(membMap);

        	//조합원계좌관리 이력 등록
    		paramMap.put("histReason", "조합원정보수정");
    		membAccMapper.insertMembAccHist(membMap);
		}*/


		//3. 조합원 수정

        //계정수정시 패스워드 암호화처리 (홈페이지 -모바일용)
        if(("H").equals(membMap.get("gubun"))) {
        	if(("").equals(membMap.get("userId")) && !("").equals(membMap.get("newUserId"))) { //기존아이디없고, 새로운 아디이 있을때 => 최초등록
        		membMap.put("userId",membMap.get("newUserId"));
        	}

        	membMap.put("userPwEnc", CryptoUtil.setHashEnc((String)membMap.get("userId")+(String)membMap.get("newPw")));
        	membMapper.updateMembHome(membMap);	//홈페이지 수정

        }else {
        	membMapper.updateMemb(membMap);		//관리자 수정
        }

        LOGGER.debug("암호화처 : " + membMap.get("userId") + "::" +  membMap.get("newPw")+ "::" +  membMap.get("userPwEnc"));


		//조합원 히스토리 쌓는방식 통일을 위해 update 후에 넣는 방식으로 수정(2023-12-07)
		membMapper.insertMembHist(membMap);


		//if(!(("H").equals(membMap.get("gubun")))) { //홈페이지가 아닌경우 (관리자)

			//4. 조합원 대표자정보 수정
			List ceoList = (List) membCeoMap.get("data");
			for(int i =0; i < ceoList.size(); i++){
				HashMap<String, Object> setMap = (HashMap) ceoList.get(i);
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("ceoAddr2", setMap.get("doroDtl"));

				if("Added".equals((String)setMap.get("STATUS"))){
					membMapper.insertMembCeo(setMap);
				}else if("Changed".equals((String)setMap.get("STATUS"))){
					membMapper.updateMembCeo(setMap);
				}else if("Deleted".equals((String)setMap.get("STATUS"))){
					membMapper.deleteMembCeo(setMap);
				}
			}

			//5. 조합원 담당자정보 수정
			List personList = (List) membPersonMap.get("data");
			for(int i =0; i < personList.size(); i++){
				HashMap<String, Object> setMap = (HashMap) personList.get(i);
				setMap.put("userNm", SessionUtil.getSess("userNm"));
				setMap.put("gubun", membMap.get("gubun"));

				if("Added".equals((String)setMap.get("STATUS"))){
					membMapper.insertMembPerson(setMap);
				}else if("Changed".equals((String)setMap.get("STATUS"))){
					membMapper.updateMembPerson(setMap);
				}else if("Deleted".equals((String)setMap.get("STATUS"))){
					membMapper.deleteMembPerson(setMap);
				}
			}
		//}
	}

	//조합원관리 삭제 처리(단건)
	public void deleteMemb(HashMap paramMap) {
		membMapper.deleteMemb(paramMap);

	}



	//조합원관리 > 대표자현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembCeoList(HashMap paramMap) {
		return membMapper.selectMembCeoList(paramMap);
	}

	//조합원관리 대표자 저장 처리
	public void saveMembCeo(HashMap paramMap) {
		List getList = (List) paramMap.get("data");

		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.debug("saveGridCols : " + setMap);
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				membMapper.insertMembCeo(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				membMapper.updateMembCeo(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				membMapper.deleteMembCeo(setMap);
			}
		}

	}

	//조합원관리 > 담당자현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembPersonList(HashMap paramMap) {
		return membMapper.selectMembPersonList(paramMap);
	}


	//조합원관리 담당자 저장 처리
	public void saveMembPerson(HashMap paramMap) {
		List getList = (List) paramMap.get("data");

		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) getList.get(i);
			LOGGER.debug("saveGridCols : " + setMap);

			setMap.put("userNm", SessionUtil.getSess("userNm"));
			if("Added".equals((String)setMap.get("STATUS"))){
				membMapper.insertMembPerson(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				membMapper.updateMembPerson(setMap);
			}else if("Deleted".equals((String)setMap.get("STATUS"))){
				membMapper.deleteMembPerson(setMap);
			}
		}

	}


	//공제가입등록 등록 처리(단건)
	public void insertMembHist(HashMap paramMap) {
		//등록자, 수정자 이름
		paramMap.put("userNm", SessionUtil.getSess("userNm"));

		//공제가입등록 insert
		membMapper.insertMembHist(paramMap);
	}


	//보증약정 출자 유형 업데이트
	public void updateGuarContrType(HashMap paramMap) {
		//등록자, 수정자 이름
		paramMap.put("userNm", SessionUtil.getSess("userNm"));

		//보증약정 출자 유형 업데이트
		membMapper.updateGuarContrType(paramMap);
	}

	//조합원 대표 + 담당자 리스트(보증발급관리 등록,수정에서 사용 - selectBox)
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectCeoPersList(HashMap paramMap) {
		return membMapper.selectCeoPersList(paramMap);
	}

	//조합원 조회 건수
	@Override
	public int selectMembCnt(HashMap paramMap) {
		return membMapper.selectMembCnt(paramMap);
	}

	//조합원관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembPers(HashMap paramMap) {
		return membMapper.selectMembPers(paramMap);
	}



}
