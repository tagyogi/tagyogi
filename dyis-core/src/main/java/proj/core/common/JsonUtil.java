package proj.core.common;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	static JsonUtil instance = null;
	static ObjectMapper objectMapper = new ObjectMapper();
	
    public static JsonUtil getInstance() throws Exception {
        if (instance == null) instance = new JsonUtil();
        return instance;
    }
	
    @SuppressWarnings("deprecation")
	public static String map2Json(HashMap<String, Object> jsonMap) throws Exception {
	     Writer writer = new StringWriter();
	     JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(writer);
	     ObjectMapper mapper = new ObjectMapper();
	     mapper.writeValue(jsonGenerator, jsonMap);
	     jsonGenerator.close();
	     return writer.toString();
    }
	
    public static String map2JsonLower(HashMap<String, Object> jsonMap) throws Exception {
    	return map2Json(toLowerMap(jsonMap));
    }
    
    
    public static String list2Json(List<Object> list) throws Exception {
    	return list2Json(list, "rows");
    }
    
    public static String list2Json(List<Object> list, String rowStr) throws Exception {
		HashMap<String, Object>  jsonMap = new HashMap<String, Object>();
		jsonMap.put(rowStr, list);
    	return map2Json(toLowerList(jsonMap, rowStr));
    }
    
    @SuppressWarnings("unchecked")
	public static List<HashMap<String, Object>> json2List(String jsonStr) throws Exception {
    	return objectMapper.readValue(jsonStr, List.class);
    }
    
    public static List<Object> json2ListLower(String jsonStr) throws Exception {
    	return toLowerList(json2List(jsonStr));
    }
    
    @SuppressWarnings("rawtypes")
	public static List<Object> toLowerList(List<HashMap<String, Object>> list) throws Exception {
    	List<Object> rtnList = new ArrayList<>();
    	for(HashMap<String, Object> paramMap : list) {
    		Map<String, Object> rtnMap = new HashMap<String, Object>();
    		for(Iterator keys = paramMap.keySet().iterator(); keys.hasNext();) {
    			String keyUpper = (String)keys.next();
    			String keyLower = keyUpper.toLowerCase();
    			rtnMap.put(keyLower, paramMap.get(keyUpper));
    		}
    		rtnList.add(rtnMap);
    	}
    	return rtnList;
    }
    
	@SuppressWarnings("rawtypes")
    public static HashMap<String, Object> toLowerMap(HashMap<String, Object> map) throws Exception {
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		for(Iterator keys = map.keySet().iterator(); keys.hasNext();) {
			String keyUpper = (String)keys.next();
			String keyLower = keyUpper.toLowerCase();
			rtnMap.put(keyLower, map.get(keyUpper));
		}
    	return rtnMap;
    }
    
    public static HashMap<String, Object> toLowerList(HashMap<String, Object> jsonMap, String rowStr) throws Exception {
    	List<HashMap<String, Object>> rtnList = new ArrayList<>();
    	List<HashMap<String, Object>> paramList = (List<HashMap<String, Object>>) jsonMap.get(rowStr);
    	for(HashMap<String, Object> paramMap : paramList) rtnList.add(toLowerMap(paramMap));
    	HashMap<String, Object> lowerMap = new HashMap<String, Object>();
    	lowerMap.put(rowStr, rtnList);
    	return lowerMap;
    }
    
    //STring to JSON
    public static JSONObject string2Json(String jsonStr) throws Exception {
    	JSONParser jPar = new JSONParser();
    	JSONObject jsonObj = new JSONObject();
    	
    	if(!"".equals(jsonStr) && jsonStr != null) {
    		Object obj = jPar.parse(jsonStr);
    		jsonObj = (JSONObject) obj;
    	}
		
    	return jsonObj;
   }
    
    
}