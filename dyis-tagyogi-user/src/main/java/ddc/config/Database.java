package ddc.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ddc.core.datacon.DefaultConnMapper;
import ddc.core.datasource.JndiPropertyHolder;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>Database.java (디비 설정)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Configuration
@MapperScan(value = "ddc.biz.*.repository", annotationClass = DefaultConnMapper.class, sqlSessionFactoryRef = "defaultSqlSessionFactory")
@EnableTransactionManagement
public class Database {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.default.datasource")
    public JndiPropertyHolder getJndiPropertyHolder() {
        return new JndiPropertyHolder();
    }

    @Primary
    @Bean(name = "defaultDataSource", destroyMethod = "")
    public DataSource defaultDataSource() throws NamingException {
        String jndiName = getJndiPropertyHolder().getJndiName();
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }

    @Primary
    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(defaultDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis.xml"));

        //List<String> packages = new ArrayList<>();
        String packages = "ddc.core.domain";
        sqlSessionFactoryBean.setTypeAliasesPackage(packages);

        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "defaultSqlSessionTemplate")
    public SqlSessionTemplate defaultSqlSessionTemplate(SqlSessionFactory defaultSqlSessionFactory) {
        return new SqlSessionTemplate(defaultSqlSessionFactory);
    }

}

