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
    public String write(@RequestParam("categoryType")Long categoryType,
                        @RequestParam("title")String title,
                        @RequestParam("content")String content,
                        @RequestParam("file")MultipartFile file){     // RequestParam 으로 값을 받아준다.
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        Board board = new Board();
        board.setDepth(0);
        board.setReplySeq(0);
        board.setCategoryId(categoryType);
        board.setMemberId(2L);
        board.setTitle(title);
        board.setContent(content);
        board.setRegDate(new Date());
        board.setIpAddr(ip);
        FileInfo fileInfo = fileUtil.handleFileStream(file);
        board.setFileInfo(fileInfo);
        boardService.writeBoard(board);

        return "redirect:/boards/" + categoryType + "/" + board.getId();          // redirect 하라는 뜻!
    }

    @GetMapping("/{categoryId}")
    public String list(@PathVariable Long categoryId,
                       @ModelAttribute("criteria") Criteria criteria,
                       ModelMap modelMap) throws Exception{

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
                         @ModelAttribute("criteria")Criteria criteria,
                         ModelMap modelMap) {

        Board board = boardService.showBoardDetail(id);
        List<Comment> commentList = commentService.getComments(id);

        modelMap.addAttribute("board", board);
        modelMap.addAttribute("commentList", commentList);
        modelMap.addAttribute("criteria", criteria);

        return "detail";
    }

    // TODO : 삭제된 게시글도 URL 창으로 접근할 수 있음, 이를 막아주어야 함! and confirm 창으로 변경 필요
    @GetMapping("/delete")
    public String delete(@RequestParam("boardId")String id,
                         @RequestParam("categoryType")String categoryType){
        boardService.deleteBoard(Long.parseLong(id));
        return "redirect:/boards/" + categoryType;
    }
}
