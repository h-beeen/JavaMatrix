package kr.beeen.matrix.global.error.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.global.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends BasicErrorController {

    @Autowired
    public GlobalErrorController(
            ErrorAttributes errorAttributes,
            ErrorProperties errorProperties,
            List<ErrorViewResolver> errorViewResolvers
    ) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return super.errorHtml(request, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));

        int statusCode = (int) errorAttributes.getOrDefault("status", 500);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        Map<String, Object> defaultErrorResponse = CommonResponse.getDefaultErrorResponse(httpStatus);

        return ResponseEntity.status(statusCode).body(defaultErrorResponse);
    }
}

