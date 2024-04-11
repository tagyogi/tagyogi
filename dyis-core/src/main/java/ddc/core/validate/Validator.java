package ddc.core.validate;

import org.springframework.validation.Errors;

public interface Validator extends org.springframework.validation.Validator {

	/**
	 * <p>구분별 유효성 검사를 합니다.</p>
	 * 
	 * @param tp 유형
	 * @param target target
	 * @param errors 에러
	 */
	public void validate(String tp, Object target, Errors errors);
	
}
