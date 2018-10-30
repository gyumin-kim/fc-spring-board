package example.springboard.dao;

import example.springboard.dto.Comment;

import java.util.Date;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    @Override
    public List<Comment> getCommentList(int parentCommentId) {
        return null;
    }

    @Override
    public int addComment(Long id, Long boardId, Long parentCommentId, int seq, Long memberId, String content, String ipAddr, Date regDate) {
        return 0;
    }

    @Override
    public int updateComment(Long id, Long memberId, String content, Date regDate, String ipAddr) {
        return 0;
    }

    @Override
    public int deleteComment(Long id, Long memberId) {
        return 0;
    }
}
