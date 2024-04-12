package proj.core.validate;

import org.apache.commons.validator.GenericValidator;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.google.common.primitives.Doubles;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>ValidationUtil.java (ValidationUtil)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */

public class ValidationUtil extends ValidationUtils {
	
	/**
	 * <p>ValidationUtil 기본 생성자</p>
	 */
	public ValidationUtil() {
		super();
	}
	
	/**
     * <p>문자의 길이가 작으면 Reject합니다</p>
     * 
     * @param errors 에러 에러 객체
     * @param field field 필드
     * @param len 문자 길이 길이
     * @param errorCode 에러코드 에러코드
     */
    public static void rejectIfMinLength(Errors errors, String field, int len, String errorCode) {
    	rejectIfMinLength(errors, field, len, errorCode, null, null);
    }
    
    /**
     * <p>문자의 길이가 작으면 Reject합니다</p>
     * 
     * @param errors 에러 에러 객체
     * @param field field 필드
     * @param len 문자 길이 길이
     * @param errorCode 에러코드 에러코드
     * @param defaultMessage defaultMessage  기본메세지
     */
    public static void rejectIfMinLength(Errors errors, String field, int len, String errorCode, String defaultMessage) {
    	rejectIfMinLength(errors, field, len, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>문자의 길이가 작으면 Reject합니다</p>
     * 
     * @param errors 에러 에러 객체
     * @param field field 필드
     * @param len 문자 길이 길이
     * @param errorCode 에러코드 에러코드
     * @param errorArgs errorArgs  에러 에그먼트
     */
    public static void rejectIfMinLength(Errors errors, String field, int len,
                                         String errorCode, Object[] errorArgs) {
    	rejectIfMinLength(errors, field, len, errorCode, errorArgs, null);
    }
    
    /**
     * <p>문자의 길이가 작으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param len 문자 길이
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinLength(Errors errors, String field, int len,
                                         String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || val.toString().length() < len) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>문자의 길이가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param len 문자 길이
     * @param errorCode 에러코드 
     */
    public static void rejectIfMaxLength(Errors errors, String field, int len, String errorCode) {
    	rejectIfMaxLength(errors, field, len, errorCode, null, null);
    }
    
    /**
     * <p>문자의 길이가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param len 문자 길이
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxLength(Errors errors, String field,
                                         int len, String errorCode, String defaultMessage) {
    	rejectIfMaxLength(errors, field, len, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>문자의 길이가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param len 문자 길이
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMaxLength(Errors errors, String field, int len,
                                         String errorCode, Object[] errorArgs) {
    	rejectIfMaxLength(errors, field, len, errorCode, errorArgs, null);
    }
    
    /**
     * <p>문자의 길이가 크면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param len 문자 길이
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxLength(Errors errors, String field, int len,
                                         String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || val.toString().length() > len) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    
    /**
     * <p>문자의 길이가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minLen 최소 길이
     * @param maxLen 최대 길이
     * @param errorCode 에러코드
     */
    public static void rejectIfRangeLength(Errors errors, String field, int minLen, int maxLen, String errorCode) {
    	rejectIfRangeLength(errors, field, minLen, maxLen, errorCode, null, null);
    }
    
    /**
     * <p>문자의 길이가 범위에 없으면 Reject합니다</p>
     * @param errors 에러
     * @param field field
     * @param minLen 최소 길이
     * @param maxLen 최대 길이
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfRangeLength(Errors errors, String field,
                                           int minLen, int maxLen, String errorCode, String defaultMessage) {
    	rejectIfRangeLength(errors, field, minLen, maxLen, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>문자의 길이가 범위에 없으면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minLen 최소 길이
     * @param maxLen 최대 길이
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     */
    public static void rejectIfRangeLength(Errors errors, String field, int minLen, int maxLen,
                                           String errorCode, Object[] errorArgs) {
    	rejectIfRangeLength(errors, field, minLen, maxLen, errorCode, errorArgs, null);
    }

    /**
     * <p>문자의 길이가 범위에 없으면 Reject한다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minLen 최소 길이
     * @param maxLen 최대 길이
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfRangeLength(Errors errors, String field, int minLen, int maxLen,
                                           String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || val.toString().length() < minLen || val.toString().length() > maxLen) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     */
    public static void rejectIfMinValue(Errors errors, String field, int minVal, String errorCode) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfMinValue(Errors errors, String field,
                                        int minVal, String errorCode, String defaultMessage) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     */
    public static void rejectIfMinValue(Errors errors, String field, int minVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMinValue(errors, field, minVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject한다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfMinValue(Errors errors, String field, int minVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        int val = Integer.parseInt((String)errors.getFieldValue(field));
        
        if (val < minVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     */
    public static void rejectIfMaxValue(Errors errors, String field, int maxVal, String errorCode) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     *
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfMaxValue(Errors errors, String field,
                                        int maxVal, String errorCode, String defaultMessage) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMaxValue(Errors errors, String field, int maxVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field, int maxVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        int val = Integer.parseInt((String)errors.getFieldValue(field));
        
        if (val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드 
     */
    public static void rejectIfRangeValue(Errors errors, String field, int minVal, int maxVal, String errorCode) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field,
                                          int minVal, int maxVal, String errorCode, String defaultMessage) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfRangeValue(Errors errors, String field, int minVal, int maxVal,
                                          String errorCode, Object[] errorArgs) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field, int minVal, int maxVal,
                                          String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        int val = Integer.parseInt((String)errors.getFieldValue(field));
        
        if (val < minVal || val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드 
     */
    public static void rejectIfMinValue(Errors errors, String field, long minVal, String errorCode) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field,
                                        long minVal, String errorCode, String defaultMessage) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMinValue(Errors errors, String field, long minVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMinValue(errors, field, minVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field, long minVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        long val = Long.parseLong((String)errors.getFieldValue(field));
        
        if (val < minVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드 
     */
    public static void rejectIfMaxValue(Errors errors, String field, long maxVal, String errorCode) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field,
                                        long maxVal, String errorCode, String defaultMessage) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMaxValue(Errors errors, String field, long maxVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field, long maxVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        long val = Long.parseLong((String)errors.getFieldValue(field));
        
        if (val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드 
     */
    public static void rejectIfRangeValue(Errors errors, String field, long minVal, long maxVal, String errorCode) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field,
                                          long minVal, long maxVal, String errorCode, String defaultMessage) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfRangeValue(Errors errors, String field, long minVal, long maxVal,
                                          String errorCode, Object[] errorArgs) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject한다</p>
     *
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage
     */
    public static void rejectIfRangeValue(Errors errors, String field, long minVal, long maxVal,
                                          String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        long val = Long.parseLong((String)errors.getFieldValue(field));
        
        if (val < minVal || val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드 
     */
    public static void rejectIfMinValue(Errors errors, String field, float minVal, String errorCode) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field,
                                        float minVal, String errorCode, String defaultMessage) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMinValue(Errors errors, String field, float minVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMinValue(errors, field, minVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field, float minVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        float val = Float.parseFloat((String)errors.getFieldValue(field));
        
        if (val < minVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
	
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드 
     */
    public static void rejectIfMaxValue(Errors errors, String field, float maxVal, String errorCode) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field,
                                        float maxVal, String errorCode, String defaultMessage) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMaxValue(Errors errors, String field, float maxVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field, float maxVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        float val = Float.parseFloat((String)errors.getFieldValue(field));
        
        if (val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     */
    public static void rejectIfRangeValue(Errors errors, String field, float minVal, float maxVal, String errorCode) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field,
                                          float minVal, float maxVal, String errorCode, String defaultMessage) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfRangeValue(Errors errors, String field, float minVal, float maxVal,
                                          String errorCode, Object[] errorArgs) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field, float minVal, float maxVal,
                                          String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        float val = Float.parseFloat((String)errors.getFieldValue(field));
        
        if (val < minVal || val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     */
    public static void rejectIfMinValue(Errors errors, String field, double minVal, String errorCode) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field,
                                        double minVal, String errorCode, String defaultMessage) {
    	rejectIfMinValue(errors, field, minVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMinValue(Errors errors, String field, double minVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMinValue(errors, field, minVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 작으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMinValue(Errors errors, String field, double minVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        double val = Double.parseDouble((String)errors.getFieldValue(field));
        
        if (val < minVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
	
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드 
     */
    public static void rejectIfMaxValue(Errors errors, String field, double maxVal, String errorCode) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field,
                                        double maxVal, String errorCode, String defaultMessage) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 크면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfMaxValue(Errors errors, String field, double maxVal,
                                        String errorCode, Object[] errorArgs) {
    	rejectIfMaxValue(errors, field, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 크면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfMaxValue(Errors errors, String field, double maxVal,
                                        String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        double val = Double.parseDouble((String)errors.getFieldValue(field));
        
        if (val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     */
    public static void rejectIfRangeValue(Errors errors, String field, double minVal, double maxVal, String errorCode) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field,
                                          double minVal, double maxVal, String errorCode, String defaultMessage) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfRangeValue(Errors errors, String field, double minVal, double maxVal,
                                          String errorCode, Object[] errorArgs) {
    	rejectIfRangeValue(errors, field, minVal, maxVal, errorCode, errorArgs, null);
    }
    
    /**
     * <p>값의 크기가 범위에 없으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param minVal 최소값
     * @param maxVal 최대값
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRangeValue(Errors errors, String field, double minVal, double maxVal,
                                          String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        double val = Double.parseDouble((String)errors.getFieldValue(field));
        
        if (val < minVal || val > maxVal) {
        	errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    
    /**
     * <p>숫자가 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfNotNumber(Errors errors, String field, String errorCode) {
    	rejectIfNotNumber(errors, field, errorCode, null, null);
    }
   
    /**
     * <p>숫자가 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotNumber(Errors errors, String field,
                                         String errorCode, String defaultMessage) {
    	rejectIfNotNumber(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>숫자가 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfNotNumber(Errors errors, String field,
                                         String errorCode, Object[] errorArgs) {
    	rejectIfNotNumber(errors, field, errorCode, errorArgs, null);
    }
    
    /**
     * <p>숫자가 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotNumber(Errors errors, String field,
                                         String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        
        
        if (val == null || Doubles.tryParse(val.toString()) == null) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }

    /**
     * <p>숫자가 또는 영문이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfAlphaNumeric(Errors errors, String field,
                                            String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        //Object val = errors.getFieldValue(field);
        
        //if (val == null || !StringUtil.isAlphanumeric(val.toString())) {
        //    errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        //}
    }
    
    /**
     * <p>숫자가 또는 영문이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfAlphaNumeric(Errors errors, String field,
                                            String errorCode) {
    	rejectIfAlphaNumeric(errors, field, errorCode, null, null);
    }
    
    /**
     * <p>숫자가 또는 영문이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfAlphaNumeric(Errors errors, String field,
                                            String errorCode, String defaultMessage) {
    	rejectIfAlphaNumeric(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>숫자가 또는 영문이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfAlphaNumeric(Errors errors, String field,
                                            String errorCode, Object[] errorArgs) {
    	rejectIfAlphaNumeric(errors, field, errorCode, errorArgs, null);
    }
    
    /**
     * <p>날짜 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param datePattern 날짜 형식
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotDate(Errors errors, String field, String datePattern,
                                       String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || !GenericValidator.isDate(val.toString(), datePattern, true)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>날짜 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param datePattern 날짜 형식
     * @param errorCode 에러코드 
     */
    public static void rejectIfNotDate(Errors errors, String field, String datePattern,
                                       String errorCode) {
        rejectIfNotDate(errors, field, datePattern, errorCode, null, null);
    }
    
    /**
     * <p>날짜 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param datePattern 날짜 형식
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotDate(Errors errors, String field, String datePattern,
                                       String errorCode, String defaultMessage) {
        rejectIfNotDate(errors, field, datePattern, errorCode, null, defaultMessage);
    }
    
	/**
     * <p>날짜 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param datePattern 날짜 형식
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfNotDate(Errors errors, String field, String datePattern,
                                       String errorCode, Object[] errorArgs) {
        rejectIfNotDate(errors, field, datePattern, errorCode, errorArgs, null);
    }
    
    /**
     * <p>URL 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotUrl(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || !GenericValidator.isUrl(val.toString())) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>URL 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfNotUrl(Errors errors, String field, String errorCode) {
    	rejectIfNotUrl(errors, field, errorCode, null, null);
    }
    
    /**
     * <p>URL 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfNotUrl(Errors errors, String field, String errorCode, String defaultMessage) {
    	rejectIfNotUrl(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>URL 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfNotUrl(Errors errors, String field, String errorCode, Object[] errorArgs) {
    	rejectIfNotUrl(errors, field, errorCode, errorArgs, null);
    }
    
    /**
     * <p>E-mail 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfEmail(Errors errors, String field,
                                     String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || !GenericValidator.isEmail(val.toString())) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>E-mail 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfEmail(Errors errors, String field,
                                     String errorCode) {
        rejectIfEmail(errors, field, errorCode, null, null);
    }
    
    /**
     * <p>E-mail 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfEmail(Errors errors, String field,
                                     String errorCode, String defaultMessage) {
        rejectIfEmail(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>E-mail 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfEmail(Errors errors, String field,
                                     String errorCode, Object[] errorArgs) {
        rejectIfEmail(errors, field, errorCode, errorArgs, null);
    }
    
    
    /**
     * <p>전화번호 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfPhone(Errors errors, String field,
                                     String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        String regexp = "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        
        if (val == null || !GenericValidator.matchRegexp(val.toString(), regexp)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>전화번호 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfPhone(Errors errors, String field,
                                     String errorCode) {
    	rejectIfPhone(errors, field, errorCode, null, null);
    }
    
    /**
     * <p>전화번호 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfPhone(Errors errors, String field,
                                     String errorCode, String defaultMessage) {
    	rejectIfPhone(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>전화번호 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfPhone(Errors errors, String field,
                                     String errorCode, Object[] errorArgs) {
    	rejectIfPhone(errors, field, errorCode, errorArgs, null);
    }
    
    
    /**
     * <p>한글 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfHangul(Errors errors, String field,
                                      String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        String regexp = "/^[가-힣]+$/";
        
        if (val == null || !GenericValidator.matchRegexp(val.toString(), regexp)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }
    
    /**
     * <p>한글 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드 
     */
    public static void rejectIfHangul(Errors errors, String field,
                                      String errorCode) {
        rejectIfEmail(errors, field, errorCode, null, null);
    }
    
    /**
     * <p>한글 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfHangul(Errors errors, String field,
                                      String errorCode, String defaultMessage) {
        rejectIfEmail(errors, field, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>한글 형식이 아니면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfHangul(Errors errors, String field,
                                      String errorCode, Object[] errorArgs) {
        rejectIfEmail(errors, field, errorCode, errorArgs, null);
    }
    
    /**
     * <p>정규표현식에 맞지 않으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param regexp 정규표현식
     * @param errorCode 에러코드 
     */
    public static void rejectIfRegexp(Errors errors, String field, String regexp, String errorCode) {
    	rejectIfRegexp(errors, field, regexp, errorCode, null, null);
    }
    
    /**
     * <p>정규표현식에 맞지 않으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param regexp 정규표현식
     * @param errorCode 에러코드
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRegexp(Errors errors, String field,
                                      String regexp, String errorCode, String defaultMessage) {
    	rejectIfRegexp(errors, field, regexp, errorCode, null, defaultMessage);
    }
    
    /**
     * <p>정규표현식에 맞지 않으면 Reject합니다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param regexp 정규표현식
     * @param errorCode 에러코드
     * @param errorArgs errorArgs 
     */
    public static void rejectIfRegexp(Errors errors, String field, String regexp,
                                      String errorCode, Object[] errorArgs) {
    	rejectIfRegexp(errors, field, regexp, errorCode, errorArgs, null);
    }
    
    /**
     * <p>정규표현식에 맞지 않으면 Reject한다</p>
     * 
     * @param errors 에러
     * @param field field
     * @param regexp 정규표현식
     * @param errorCode 에러코드
     * @param errorArgs errorArgs
     * @param defaultMessage defaultMessage 
     */
    public static void rejectIfRegexp(Errors errors, String field, String regexp,
                                      String errorCode, Object[] errorArgs, String defaultMessage) {
        Assert.notNull(errors, "Errors object must not be null");
        Object val = errors.getFieldValue(field);
        
        if (val == null || !GenericValidator.matchRegexp(val.toString(), regexp)) {
            errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
        }
    }

}
