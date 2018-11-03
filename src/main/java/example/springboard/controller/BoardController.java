package example.springboard.controller;

import example.springboard.dto.Board;
import example.springboard.dto.Criteria;
import example.springboard.dto.PageMaker;
import example.springboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/boards/list")
    public String list(@RequestParam(value = "categoryId", defaultValue ="1")Long categoryId,
                       @ModelAttribute("criteria") Criteria criteria,
                       ModelMap modelMap) throws Exception{
        // 게시판 글 리스트
        modelMap.addAttribute("boards", boardService.showBoardListAll(categoryId, criteria));

        // 페이지 나누기 관련 처리
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
        pageMaker.setTotalBoardCount(boardService.getBoardCount(categoryId));  // 게시글의 총 개수

        // 게시판 하단의 페이징 관련, 이전 / 페이지 링크 / 다음
        modelMap.addAttribute("pageMaker", pageMaker);

        return "list";
    }

}
