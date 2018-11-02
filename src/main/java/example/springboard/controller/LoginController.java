package example.springboard.controller;

import example.springboard.dto.Member;
import example.springboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }

//    @PostMapping("/login")
//    public String login(@RequestParam(name = "email") String email,
//                        @RequestParam(name = "password") String password) {
//        Member member = new Member();
//
//        memberService.findPasswordByName(email);
//    }
}
