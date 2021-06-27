package com.emu.apps.qcm.rest.controllers.errors;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Basic Controller which is called for unhandled errors
 */
@Controller
@Profile("webmvc")
public class AppErrorController implements ErrorController {


    private static final String ERROR_PATH = "/error";


    @Autowired
    private ErrorAttributes errorAttributes;


    /**
     * Supports the HTML Error View
     *
     * @param request HttpServletRequest
     * @return view /errors/error
     */
    @GetMapping(value = ERROR_PATH, produces = "text/html")
    @Operation(hidden = true)
    public ModelAndView errorHtml(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return new ModelAndView("/errors/error", getErrorAttributes(servletWebRequest, false));
    }

    /**
     * Supports other formats like JSON, XML
     *
     * @param request HttpServletRequest
     * @return Send status  specified in the request or Send Http 500 Internal erreur
     */
    @GetMapping(value = ERROR_PATH)
    @ResponseBody
    @Operation(hidden = true)
    public ResponseEntity <Map <String, Object>> error(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map <String, Object> body = getErrorAttributes(servletWebRequest, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity <>(body, status);
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
        return !"false".equalsIgnoreCase(parameter);
    }

    private Map <String, Object> getErrorAttributes(ServletWebRequest servletWebRequest, boolean includeStackTrace) {
        ErrorAttributeOptions options = includeStackTrace ? ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) : ErrorAttributeOptions.of();
        return this.errorAttributes.getErrorAttributes(servletWebRequest, options);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (Objects.nonNull(statusCode)) {
            return HttpStatus.valueOf(statusCode);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
