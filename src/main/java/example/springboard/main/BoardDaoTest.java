package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.BoardDao;
import example.springboard.dao.BoardDaoImpl;
import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class BoardDaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        BoardDao boardDao = context.getBean(BoardDaoImpl.class);

        BoardBody boardBody = new BoardBody();
        boardBody.setId(20L);
        boardBody.setContent("고의였든 아니었든 상대 선수 부상당할뻔 했는데 '경기에 집중하다 보니 과했다. 미안하다. 오늘은 서로 예민하지 않게 잘 해보자.' 이거 한 마디면 끝나는 거 아닌가요?");

        boardDao.addBoardBody(boardBody);
    }
}
