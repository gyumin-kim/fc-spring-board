package example.springboard.controller;

import example.springboard.dto.Board;
import example.springboard.dto.FileInfo;
import example.springboard.service.BoardService;
import example.springboard.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/boards")
public class BoardController {
    private BoardService boardService;
    private FileUtil fileUtil;

    public BoardController(BoardService boardService, FileUtil fileUtil) {
        this.boardService = boardService;
        this.fileUtil = fileUtil;
    }
    @GetMapping("/write")
    public String writeform(){return "write";}

    @GetMapping
    public String list1(ModelMap modelMap){
        List<Board> boards = boardService.showBoardListAll(1L);
        modelMap.addAttribute("boards", boards);
        return "list";
    }

    @PostMapping              // Post 방식의 요청
    public String write(@RequestParam("title")String title,
                        @RequestParam("content")String content,
                        @RequestParam("file")MultipartFile file){     // RequestParam 으로 값을 받아준다.
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
        FileInfo fileInfo = fileUtil.handleFileStream(file);
        board.setFileInfo(fileInfo);
        boardService.writeBoard(board);

        return "redirect:/boards";          // redirect 하라는 뜻!
    }
}
