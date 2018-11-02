package example.springboard.service;

import example.springboard.dto.Board;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> showBoardListAll(Long categoryId);
    List<Board> showBoardListByMember(Long categoryId, String memberName);
    List<Board> showBoardListByTitle(Long categoryId, String title);
    List<Board> showBoardListByContent(Long categoryId, String content);
    List<Board> showBoardListByTitleOrContent(Long categoryId, String titleOrContent);
    Board showBoardDetail(Long id);
    Board writeBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(Board board);
    Long writeBoardReply(Long id, Board board);
//    public void upload(Map<String, Object> map,@RequestParam("file") MultipartFile file);
}
