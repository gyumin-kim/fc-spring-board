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

    @PostMapping("/write")           // Post 방식의 요청
    public String write(@RequestParam("categoryType")Long categoryType,
                        @RequestParam("title")String title,
                        @RequestParam("content")String content,
                        @RequestParam("file")MultipartFile file, HttpSession session){     // RequestParam 으로 값을 받아준다.
        // 로그인 한 사용자의 Id를 얻기 위함
        Member member = new Member();
        member = (Member)session.getAttribute("authUser");

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        Board board = new Board();
        board.setDepth(0);
        board.setReplySeq(0);
        board.setCategoryId(categoryType);
        board.setMemberId(member.getId());
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
                       @ModelAttribute("criteria") Criteria criteria, HttpSession session,
                       ModelMap modelMap) throws Exception {
        // TODO 로그인 한 사용자만 접근 가능하도록 만듦, 모든 url에 해당 동작을 추가해야 할까??
        if(session.getAttribute("authUser") == null){
            return "redirect:/";
        }

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

        // 삭제된 글로 url을 통해 접근하면 인덱스 페이지로 보내버린다.
        if (boardService.getBoardDeleted(id) == 1) {
            return "redirect:/";
        }

        Board board = boardService.showBoardDetail(id);
        List<Comment> commentList = commentService.getComments(id);

        modelMap.addAttribute("board", board);
        modelMap.addAttribute("commentList", commentList);
        modelMap.addAttribute("criteria", criteria);

        return "detail";
    }

    // 삭제된 게시글도 URL 창으로 접근할 수 있음, 이를 막아주어야 함!  and 아직 경고창 없이 바로 삭제되도록 구현해놓
    @GetMapping("/de음lete")
    public String delete(@RequestParam("boardId")Long id,
                         @RequestParam("categoryType")Long categoryType){
        boardService.deleteBoard(id);
        return "redirect:/boards/" + categoryType;
    }

    @GetMapping("/modify")
    public String modify(@ModelAttribute("board")Board board, ModelMap modelMap){
        modelMap.addAttribute("board", board);
        return "modify";
    }

    // TODO : 수정하기 Post 미완성, detail.jsp의 url도 수정 필요
//    @PostMapping("/modify")
//    public String modify(@ModelAttribute("board")Board board){
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//        if (ip == null)
//            ip = req.getRemoteAddr();
//
//        board.setCategoryId(categoryType);
//        board.setTitle(title);
//        board.setContent(content);
//        board.setRegDate(new Date());
//        board.setIpAddr(ip);
//        FileInfo fileInfo = fileUtil.handleFileStream(file);
//        board.setFileInfo(fileInfo);
//
//        boardService.updateBoard(board);
//    }

//     답글에 처음에 넣어야 할 정보 => origin_id에는 원글의 id & 원글의 Depth & 원글의 reply_seq 이 3가지 값은 처음에 넣어야 함
//     입력받을 값 -> 제목, 작성자, IP, 입력 날짜
    @GetMapping("/reply")
    public String reply(@RequestParam("boardId")Long boardId, ModelMap modelMap){
        modelMap.addAttribute("boardId", boardId);
        return "reply";
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
}
