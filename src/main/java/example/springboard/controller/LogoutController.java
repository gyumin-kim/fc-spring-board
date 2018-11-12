package example.springboard.controller;

import example.springboard.service.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    private static final Log log = LogFactory.getLog(LogoutController.class);
    private MemberService memberService;

    public LogoutController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession.getAttribute("admin") != null)
            httpSession.removeAttribute("admin");
        httpSession.removeAttribute("authUser");

        return "redirect:/";
    }
}
