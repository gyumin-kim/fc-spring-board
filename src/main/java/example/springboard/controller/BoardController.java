package example.springboard.controller;

import example.springboard.dto.*;
import example.springboard.service.BoardService;
import example.springboard.service.CommentService;
import example.springboard.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/boards")
public class BoardController {
    private BoardService boardService;
    private CommentService commentService;
    private FileUtil fileUtil;

    public BoardController(BoardService boardService, CommentService commentService, FileUtil fileUtil) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.fileUtil = fileUtil;
    }

    @GetMapping("/write")
    public String writeform() {
        return "write";
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

    @GetMapping("/{categoryId}")
    public String list(@PathVariable Long categoryId,
                       @ModelAttribute("criteria") Criteria criteria,
                       ModelMap modelMap) throws Exception{
        // categoryId 값 유지를 위해
//        request.setAttribute("categoryId", categoryId);

        // 게시판 글 리스트
        modelMap.addAttribute("boards", boardService.showBoardListAll(categoryId, criteria));

        // 페이지 나누기 관련 처리
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
        pageMaker.setTotalBoardCount(boardService.getBoardCountAll(categoryId));  // 게시글의 총 개수

        // 게시판 하단의 페이징 관련, 이전 / 페이지 링크 / 다음
        modelMap.addAttribute("pageMaker", pageMaker);

        return "list";
    }

    // TODO : categoryId의 defaultValue는 나중에 지우자
    @GetMapping("/search/{categoryId}")
    public String search(@PathVariable Long categoryId,
                         @ModelAttribute("criteria")Criteria criteria,
                         ModelMap modelMap) throws Exception{
        // 값 유지를 위해
//        request.setAttribute("categoryId", categoryId);
//        request.setAttribute("criteria", criteria);

        // 게시판 글 리스트
        modelMap.addAttribute("boards", boardService.showBoardListSearch(categoryId, criteria));

        // 페이지 나누기 관련 처리
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
        pageMaker.setTotalBoardCount(boardService.getBoardCountBySearch(categoryId, criteria));  // 게시글의 총 개수

        // 게시판 하단의 페이징 관련, 이전 / 페이지 링크 / 다음
        modelMap.addAttribute("pageMaker", pageMaker);

        return "list";
    }

    @GetMapping("/{categoryId}/{id}")
    public String detail(@PathVariable Long categoryId,
                         @PathVariable Long id, ModelMap modelMap) {
        Board board = boardService.showBoardDetail(id);
        List<Comment> commentList = commentService.getComments(id);

        modelMap.addAttribute("board", board);
        modelMap.addAttribute("commentList", commentList);

        return "detail";
    }
}
