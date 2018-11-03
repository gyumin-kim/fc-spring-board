package example.springboard.controller;

import example.springboard.dto.Member;
import example.springboard.service.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    private static final Log log = LogFactory.getLog(LoginController.class);
    private MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam(name = "email") String email,
                 @RequestParam(name = "password") String password,
                 HttpSession httpSession) {
        log.info("**** Accepted value from AJAX. email: " + email + ", password: " + password + " ****");
        Member member = memberService.findMemberByEmail(email);

        // member 정보가 없는 경우
        if (member == null)
            return "noSuchMember";
        else {
            // 비밀번호 일치하지 않을 경우
            if (!password.equals(member.getPassword()))
                return "wrongPassword";
            // 비밀번호 일치할 경우 (로그인 성공)
            else {
                httpSession.setAttribute("authUser", member);
                log.info(member.toString());
                return "loginSuccess";
            }
        }
    }
}
