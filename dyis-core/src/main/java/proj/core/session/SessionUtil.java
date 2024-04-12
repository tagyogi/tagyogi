package proj.core.session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import proj.core.common.CommonUtil;
import proj.core.config.ConfigProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>CommonUtil.java (공통 Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  : 세션 처리 영역
 */
public class SessionUtil {

	//private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
	
	/** 시스템명 */
    private final static String service = ConfigProperty.getString("service");
    
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return sra.getRequest();
	}
	
	public static HttpSession getCurrentSession(){
		return getCurrentRequest().getSession();
	}
	
	
	//세션 초기화
	public static void setSessInit() {
		getCurrentSession().invalidate();
	}
	
	//세션 설정
	public static void setSessToMap(HashMap<String, Object> getMap) {
		
		//홈페이지와 관리자 페이지 세션 구분
		if("home".equals(service)) { 
			String[] sessList = {"membNo", "membNm", "ceoNm"
					, "lawNo", "bizNo", "membDivNm", "membStatNm"
					, "telNo", "faxNo", "addrFull"
					, "mainBussNm"
					, "bankCd", "acctNo"
					, "membStat", "cardYn"
					, "guarContrNo", "lendContrNo"
					};
			
			for(int i = 0; i < sessList.length; i++) {
				//System.out.println(sessList[i]);
				getCurrentSession().setAttribute(sessList[i], CommonUtil.toString(getMap.get(sessList[i])));
			}
				
		}else {
			Iterator<String> keys = getMap.keySet().iterator();
			while( keys.hasNext() ){
				String strKey = keys.next();
				String strValue = (String) getMap.get(strKey);
				if(strKey.toLowerCase().indexOf("pw") == -1) {
					System.out.println("setSess " + strKey + " : " + strValue);
					getCurrentSession().setAttribute(strKey, strValue);
				}	
			}
		}
		
	}
	
	//세션 맵에 담기
	public static HashMap<String, Object>getSessToMap() {
		
		HashMap<String, Object>setMap = new HashMap<String, Object>();
		
		Enumeration getEnm = getCurrentSession().getAttributeNames();
		while( getEnm.hasMoreElements() ){
			
			String key = getEnm.nextElement().toString();
			setMap.put(key, getCurrentSession().getAttribute(key).toString());
		}
		
		return setMap;
	}
	
	//세션 추가
	public static void addSess(String key, String val) {
		getCurrentSession().setAttribute(key, val);	
	}
	
	//세션 삭제
	public static void removeSess(String key) {
		getCurrentSession().removeAttribute(key);	
	}
	
	//String
	public static String getSess(String key) {	
		String getSession = "";
		HttpSession session = getCurrentSession();
		if(session != null){
			getSession = (String) session.getAttribute(key);
		}
		return getSession;
	}
	
}