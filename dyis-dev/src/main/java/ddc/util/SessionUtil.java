package ddc.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ddc.core.config.ConfigProperty;
import ddc.core.exception.BaseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
	
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
	public static void setSessToMap(Map<String, Object> getMap) {
		
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
	
	//세션 맵에 담기
	public static HashMap getSessToMap() {
		
		HashMap setMap = new HashMap();
		
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
