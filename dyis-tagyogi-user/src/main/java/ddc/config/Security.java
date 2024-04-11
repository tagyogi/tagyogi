package ddc.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import ddc.security.filter.ConcurrentSessionFilter;

import java.net.URI;


/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>Security.java (시큐리티 설정)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Configuration
@EnableWebSecurity
public class Security {

//    @Autowired
//    private UserDetailServiceImpl userDetailService;

    //@Autowired
    //private MemberService memberService;

    // 시큐리티 제외 패턴
    private static final String[] EXCEPT_PATHS = {
            "/resources/**", "/static/**", "/com/**", "robots.txt", "/favicon.ico", "/error/**",
            "/css/**", "/fonts/**", "/images/**", "/js/**"
    };

    /**
     * <p>Security 제외 패턴 설정</p>
     *
     * @param web web
     * @throws Exception 예외처리
     */
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(EXCEPT_PATHS);
    }
    
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
  
        //chrome-error://chromewebdata/:1 Refused to display 'http://localhost:9090/' in a frame because it set 'X-Frame-Options' to 'deny'.
        http.headers().frameOptions().disable().addHeaderWriter(
        			new XFrameOptionsHeaderWriter(
        					new StaticAllowFromStrategy(URI.create("*"))
        			)
        		);
      
        return http.build();
    }

    /**
     * <p>Security 설정</p>
     *
     * @param http http
     * @throws Exception 예외처리
     * 
     * ##########################  사용  ###################
     */
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().ignoringAntMatchers( "/com/**"
				,"/index/**"
				,"/login/**"
				,"/sys/**"	
    			)
    				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.headers().frameOptions().sameOrigin();
       
        http.authorizeRequests().antMatchers("/com/**","/login/**","/error/**").permitAll();
    	
        //http.csrf().disable();
    	   /*http.csrf().ignoringAntMatchers( "/com/**"
				,"/login/**"
    								
    			)
    				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.headers().frameOptions().sameOrigin();
       
        http.authorizeRequests().antMatchers("/com/**","/login/**","/error/**").permitAll();
        		//.antMatchers("/member/my_page/**").authenticated()
                //.anyRequest().access("@authorizationChecker.check(request, authentication)");
     
        http.formLogin()
                .loginPage("/lgin/lginProc.do").permitAll()
                .loginProcessingUrl("/j_spring_security_check.do")
                .usernameParameter("usrId") 
                .passwordParameter("usrPw");  

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/j_spring_security_logout.do"))
                .addLogoutHandler(this.logoutSuccessHandler());

        http.sessionManagement().maximumSessions(20)
                .expiredUrl("/index/ssoCheck.do")
                .maxSessionsPreventsLogin(true);
*/
        // 커스텀 필터 사용 정의
        //http.addFilterAt(this.conCurrentSessionFilter(), org.springframework.security.web.session.ConcurrentSessionFilter.class);
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    private ConcurrentSessionFilter conCurrentSessionFilter() throws Exception {
        ConcurrentSessionFilter filter = new ConcurrentSessionFilter();
        filter.setSessionRegistry(sessionRegistry());
        filter.setExpiredUrl("/index.do");

        return filter;
    }
 
    
}
