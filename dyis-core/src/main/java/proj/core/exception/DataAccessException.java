package proj.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>DataAccessException.java (DataAccess Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class DataAccessException extends BaseException {

	private static final long serialVersionUID = 483005678713856896L;

	public DataAccessException(Throwable throwable) {
		super(throwable);
	}

	public DataAccessException(String str) {
		super(str);
	}

	public DataAccessException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
