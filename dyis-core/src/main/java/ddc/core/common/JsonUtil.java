package ddc.core.common;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	public static String map2Json(HashMap jsonMap) throws Exception {
	     Writer writer = new StringWriter();
	     JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(writer);
	     ObjectMapper mapper = new ObjectMapper();
	     mapper.writeValue(jsonGenerator, jsonMap);
	     jsonGenerator.close();
	     return writer.toString();
    }
	
    public static String map2JsonLower(HashMap jsonMap) throws Exception {
    	return map2Json(toLowerMap(jsonMap));
    }
    
    
    public static String list2Json(List list) throws Exception {
    	return list2Json(list, "rows");
    }
    
    public static String list2Json(List list, String rowStr) throws Exception {
		HashMap jsonMap = new HashMap();
		jsonMap.put(rowStr, list);
    	return map2Json(toLowerList(jsonMap, rowStr));
    }
    
    public static List json2List(String jsonStr) throws Exception {
    	List<HashMap> paramList = objectMapper.readValue(jsonStr, List.class);
    	return paramList;
    }
    
    public static List json2ListLower(String jsonStr) throws Exception {
    	return toLowerList(json2List(jsonStr));
    }
    
    public static List toLowerList(List<HashMap> paramList) throws Exception {
    	List rtnList = new ArrayList();
    	for(HashMap paramMap : paramList) {
    		Map rtnMap = new HashMap();
    		for(Iterator keys = paramMap.keySet().iterator(); keys.hasNext();) {
    			String keyUpper = (String)keys.next();
    			String keyLower = keyUpper.toLowerCase();
    			rtnMap.put(keyLower, paramMap.get(keyUpper));
    		}
    		rtnList.add(rtnMap);
    	}
    	return rtnList;
    }
    
    public static HashMap toLowerMap(HashMap map) throws Exception {
    	HashMap rtnMap = new HashMap();
		for(Iterator keys = map.keySet().iterator(); keys.hasNext();) {
			String keyUpper = (String)keys.next();
			String keyLower = keyUpper.toLowerCase();
			rtnMap.put(keyLower, map.get(keyUpper));
		}
    	return rtnMap;
    }
    
    public static HashMap toLowerList(HashMap jsonMap, String rowStr) throws Exception {
    	List rtnList = new ArrayList();
    	List<HashMap> paramList = (List) jsonMap.get(rowStr);
    	for(HashMap paramMap : paramList) rtnList.add(toLowerMap(paramMap));
    	HashMap lowerMap = new HashMap();
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