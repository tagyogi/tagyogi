/**
 * 프로젝트명		: 프로젝트명
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: Constants
 * 프로그램설명		:
 **/

package proj.core;

import java.io.Serializable;

/**
 * @author 		: BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	: 
 * modify-date  :
 * description  :
 */
public final class Constants implements Serializable {

	private static final long serialVersionUID = -5154419338908752253L;

	/**
	 * 프로젝트 관련 환경변수를 정의 한 xml 파일 경로
	 */
	public static final String PROJECT_CONFIG_XML_PATH = getPath();

	public static String getPath() {
		String result = "project-local-config.xml";

		String spring_profiles_active = System.getProperty("spring.profiles.active");

		if ("dev".equals(spring_profiles_active)) {
			result = "project-dev-config.xml";
		} else if ("prd".equals(spring_profiles_active)) {
			result = "project-prd-config.xml";
		}

//		 result = "config/project-config.xml";
		return result;
	}
}
