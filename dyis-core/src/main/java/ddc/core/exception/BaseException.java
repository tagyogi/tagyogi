package ddc.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>BaseException.java (Base Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -3691995847842319765L;
	
	BaseException() {
    }
	
	public BaseException(Throwable throwable) {
		super(throwable); 
	}

	public BaseException(String str) {
		super(str);
	}

	public BaseException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
