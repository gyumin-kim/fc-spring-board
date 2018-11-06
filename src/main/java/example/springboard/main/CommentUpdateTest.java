package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.CommentDao;
import example.springboard.dto.Comment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class CommentUpdateTest {
    public static void main(String[] args) {
        ApplicationContext ac =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentDao commentDao
                = ac.getBean(CommentDao.class);

        Comment comment = new Comment();
        comment.setSeq(1);
        comment.setContent("사이렌 이유이융");
        comment.setIpAddr("111.111.111.111");
        comment.setRegDate(new Date());
        comment.setMemberId(19L);
        comment.setId(27L);
        comment.setParentCommentId(20L);
        comment.setBoardId(46L);
        commentDao.updateComment(comment);
    }
}
