package ddc.core.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author      : BSG
 * @date        : 2023-01-04
 * <p> 
 * modifier 	:
 * modify-date  :
 * description  : 로그 필터
 */
public class LogBackFilter extends Filter<ILoggingEvent> {
	
	@Override
	public FilterReply decide(ILoggingEvent event) {    
		
        if (event.getMessage().indexOf("RMI") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("Call to method") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("apache") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("Received") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("Using org") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("log4jdbc") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("JDBC Connection") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("d.w") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("o.s.") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("org.mybatis.spring.SqlSessionUtils") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("SqlSessionUtils") > -1) {
            return FilterReply.DENY;
        }else if (event.getMessage().indexOf("SpringManagedTransaction") > -1) {
            return FilterReply.DENY;
        }else {
        	return FilterReply.ACCEPT;
        }
    	
        
    }
}
