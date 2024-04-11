package ddc.core.domain;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>WsErrorCode (WsErrorCode Domain)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public enum WsErrorCode {
    CODE_0(0, "SUCCESS"),
    CODE_1(-1, "NULL_OBJECT"),
    CODE_2(-2, "NOT_VALIDITY"),
    CODE_3(-3, "NOT_REQUIRED"),
    CODE_99(-99, "SYSTEM_ERROR");

    private int cd;
    private String msg;


    WsErrorCode(int cd, String msg) {
        this.cd = cd;
        this.msg = msg;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
