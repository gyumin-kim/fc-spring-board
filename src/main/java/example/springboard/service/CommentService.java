package example.springboard.service;

import example.springboard.dto.Comment;

import java.util.List;

public interface CommentService {
    public Comment addComment(Comment comment);
//    public Comment getComment(Long id);
    public List<Comment> getComments(Long boardId);
    public Comment updateComment(Comment comment);
    public void deleteComment(Long id);
}
