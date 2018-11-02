package example.springboard.controller;

import example.springboard.dto.Member;
import example.springboard.service.MemberService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class LoginController {
    private MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "email") String email,
                        @RequestParam(name = "password") String password,
                        HttpSession httpSession,
                        HttpServletRequest req) {
        Member member = memberService.findMemberByEmail(email);

        // member 정보가 없는 경우
        if (member == null) {
            req.setAttribute("isIdNull", true);

            return "";
        } else {
            // 비밀번호 일치하지 않을 경우
            if (!password.equals(member.getPassword())) {
                req.setAttribute("wrongPw", true);

                return "";
            }
            // 비밀번호 일치할 경우 (로그인 성공)
            else {
                httpSession.setAttribute("authUser", member);
                System.out.println(member.toString());
                return "redirect:/";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("authUser");
        return "redirect:/";
    }
}
