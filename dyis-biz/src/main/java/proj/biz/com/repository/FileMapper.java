package proj.biz.com.repository;

import org.springframework.stereotype.Repository;

import proj.core.datacon.DefaultConnMapper;

import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>FileMapper (파일관리 Mapper)</p>
 *
 * @author 		: BSG
 * date 		: 2023.01.04
 *
 * modifier 	:
 * modify-date  :
 * description  : 파일관리
 */
@DefaultConnMapper
@Repository
public interface FileMapper {
	
	/**
	 * <p> 첨부파일관리번호 추출</p> 
	 *
	 * @param 첨부유형
	 * @return 관리번호
	 */
	public String selectMaxFileNo(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 파일목록조회</p> 
	 *
	 * @param 
	 * @return 목록
	 */
	public List<Object> selectFileList(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 파일상세조회</p> 
	 *
	 * @param 첨부유형
	 * @return 상세
	 */
	public HashMap<String, Object>selectFile(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 파일정보등록</p> 
	 *
	 * @param 첨부유형
	 * @return 관리번호
	 */
	public void insertFile(HashMap<String, Object> paramMap);
	
	/**
	 * <p> 파일정보 단순수정</p> 
	 *
	 * @param 첨부유형
	 * @return 관리번호
	 */
	public void updateFile(HashMap<String, Object> paramMap);

	/**
	 * <p> 파일정보삭제</p> 
	 *
	 * @param 
	 * @return 
	 */
	public void deleteFile(HashMap<String, Object> paramMap);

	
	
	
}