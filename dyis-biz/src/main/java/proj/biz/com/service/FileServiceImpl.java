package proj.biz.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import proj.biz.com.repository.FileMapper;
import proj.core.common.CommonUtil;
import proj.core.common.FileUploadUtil;
import proj.core.config.ConfigProperty;
import proj.core.session.SessionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *<p>FileServiceImpl </p>
 *
 * @author 	: 변성균
 * date 	: 2022.06.24
 * modifier :
 * modify-date :
 * description : 파일처리
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class FileServiceImpl implements FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	public static final String SEPARATOR = File.separator;

	/** 첨부파일 위치 지정 */
    private final static String uploadBaseDir = ConfigProperty.getString("file.path");

    /** 첨부파일 위치 지정 (게시판이미지) */
    //private final static String uploadImgDir = ConfigProperty.getString("file.imgPath");

	@Autowired
	private FileMapper fileMapper;


	/**
	 * <p>파일첨부파일번호추출</p>
	 *
	 * @see FileServiceImpl#getFileAtthNo()
	 * @param atthType
	 * @return 파일첨부파일번호추출 처리
	 */
	public String selectMaxFileNo(HashMap<String, Object> paramMap) {
		String atthNo = "";

		try {
			atthNo = fileMapper.selectMaxFileNo(paramMap);

		} catch (Exception e) {
			LOGGER.error("selectMaxFileNo : " + e.toString());
		}

		return atthNo;
	}

	/**
	 * <p>조합원 정보 조회 </p>
	 *
	 * @see FileServiceImpl#selectFileList()
	 * @param 첨부유형, 첨부관리번호
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List<Object> selectFileList(HashMap<String, Object> paramMap) {
		return fileMapper.selectFileList(paramMap);
	}

	/**
	 * <p>조합원 정보 조회 </p>
	 *
	 * @see FileServiceImpl#selectFile()
	 * @param 첨부유형, 첨부관리번호, 첨부파일번호
	 * @return 상세
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object> selectFile(HashMap<String, Object> paramMap) {
		return fileMapper.selectFile(paramMap);
	}


	/**
	 * <p>파일업로드 등록 처리</p>
	 *
	 * @see FileServiceImpl#insertFile()
	 * @param
	 * @return 파일업로드 처리
	 */
	@Transactional(readOnly = true)
	public void insertFile(HashMap<String, Object> paramMap) {
		fileMapper.insertFile(paramMap);
	}

	/**
	 * <p>파일업로드  처리</p>
	 *
	 * @see FileServiceImpl#fileUpload()
	 * @param
	 * @return 파일업로드 처리
	 */
	@Override
	public void fileUpload(HashMap<String, Object> paramMap, List<MultipartFile> fileList) {
		LOGGER.debug("fileUpload START ");

		//int reseult = 0;
		List<MultipartFile> getfileList = new ArrayList<>();

		String atthType = (String)paramMap.get("atthType");
		String atthNo = (String)paramMap.get("atthNo");

		if(atthNo != null) {
			//파일업로드 처리부분 ############################################################################
			getfileList = FileUploadUtil.uploadFiles(fileList, atthType, atthNo);

			LOGGER.debug("fileUpload atthType : " + atthType + " / atthNo :" + atthType + "/ fileList :  " + getfileList.size());

			String[] docCds = CommonUtil.toStrings(paramMap.get("docCd"));
			String[] submitDts = CommonUtil.toStrings(paramMap.get("docSubmitDt"));
			String[] submitYns = CommonUtil.toStrings(paramMap.get("docSubmitYn"));
			String[] remarks = CommonUtil.toStrings(paramMap.get("docRemark"));

			String modFileCd = CommonUtil.nvl(paramMap.get("modFileCds"), "");
			String[] modFileCds = modFileCd.split(",");

			//파일저장처리
			int cdLoop = 0; //서식코드관련
			for (int i = 0; i < getfileList.size(); i++) {
				HashMap<String, Object> getMap = (HashMap<String, Object>)getfileList.get(i);

				getMap.put("userNm", SessionUtil.getSess("userNm"));
				getMap.put("atthType", atthType);
				getMap.put("atthNo",atthNo);
				getMap.put("refNo", paramMap.get("refNo")); //참조번호
				getMap.put("docCd", paramMap.get("docCd")); //문서코드

				int modChk = 0;
				if(docCds != null) {
					for(int y = cdLoop; y < modFileCds.length; y++) {
						String chkCd = modFileCds[y];
						for(int x = 0; x < docCds.length; x++) {
							LOGGER.debug("fileUpload docCds["+x+"] : " + docCds[x]);
							if(chkCd.equals(docCds[x])) {
								getMap.put("docCd", docCds[x]); //문서코드

								//단건, 다중 첨부파일에 submitDts, submitYns, remarks이 null이라 널체크 추가
								if(submitDts != null)	getMap.put("submitDt", submitDts[x]); //제출일자
								if(submitYns != null)	getMap.put("submitYn", submitYns[x]); //제출여부
								if(remarks != null)		getMap.put("remark", remarks[x]); //비고

								cdLoop++; //수정 for 시작점 변경
								modChk = 1; //통과용
								break;
							}
						}
						if(modChk == 1) break;
					}
				}
				LOGGER.debug("fileUpload getMap : " + getMap);
				//파일DB 처리부분 ############################################################################
				//fileMapper.insertFile(getMap);
			}

		}else {
			LOGGER.error("fileUpload 첨부파일 번호 없음 : ");
		}


	}
	//파일 정보 수정
	@Override
	public void updateFile(HashMap<String, Object> paramMap, List<MultipartFile> fileList) {

		LOGGER.debug("fileUpdtae START ");


		String atthType = (String)paramMap.get("atthType");
		String atthNo = (String)paramMap.get("atthNo");

		if(atthNo != null) {

			LOGGER.debug("fileUpdtae param : " + atthType);
			LOGGER.debug("fileUpdtae param : " + atthNo);

			String[] docCds = CommonUtil.toStrings(paramMap.get("docCd"));
			String[] submitDts = CommonUtil.toStrings(paramMap.get("docSubmitDt"));
			String[] submitYns = CommonUtil.toStrings(paramMap.get("docSubmitYn"));
			String[] remarks = CommonUtil.toStrings(paramMap.get("docRemark"));
			String[] atthSeqs = CommonUtil.toStrings(paramMap.get("docSeq"));
			paramMap.put("modNm", SessionUtil.getSess("userNm"));

			//파일저장처리
			if(docCds != null) {

				for (int i = 0; i < docCds.length; i++) {

					paramMap.put("atthType", atthType);
					paramMap.put("atthNo",atthNo);
					paramMap.put("atthSeq", atthSeqs[i]); //시퀀스
					//paramMap.put("refNo", paramMap.get("refNo")); //참조번호

					//paramMap.put("docCd", docCds[i]); //문서코드
					paramMap.put("submitDt", submitDts[i]); //제출일자

					String submitYn = "";
					if(!CommonUtil.isNull(submitYns)) submitYn = submitYns[i];
					paramMap.put("submitYn", submitYn); //제출여부
					paramMap.put("remark", remarks[i]); //비고

					LOGGER.debug("fileUpdtae getMap : " + paramMap);
					fileMapper.updateFile(paramMap);
				}
			}
		}

	}

	/**
	 * <p>파일업로드 삭제 처리</p>
	 *
	 * @see FileServiceImpl#deleteFile()
	 * @param
	 * @return 파일삭제처리
	 */
	@Override
	public void deleteFile(HashMap<String, Object> paramMap) {
		fileMapper.deleteFile(paramMap);
	}

	/**
	 * <p>파일업로드 삭제 처리</p>
	 *
	 * @see
	 * @param
	 * @return 파일삭제처리 mater테이블관련된 메인업무가 전체삭제될 경우 사용
	 */
	@Override
	public void deleteFileMaster (HashMap<String, Object> paramMap) {

		LOGGER.debug("deleteFileMaster START ");

		//String atthType = String.valueOf(paramMap.get("atthType"));
		String atthNo = String.valueOf(paramMap.get("atthNo"));

		if(!"null".equals(atthNo)) {

			List<Object> fileList = fileMapper.selectFileList(paramMap);

			for (int i = 0; i < fileList.size(); i++) {

				HashMap<String, Object> getMap = (HashMap<String, Object>)fileList.get(i);
				paramMap.put("atthSeq", getMap.get("atthSeq")); //시퀀스

				//파일삭제
				HashMap<String, Object> fileMap = fileMapper.selectFile(paramMap); //파일정보조회
				FileUploadUtil.uploadFileDel(uploadBaseDir + SEPARATOR + fileMap.get("filePath") + SEPARATOR + fileMap.get("fileMngNm"));

				//파일삭제업데이트 처리
				paramMap.put("userNm", SessionUtil.getSess("userNm"));
				fileMapper.deleteFile(paramMap);
			}
		}
	}
}