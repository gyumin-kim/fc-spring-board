package example.springboard.service;

import example.springboard.dao.BoardDao;
import example.springboard.dao.FileUploadDao;
import example.springboard.dto.Board;
import example.springboard.dto.FileInfo;
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
    public List<Board> showBoardListByMember(Long categoryId, String memberName) {
        return boardDao.selectBoardListByMember(categoryId, memberName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Board> showBoardListByTitle(Long categoryId, String title) {
        return boardDao.selectBoardListByTitle(categoryId,title);
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
    public int deleteBoard(Board board) {
        return boardDao.deleteBoard(board.getId());
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
//    @Transactional
//    @Override
//    public void upload(Map<String, Object> map, MultipartFile file) {
//        System.out.println("------file info start ----");
//        System.out.println(file.getContentType());
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getName());
//        System.out.println(file.getSize());
//        System.out.println("------file info end ----");
//
//    }
}
