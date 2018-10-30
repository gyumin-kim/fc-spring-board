package example.springboard.dao;

import example.springboard.dto.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentList(Long boardId);
    int addComment(Comment comment);
    int updateComment(Comment comment);
    int deleteComment(Long id);
}
