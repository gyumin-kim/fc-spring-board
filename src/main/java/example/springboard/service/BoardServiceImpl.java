package example.springboard.service;

import example.springboard.dao.BoardDao;
import example.springboard.dao.FileUploadDao;
import example.springboard.dto.Board;
import example.springboard.dto.FileInfo;
import example.springboard.dto.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardDao boardDao;
    private FileUploadDao fileUploadDao;

    public BoardServiceImpl(BoardDao boardDao,FileUploadDao fileUploadDao) {
        this.boardDao = boardDao;
        this.fileUploadDao = fileUploadDao;
    }

    @Transactional
    @Override
    public int getBoardCountAll(Long categoryId){
        return boardDao.selectBoardCountAll(categoryId);
    }

    @Transactional
    @Override
    public int getBoardCountBySearch(Long categoryId, Criteria criteria){
        return boardDao.selectBoardCountBySearch(categoryId, criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardList(Long categoryId, Criteria criteria) {
        return boardDao.selectBoardList(categoryId, criteria);
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
        boardDao.updateOriginId(id);
        board.setId(id);
        boardDao.insertBoardBody(id, board.getContent());   // BoardBody(본문)
        FileInfo fileInfo = board.getFileInfo();
        fileInfo.setBoardIdx(id);
        fileUploadDao.addfile(fileInfo);
        return board;
    }

    @Transactional
    @Override
    public int updateBoard(Board board) {
        return boardDao.updateBoard(board);
    }

    @Transactional
    @Override
    public int deleteBoard(Long id) {
        return boardDao.deleteBoard(id);
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
        board.setOriginId(tmp.getOriginId());
        board.setCategoryId(tmp.getCategoryId());
        Long replyBoardId = boardDao.insertBoard(board);
        boardDao.insertBoardBody(replyBoardId, board.getContent());
        return replyBoardId;
    }

    @Transactional
    @Override
    public int getBoardDeleted(Long id){
        return boardDao.seletBoardDeleted(id);
    }
}
