package example.springboard.controller;

import example.springboard.service.BoardService;
import org.springframework.stereotype.Controller;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


}
