package example.springboard.service;

import example.springboard.dao.BoardDao;
import example.springboard.dto.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardDao boardDao;

    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListAll(Long categoryId) {
        return boardDao.selectBoardListAll(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListByMember(Long categoryId, Long memberId) {
        return boardDao.selectBoardListByMember(categoryId, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListByTitle(Long categoryId, String title) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListByContent(Long categoryId, String content) {
        return boardDao.selectBoardListByContent(categoryId, content);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListByTitleOrContent(Long categoryId, String titleOrContent) {
        return boardDao.selectBoardListByTitleOrContent(categoryId, titleOrContent);
    }

    @Transactional(readOnly = true)
    @Override
    public Board showBoardDetail(Long id) {
        return boardDao.selectBoardDetail(id);
    }

    @Transactional
    @Override
    public Board writeBoard(Board board) {
        Long id = boardDao.insertBoard(board);  // Board
        board.setId(id);
        boardDao.insertBoardBody(id, board.getContent());   // BoardBody(본문)
        return board;
    }

    @Transactional
    @Override
    public int updateBoard(Board board) {
        return boardDao.updateBoard(board);
    }

    // TODO: 미완성. DAO에서 board 삭제하는 메소드 구현해야 함.
    @Transactional
    @Override
    public int deleteBoard(Board board) {
        return 0;
    }

    /**
     * 매개변수로 들어온 id에 대한 답글을 작성
     * @param id - 원글(현재 보고있는 글)의 id
     * @param board - 추가할 답글
     */
    @Transactional
    @Override
    public Long writeBoardReply(Long id, Board board) {
        Board tmp = boardDao.selectBoardInfoForReply(id);
        boardDao.updateBoardForReply(tmp);  // 원글에 달린 답글들의 reply_seq를 1씩 증가시킴

        int depth = tmp.getDepth() + 1;
        int replySeq = tmp.getReplySeq() + 1;
        board.setDepth(depth);
        board.setReplySeq(replySeq);

        return boardDao.insertBoard(board);
    }
}
