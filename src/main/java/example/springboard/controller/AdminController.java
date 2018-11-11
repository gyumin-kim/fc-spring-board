package example.springboard.controller;

import example.springboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    private MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/admin")
    public String showAdminPage(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        modelMap.addAttribute("members", memberService.findMembers());

        return "admin";
    }

    /**
     * 회원 권한 수정사항을 적용
     */
    @PostMapping("/admin")
    public String applyPermission(@RequestParam("permissionRead") String read,
                                  @RequestParam("permissionWrite") String write,
                                  @RequestParam("permissionUpdate") String update,
                                  @RequestParam("permissionDelete") String delete,
                                  @RequestParam("memberId") Long memberId) {


        return "redirect:/admin";
    }
}
