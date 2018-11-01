package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.BoardDao;
import example.springboard.dao.BoardDaoImpl;
import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

public class BoardDaoTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        BoardDao boardDao = ac.getBean(BoardDao.class);

        Board board = new Board();


        // Controller에서 받아야 할 값들...?
        board.setOriginId(22L);
        board.setDepth(0);
        board.setReplySeq(0);
        board.setCategoryId(1L);
        board.setRegDate(new Date());
        board.setMemberId(4L);
        board.setIpAddr("192.168.10.10");
        board.setTitle("board/board_body insert test");
        board.setContent("this is content test 1.");

        // Service에서 수행될 과정??
        Long id = boardDao.addBoard(board);
        board.setId(id);
        // return board

        boardDao.addBoardBody(id, board.getContent());

    }
}
