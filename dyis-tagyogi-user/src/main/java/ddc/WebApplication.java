/**
 * 프로젝트명		: 한국콘텐츠공제시스템 
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: WebApplication.java
 * 프로그램설명		: 애플리케이션 구동
 **/
package ddc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@SpringBootApplication
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
