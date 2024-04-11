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

import ddc.biz.memb.repository.MembDescMapper;
import ddc.biz.memb.repository.MembMapper;
import ddc.core.session.SessionUtil;

/**
 * 운영 메인 기능  처리하는 비즈니스 인터페이스 클래스
 * @author 시스템 개발팀 김상민
 * @since 2023-04-27
 * @version 1.0
 * @see 기재변경관리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-27  김상민          최초 생성
 *
 *  </pre>
 */
@Service("membDescService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MembDescServiceImpl implements MembDescService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembDescServiceImpl.class);

	@Autowired(required = false)
	private MembDescMapper membDescMapper;

	@Autowired(required = false)
	private MembMapper membMapper;

	//기재변경관리 현황 조회
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembDescList(HashMap paramMap) {
		return membDescMapper.selectMembDescList(paramMap);
	}

	//기재변경관리 상세 조회
	@Transactional(readOnly = true)
	public HashMap selectMembDesc(HashMap paramMap) {
		return membDescMapper.selectMembDesc(paramMap);
	}

	//기재변경관리 등록 처리(단건)
	public void insertMembDesc(HashMap paramMap) {
		//등록자, 수정자 이름
		paramMap.put("userNm", SessionUtil.getSess("userNm"));

		//기재변경 등록
		paramMap.put("reqNo", membDescMapper.selectMembDescReqNo());	//첨부파일 참조를 위해 따로 조회해서 map에 담음
		membDescMapper.insertMembDesc(paramMap);

		//기재변경처리상태(10:신청,11:접수,20:처리불가,30:처리완료)
		String procStat = (String) paramMap.get("procStat");
		if(procStat != null && "30".equals(procStat)) {
            //조합원 이력
			paramMap.put("histReason", "기재변경 등록");
			membMapper.insertMembHist(paramMap);

            //조합원 수정(기재변경)
            membMapper.updateMembChg(paramMap);

            //대표자변경시 해당대표자가 입보한 한도거래약정해제
            if("Y".equals(paramMap.get("ceoNmChgYn"))) {
            	membDescMapper.updateGuarContrCeoChg(paramMap);	//임시ㅇ
            }

			//더존?
            //if("Y".equals(propertyService.getString("operationYn"))) {
            //    HashMap partnerMap = memberDao.getDuzonPartnerData(param);
            //
            //    try {
            //        if(!partnerMap.isEmpty()){
            //            param.put("cd_partner", partnerMap.get("CD_PARTNER"));
            //            memberDao.duzonPartnerModAction(param);
            //        }
            //    } catch (NullPointerException npe) {
            //        memberDao.duzonPartnerRegAction(param);
            //    }
            //}

        }

	}

	//기재변경관리 수정 처리(단건)
	public void updateMembDesc(HashMap paramMap) {
		//등록자, 수정자 이름
		paramMap.put("userNm", SessionUtil.getSess("userNm"));

		//기재변경관리 수정
		membDescMapper.updateMembDesc(paramMap);

		//기재변경처리상태(10:신청,11:접수,20:처리불가,30:처리완료)
		String procStat = (String) paramMap.get("procStat");
		if(procStat != null && "30".equals(procStat)) {
            //조합원 이력
			paramMap.put("histReason", "기재변경 수정");
			membMapper.insertMembHist(paramMap);

            //조합원 수정(기재변경)
            membMapper.updateMembChg(paramMap);

            //대표자변경시 해당대표자가 입보한 한도거래약정해제
            if("Y".equals(paramMap.get("ceoNmChgYn"))) {
            	membDescMapper.updateGuarContrCeoChg(paramMap);	//임시
            }

			//더존?
            //if("Y".equals(propertyService.getString("operationYn"))) {
            //    HashMap partnerMap = memberDao.getDuzonPartnerData(param);
            //
            //    try {
            //        if(!partnerMap.isEmpty()){
            //            param.put("cd_partner", partnerMap.get("CD_PARTNER"));
            //            memberDao.duzonPartnerModAction(param);
            //        }
            //    } catch (NullPointerException npe) {
            //        memberDao.duzonPartnerRegAction(param);
            //    }
            //}

        }


	}

}
