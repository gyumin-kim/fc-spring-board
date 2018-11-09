package example.springboard.service;

import example.springboard.dto.Board;
import example.springboard.dto.Criteria;
import example.springboard.dto.FileInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BoardService {
    int getBoardCountAll(Long categoryId);
    int getBoardCountBySearch(Long categoryId, Criteria criteria);

    List<Board> showBoardList(Long categoryId, Criteria criteria);

    Board showBoardDetail(Long id);
    Board writeBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(Board board);
    Long writeBoardReply(Long id, Board board);
//    public void upload(Map<String, Object> map,@RequestParam("file") MultipartFile file);
    FileInfo showFileName(Long boardIdx);
//    void downloadFile(HttpServletResponse response);
}
