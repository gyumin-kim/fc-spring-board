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
        return comment;//여기서 다시 comment를 리턴해주는 이유는 comment를 리다이렉트나 다른데 뿌려줄 수 있기때문이다.
    }

//    @Override 댓글하나씩 가져오는거는 필요없을거 같아서 getComment는 하지않았습니다.
//    public Comment getComment(Long id) {
//        return null;
//    }

    @Override
    @Transactional
    public List<Comment> getComments(Long boardId) {//boardId 넘겨주는 이유가 어떤 게시물의 댓글인지 알 수 있게 하려고 boardId값을 받는다 .
                                                     //그러면 controller에서 그 게시물의 id값을 받아야 하지 않을까?
        return commentDao.getCommentList(boardId);
    }

    @Override
    @Transactional
    public Comment upComment(Comment comment) {
        commentDao.updateComment(comment);//controller에서 수정된 몇몇 content,regdate,ipadd,memberid,id값을 넘겨줘야함
        return comment;
    }

    @Override
    @Transactional
    public void delComment(Long id) {//댓글 삭제만하면 되니깐 리턴값이 필요없을거 같아서 타입을 void로 했습니다.
        commentDao.deleteComment(id);
    }
}
