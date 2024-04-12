package proj.core.common;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import proj.core.config.ConfigProperty;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
 * description  :
 */
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * <p>MAC 주소 조회</p>
	 *
	 * @param request request객체
	 * @param
	 * @return MAC 주소 반환
	 */
	public static String getMacAddr(HttpServletRequest request) throws SocketException {
	    //HashMap<String, Object> map = new HashMap<String, Object>();

	    String result = "";
    	InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	    	byte[] mac = network.getHardwareAddress();

	    	StringBuilder sb = new StringBuilder();
	    	for( int i  = 0; i < mac.length; i++) {
	    		sb.append(String.format("%02X%s",  mac[i], (i < mac.length - 1)? "-" :""));
	    	}
	    	result = sb.toString();

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}


	    return result;
	}

	/**
	 * <p>파라미터 획득, null처리</p>
	 *
	 * @param request request객체
	 * @param paramName 파람명
	 * @param defalutValue 기본값
	 * @return 해당 값(String)을 반환
	 */
	public static String getParameter(HttpServletRequest request, String paramName, String defalutValue){
		String paramValue = request.getParameter(paramName);
		String returnValue = "";

		if(paramValue !=null && !"".equals(paramValue.trim())) {
			returnValue = paramValue;
		}else {
			if(defalutValue !=null && !"".equals(defalutValue.trim())) {
				returnValue = defalutValue;
			}
		}

		return returnValue;

	}

	/**
	 * <p>확장자 체크 (구분자 '|')</p>
	 *
	 * @param fileName 파일명
	 * @param allowExtension 허용확장자
	 * @return 일치여부 boolean 값 반환
	 */
	public static boolean checkFileExtension (String fileName, String allowExtension) {
		if (fileName == null || "".equals(fileName)) {
			return false;
		}

		if (allowExtension == null || "".equals(allowExtension)) {
			return false;
		}
		try {
			int pos = fileName.lastIndexOf(".");
			String fileExt = fileName.substring(pos + 1).toLowerCase();
			String[] extensions = allowExtension.split("\\|");

			for (String extension : extensions) {
				if (extension.equals(fileExt)) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.info("error : " + e);
			return false;
		}

		return false;
	}

	/**
	 * <p>HTML 태그 제거</p>
	 *
	 * @param paramValue 파라미터값
	 * @return 제거된 값(String)을 반환
	 */
	public static String removeHtml(String paramValue) {
		String returnValue = "";

		if(paramValue !=null && !"".equals(paramValue.trim())) {
			//returnValue = paramValue.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			returnValue = paramValue.replaceAll("<[^>]*>", "");
		}

		returnValue = returnValue.replaceAll("&nbsp;", " ");

		return returnValue;
	}

	/**
	 * <p>SQL Injection 기호 치환</p>
	 *
	 * @param paramValue 파라미터값
	 * @return 값(String) 반환
	 */
	public static String removeSqlInjection(String paramValue) {
		String returnValue = paramValue;

		if (returnValue != null && !"".equals(returnValue.trim())) {
			returnValue = returnValue.replaceAll("\"", "&quot;");
			returnValue = returnValue.replaceAll("'", "&#39;");
			returnValue = returnValue.replaceAll("<", "&lt;");
			returnValue = returnValue.replaceAll(">", "&gt;");
		}

		return returnValue;
	}

	/**
	 * <p>SQL Injection 기호 치환</p>
	 *
	 * @param paramValue 파라미터값
	 * @return 값(String) 반환
	 */
	public static String restoreSqlInjection(String paramValue) {
		String returnValue = paramValue;

		if (returnValue != null && !"".equals(returnValue.trim())) {
			returnValue = returnValue.replaceAll("&quot;", "\"");
			returnValue = returnValue.replaceAll("&#39;", "'");
			returnValue = returnValue.replaceAll("&lt;", "<");
			returnValue = returnValue.replaceAll("&gt;", ">");
		}

		return returnValue;
	}

	/**
	 * <p>허용 태그를 다시 치환한다.</p>
	 *
	 * @param ttl 값
	 * @return 값(String) 반환
	 */
	public static String replaceAllowTag(String ttl) {
		String allowTag = ConfigProperty.getString("xss.allowTag");
		if (!"".equals(allowTag)) {
			String[] allowTags = allowTag.split("|");

			for (String tag : allowTags) {
				String tag_ = "&lt;" + tag.toLowerCase() + "&gt;";
				ttl = ttl.replaceAll(tag_, "<"+ tag +">");

				tag_ = "&lt;" + tag.toLowerCase() + "/&gt;";
				ttl = ttl.replaceAll(tag_, "<"+ tag +"/>");

				tag_ = "&lt;/" + tag.toLowerCase() + "&gt;";
				ttl = ttl.replaceAll(tag_, "</"+ tag +">");
			}
		}

		return ttl;
	}


	/**
	 * <p>XSS값 복원</p>
	 *
	 * @param var0 값
	 * @return 값(String) 반환
	 */
	public static String restoreXSS(String var0) {
		String var1 = var0;
		if (!"".equals(var0)) {
			var1 = var0.replaceAll("&amp;", "&");
			var1 = var1.replaceAll("&quot;", "\"");
			var1 = var1.replaceAll("&lt;", "<");
			var1 = var1.replaceAll("&gt;", ">");
			var1 = var1.replaceAll("&#35;", "#");
			var1 = var1.replaceAll("&#37;", "%");
			var1 = var1.replaceAll("&#39;", "\'");
			var1 = var1.replaceAll("&#40;", "(");
			var1 = var1.replaceAll("&#41;", ")");
			var1 = var1.replaceAll("<script>", "&lt;script&gt");
		}

		return var1;
	}

	/**
	 * <p>XSS 클리어 </p>
	 *
	 * @param var0 값
	 * @return 값(String) 반환
	 */
	public static String clearXSS(String var0) {
		String var1 = var0;
		if (!"".equals(var0)) {
			var1 = var1.replaceAll("<script>", "&lt;script&gt");
			var1 = var1.replaceAll("</script>", "&lt;/script&gt");
		}

		return var1;
	}

	public static byte[] getSHA256(String input){
		//String res = "";

		byte[] sha256Byte = null;
		try{
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

			sha256.update(input.getBytes());
			sha256Byte = sha256.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		finally{
			
		}
		return sha256Byte;
	}

	/**
	 * <p>마스팅 값을 구합니다.</p>
	 *
	 * @param val 값
	 * @return 값(String) 반환
	 */
	public static String masking(String val) {
		String result = "";

		if (!"".equals(val)) {
			String masking = "";

			for (int i = 0; i < val.length() - 2; i++) {
				masking += "*";
			}

			result = val.substring(0, 1) + masking + val.substring(val.length() -1);
		}

		return result;
	}

	public static boolean isViewerExt(String fleNm) {
		if (!"".equals(fleNm)) {
			//List<String> fileExts = ArrayUtil.casting(ConfigProperty.getList("fileViewer.whiteExtensions"), String.class);
			//String ext = StringUtil.lowerCase(FilenameUtil.getExtension(fleNm));

			//if (fileExts.contains(ext)) {
			//	return true;
			//}
		}

		return false;
	}

	public static String encodeURIComponent(String s)
	{
		String result = null;

		try
		{
			result = URLEncoder.encode(s, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		}

		// This exception should never occur.
		catch (UnsupportedEncodingException e)
		{
			result = s;
		}

		return result;
	}

	/**
	 * <p>년도 값 4자리</p>
	 *
	 * @param
	 * @return 값(date) 반환
	 */
	public static String getCurrentYear() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

		return dateFormat.format(date);
	}

	/**
	 * <p>연월일 8자리 전송일시처리</p>
	 *
	 * @param
	 * @return 값(date) 반환
	 */
	public static String getCurrentDate() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		return dateFormat.format(date);
	}

	/**
	 * <p>연월일 8자리 전송일시처리(대쉬구분)</p>
	 *
	 * @param
	 * @return 값(date) 반환
	 */
	public static String getCurrentDateDash() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dateFormat.format(date);
	}

	/**
	 * <p>연월일 시분초 6자리 전송일시처리</p>
	 *
	 * @param
	 * @return 값(date) 반환
	 */
	public static String getCurrentTime() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");

		return dateFormat.format(date);
	}

	/**
	 * <p>연월일 시분초 6자리 전송일시처리</p>
	 *
	 * @param
	 * @return 값(date) 반환
	 */
	public static String getCurrentMil() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSSS");

		return dateFormat.format(date);
	}

	/**
	 * <p>원하는 포멧 날짜타입 추출</p>
	 *
	 * @param  formatType("yyyyMMddHHmmss")
	 * @return 값(date) 반환
	 */
	public static String getCurrentDateFormat(String formatType) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);

		return dateFormat.format(date);
	}



	/**
	 * <p>JSONObject map변환</p>
	 * @param <CrtTmg>
	 * @param str
	 * @param len
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getJsonToMap(JSONObject jsonMap) {

		Map<String, Object> map = null;

		try {
			map = new ObjectMapper().readValue(jsonMap.toString(), Map.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}


	public static String stringNullToDefault(String str) {
		if(str == null || "".equals(str)) {
			return "";
		} else {
			return str;
		}
	}

	public static String stringNullToZero(String str) {
		if(str == null || "".equals(str)) {
			return "0";
		} else {
			return str;
		}
	}

	//문자열 길이 가져오기
	public static int getSize(String str) {
    	int cnt = 0;

    	if(str != null) {
    		for (int j=0; j<str.length();) {
            	String tmp = str.substring(j++, j);
            	if(tmp.getBytes().length == tmp.length()) {
            		cnt++;
            	}else {
            		cnt += 2;
            	}
        	}
    	}
    	return cnt;
	}

	//문자열 특정문자로 자리수 채우기 (왼쪽)
	public static String setStrLFill(String str, String fill, int len) {
		if(str == null) str = "";

		for(int i = str.length(); i < len; i++) {
			str = fill + str;
			str = str.trim();
		}

		return str;

	}

	//문자열 특정문자로 자리수 채우기 (오른쪽)
	public static String setStrRFill(String str, String fill, int len) {
		if(str == null) str = "";

		for(int i = str.length(); i < len; i++) {
			str = str + fill;
			str = str.trim();
		}

		return str;

	}

	//숫자로변환 (콤마제거후 변환)
	public static int toInt(Object obj) {

		int result = 0;

		try {
			if(obj instanceof BigDecimal)
				result = ((BigDecimal)obj).intValue();
			else
				result = Integer.parseInt(obj.toString().replaceAll(",", ""));
		} catch(Exception e) {
			result = 0;
		}

		return result;
	}

	//string으로 반환
	public static String toString(Object obj) {

		String result = null;

		try {
			if(obj instanceof BigDecimal){
				result = ((BigDecimal)obj).toString();
			} else if(obj instanceof Long){
				result = ((Long)obj).toString();
			} else if(obj instanceof Integer){
				result = ((Integer)obj).toString();
			} else if(obj instanceof Double){
				result = ((Double)obj).toString();
			} else if(obj instanceof java.util.Date){
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj);
			} else {
				result = (String)obj;
			}
		} catch (Exception e) {
			result = null;
		}

		return result;
	}

	//배열 스트링으로 반환
	public static String arrayToString(Object obj) {
		String str = null;
		try {

			str = "";

			if(obj instanceof String) {
				str = toString(obj);
			} else if(obj instanceof ArrayList) {
				List<Object> list = new ArrayList<>();	  //default 0크기에 더 크면 자동 할당

				for(int i=0; i<list.size(); i++) {
					String get = (String) list.get(i);
					if(!isNull(get)) str += get + ",";
					else str += "" + ",";
				}
				str = str.substring(0, str.length() - 1);
			} else {
				str = null;
			}


		} catch (Exception e) {
			str = null;
		}

		return str;
	}

	//배열 스트링으로 반환
	public static String arrayToString(List<Object> list) {
		String str = null;
		try {

			str = "";
			for(int i=0; i<list.size(); i++) {
				String get = (String) list.get(i);
				if(!isNull(get)) str += get + ",";
				else str += "" + ",";
			}

			str = str.substring(0, str.length() - 1);
		} catch (Exception e) {
			str = null;
		}

		return str;
	}

	//list to Strings
	public static String[] toStrings(List<Object> str) {

		String[] sArrays =  (String[]) str.toArray(new String[str.size()]);

		return sArrays;
	}

	//obj to Strings
	public static String[] toStrings(Object obj) {

		String[] result = null;

		if(obj instanceof String[]) {
			result = (String[])obj;
		} else if(obj instanceof String) {
			result = new String[1];
			result[0] = toString(obj);
		} else if(obj instanceof ArrayList) {
			result =  (String[]) (new ArrayList<>()).toArray(new String[0]);	  //default 0크기에 더 크면 자동 할당
		} else {
			result = null;
		}

		return result;
	}


	//널(null) 체크
	static public boolean isNull(String str) {
        if(str == null || str.equals("null") || str.equals("")) return true;
        else return false;
    }

	//널(null) 체크 obj
    static public boolean isNull(Object obj) {
    	String str = toString(obj);
        if(str == null || str.equals("")) return true;
        else return false;
    }

	//nvl(Object obj, String newStr)
	static public String nvl(Object obj, String newStr) {
    	String str = toString(obj);
    	String param = "";

    	if(!isNull(str)) param = str;
    	else param = newStr;

    	return param;
    }

	//nvl(String str,String newStr)
	static public String nvl(String str,String newStr) {
    	String param = "";

    	if(!isNull(str)) param = str;
    	else param = newStr;

    	return param;
    }

	/**
     * 왼쪽공백에 문자를 채운다
     *
     *   String p = StringUtil.StringToDate(String str);
     */
    static public String lpad(Object obj,int cnt,String divs) {
    	String str = toString(obj);
        String convertStr = "";
        if(str.length() < cnt) {
            int fillCnt = cnt - str.length();
            for(int i=cnt; i>0; i--) {
                int startInt = cnt - fillCnt - i;
                int endInt = startInt + 1;

                if(i > str.length())
                    convertStr += divs;
                else {
                    if(i == 1)
                        convertStr += str.substring(startInt);
                    else
                        convertStr += str.substring(startInt, endInt);
                }
            }
        } else convertStr = str;

        return convertStr;
    }


    /**     
     * underscore('_')가 포함되어 있는 문자열을 Camel Case (단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는 utility 
     * 메서드('_') 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리 함)     
     * CommonUtil.convert2CamelCase("guar_no") > return guarNo;
     * CommonUtil.convert2CamelCase("GuarNo")  > return guarNo;
     *
     * @param underScore('_')가 포함된 변수명     
     * @return Camel 표기법 변수명     
    */
    static public String convert2CamelCase(String underScore) {
    	// '_' 가 나타나지 않으면 이미 camel case 로 가정함.        
    	// 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
    	// 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜  
    	if(underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
    		return underScore;
    	}

    	StringBuilder result = new StringBuilder();
    	boolean nextUpper = false;
    	int len = underScore.length();

    	for(int i=0; i<len; i++) {
    		char currentChar = underScore.charAt(i);
    		if(currentChar == '_')	{
    			nextUpper = true;
    		} else {
    			if(nextUpper) {
    				result.append(Character.toUpperCase(currentChar));
    				nextUpper = false;
    			}else {
    				result.append(Character.toLowerCase(currentChar));
    			}
    		}
    	}

    	return result.toString();
    }

}
