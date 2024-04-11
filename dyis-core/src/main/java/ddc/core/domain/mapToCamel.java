package ddc.core.domain;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>JsonResult (JsonResult Domain)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  : mybatis 결과 카멜케이스로 변환
 */
@SuppressWarnings("unchecked")
public class mapToCamel extends HashMap<String, Object> {
	
	@Override
	public Object put(String key, Object value) {
		//return super.put(JdbcUtils.convertUnderscoreNameToPropertyName((String) key), value);
		return super.put(convertToCamel((String) key), value);
	}
	
	public static String convertToCamel(String key) {
		
		if (key.indexOf('_') < 0 && Character.isLowerCase(key.charAt(0))) {
			return key;
		}
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = key.length();

		for (int i = 0; i < len; i++) {
			char currentChar = key.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}
}