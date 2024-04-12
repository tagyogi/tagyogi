package proj.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>UnmatchException.java (Unmatch Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class UnmatchException extends BaseException {

	private static final long serialVersionUID = 763609228765561004L;

	public UnmatchException(Throwable throwable) {
		super(throwable);
	}

	public UnmatchException(String str) {
		super(str);
	}

	public UnmatchException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
