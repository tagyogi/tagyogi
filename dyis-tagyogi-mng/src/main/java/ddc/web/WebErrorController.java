package ddc.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MyErrorController.java (에러 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2019. 10. 23.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Controller
public class WebErrorController implements ErrorController {

    /**
     * <p>handleError</p>
     *
     * @param request request
     * @param model model
     * @return String
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object url = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        String viewPath = "/com/error";
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode);

            if (message != null) {
                model.addAttribute("message", message.toString());
            }

            // 파일/이미지 업로드
            //if (statusCode == 500 && ArrayUtil.contains(UPLOAD_URLS, url)) {
           //     return viewPath + "/upload_error";
            //}

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return viewPath + "/error404";
            }

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return viewPath + "/error500";
            }
        }

        return viewPath + "/error";
    }

    //@Override
    //public String getErrorPath() {
    //    return "/error";
    //}

}