package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import example.springboard.dto.Criteria;

import java.util.List;

public interface BoardDao {
    int selectBoardCountAll(Long categoryId);
    int selectBoardCountBySearch(Long categoryId, Criteria criteria);
    List<Board> selectBoardList(Long categoryId, Criteria criteria);
    Board selectBoardDetail(Long id);
    Long insertBoard(Board board);
    int insertBoardBody(Long id, String content);
    int updateBoard(Board board);
    int updateBoardBody(String content, Long id);
//    int insertBoardReply(Long id, Long originId, int depth, int replySeq, Long categoryId, Long memberId, String title, String ipAddr, Date regDate);

    Board selectBoardInfoForReply(Long id);
    int updateBoardForReply(Board board);

    int deleteBoard(Long id);

    int updateOriginId(Long id);
}
