package proj.biz.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import proj.biz.sys.repository.MenuMapper;
import proj.core.session.SessionUtil;

import java.util.HashMap;
import java.util.List;

/**
 *<p>MenuServiceImpl </p>
 *
 * @author 	: 변성균 
 * date 	: 2022.06.24
 * modifier :
 * modify-date :
 * description : 메뉴관리 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class MenuServiceImpl implements MenuService {

	//private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;

 
	/**
	 * <p>현황 조회 </p>
	 *
	 * @see MenuServiceImpl#selectMenuList()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List<Object> selectMenuList(HashMap<String, Object> paramMap) {
		return menuMapper.selectMenuList(paramMap);
	} 
	
	/**
	 * <p>메뉴상세  조회 </p>
	 *
	 * @see MenuServiceImpl#selectMenu()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object>selectMenu(HashMap<String, Object> paramMap) {
		return menuMapper.selectMenu(paramMap);
	} 
	
	/**
	 * <p>저장 처리 </p>
	 *
	 * @see MenuServiceImpl#save()
	 * @param domain 도메인
	 * @return 현황
	 */
	public void saveMenu(HashMap<String, Object> paramMap) {
		List<HashMap<String, Object>> getList = (List<HashMap<String, Object>>) paramMap.get("data");
		
		for(int i =0; i < getList.size(); i++){
			HashMap<String, Object> setMap = (HashMap<String, Object>) getList.get(i);
			
			setMap.put("userNm", SessionUtil.getSess("userNm"));
			
			if("Added".equals((String)setMap.get("STATUS"))){
				menuMapper.insertMenu(setMap);
			}else if("Changed".equals((String)setMap.get("STATUS"))){
				menuMapper.updateMenu(setMap);
			}
		}
	}

	 
    /**
	 * <p>즐겨찾기 현황 조회 </p>
	 *
	 * @see MenuServiceImpl#selectLst()
	 * @param domain 도메인
	 * @return 현황
	 */
	@Transactional(readOnly = true)
	public List<Object> selectMenuFavLst(String usrId) {
		return menuMapper.selectMenuFavLst(usrId);
	} 
	
}