package proj.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>FileUploadException.java (FileUpload Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class FileUploadException extends BaseException {

	private static final long serialVersionUID = -3895042219543042520L;

	public FileUploadException(Throwable throwable) {
		super(throwable);
	}

	public FileUploadException(String str) {
		super(str);
	}

	public FileUploadException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
