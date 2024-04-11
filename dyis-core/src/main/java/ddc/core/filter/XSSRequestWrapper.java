/**
 * 프로젝트명		: 프로젝트명
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: XSSRequestWrapper
 * 프로그램설명		:
 **/

package ddc.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ddc.core.config.ConfigProperty;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
	
	String xssAllowTag = (String)ConfigProperty.getString("xss.allowTag");

	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			if ("".equals(xssAllowTag)) {
				//encodedValues[i] = SecureUtil.clearXSS(values[i]);
			} else {
				//encodedValues[i] = SecureUtil.clearXSS(values[i], xssAllowTag);
			}
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		
		if (value == null) {
			return null;
		}
		
		if ("".equals(xssAllowTag)) {
			//return SecureUtil.clearXSS(value);
		} else {
			//return SecureUtil.clearXSS(value, xssAllowTag);
		}
		return null;
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		
		if (value == null) {
			return null;
		} else {
			if ("".equals(xssAllowTag)) {
				//return SecureUtil.clearXSS(value);
			} else {
				//return SecureUtil.clearXSS(value, xssAllowTag);
			}
		}
		return null;
	}
	
	@Override
    public Map<String,String[]> getParameterMap() {
		Map<String, String[]> parameterMap = super.getParameterMap();
    	Set<String> keySet = parameterMap.keySet();
    	Iterator<String> itrator = keySet.iterator();
    	Map<String, String[]> cleanMap = new HashMap<String, String[]>();

    	while (itrator.hasNext()) {
    		String key = itrator.next();
    		String[] paramValues = parameterMap.get(key);

    		if (paramValues == null) {
    			cleanMap.put(key, paramValues);
    		} else{
    			int count = paramValues.length;
    			String[] encodedValues = new String[count];

    			for (int i = 0; i < count; i++) {
    				if ("".equals(xssAllowTag)) {
    					//encodedValues[i] = SecureUtil.clearXSS(paramValues[i]);
    				} else {
    					//encodedValues[i] = SecureUtil.clearXSS(paramValues[i], xssAllowTag);
    				}
    			}

    			cleanMap.put(key, encodedValues);
    		}
    	}

    	return cleanMap;
    }
	
}

