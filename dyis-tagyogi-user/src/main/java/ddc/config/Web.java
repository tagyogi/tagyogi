package ddc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import ddc.intercept.*;
import javax.servlet.http.HttpSessionListener;

import java.util.Properties;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>Web.java (스프링 웹 프로젝트 설정)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Configuration
public class Web implements WebMvcConfigurer {

//	@Autowired
//	private TotalSearchInterceptor totalSearchInterceptor;

	@Autowired
	private AgentInterceptor agentInterceptor;

	@Value("${spring.messages.basename}")
	String messagesBasename = null;
	@Value("${spring.messages.encoding}")
	String messagesEncoding = null;

	// 허용 패스 (사용안함)
	private static final String[] ACCEPT_PATHS = {
			"/", "/**/**.do"
	};

	// 제외 패스
	private static final String[] EXCLUDE_PATHS = {
			//"/", "/index/index.do", "/index/accesFail.do", "/index/sessChk.do", "/lgin/**.do", "/com/**.do",
			"/", "/main.do", "/kocfc.do", "/index/**.do", "/login/**.do", "/com/**.do", "/membReq/**.do", "/brd/**.do", "/file/fileDownBrd.do", "/file/fileImgDown.do"
			, "/acciGuar/selectAcciGuarList.do", "/acciGuar/selectAcciGuarConfirm.do", "/acciGuar/selectAcciGuarReq.do"
			, "/guarSure/selectGuarSureList.do", "/guarSure/selectGuarSure.do", "/guarSure/updateGuarSureSign.do", "/selectMainPop.do"
			, "/guar/updateGuarPrintAfter.do", "/installInfoView.do", "/comConf/selectGuarConfirm.do"
	};



	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		//registry.addInterceptor(totalSearchInterceptor).addPathPatterns(ACCEPT_PATHS).excludePathPatterns("/download.do", "/browser.do");
		registry.addInterceptor(agentInterceptor).addPathPatterns(ACCEPT_PATHS).excludePathPatterns(EXCLUDE_PATHS);
	}


	/**
	 * <p>타입리프 커스텀 태그 빈 등록</p>
	 *
	 * @return MyDialect
	 */
	@Bean
	public MyDialect myDialect() {
		return new MyDialect();
	}

	/**
	 * <p>세션 빈 등록</p>
	 *
	 * @return HttpSessionListener
	 */
	@Bean
	public HttpSessionListener httpSessionListener(){
		return new Session();
	}

	/**
	 * <p>jsonView 빈 등록</p>
	 *
	 * @return MappingJackson2JsonView
	 */
	@Bean
	public MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}

	/**
	 * <p>jsonViewText 빈 등록</p>
	 *
	 * @return MappingJackson2JsonView
	 */
	@Bean
	public MappingJackson2JsonView jsonViewText() {
		MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
		mappingJackson2JsonView.setContentType("text/plain");
		return mappingJackson2JsonView;
	}

	/**
	 * <p>message source</p>
	 *
	 * @return ReloadableResourceBundleMessageSource
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(messagesBasename.split(","));
		messageSource.setDefaultEncoding(messagesEncoding);
		return messageSource;
	}


}
