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
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
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
    public String writeform(HttpSession httpSession) {
        // 로그인 안된 상태로 글쓰기 페이지 접근 시 index 페이지로 redirect
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        return "write";
    }

    @PostMapping
    public String write(@RequestParam("categoryType")int categoryType,
                        @RequestParam("title")String title,
                        @RequestParam("content")String content,
                        @RequestParam("file")MultipartFile file,
                        HttpSession httpSession) {

        // 로그인 안된 상태로 글쓰기 제출 시 index 페이지로 redirect
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        String ipAddr = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ipAddr = ia.getHostAddress();
        } catch (Exception ex) { ex.printStackTrace(); }

        Member member = (Member)httpSession.getAttribute("authUser");
        Board board = new Board();
        board.setDepth(0);
        board.setReplySeq(0);
        board.setCategoryId((long) categoryType);
        board.setMemberId(member.getId());
        board.setTitle(title);
        board.setContent(content);
        board.setRegDate(new Date());
        board.setIpAddr(ipAddr);
        FileInfo fileInfo = fileUtil.handleFileStream(file);
        board.setFileInfo(fileInfo);
        boardService.writeBoard(board);

        return "redirect:/boards/" + categoryType + "/" + board.getId();
    }

    @GetMapping("/{categoryId}")
    public String list(@PathVariable Long categoryId,
                       @ModelAttribute("criteria") Criteria criteria,
                       ModelMap modelMap, HttpSession httpSession) throws Exception{

        // 로그인 안된 상태로 특정 글 list 접근 시 index 페이지로 redirect
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        // 게시판 글 리스트
        modelMap.addAttribute("boards", boardService.showBoardList(categoryId, criteria));

        // 페이지 나누기 관련 처리
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);

        // all & search 에 따라 게시글의 총 개수를 다르게 가져옴
        if (criteria.isSearched()) {
            pageMaker.setTotalBoardCount(boardService.getBoardCountBySearch(categoryId, criteria));
        } else {
            pageMaker.setTotalBoardCount(boardService.getBoardCountAll(categoryId));
        }

        // 게시판 하단의 페이징 관련, 이전 / 페이지 링크 / 다음
        modelMap.addAttribute("pageMaker", pageMaker);

        return "list";
    }

    @GetMapping("/{categoryId}/{id}")
    public String detail(@PathVariable Long categoryId,
                         @PathVariable Long id,
                         @ModelAttribute("criteria") Criteria criteria,
                         HttpSession httpSession,
                         ModelMap modelMap) {

        // 로그인 안된 상태로 특정 글 상세페이지 접근 시 index 페이지로 redirect
        if (httpSession.getAttribute("authUser") == null)
            return "redirect:/";

        Board board = boardService.showBoardDetail(id);
        List<Comment> commentList = commentService.getComments(id);

        modelMap.addAttribute("board", board);
        modelMap.addAttribute("commentList", commentList);
        modelMap.addAttribute("categoryId", categoryId);
        modelMap.addAttribute("criteria", criteria);
        Member member = (Member)httpSession.getAttribute("authUser");
        modelMap.addAttribute("memberName", member.getName());
        modelMap.addAttribute("regDate", board.getRegDate());

        return "detail";
    }
}
