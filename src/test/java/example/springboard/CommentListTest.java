package example.springboard;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.CommentDaoImpl;
import example.springboard.dto.Comment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CommentListTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentDaoImpl commentDao = ac.getBean(CommentDaoImpl.class);
        List<Comment> list = commentDao.getCommentList(13L);
        for (Comment comment : list) {
            System.out.println(comment);
        }
    }
}
