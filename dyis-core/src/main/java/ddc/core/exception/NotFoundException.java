package ddc.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>NotFoundException.java (NotFound Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class NotFoundException extends BaseException {

	private static final long serialVersionUID = -5716716425935339280L;

	public NotFoundException(Throwable throwable) {
		super(throwable);
	}

	public NotFoundException(String str) {
		super(str);
	}

	public NotFoundException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}