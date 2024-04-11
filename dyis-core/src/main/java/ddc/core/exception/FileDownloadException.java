package ddc.core.exception;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>FileDownloadException.java (FileDownload Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class FileDownloadException extends BaseException {

	private static final long serialVersionUID = 6674178171593248333L;

	public FileDownloadException(Throwable throwable) {
		super(throwable);
	}

	public FileDownloadException(String str) {
		super(str);
	}

	public FileDownloadException(String str, Throwable throwable) {
		super(str, throwable);
	}

	public String getStrStackTrace() {
		return this.getStrStackTrace();
	}

}
