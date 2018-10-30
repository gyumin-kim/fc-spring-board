package example.springboard.dao;

import example.springboard.dto.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentList(int parentCommentId);
    int addComment(Comment comment);
    int updateComment(Comment comment);
    int deleteComment(Long id);
}
