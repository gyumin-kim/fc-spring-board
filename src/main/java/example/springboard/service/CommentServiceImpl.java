package example.springboard.service;

import example.springboard.dao.CommentDao;
import example.springboard.dto.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao){
        this.commentDao = commentDao;
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        commentDao.addComment(comment);
        Long id = commentDao.selectLastId(1);
        commentDao.updateCommentId(id); // 원댓글을 달 경우 id와 parent_comment_id를 동일하게 맞춰줌
//        comment.setParentCommentId(id);
        // 여기서 다시 comment를 리턴해주는 이유는 comment를 리다이렉트나 다른데 뿌려줄 수 있기 때문이다.
        return comment;
    }

    @Override
    public Comment addRecomment(Comment comment) {
        commentDao.addComment(comment);
        return comment;
    }

    //    @Override 댓글하나씩 가져오는거는 필요없을거 같아서 getComment는 하지 않았습니다.
//    public Comment getComment(Long id) {
//        return null;
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getComments(Long boardId) {
        // boardId 넘겨주는 이유가 어떤 게시물의 댓글인지 알 수 있게 하려고 boardId값을 받는다
        // 그러면 controller에서 그 게시물의 id값을 받아야 하지 않을까?
        List<Comment> commentList = commentDao.getCommentList(boardId);

        // list의 각 comment 객체에 childCommentCount를 반영
        for (Comment comment : commentList)
            comment.setChildCommentCount(getChildCommentCount(comment.getId()));
        return commentList;
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        //controller에서 수정된 몇몇 content, reg_date, ip_addr, member_id, id값을 넘겨줘야함
        commentDao.updateComment(comment);
        return comment;
    }

    @Override
    @Transactional
    public int deleteComment(Long id) {
        return commentDao.deleteComment(id);
    }

    @Override
    public int getChildCommentCount(Long id) {
        return commentDao.selectChildCommentCount(id);
    }
}
