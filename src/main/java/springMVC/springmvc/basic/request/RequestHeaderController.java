package springMVC.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request, //request 정보
                          HttpServletResponse response, //response 정보
                          HttpMethod httpMethod, //어떤 HTTP Method로 요청이 들어왔는가
                          Locale locale, //언어 정보
                          @RequestHeader MultiValueMap<String, String> headerMap, //모든 헤더 정보 - MultiValueMap 형식
                          @RequestHeader("host") String host, // host 정보
                          @CookieValue(value = "myCookie", required = false) String cookie// 쿠키 정보
                          ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}
