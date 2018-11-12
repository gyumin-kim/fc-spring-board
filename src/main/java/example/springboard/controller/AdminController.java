package example.springboard.controller;

import example.springboard.dto.Member;
import example.springboard.service.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Log log = LogFactory.getLog(AdminController.class);
    private MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 관리자가 member의 권한 관리하는 페이지를 보여준다
     */
    @GetMapping
    public String showAdminPage(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        modelMap.addAttribute("members", memberService.findMembers());

        return "admin";
    }

    /**
     * memberId를 받아 권한 수정사항을 적용
     */
    @PostMapping
    public String applyPermission(@RequestParam(value = "permissionRead", required = false) String read,
                                  @RequestParam(value = "permissionWrite", required = false) String write,
                                  @RequestParam(value = "permissionUpdate", required = false) String update,
                                  @RequestParam(value = "permissionDelete", required = false) String delete,
                                  @RequestParam("memberId") Long memberId) {
        Member member = memberService.findMemberById(memberId);
        int[] permissions = new int[4];
        if (read != null) {
            permissions[0] = 1;
        }
        if (write != null) {
            permissions[1] = 2;
        }
        if (update != null) {
            permissions[2] = 3;
        }
        if (delete != null) {
            permissions[3] = 4;
        }

        memberService.applyMemberPermission(member, permissions);
        log.info("Member " + memberId + ": 회원 권한 수정 완료");

        return "redirect:/admin";
    }
}
