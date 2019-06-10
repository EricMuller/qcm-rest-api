package com.emu.apps.qcm.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Basic Controller which is called for unhandled errors
 */
@Controller
public class AppErrorController implements ErrorController {


    private final static String ERROR_PATH = "/error";


    @Autowired
    private ErrorAttributes errorAttributes;


    /**
     * Supports the HTML Error View
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html", method = RequestMethod.GET)
    public ModelAndView errorHtml(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return new ModelAndView("/errors/error", getErrorAttributes(servletWebRequest, false));
    }

    /**
     * Supports other formats like JSON, XML
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity <Map <String, Object>> error(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map <String, Object> body = getErrorAttributes(servletWebRequest, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map <String, Object> getErrorAttributes(ServletWebRequest servletWebRequest,
                                                    boolean includeStackTrace) {
        return this.errorAttributes.getErrorAttributes(servletWebRequest, includeStackTrace);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
