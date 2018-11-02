package example.springboard.controller;

import example.springboard.dto.Member;
import example.springboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam(name = "email")String email,
                         @RequestParam(name = "name")String name,
                         @RequestParam(name = "password")String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);

        // member 정보 받아 회원가입 처리
        memberService.addMember(member);

        return "index";
    }
}
