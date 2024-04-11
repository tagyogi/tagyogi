/**
 * 프로젝트명		: 한국콘텐츠공제시스템 
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: Validator.java
 * 프로그램설명		: 유효성 Domain
 **/
package ddc.validation.util;


import java.util.List;

import ddc.core.exception.BaseException;
import ddc.core.message.MessageProperty;

public class ValidatorUtil {

    public static final String BBR_EXP_PFX = "exception.common.";

    public static void isObjectNull(Object obj, String msg1) {
        if (obj == null) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "not.exist.object", new String[]{msg1}));
        }

        if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "not.exist.objects", new String[]{msg1}));
            }
        }
    }

    public static void isNotExist(String val, String msg1, String msg2) {
        if ("".equals(val)) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "not.exist", new String[]{msg1, msg2}));
        }
    }

    public static void isBlank(String val, String msg1, String msg2) {
        if ("".equals(val)) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "required", new String[]{msg1, msg2}));
        }
    }

    public static void isBlank(int val, String msg1, String msg2) {
        if (val <= 0) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "required", new String[]{msg1, msg2}));
        }
    }

    public static void isBlank(int val, String msg1, int msg2, String msg3) {
        if (val <= 0) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "required", new String[]{msg1, Integer.toString(msg2), msg3}));
        }
    }

    public static void isBlank(String val1, String val2, String msg1, String msg2) {
        if (val1.equals(val2)) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "required", new String[]{msg1, msg2}));
        }
    }

    public static void isBlank(String val, String msg1, int msg2, String msg3) {
        if ("".equals(val)) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "required2", new String[]{msg1, Integer.toString(msg2), msg3}));
        }
    }

    public static void isNumberZero(Object val, String msg1, String msg2) {
        if (val == null) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt", new String[]{msg1, msg2}));
        }

        if (val instanceof Number) {
            if (Long.parseLong((String) val) == 0) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt", new String[]{msg1, msg2}));
            }
        }
        if (val instanceof Float) {
            if (Float.parseFloat((String) val) == 0) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt", new String[]{msg1, msg2}));
            }
        }
    }

    public static void isNumberZero(Object val, String msg1, int msg2, String msg3) {
        if (val == null) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt2", new String[]{msg1, Integer.toString(msg2), msg3}));
        }

        if (val instanceof Integer || val instanceof Long) {
            if (Long.parseLong((String) val) == 0) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt2", new String[]{msg1, Integer.toString(msg2), msg3}));
            }
        }
        if (val instanceof Float) {
            if (Float.parseFloat((String) val) == 0) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt2", new String[]{msg1, Integer.toString(msg2), msg3}));
            }
        }
    }

    public static void isNumberMinus(Object val, String msg1, int msg2, String msg3) {
        if (val == null) {
            throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt2", new String[]{msg1, Integer.toString(msg2), msg3}));
        }

        if (val instanceof Integer || val instanceof Long) {
            if (Long.parseLong((String) val) <= -1) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt3", new String[]{msg1, Integer.toString(msg2), msg3}));
            }
        }
        if (val instanceof Float) {
            if (Float.parseFloat((String) val) <= -1) {
                throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "number.zero.gt3", new String[]{msg1, Integer.toString(msg2), msg3}));
            }
        }
    }

    public static void isNotMatchKey(String msg1, int msg2, String msg3) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "not.match.key", new String[]{msg1, Integer.toString(msg2), msg3}));
    }

    public static void isNotMatchVal(String msg1, String msg2) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "not.match.val", new String[]{msg1, msg2}));
    }

    public static void isAlreadyExist(String msg1, String msg2) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "already.exist", new String[]{msg1, msg2}));
    }

    public static void isAlreadyExist(String msg1, int msg2, String msg3) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "already.exist", new String[]{msg1, Integer.toString(msg2), msg3}));
    }

    public static void isIncorrectBehavior(String msg1) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "incorrect.behavior3"));
    }

    public static void isIncorrectBehavior(String msg1, String msg2) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "incorrect.behavior", new String[]{msg1, msg2}));
    }

    public static void isIncorrectBehavior(String msg1, int msg2, String msg3) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "incorrect.behavior2", new String[]{msg1, Integer.toString(msg2), msg3}));
    }

    public static void isDateGt(String msg1, String msg2, String msg3) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "date.gt", new String[]{msg1, msg2, msg3}));
    }

    public static void isDupliation(String msg1, String msg2) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "duplication", new String[]{msg1, msg2}));
    }

    public static void isOverCount(String msg1, String msg2, String msg3) {
        throw new BaseException(MessageProperty.getMsg(BBR_EXP_PFX + "over.count", new String[]{msg1, msg2, msg3}));
    }

}
