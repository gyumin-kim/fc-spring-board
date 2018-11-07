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

    @GetMapping("/login")
    @ResponseBody
    public String login(@RequestParam(name = "email") String email,
                 @RequestParam(name = "password") String password,
                 HttpSession httpSession) {
        log.info("**** Accepted value from AJAX(loginFormBtn). email: " + email + ", password: " + password + " ****");
        Member member = memberService.findMemberByEmail(email);

        if (email == null)
            return "nullValue";
        else {
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

    @GetMapping("/signup")
    @ResponseBody
    public String signup(@RequestParam(name = "email")String email,
                         @RequestParam(name = "name")String name,
                         @RequestParam(name = "password")String password) {
        log.info("**** Accepted value from AJAX(signupFormBtn). email: " + email + ", name: " + name + ", password: " + password + " ****");
        Member member = memberService.findMemberByEmail(email);
        log.info("member: " + member);

//        if (email == null || name == null)
//            return "nullValue";
//        else {
            // member 정보가 없는 경우 -> 가입 처리
            if (member == null)
                return "noSuchMember";
            // 이미 해당 member 정보가 존재하는 경우 -> 가입 불가
            else {

                return "existsSuchMember";
            }
//        }
    }
}
