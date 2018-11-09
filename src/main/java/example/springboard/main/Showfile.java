package example.springboard.main;

import example.springboard.config.ApplicationConfig;
import example.springboard.dto.FileInfo;
import example.springboard.service.BoardService;
import example.springboard.service.BoardServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Showfile {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        BoardService boardService = ac.getBean(BoardService.class);
        FileInfo fileName = boardService.showFileName(86L);
        System.out.println(fileName.getOriginalFileName());
    }
}
