/**
 * 프로젝트명		: 프로젝트명
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: MessageProperty
 * 프로그램설명		:
 **/
package ddc.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author 		: BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Component
public class MessageProperty implements MessageSourceAware {

	private static MessageSource messageSource;
	
	public static String getMsg(String id) {
		String result = "";
		
		if (messageSource != null) {
			result = messageSource.getMessage(id, null, null, null);	
		}
		
		return result;
	}
	
	public static String getMsg(String id, Object[] args) {
		String result = "";
		
		if (messageSource != null) {
			result = messageSource.getMessage(id, args, null, null);	
		}
		
		return result;
	}
	
	public static String getMsg(String id, Object[] args, String defaultMessage) {
		String result = "";
		
		if (messageSource != null) {
			result = messageSource.getMessage(id, args, defaultMessage, null);	
		}
		
		return result;
	}

	public static String getMsg(String id, Object[] args, String defaultMessage, Locale locale) {
		String result = "";
		
		if (messageSource != null) {
			result = messageSource.getMessage(id, args, defaultMessage, locale);	
		}
		
		return result;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		MessageProperty.messageSource = messageSource;
	}
	
}