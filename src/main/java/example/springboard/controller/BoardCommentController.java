package example.springboard.controller;

import example.springboard.dto.Comment;
import example.springboard.service.BoardService;
import example.springboard.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

@RestController
@RequestMapping(value = "/boards")
public class BoardCommentController {
    private BoardService boardService;
    private CommentService commentService;

    public BoardCommentController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @PostMapping("/{boardId}")
    @ResponseBody
    public String submitComment(@PathVariable Long boardId, @RequestParam String content,
                                @RequestParam Long memberId) {
        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setParentCommentId(boardId);
        comment.setSeq(0);
        comment.setMemberId(memberId);
        comment.setContent(content);

        String ip = "";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        comment.setIpAddr(ip);
        comment.setRegdate(new Date());

        commentService.addComment(comment);
        return "addcomment";
    }
}
