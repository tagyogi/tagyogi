package ddc.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>NotExistException.java (NotExist Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class NotExistException extends BaseException {

	private static final long serialVersionUID = 4816359529641994780L;

	public NotExistException(Throwable throwable) {
		super(throwable);
	}

	public NotExistException(String str) {
		super(str);
	}

	public NotExistException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
