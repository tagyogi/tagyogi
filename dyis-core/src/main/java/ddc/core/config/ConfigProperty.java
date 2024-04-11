package ddc.core.config;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.configuration.XMLConfiguration;

import ddc.core.Constants;
import ddc.core.exception.ConfigPropertyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>configProperty.java (configProperty 접근 Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2023.01.03
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class ConfigProperty {

	/**
	 * XML 접근 변수
	 */
	private static final XMLConfiguration config;

	public static final boolean DEFAULT_BOOLEAN = false;
	public static final int DEFAULT_INT = 0;
	public static final long DEFAULT_LONG = 0L;
	public static final float DEFAULT_FLOAT = 0f;
	public static final double DEFAULT_DOUBLE = 0d;
	public static final String DEFAULT_STRING = "";

	static {
		try {
			config = new XMLConfiguration(Constants.PROJECT_CONFIG_XML_PATH);
		} catch(org.apache.commons.configuration.ConfigurationException ce) {
			throw new ConfigPropertyException("Can't open application configuration file", ce);
		}
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 boolean 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return boolean 리턴
	 */
	public static boolean getBoolean(String key) {
		return config.getBoolean(key, DEFAULT_BOOLEAN);
    }

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 boolean 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return boolean 리턴
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 int 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return int 리턴
	 */
	public static int getInt(String key) {
		return config.getInt(key, DEFAULT_INT);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 int 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return int 리턴
	 */
	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 long 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return long 리턴
	 */
	public static long getLong(String key) {
		return config.getLong(key, DEFAULT_LONG);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 long 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return long 리턴
	 */
	public static long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 float 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return float 리턴
	 */
	public static float getFloat(String key) {
		return config.getFloat(key, DEFAULT_FLOAT);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 float 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return float 리턴
	 */
	public static float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 double 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return double 리턴
	 */
	public static double getDouble(String key) {
		return config.getDouble(key, DEFAULT_DOUBLE);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 double 데이타를 가져옵니다</p>
	 *
	 * @param key 킨
	 * @param defaultValue 기본값
	 * @return double 리턴
	 */
	public static double getDouble(String key, double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 String 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return String 리턴
	 */
	public static String getString(String key) {
		return config.getString(key, DEFAULT_STRING);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 String 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return String 리턴
	 */
	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 StringArray 데이타를 가져옵니다</p>
	 *
	 * @param key 킨
	 * @return String[] 리턴
	 */
	public static String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 Properties 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return Properties 리턴
	 */
	public static Properties getProperties(String key) {
		return config.getProperties(key);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 Properties 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return Proerties 리턴
	 */
	public static Properties getProperties(String key, Properties defaultValue) {
		return config.getProperties(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 List 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return List 리턴
	 */
	public static List<Object> getList(String key) {
		return config.getList(key);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 Map 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @return ListOrderedMap 리턴
	 */
	public static ListOrderedMap getMap(String key) {
		ListOrderedMap result = new ListOrderedMap();
		
		for (Object obj : config.getList(key)) {
			result.put((String) obj, (String) obj);
		}
		
		return result;
	}
	
	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 List 데이타를 가져옵니다</p>
	 *
	 * @param key 키
	 * @param defaultValue 기본값
	 * @return List 리턴
	 */
	public static List<Object> getList(String key, List<Object> defaultValue) {
		return config.getList(key, defaultValue);
	}

	/**
	 * <p>지정된 키(XML노드)에 맵핑되는 키가 존재하는지 검사합니다</p>
	 *
	 * @param key 키
	 * @return boolean 리턴
	 */
	public static boolean containsKey(String key) {
		return config.containsKey(key);
	}

	/**
	 * <p>지정된 키(XML노드)와 속성에 맵핑되는 List 데이타를 Map으로 가져옵니다</p>
	 *
	 * @param key 키
	 * @param attrKey 속성키
	 * @return Map 리턴
	 */
	public static Map<String, String> getAttributeMap(String key, String attrKey) {
		Map<String, String> result = new HashMap<String, String>();
		String[] valueArr = getStringArray(key);
		String[] attrArr = getStringArray(attrKey);

		for (int i = 0, size = valueArr.length; i < size; i++) {
			result.put(attrArr[i], valueArr[i]);
		}

		return result;
	}
}
