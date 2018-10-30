package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.BoardDao;
import example.springboard.dao.BoardDaoImpl;
import example.springboard.dto.Board;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class BoardDaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        BoardDao boardDao = context.getBean(BoardDaoImpl.class);

        Board board = new Board();
        board.setId(21L);
        board.setOriginId(6L);
        board.setDepth(1);
        board.setReplySeq(1);
        board.setCategoryId(1L);
        board.setMemberId(8L);
        board.setTitle("RE:무슨 소리세요 겁나 춥구");
        board.setRegDate(new Date());
        board.setIpAddr("222.444.222.11");

        boardDao.addBoard(board);
    }
}
