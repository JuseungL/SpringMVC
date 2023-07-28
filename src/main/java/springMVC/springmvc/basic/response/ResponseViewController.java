package springMVC.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        // ModelAndView 객체를 생성하여 View의 이름을 "response/hello"로 설정하고, "data"라는 속성에 "hello!" 값을 추가
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        // Model 객체를 사용하여 "data"라는 속성에 "hello!!" 값을 추가하고, View의 이름인 "response/hello"를 반환
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }

    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        // 해당 메소드는 View의 이름을 반환하지 않고, Model 객체를 사용하여 "data"라는 속성에 "hello!!" 값을 추가
        // 이 경우, View의 이름은 요청 URL "/response/hello"를 기반으로 결정
        model.addAttribute("data", "hello!!");
    }
}
