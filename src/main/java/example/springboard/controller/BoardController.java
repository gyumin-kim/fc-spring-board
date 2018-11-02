package example.springboard.controller;

import example.springboard.dto.Board;
import example.springboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public String list1(ModelMap modelMap){
        List<Board> boards = boardService.showBoardListAll(1L);
        modelMap.addAttribute("boards", boards);
        return "list";
    }

    @GetMapping("/boards/write")
    public String write() { return "write"; }

    @PostMapping("/boards")                 // Post 방식의 요청
    public String write(@RequestParam(name = "title")String title,
                        @RequestParam(name = "content")String content){     // RequestParam 으로 값을 받아준다.

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        Board board = new Board();
        board.setDepth(0);
        board.setReplySeq(0);
        board.setCategoryId(1l);
        board.setMemberId(2L);
        board.setTitle(title);
        board.setContent(content);
        board.setRegDate(new Date());
        board.setIpAddr(ip);
        boardService.writeBoard(board);

        return "redirect:/boards";          // redirect 하라는 뜻!
    }
}
