package example.springboard.dao;

import example.springboard.dto.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentList(Long boardId);
    int addComment(Comment comment);
    int updateComment(Comment comment);
    int updateCommentId(Long id);
    int deleteComment(Long id);
    Long selectLastId(int count);
}
