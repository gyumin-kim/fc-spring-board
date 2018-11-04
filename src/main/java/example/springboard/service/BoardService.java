package example.springboard.service;

import example.springboard.dto.Board;
import example.springboard.dto.Criteria;

import java.util.List;

public interface BoardService {
    int getBoardCount(Long categoryId);
    List<Board> showBoardListAll(Long categoryId, Criteria criteria);
    List<Board> showBoardListByMember(Long categoryId, String memberName);
    List<Board> showBoardListByTitle(Long categoryId, String title);
    List<Board> showBoardListByContent(Long categoryId, String content);
    List<Board> showBoardListByTitleOrContent(Long categoryId, String titleOrContent);
    Board showBoardDetail(Long id);
    Board writeBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(Board board);
    Long writeBoardReply(Long id, Board board);
}
