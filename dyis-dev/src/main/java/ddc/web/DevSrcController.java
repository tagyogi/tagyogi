package ddc.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.config.ConfigProperty;
import ddc.core.domain.mapToCamel;
import ddc.service.DevDbService;
import ddc.util.ComUtil;
import ddc.util.DateUtil;
import ddc.util.SessionUtil;

/** 
 * 소스생성관리  Controller 클래스
 * @author 공통 서비스 개발팀 BSG
 * @since 2022.02
 * @version 1.0
 * @see 개발용 
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2022.02  BSG            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@Controller
public class DevSrcController {

	private static final Logger logger = LoggerFactory.getLogger(DevSrcController.class);
	
	/** 개발경로 */
	//protected final static String DEV_SRC  = ConfigProperty.getString("Globals.dev.src"); //
	public static final String SEPERATOR = File.separator;
    
	/** devDbService */
	@Autowired
	protected DevDbService devDbService;
	
	/**
	 * ################################# 개발 소스 생성 관리 #############################################################################
	 */
	
	/**
	 * 개발소스생성처리
	 */
	@RequestMapping(value="/dev/devSrcCreatePrcs.do")
	public ModelAndView devSrcCreatePrcs(@RequestBody(required=false) HashMap paramMap) throws Exception {
		ModelAndView josnView = new ModelAndView("jsonView");
		
		logger.debug("devCreateFile START paramMap : " + paramMap);		
		
		String getToday = DateUtil.getToday();
		
		FileReader reader = null;
		BufferedReader br = null;
		FileWriter writer = null;
		BufferedWriter bw = null;
		
		try{
			String jobNm = (String)paramMap.get("srcJobNm"); //업무폴더명
			String jobNmUpper = jobNm.substring(0, 1).toUpperCase()+jobNm.substring(1); //업무폴더명(첫문자대문자)
			String fileNm = (String)paramMap.get("srcFileNm"); //업무파일명
			String fileNmUpper = fileNm.substring(0, 1).toUpperCase()+fileNm.substring(1); //업무파일명(첫문자대문자)
			String tblNm = (String)paramMap.get("srcTblNm"); //테이블명
			String jobContet = (String)paramMap.get("jobContent"); //작업명 
			
			//소스영역
			String sampleDefPath = (String)paramMap.get("srcSample"); //샘플 소스 기본 경로 (./web, ./service, ./service/repository)
			String sampleDefViewPath = (String)paramMap.get("srcView") + SEPERATOR + "sample" ;
			
			String srcPath = ""; //controller
			if("adm".equals(paramMap.get("srcType"))) {
				srcPath = (String)paramMap.get("srcWebAdm") + SEPERATOR + jobNm;
			}else if("home".equals(paramMap.get("srcType"))) {
				srcPath = (String)paramMap.get("srcWebHome") + SEPERATOR + jobNm;
			}
			String srcBizPath = (String)paramMap.get("srcBiz") + SEPERATOR + jobNm; 
			String srcBizServPath = (String)paramMap.get("srcBiz") + SEPERATOR + jobNm + SEPERATOR + "service" ; //service
			String srcBizRepoPath = (String)paramMap.get("srcBiz") + SEPERATOR + jobNm + SEPERATOR + "repository" ; //repository
			
			String srcViewPath = (String)paramMap.get("srcView") + SEPERATOR + jobNm ; //페이지
			
			
			
			//샘플 파일 복사 
			String[] fileFroms = {"web", "service", "service", "service"+SEPERATOR+"repository", "viewList", "viewIns", "viewUpt", "viewDtl"};
			String[] fileTos = {srcPath, srcBizServPath, srcBizServPath, srcBizRepoPath, srcViewPath, srcViewPath, srcViewPath, srcViewPath};
			String[] fileTypes = {"Controller", "Service", "ServiceImpl", "Mapper", "List", "Ins", "Upt", "Dtl"};
			String[] fileExts = {"java", "java", "java", "java", "html", "html", "html", "html"};
			
			for(int i = 0; i < fileFroms.length; i++) {
				if("Controller".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewCont")) || paramMap.get("viewCont") == null )) continue;
				if("Service".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewServ")) || paramMap.get("viewServ") == null )) continue;
				if("ServiceImpl".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewServ")) || paramMap.get("viewServ") == null )) continue;
				if("Mapper".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewMapr")) || paramMap.get("viewMapr") == null )) continue;
				if("List".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewLst")) || paramMap.get("viewLst") == null )) continue;
				if("Ins".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewIns")) || paramMap.get("viewIns") == null )) continue;
				if("Upt".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewUpt")) || paramMap.get("viewUpt") == null )) continue;
				if("Dtl".equals(fileTypes[i]) && ( "".equals(paramMap.get("viewDtl")) || paramMap.get("viewDtl") == null )) continue;
				
				//폴더 확인 및 생성
				String[] folders = {srcPath, srcBizPath, srcBizServPath, srcBizRepoPath, srcViewPath};
				ComUtil.folderCreate(folders);
				
				File fileFrom = null;
				File fileTo = null;
				
				if(fileFroms[i].indexOf("view") > -1) {
					if(fileFroms[i].indexOf("viewList") > -1) {
						if("2".equals(paramMap.get("viewList"))) {
							fileFrom = new File(sampleDefViewPath + SEPERATOR + "sample2"+ fileTypes[i] + "." + fileExts[i]); //2개 그리드 샘플 
						}else if("3".equals(paramMap.get("viewList"))) {
							fileFrom = new File(sampleDefViewPath + SEPERATOR + "sample3"+ fileTypes[i] + "." + fileExts[i]); //	
						}else if("4".equals(paramMap.get("viewList"))) {
							fileFrom = new File(sampleDefViewPath + SEPERATOR + "sample4"+ fileTypes[i] + "." + fileExts[i]); //
						}else if("5".equals(paramMap.get("viewList"))) {
							continue;
						}else {
							fileFrom = new File(sampleDefViewPath + SEPERATOR + "sample"+ fileTypes[i] + "." + fileExts[i]); //	
						}
					}else {
						fileFrom = new File(sampleDefViewPath + SEPERATOR + "sample"+ fileTypes[i] + "." + fileExts[i]); //
					}
					
					
					fileTo = new File(fileTos[i] + SEPERATOR + fileNm + fileTypes[i] + "." + fileExts[i]);
				}else {
					fileFrom = new File(sampleDefPath + SEPERATOR + fileFroms[i] + SEPERATOR + "sample" + fileTypes[i] + "." + fileExts[i]); 
					fileTo = new File(fileTos[i] + SEPERATOR + fileNmUpper + fileTypes[i] + "." + fileExts[i]);
				}
				
				logger.debug("devCreateFile START fileFrom : " + fileFrom);		
				logger.debug("devCreateFile START fileTo : " + fileTo);		
				if(!fileTo.exists()) {
					String pagNmDef = (String)paramMap.get("srcPackage"); //기본패키지명
					//파일 읽기
					
					reader = new FileReader(fileFrom);
					br = new BufferedReader( reader );
					
					writer = new FileWriter(fileTo);
					bw = new BufferedWriter( writer );
					  
					String readLine = null;
					//logger.debug("devCreateFile userNm : " +  SessionUtil.getSess("userNm"));
					
					while ((readLine = br.readLine()) != null) {
						//logger.debug("devCreateFile reader : " +  readLine);
						
						if(readLine.indexOf("DEV") > -1) readLine = readLine.replaceAll("DEV", (String)SessionUtil.getSess("userNm"));
						if(readLine.indexOf("1900.01.01") > -1) readLine = readLine.replaceAll("1900.01.01", DateUtil.getToday());
						if(readLine.indexOf("jobContent") > -1) readLine = readLine.replaceAll("jobContent", jobContet);
						
						if(readLine.indexOf("ddc.service.repository") > -1) readLine = readLine.replaceAll("ddc.service.repository", "ddc.biz." + jobNm +".repository");
						if(readLine.indexOf("ddc.service") > -1) readLine = readLine.replaceAll("ddc.service", "ddc.biz." + jobNm +".service");
						
						if(readLine.indexOf("Sample") > -1) readLine = readLine.replaceAll("Sample", fileNmUpper);
						if(readLine.indexOf("sample") > -1) readLine = readLine.replaceAll("sample", fileNm);
						
						if(fileTypes[i].equals("Controller")) {
							if(readLine.indexOf("package") > -1) readLine = "package "+ pagNmDef + ".web."+jobNm + ";";
						
						}else if(fileTypes[i].equals("Service")) {
							if(readLine.indexOf("package") > -1) readLine = "package "+ pagNmDef + ".biz."+jobNm + ".service;";
							
						}else if(fileTypes[i].equals("ServiceImpl")) {
							if(readLine.indexOf("package") > -1) readLine = "package "+ pagNmDef + ".biz."+jobNm + ".service;";
						
						}else if(fileTypes[i].equals("Mapper") && fileExts[i].equals("java")) {
							if(readLine.indexOf("package") > -1) readLine = "package "+ pagNmDef + ".biz."+jobNm + ".repository;";
						}
						
						bw.write(readLine + "\r\n");
						bw.flush();
					}	
					bw.close();
					br.close();
				
				};
			}
			logger.debug("java file create END");
			//쿼리문파일 별도 생성
			
			if(!"".equals(paramMap.get("viewMapr")) && paramMap.get("viewMapr") != null ) {
				String xmlFileNm = srcBizRepoPath + SEPERATOR + fileNmUpper + "Mapper.xml";
				devSqlCreate(jobNm, jobNmUpper, fileNm, fileNmUpper, xmlFileNm, tblNm, jobContet);
			}
			
			josnView.addObject("resultCode", 0);
			josnView.addObject("resultMsg", "정상처리되었습니다");
 		}catch(Exception ex){
 			logger.debug("devCreateFile Exception : " + ex.getMessage());
 			josnView.addObject("resultCode", 9);
 			josnView.addObject("resultMsg", "오류가 발생하였습니다.");
 			
 			bw.flush();
 			bw.close();
			br.close();
 		}
		
		
		logger.debug("devSrcCreatePrcs END");
		
		return josnView;		
	}
	
	
	public void devSqlCreate(String jobNm, String jobNmUpper, String fileNm, String fileNmUpper, String sqlFileNm, String tblNm, String jobContet) throws Exception {
		
		logger.debug("devSqlCreate file path : " + sqlFileNm);
		
		String[] tblNms = tblNm.split(",");
		
		for(int x = 0; x < tblNms.length; x++) {
			
			
			//테이블 컬럼 조회
			HashMap paramMap = new HashMap();
			paramMap.put("tblNm", tblNms[x]);
			List getCols = devDbService.selectBefTblColList(paramMap);
			
			//쿼리파일 별도 생성
			FileWriter writer = new FileWriter(new File(sqlFileNm));
			BufferedWriter bw = new BufferedWriter( writer );
			
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n");
			bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> \r\n\r\n");
			bw.write("<mapper namespace=\"ddc.biz."+jobNm+".repository."+fileNmUpper+"Mapper\"> \r\n");
			bw.write("\r\n");
			
			//////////////////////////////////////////////////////////// 리스트 조회 쿼리 
			bw.write("	<!-- " + jobContet + " 정보 조회 --> \r\n");
			bw.write("	<select id=\"select" + fileNmUpper + "List\" parameterType=\"Map\" resultType=\"camelMap\"> \r\n");
			bw.write("		/* " + fileNmUpper + "Mapper.select" + fileNmUpper + "List */ \r\n");
			bw.write("		SELECT \r\n");
			//컬럼명 설정(조회, 등록)
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				
				if( i == 0) bw.write("			" + colNm + " \r\n");
				else bw.write("			, " + colNm+ " \r\n");
	
			}
			bw.write("		  FROM " + tblNm + " \r\n");
			bw.write("		<where> \r\n");
			
			
			//조건부 기본키
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String pkAt = (String)getCol.get("pkAt");
				
				if(!"".equals(pkAt) && pkAt != null) {
					String mapColNm = mapToCamel.convertToCamel("S_"+colNm);
					
					bw.write("		<if test=\"" + mapColNm+" != null and " +mapColNm+ "  != ''\"> \r\n");
					
					mapColNm = "#{"+mapColNm+"}";
					if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE("+mapColNm+", '-', '')";
					if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE("+mapColNm+", ',', '')";
					if(colNm.indexOf("REG_ID") > -1 || colNm.indexOf("MOD_ID") > -1 ) mapColNm = "#{userId}";
					
					bw.write("		AND " + colNm + " = " + mapColNm + " \r\n");
					bw.write("		</if> \r\n");
				}
			}
			bw.write("		</where> \r\n");
			bw.write("		ORDER BY ");
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String pkAt = (String)getCol.get("pkAt");
				
				if(!"".equals(pkAt) && pkAt != null) {
					if(i == 0 ) bw.write(colNm + " DESC");
					else bw.write(", " + colNm + " DESC");
				}
			}
			bw.write("\r\n");
			
			bw.write("	</select> \r\n");
			bw.write("\r\n");
			
			////////////////////////////////////////////////////////////상세 조회 쿼리 
			bw.write("	<!-- " + jobContet + " 상세 조회 --> \r\n");
			bw.write("	<select id=\"select" + fileNmUpper + "\" parameterType=\"Map\" resultType=\"camelMap\"> \r\n");
			bw.write("		/* " + fileNmUpper + "Mapper.select" + fileNmUpper + "*/ \r\n");
			bw.write("		SELECT \r\n");
			//컬럼명 설정(조회, 등록)
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				
				if( i == 0) bw.write("			" + colNm + " \r\n");
				else bw.write("			, " + colNm+ " \r\n");
	
			}
			bw.write("		  FROM " + tblNm + " \r\n");
			bw.write("		<where> \r\n");
			//조건부 기본키
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String pkAt = (String)getCol.get("pkAt");
				
				if(!"".equals(pkAt) && pkAt != null) {
					String mapColNm = mapToCamel.convertToCamel(colNm);
					
					bw.write("		<if test=\"" + mapColNm+" != null and " +mapColNm+ "  != ''\"> \r\n");
					
					if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE(#{"+mapColNm+"}, '-', '')";
					if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE(#{"+mapColNm+"}, ',', '')";
					
					bw.write("		AND " + colNm + " = " + mapColNm + " \r\n");
					bw.write("		</if> \r\n");
				}
			}
			bw.write("		</where> \r\n");
			bw.write("	</select> \r\n");
			bw.write("\r\n");
			
	////////////////////////////////////////////////////////////등록 처리 쿼리 
			bw.write("	<!-- " + jobContet + " 등록 처리 --> \r\n");
			bw.write("	<insert id=\"insert" + fileNmUpper + "\" parameterType=\"Map\"  > \r\n");
			bw.write("		/* " + fileNmUpper + "Mapper.insert" + fileNmUpper + " */ \r\n");
			bw.write("		INSERT INTO " + tblNm + " ( \r\n");
			//컬럼명 설정(조회, 등록)
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				
				if( i == 0) bw.write("			" + colNm + " \r\n");
				else bw.write("			, " + colNm+ " \r\n");
	
			}
			bw.write("		) VALUES ( \r\n");
			
			//등록 변수 설정
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String mapColNm = "#{"+mapToCamel.convertToCamel(colNm)+"}";
				
				if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, '-', '')";
				if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, ',', '')";
				if(colNm.indexOf("DTM") > -1) mapColNm = "SYSDATE";
				if(colNm.indexOf("REG_ID") > -1 || colNm.indexOf("MOD_ID") > -1 ) mapColNm = "#{userId}";
			
				if(i != 0 ) mapColNm = ", "+ mapColNm;
				
				bw.write("			" + mapColNm + " \r\n");
				
			}
			
			bw.write("		) \r\n");
			bw.write("	</insert> \r\n");
			bw.write("\r\n");
	////////////////////////////////////////////////////////////수정 처리 쿼리 		
			bw.write("	<!-- " + jobContet + " 수정 처리 --> \r\n");
			bw.write("	<update id=\"update" + fileNmUpper + "\" parameterType=\"Map\"  > \r\n");
			bw.write("		/* " + fileNmUpper + "Mapper.update" + fileNmUpper + " */ \r\n");
			bw.write("		UPDATE " + tblNm + " \r\n");
			bw.write("		SET \r\n");
			
			//업데이트 컬럼 설정
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String mapColNm = "#{"+mapToCamel.convertToCamel(colNm)+"}";
				
				if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, '-', '')";
				if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, ',', '')";
				if(colNm.indexOf("DTM") > -1) mapColNm = "SYSDATE";
				if(colNm.indexOf("REG_ID") > -1 || colNm.indexOf("MOD_ID") > -1 ) mapColNm = "#{userId}";
				
				mapColNm = colNm + " = " + mapColNm;
				
				if(i != 0 ) mapColNm = ", "+ mapColNm;
				
				bw.write("			" + mapColNm + " \r\n");
				
			}
			//업데이트 컬럼 설정(조건부 기본키)
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String pkAt = (String)getCol.get("pkAt");
				
				if(!"".equals(pkAt) && pkAt != null) {
					String mapColNm = "#{"+mapToCamel.convertToCamel(colNm)+"}";
					if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, '-', '')";
					if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, ',', '')";
					if(colNm.indexOf("DTM") > -1) mapColNm = "SYSDATE";
					
					if("1".equals(pkAt)) {
						mapColNm = "WHERE " + colNm + " = " + mapColNm;
					}else {
						mapColNm = "  AND " + colNm + " = " + mapColNm;
					}
					
					bw.write("		" + mapColNm + " \r\n");
				}
			}
			
			bw.write("	</update> \r\n");
			bw.write("\r\n");
	////////////////////////////////////////////////////////////삭제 처리 쿼리		
			bw.write("	<!-- " + jobContet + " 삭제 처리 --> \r\n");
			bw.write("	<delete id=\"delete" + fileNmUpper + "\" parameterType=\"Map\"  > \r\n");
			bw.write("		/* " + fileNmUpper + "Mapper.delete" + fileNmUpper + " */ \r\n");
			bw.write("		DELETE FROM " + tblNm + " \r\n");
			//삭제 컬럼 설정(조건부 기본키)
			for(int i = 0; i < getCols.size(); i++) {
				HashMap getCol = (HashMap) getCols.get(i);
				String colNm = (String)getCol.get("columnName");
				String pkAt = (String)getCol.get("pkAt");
				
				if(!"".equals(pkAt) && pkAt != null) {
					String mapColNm = "#{"+mapToCamel.convertToCamel(colNm)+"}";
					if(colNm.indexOf("DT") > -1 && colNm.indexOf("DTL") == -1 && colNm.indexOf("DTM") == -1 ) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, '-', '')";
					if(colNm.indexOf("AMT") > -1) mapColNm = "REPLACE(#{"+mapToCamel.convertToCamel(colNm)+"}, ',', '')";
					if(colNm.indexOf("DTM") > -1) mapColNm = "SYSDATE";
					
					if("1".equals(pkAt)) {
						mapColNm = "WHERE " + colNm + " = " + mapColNm;
					}else {
						mapColNm = "  AND " + colNm + " = " + mapColNm;
					}
					
					bw.write("		" + mapColNm + " \r\n");
						
				}
				
			}
			bw.write("	</delete> \r\n");
			bw.write("\r\n");
			
			
			bw.write("</mapper>\r\n");
			bw.flush();
			bw.close();
		}
	}
	
	
}