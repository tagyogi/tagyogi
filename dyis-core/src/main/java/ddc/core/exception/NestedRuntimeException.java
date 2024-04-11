package ddc.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>NestedRuntimeException.java (NestedRuntime Exception)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class NestedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2369038646145880976L;

	private Throwable cause;

	public NestedRuntimeException(Throwable throwable) {
        cause = throwable;
    }

    public NestedRuntimeException(String str) {
        super(str);
    }

    public NestedRuntimeException(String str, Throwable throwable) {
        super(str);
        cause = throwable;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        if(cause == null) {
            return super.getMessage();
        } else {
            return new StringBuilder().append(super.getMessage()).append("; nested exception is ").append(cause.getClass().getName()).append(": ").append(cause.getMessage()).toString();
        }
    }

    public void printStackTrace(PrintStream printstream) {
        if(cause == null) {
            super.printStackTrace(printstream);
        } else {
            printstream.println(this);
            //cause.printStackTrace(printstream);
        }
    }

    public void printStackTrace(PrintWriter printwriter) {
        if(cause == null) {
            super.printStackTrace(printwriter);
        } else {
            printwriter.println(this);
            //cause.printStackTrace(printwriter);
        }
    }

}
