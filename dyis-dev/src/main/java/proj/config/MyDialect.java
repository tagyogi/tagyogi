package proj.config;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import proj.core.common.CommonUtil;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MyDialect.java (타임리프 커스텀 테그)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class MyDialect extends AbstractDialect implements IExpressionObjectDialect {

    MyDialect() {
        super("My Dialect");
    }

    /**
     * <p>커스텀 태그 명명 규칙</p>
     *
     * 타입리프에서 기본으로 제공하는 태그
     * https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-a-expression-basic-objects
     */
    protected static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
            Collections.unmodifiableSet(new LinkedHashSet<String>(asList(
                    new String[]{
                            "commonUtil",
                            "stringUtil",
                            "stringEscapeUtil",
                            "numberUtil",
                            "numberFormatUtil",
                            "dateUtil",
                            "dateFormatUtil",
                            "collectionUtil",
                            "htmlUtil",
                            "httpUtil",
                            "encodeUtil",
                            "decodeUtil",
                            "seedCryptUtil",
                            "configProperty",
                            "messageProperty",
                            "mapUtil",
                            "fileUtil",
                            "secureUtil",
                            "filenameUtil"
                    }
            )));

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return ALL_EXPRESSION_OBJECT_NAMES;
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                if ("commonUtil".equals(expressionObjectName)) {
                    return new CommonUtil();
                }

                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

}
