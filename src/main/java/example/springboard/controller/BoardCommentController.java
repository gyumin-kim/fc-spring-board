package example.springboard.controller;

import example.springboard.dto.Comment;
import example.springboard.dto.CommentData;
import example.springboard.service.BoardService;
import example.springboard.service.CommentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/boards")
public class BoardCommentController {
    private CommentService commentService;
    private static final Log log = LogFactory.getLog(BoardCommentController.class);

    public BoardCommentController(BoardService boardService, CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 등록을 처리하는 REST 메소드
     * @param boardId
     * @param commentData
     * @return
     */
    @PostMapping("/{boardId}/comment")
    @ResponseBody
    public ResponseEntity<String> submitComment(@PathVariable(value = "boardId") Long boardId,
                                        @RequestBody CommentData commentData){
        log.info("**** Accepted value from AJAX(commentFormBtn). ****");
        log.info("commentData: " + commentData.toString());

        String ipAddr = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ipAddr = ia.getHostAddress();
        } catch (Exception ex) { ex.printStackTrace(); }

        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setParentCommentId(boardId);   // 일반 댓글은 parentCommentId와 자신의 id가 같다고 가정함
        comment.setSeq(0);                                      // 일반 댓글은 seq가 0이라고 가정함
        comment.setMemberId(commentData.getMemberId());
        comment.setContent(commentData.getContent());
        comment.setIpAddr(ipAddr);

        commentService.addComment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 대댓글 등록을 처리하는 REST 메소드
     * @param boardId
     * @param commentData
     * @return
     */
    @PostMapping("/{boardId}/recomment")
    @ResponseBody
    public ResponseEntity<String> submitRecomment(@PathVariable(value = "boardId") Long boardId,
                                                @RequestBody CommentData commentData){
        log.info("=====================================================================================");
        log.info("**** Accepted value from AJAX(recommentFormBtn). ****");
        log.info("commentData: " + commentData.toString());
        log.info("parentCommentData: " + commentData.getParentCommentId());
        log.info("=====================================================================================");

        String ipAddr = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            ipAddr = ia.getHostAddress();
        } catch (Exception ex) { ex.printStackTrace(); }
        // 부모 댓글이 가진 대댓글의 수
        int seqCount = commentService.getChildCommentCount(commentData.getParentCommentId());

        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setParentCommentId(commentData.getParentCommentId());   // 일반 댓글은 parentCommentId와 자신의 id가 같다고 가정함
        comment.setSeq(seqCount);                                     //
        comment.setMemberId(commentData.getMemberId());
        comment.setContent(commentData.getContent());
        comment.setIpAddr(ipAddr);

        log.info("(addComment 직전)Comment 객체 정보: " + comment);
        commentService.addRecomment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 특정 id를 가진 댓글을 삭제하는 메소드.
     * HTTP 메소드는 DELETE 메소드를 사용할 수도 있으나, 실제 DB에서 데이터를 삭제하는 것이 아니라 'is_deleted' 컬럼의 값만 바꾸는 방식이므로 POST 메소드를 사용함.
     * @param commentId - 삭제하려는 댓글의 id
     */
    @PostMapping("/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        log.info("=====================================================================================");
        log.info("**** Accepted value from AJAX(deleteCommentBtn). ****");
        log.info("**** commentId: " + commentId + " ****");
        log.info("=====================================================================================");
        // 매개변수로 받은 id에 해당하는 댓글의 'is_deleted' 컬럼값을 1로 변경
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
