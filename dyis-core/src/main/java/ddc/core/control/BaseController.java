/**
 * 프로젝트명		: 프로젝트명
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: BaseController
 * 프로그램설명		: 기본 Controller
 **/
package ddc.core.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.WebApplicationContext;


/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>BaseController.java (BaseController)</p>
 *
 * Controller 공통 상속  
 *
 * @author 	    : BSG
 * @date 		: 2023.01.03
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class BaseController implements ApplicationContextAware {

	private WebApplicationContext context;

	private MessageSource messageSource;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (WebApplicationContext) applicationContext;
	}

	/**
	 * <p>특정 URL Path의 로컬 경로를 얻는 메서드</p>
	 *
	 * @param path = url path
	 * @return 로컬 경로
	 */
	protected String getRealPath(String path) {
		return this.context.getServletContext().getRealPath(path);
	}
	
}
