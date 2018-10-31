package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.BoardDao;
import example.springboard.dao.CommentDao;
import example.springboard.dao.CommentDaoImpl;
import example.springboard.dto.Comment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class CommentAddTest {
    public static void main(String[] args) {
        ApplicationContext ac =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentDao commentDao
                = ac.getBean(CommentDao.class);
        System.out.println(commentDao.getClass());
        Comment comment = new Comment();
        comment.setBoardId(46L);
        comment.setParentCommentId(20L);
        comment.setSeq(1);
        comment.setMemberId(19L);
        comment.setContent("아아 테스트중입니다.");
        comment.setIpAddr("110.333.4444");
        comment.setRegdate(new Date());


        commentDao.addComment(comment);
    }
}
