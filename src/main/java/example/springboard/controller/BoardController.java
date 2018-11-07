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

    @PostMapping("/write")
    public String write(@RequestParam("categoryType")Long categoryType,
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
        board.setCategoryId(categoryType);
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

        // 삭제된 글로 url을 통해 접근하면 index 페이지로 redirect
        if (boardService.getBoardDeleted(id) == 1)
            return "redirect:/";
      
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

    @GetMapping("/modify")
    public String modify(@ModelAttribute("board")Board board, ModelMap modelMap){
        modelMap.addAttribute("board", board);
        return "modify";
    }
    

    // TODO 답글인 것을 표시해줄 무언가가 필요함
    @PostMapping("/reply")
    public String reply(@RequestParam("boardId")Long boardId,
                        @RequestParam("title")String title,
                        @RequestParam("content")String content,
                        @RequestParam("file")MultipartFile file, HttpSession session){

        // 로그인 한 사용자의 Id를 얻기 위함
        Member member = new Member();
        member = (Member)session.getAttribute("authUser");

        // TODO IP주소 얻어오는 것 수정하기(규민이 형이 해결한 방법 참고)
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        Board board = new Board();
        board.setIpAddr(ip);
        board.setMemberId(member.getId());
        board.setRegDate(new Date());
        board.setTitle(title);
        board.setContent(content);
        FileInfo fileInfo = fileUtil.handleFileStream(file);
        board.setFileInfo(fileInfo);

        Long replyBoardId = boardService.writeBoardReply(boardId, board);

        return "redirect:/boards/" + board.getCategoryId() + "/" + replyBoardId;          // redirect 하라는 뜻!
    }

//     답글에 처음에 넣어야 할 정보 => origin_id에는 원글의 id & 원글의 Depth & 원글의 reply_seq 이 3가지 값은 처음에 넣어야 함
//     입력받을 값 -> 제목, 작성자, IP, 입력 날짜
    @GetMapping("/reply")
    public String reply(@RequestParam("boardId")Long boardId, ModelMap modelMap){
        modelMap.addAttribute("boardId", boardId);
        return "reply";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("boardId")String id,
                         @RequestParam("categoryType")String categoryType){
        boardService.deleteBoard(Long.parseLong(id));
        return "redirect:/boards/" + categoryType;
    }
}
