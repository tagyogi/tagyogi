package ddc.config;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>TomcatEmbdded.java (톰캣 embdded 설정 (로컬용))</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class TomcatEmbdded {
    @Bean
    @Profile("local")
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }
            @Override
            protected void postProcessContext(Context context) {
            	 ContextResource defaultResource = new ContextResource();
                 defaultResource.setName("KocfcPool");
                 defaultResource.setType(DataSource.class.getName());
                 defaultResource.setProperty("factory", "org.apache.commons.dbcp2.BasicDataSourceFactory");
                 //defaultResource.setProperty("driverClassName", "net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
                 defaultResource.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
                 //defaultResource.setProperty("url", "jdbc:log4jdbc:oracle:thin:@175.125.91.25:1531/KOCFC");
                 defaultResource.setProperty("url", "jdbc:oracle:thin:@192.168.50.128:1521/KOCFC");
                 defaultResource.setProperty("username", "kocfcdev");
                 defaultResource.setProperty("password", "zhsxpscm^2023");
                 
                 //defaultResource.setProperty("url", "jdbc:oracle:thin:@175.125.91.25:1531/KOCFC");
                 //defaultResource.setProperty("username", "kocfcdev");
                 //defaultResource.setProperty("password", "kocfcdev3#");
                 context.getNamingResources().addResource(defaultResource);
            }
        };
    }
}
