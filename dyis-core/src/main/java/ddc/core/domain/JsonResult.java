package ddc.core.domain;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.HashMap;

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
 * description  :
 */
@JsonRootName("result")
public class JsonResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	public JsonResult() {
		super.put("resultCode", 0);
		super.put("resultMsg", "정상 처리 되었습니다.");
		super.put("data", new ArrayList());
	}

	public void setResult(int resultCode, String resultMsg) {
		super.put("resultCode", resultCode);
		super.put("resultMsg", resultMsg);
	}
	
	public void setResult(int resultCode, String resultMsg, String resultMsgDtl) {
		super.put("resultCode", resultCode);
		super.put("resultMsg", resultMsg);
		super.put("resultMsgDtl", resultMsgDtl);
	}
	
	public void add(String key, Object val) {
		super.put(key, val);
	}

}