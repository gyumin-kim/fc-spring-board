package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;

import java.util.Date;
import java.util.List;

public interface BoardDao {
    List<Board> getBoardListAll(Long categoryId);
    List<Board> getBoardListByMember(Long categoryId, Long memberId);
    List<Board> getBoardListByTitle(Long categoryId, String title);
    List<Board> getBoardListByContent(Long categoryId, String content);
    List<Board> getBoardListByTitleOrContent(Long categoryId, String titleOrContent);
    Board getBoardDetail(int id);
    int addBoard(Board board);
    int addBoardBody(BoardBody boardBody);
    int updateBoard(Board board);
    int addBoardReply(Long id, Long originId, int depth, int replySeq, Long categoryId, Long memberId, String title, String ipAddr, Date regDate);
}
