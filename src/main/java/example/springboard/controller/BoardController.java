package example.springboard.controller;

import example.springboard.dto.Board;
import example.springboard.dto.FileInfo;
import example.springboard.service.BoardService;
import example.springboard.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/boards")
public class BoardController {
    private BoardService boardService;
    private FileUtil fileUtil;

    public BoardController(BoardService boardService, FileUtil fileUtil) {
        this.boardService = boardService;
        this.fileUtil = fileUtil;
    }
    @GetMapping("/write")
    public String write(){return "write";}

    @PostMapping
    public String write(@RequestParam("title")String title,
                         @RequestParam("content")String content,
                         @RequestParam("file")MultipartFile file){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setCategoryId(1L);
        board.setMemberId(1L);
        FileInfo fileInfo = fileUtil.handleFileStream(file);
        board.setFileInfo(fileInfo);
        boardService.writeBoard(board);

        return "redirect:/boards";
    }
    @GetMapping
    public String list(){
        return "list";
    }
}
