package ddc.core.exception;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>ConfigPropertyException.java (ConfigProperty Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class ConfigPropertyException extends BaseException {

	static final long serialVersionUID = -7661650855089844206L;

	public ConfigPropertyException(Throwable throwable) {
		super(throwable);
	}

	public ConfigPropertyException(String str) {
		super(str);
	}

	public ConfigPropertyException(String str, Throwable throwable) {
		super(str, throwable);
	}

}
