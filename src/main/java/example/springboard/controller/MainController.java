package example.springboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {
    public MainController(){
        System.out.println("MainController()실행");
    }

    @RequestMapping("/")
    public String main(){
        // view name을 리턴, /WEB-INF/views/index.jsp로 forward
        return "cover.jsp";
    }
}
