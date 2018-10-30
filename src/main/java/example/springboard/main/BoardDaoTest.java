package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.BoardDaoImpl;
import example.springboard.dto.Board;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class BoardDaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        BoardDaoImpl boardDao = context.getBean(BoardDaoImpl.class);

        List<Board> boardList = boardDao.getBoardListByTitleOrContent(1L, "오늘");
        for (Board board : boardList) {
            System.out.println(board.toString());
        }
    }
}
