/**
 * 프로젝트명		: 동양정보서비스 
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: WebApplication.java
 * 프로그램설명		: 애플리케이션 구동
 **/
package proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class DevApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DevApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
	}
}
