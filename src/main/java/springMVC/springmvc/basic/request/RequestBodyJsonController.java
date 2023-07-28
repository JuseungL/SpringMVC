package springMVC.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import springMVC.springmvc.basic.HelloData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"JuseungL", "age":23}
 * content-type: application/json
 */
// Lombok의 @Slf4j 어노테이션은 자동으로 로깅 기능을 제공
// log 변수를 사용하여 로그를 출력
@Slf4j
@Controller
public class RequestBodyJsonController {

    // Jackson 라이브러리의 ObjectMapper는 JSON 데이터를 자바 객체로 변환하는데 사용
    private ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // HttpServletRequest에서 데이터를 읽어오기 위해 ServletInputStream을 사용
        ServletInputStream inputStream = request.getInputStream();
        // StreamUtils.copyToString 메소드를 사용하여 InputStream에서 문자열 데이터를 읽어옴
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        // 읽어온 JSON 데이터를 로그로 출력
        log.info("messageBody={}", messageBody);

        // JSON 데이터를 HelloData 클래스로 변환
        // objectMapper.readValue 메소드를 사용하여 JSON 데이터를 자바 객체로 변환
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);

        // 변환된 객체의 정보를 로그로 출력
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        // 응답으로 "ok" 문자열.
        response.getWriter().write("ok");
    }


    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws
            IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-
    type: application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    (Accept: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }

}

