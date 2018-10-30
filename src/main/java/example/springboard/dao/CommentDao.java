package example.springboard.dao;

import example.springboard.dto.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentList(int parentCommentId);
    int addComment(Long id, Long boardId, Long parentCommentId, int seq, Long memberId, String content, String ipAddr, Date regDate);
    int updateComment(Long id, Long memberId, String content, Date regDate, String ipAddr);
    int deleteComment(Long id, Long memberId);
}
