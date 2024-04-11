package ddc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ddc.core.config.ConfigProperty;
import ddc.service.repository.DevDbMapper;
import ddc.web.IndexController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *<p>DevDbServiceImpl </p>
 *
 * @author 	: 변성균 
 * date 	: 2022.06.24
 * modifier :
 * modify-date :
 * description : 메뉴관리 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class DevDbServiceImpl implements DevDbService {
	private static final Logger logger = LoggerFactory.getLogger(DevDbServiceImpl.class);
	
	@Autowired
	private DevDbMapper DevDbMapper;
	
	// 개발 데이터베이스 초기생성
    @Override
	public void createDevDbInit(Map<String, Object> pMap) throws Exception {
    	logger.debug("createDevDbInit START");	
    	
    	String dbtype = (String)pMap.get("dbType");
    	pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbMas.table").replaceAll("/", ","));
		DevDbMapper.createDbsql(pMap);
		
		pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbMas.index"));
		DevDbMapper.createDbsql(pMap);
		
		pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbMas.pk"));
		DevDbMapper.createDbsql(pMap);
		
		pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbDtl.table").replaceAll("/", ","));
		DevDbMapper.createDbsql(pMap);
		
		pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbDtl.index").replaceAll("/", ","));
		DevDbMapper.createDbsql(pMap);
		
		pMap.put("sCreateDbSql", ConfigProperty.getString("devInit."+dbtype+".devDbDtl.pk").replaceAll("/", ","));
		DevDbMapper.createDbsql(pMap);
		
		DevDbMapper.insertDevDbMasInit(); //기존테이블정보 등록
		
		DevDbMapper.insertDevDbDtlInit(pMap); //기존 컬럽 정보 등록
		
		
    }
	/**
	 * <p>테이블 정보 조회 </p>
	 *
	 * @see DevDbServiceImpl#selectTblInfo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectTblInfo(Map<String, Object> pMap) {
		return DevDbMapper.selectTblInfo(pMap);
	}
	
	/**
	 * <p>개발 데이터베이스 현황 </p>
	 *
	 * @see DevDbServiceImpl#selectDevDbMasList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectDevDbMasList(Map<String, Object> pMap) {
		return DevDbMapper.selectDevDbMasList(pMap);
	} 
	
	/**
	 * <p>개발 데이터베이스 상세 </p>
	 *
	 * @see DevDbServiceImpl#selectDevDbMasInfo()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap selectDevDbMasInfo(Map<String, Object> pMap) {
		return DevDbMapper.selectDevDbMasInfo(pMap);
	} 
	
	/**
	 * <p>개발 데이터베이스 상세 현황 </p>
	 *
	 * @see DevDbServiceImpl#selectDevDbDtlList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectDevDbDtlList(Map<String, Object> pMap) {
		return DevDbMapper.selectDevDbDtlList(pMap);
	} 
	
	/**
	 * <p>테이블 이전 필드 정보 조회 </p>
	 *
	 * @see DevDbServiceImpl#selectBefTblColList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List selectBefTblColList(Map<String, Object> pMap) {
		return DevDbMapper.selectBefTblColList(pMap);
	} 
	
	
	/**
	 * <p>개발 데이터베이스  정보 등록 처리 </p>
	 *
	 * @see DevDbServiceImpl#saveDevDbMasProc()
	 * @param domain 도메인
	 * @return 현황
	 */
	public void saveDevDbMasProc(Map<String, Object> pMap) {
		List getList = (List) pMap.get("data");
    	
    	for(int i =0; i < getList.size(); i++){
			Map<String, Object> getMap = (HashMap) getList.get(i);
		
			//getMap.put("regId", SessionUtil.getUserId());
			
			if("C".equals((String)getMap.get("status"))){
				DevDbMapper.insertDevDbMas(getMap);
			}else if("U".equals((String)getMap.get("status"))){
				DevDbMapper.updateDevDbMas(getMap);
			}
		}
	}

	// 개발 데이터베이스  정보 등록 처리
    @Override
	public void saveDevDbDtlProc(Map<String, Object> pMap) throws Exception {
    	
    	//LOGGER.debug("saveDevDbProc START pMap : " + pMap);	
    	
    	//상세 경우 일괄삭제 
    	DevDbMapper.deleteDevDbDtlAll(pMap);
    	
    	List getList = (List) pMap.get("data");
    	for(int i =0; i < getList.size(); i++){
			Map<String, Object> getMap = (HashMap) getList.get(i);
			//LOGGER.debug("saveDevDbProc START setMap : " + getMap);	
			
			//getMap.put("regId", SessionUtil.getUserId());
			
			getMap.put("tblSn", pMap.get("tblSn")); //테이블 ID 설정
			getMap.put("colSn", i+1); //테이블 ID 설정
			
			DevDbMapper.insertDevDbDtl(getMap);
		}
	}
    
    // 개발 데이터베이스  삭제 처리
    @Override
	public void deleteDevDbProc(Map<String, Object> pMap) throws Exception {
    	
    	//상세 일괄삭제
    	DevDbMapper.deleteDevDbDtlAll(pMap);

    	//DEV_DB_MAS 삭제
    	DevDbMapper.deleteDevDbMas(pMap);
	}
    	
    // 개발 데이터베이스  생성 처리
    @Override
	public void createTblProc(Map<String, Object> pMap) throws Exception {
    	logger.debug("createTblProc START pMap : " + pMap);	
    	String sCreateDbSql = "";
    	if("1".equals(pMap.get("tblAt"))) { //생성시 테이블 처리
    		
    		if("1".equals(pMap.get("tblBakAt"))) { //생성시 테이블 처리
    			//pMap.put("sCreateDbSql", (String)pMap.get("dropBakPkSql"));
        		//DevDbMapper.createDbsql(pMap);
        		
        		pMap.put("sCreateDbSql", (String)pMap.get("dropBakTblSql"));
        		//LOGGER.debug("createTblProc dropBakTblSql : " + (String)pMap.get("dropBakTblSql"));	
        		DevDbMapper.createDbsql(pMap);
        	}
    		
    		
    		pMap.put("sCreateDbSql", (String)pMap.get("copyTblSql"));
    		//LOGGER.debug("createTblProc copyTblSql : " + (String)pMap.get("copyTblSql"));	
    		DevDbMapper.createDbsql(pMap);
    		
    		pMap.put("sCreateDbSql", (String)pMap.get("dropPkSql"));
    		//LOGGER.debug("createTblProc dropPkSql : " + (String)pMap.get("dropPkSql"));	
    		try {
    			DevDbMapper.createDbsql(pMap);
    		}catch (Exception e) {
    			logger.error("createTblProc drop pk e : " + e.toString());	
    		}
    		
    		
    		pMap.put("sCreateDbSql", (String)pMap.get("dropTblSql"));
    		//LOGGER.debug("createTblProc dropTblSql : " + (String)pMap.get("dropTblSql"));	
    		DevDbMapper.createDbsql(pMap);
    		
    	}
    	
    	pMap.put("sCreateDbSql", (String)pMap.get("tblSql"));
    	//LOGGER.debug("createTblProc tblSql : " + (String)pMap.get("tblSql")); //테이블생성
		DevDbMapper.createDbsql(pMap);
		
		if(!"".equals(pMap.get("unqSql"))) {
			pMap.put("sCreateDbSql", (String)pMap.get("unqSql"));
			//LOGGER.debug("createTblProc unqSql : " + (String)pMap.get("unqSql")); //유니크 생성	
			DevDbMapper.createDbsql(pMap);
		}
		
		if(!"".equals(pMap.get("pkSql"))) {
			pMap.put("sCreateDbSql", (String)pMap.get("pkSql"));
			//LOGGER.debug("createTblProc pkSql : " + (String)pMap.get("pkSql"));	//pk 생성
			DevDbMapper.createDbsql(pMap);
		}
		
    	String comtSql = (String)pMap.get("comtSql");
    	String[] comtSqls = comtSql.split(";");
    	
    	for(int i = 0; i < comtSqls.length; i++) {
    		pMap.put("sCreateDbSql", (String)comtSqls[i].replaceAll(";", ""));
    		//LOGGER.debug("createTblProc comtSqls : " + (String)comtSqls[i].replaceAll(";", ""));	
    		DevDbMapper.createDbsql(pMap);
    	}
    	if("1".equals(pMap.get("tblAt"))) { //데이터 이관 
    		pMap.put("sCreateDbSql", (String)pMap.get("insertSql"));
    		//LOGGER.debug("createTblProc insertSql : " + (String)pMap.get("insertSql"));	
    		DevDbMapper.createDbsql(pMap);
    	}
	}
    
    // 테이블 필드 정보 초기화 등록
    @Override
	public void insertColsInitProc(Map<String, Object> pMap) throws Exception {
    	
    	//이전 필드 정보 삭제 
    	DevDbMapper.deleteDevDbDtlAll(pMap);

    	//DB정보 조회하여 필드 등록
    	DevDbMapper.insertDevDbDtlInit(pMap);
	}
    
    /**
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     * ######################################## 개발 데이터베이스 영역 ################################################# 
     */

    // 테이블 데이터 export
    @Override
	public List<HashMap> selectTblList(Map<String, Object> pMap) throws Exception {
    	List<HashMap> getList = DevDbMapper.selectTblList(pMap);
		return getList;
	}
    
    // 테이블 데이터 import
    @Override
    public void importTblProc(Map<String, Object> pMap) throws Exception {
    	
    	/*
    	List dataList = new ArrayList();
    	List colsList = new ArrayList();
    	
    	List<HashMap> colList = DevDbMapper.selectBefTblColList(pMap); //컬럼 목록
    	
    	//파일 읽기
		FileInputStream file = new FileInputStream((String)pMap.get("filePath")+(String)pMap.get("fileSaveNm"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		//시트 첫번째
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows(); //행수
		//LOGGER.debug("GradResultExcelUpload START rows : " + rows);
		int init = 0;
		for(int i = 1; i < rows; i++){
			//행읽기
            XSSFRow row = sheet.getRow(i);
            //LOGGER.debug("GradResultExcelUpload START row : " + row);
            HashMap getMap = new HashMap(); 
            if(row != null){
            	int cells = row.getPhysicalNumberOfCells();
            	//LOGGER.debug("GradResultExcelUpload START cells cnt: " + cells);
            	for(int x = 0; x < colList.size(); x++){
            		//셀 읽기
                    XSSFCell cell = row.getCell(x);
                    if(cell != null) {
                    	cell.setCellType(cell.CELL_TYPE_STRING);
                        getMap.put("col" + (x+1), cell.getStringCellValue());
                    }else {
                    	getMap.put("col" + (x+1), null);
                    }
                    
                    if(init == 0) {
                    	colsList.add("col" + (x+1));
                    }
            	}
            	dataList.add(getMap);
            	
            }
            init++;
            
		}
		*/
		//pMap.put("dataList", dataList);
		//pMap.put("colsList", colsList);
		//LOGGER.debug("GradResultUploadProc getMap : " + getMap);
        //대상 존재 여부 조회
        DevDbMapper.insertTblProc(pMap);
		
    }
     

	
}