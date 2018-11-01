package example.springboard.service;

import example.springboard.dto.Board;

import java.util.List;

public interface BoardService {
    List<Board> showBoardListAll(Long categoryId);
    List<Board> showBoardListByMember(Long categoryId, Long memberId);
    List<Board> showBoardListByTitle(Long categoryId, String title);
    List<Board> showBoardListByContent(Long categoryId, String content);
    List<Board> showBoardListByTitleOrContent(Long categoryId, String titleOrContent);
    Board showBoardDetail(Long id);
    Board writeBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(Board board);
    Long writeBoardReply(Long id, Board board);
}
