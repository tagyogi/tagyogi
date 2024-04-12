package proj.biz.com.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>FileService (파일첨부 관리 Service)</p>
 *
 * @author 		: BSG
 * date 		: 2022.07.05
 *
 * modifier 	:
 * modify-date  :
 * description  : 파일첨부 처리
 */
public interface FileService {
	
	/**
     * <p>파일첨부파일번호추출</p>
     *
     * @return 첨부파일번호
     */
    public String selectMaxFileNo(HashMap<String, Object> paramMap);
    
    /**
     * <p>파일업로드처리</p>
     *
     * @return 목록
     */
	public void fileUpload(HashMap<String, Object> paramMap, List<MultipartFile> fileList);
	
    /**
     * <p>첨부파일목록</p>
     *
     * @return 목록
     */
	public List<Object> selectFileList(HashMap<String, Object> paramMap);
	
	/**
     * <p>첨부파일상세</p>
     *
     * @return 상세
     */
	public HashMap<String, Object>selectFile(HashMap<String, Object> paramMap);
    
    /**
     * <p>파일업로드 등록 처리</p>
     *
     * @return 등록
     */
    public void insertFile(HashMap<String, Object> paramMap);
    
    /**
     * <p>파일 단순 정보 수정 처리</p>
     *
     * @return 수정
     */
    public void updateFile(HashMap<String, Object> paramMap, List<MultipartFile> fileList);
    
    /**
     * <p>파일업로드 삭제 처리</p>
     *
     * @return 등록
     */
    public void deleteFile(HashMap<String, Object> paramMap);

    /**
     * <p>파일업로드 삭제 처리 (마스터자체가 삭제되는경우)</p>
     *
     * @return 
     */
    public void deleteFileMaster(HashMap<String, Object> paramMap);

    
    
}