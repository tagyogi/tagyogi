package ddc.core.common;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddc.core.config.ConfigProperty;
import ddc.core.exception.BaseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>HttpUtil.java (Http Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	: 
 * modify-date  :
 * description  :
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static String get(String url, String encoding) throws BaseException, URIException {
		CloseableHttpClient client = HttpClients.createDefault();

		HttpGet get = new HttpGet(new URI(url,"UTF-8").toString());
		
		String result = "";
		try {
			ResponseHandler<String> rh = new BasicResponseHandler();

			try {
				result = client.execute(get, rh);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.toString();
			}

		} catch (Exception e) {
			logger.info("[HttpUtil Service Call ERROR]");
			throw e;

		} finally {
			if (get != null) try {
				get.releaseConnection();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (client != null) try {
				client.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}	
		return result;
	}

	private static String getPost(String url, List<NameValuePair> params, String encoding) throws BaseException {
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);
		
		String result = "";
		
		try {
			try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.toString();
			}
			ResponseHandler<String> rh = new BasicResponseHandler();

			try {
				result = client.execute(post, rh);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.toString();
			}

		} catch (Exception e) {
			e.toString();
			logger.info("[HttpUtil Service Call ERROR]");
			throw e;

		} finally {
			if (post != null) try {
				post.releaseConnection();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (client != null) try {
				client.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	private static String getPost2(String url, String params, String encoding){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		
		String result = "";
		try {
			RequestConfig requestConf = RequestConfig.custom()
					.setSocketTimeout(6000 * 1000)
					.setConnectTimeout(6000 * 1000)
					.setConnectionRequestTimeout(6000 * 1000)
					.build();
			post.setConfig(requestConf);
			
			post.setEntity(new StringEntity(params, "UTF-8"));
			ResponseHandler<String> rh = new BasicResponseHandler();
						
			try {
				result = client.execute(post, rh);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.toString();
				result = "";
			}

		} catch (Exception e) {
			e.toString();
			logger.info("[HttpUtil Service Call ERROR]");
			throw e;

		} finally {
			if (post != null) try {
				post.releaseConnection();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (client != null) try {
				client.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	public static Map<String, Object> convertToMap(String plainData) throws BaseException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS);        	
		mapper.enable(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS);

		Map<String, Object> result = null;
		try {
			result = mapper.readValue(plainData, HashMap.class);
			
			logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.toString();
		}

		return result;
	}

	private static String convertToParmasString(Map<String, Object> map) throws BaseException {
		String result = "?";

		if (map != null) {
			int cnt = 0;
			for (String key : map.keySet()) {
				if (cnt > 0) {
					result = result + "&";
				}

				result = result + key + "=" +  map.get(key);
				cnt++;
			}
		}

		return result;
	}

	public static String convertToJsonString(Map<String, Object> value) throws BaseException {

		ObjectMapper objectMapper = new ObjectMapper();

		makeNull2String((Map)value);
		
		String result = "";
		
		try {
			result = objectMapper.writeValueAsString((Map)value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.toString();
		}
		return result;
	}

	private static List<NameValuePair> convertToParmasNamePair(Map<String, Object> map) {
		List<NameValuePair> results = new ArrayList<>();

		if (map != null) {
			int cnt = 0;
			for (String key : map.keySet()) {
				results.add(new BasicNameValuePair(key, (String) map.get(key)));
			}
		}

		return results;
	}

	private static void makeNull2String(Map<String, Object> data){
		Iterator<?> iterator = data.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry)iterator.next();

			if (entry.getValue() instanceof List){
				List<Map> list = (List<Map>) entry.getValue();
				for (int i = 0; list.size() > i; i++) {
					makeNull2String((Map<String, Object>)list.get(i));
				}

			} else if (entry.getValue() instanceof String) {
				entry.setValue((String)entry.getValue());

			} else if (entry.getValue() == null) {
				entry.setValue((String)entry.getValue());
			}
		}
	}
	
}
