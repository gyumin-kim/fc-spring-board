package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BoardDaoImpl implements BoardDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    // Constructor Injection
    public BoardDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Board> getBoardListAll(Long categoryId) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_ALL;
        return jdbcTemplate.query(sql, new RowMapper<Board>() {
            public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
                Board board = new Board();
                board.setCategoryId(categoryId);
                return board;
            }
        });
    }

    @Override
    public List<Board> getBoardListByMember(Long categoryId, Long memberId) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_MEMBER;
        return jdbcTemplate.query(sql, new RowMapper<Board>() {
            @Override
            public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
                Board board = new Board();
                board.setCategoryId(categoryId);
                board.setMemberId(memberId);
                return board;
            }
        });
    }

    @Override
    public List<Board> getBoardListByTitle(Long categoryId, String title) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_TITLE;
        return jdbcTemplate.query(sql, new RowMapper<Board>() {
            @Override
            public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
                Board board = new Board();
                board.setCategoryId(categoryId);
                board.setTitle(title);
                return board;
            }
        });
    }

    /**
     * 내용으로 게시글을 검색
     * @param categoryId
     * @param content
     * @return
     */
    @Override
    public Board getBoardListByContent(Long categoryId, String content) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_CONTENT;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Board>() {
            @Override
            public Board extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (!rs.next())
                    return null;
                Board board = new Board();
                board.setId(rs.getLong("id"));
                do {
                    BoardBody boardBody = new BoardBody();
                    boardBody.setId(rs.getLong("id"));
                    boardBody.setContent(rs.getString("content"));
                    board.getBoardBodyList().add(boardBody);
                } while (rs.next());
                return board;
            }
        });
    }

    @Override
    public List<Board> getBoardListByTitleOrContent(Long categoryId, String title, String content) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_TITLE_OR_CONTENT;
        return null;
    }

    @Override
    public Board getBoardDetail(int id) {
        String sql = BoardDaoSqls.GET_BOARD_DETAIL;
        return null;
    }

    @Override
    public int saveBoard(Board board) {
        // 'is_deleted'는 생략(DEFAULT 0)
        String sql = BoardDaoSqls.SAVE_BOARD;
        SqlParameterSource beanProps = new BeanPropertySqlParameterSource(board);
        return jdbcTemplate.update(sql, beanProps);
    }

    @Override
    public int updateBoard(Board board) {
        String sql = BoardDaoSqls.UPDATE_BOARD;
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int addBoardReply(Long id, Long originId, int depth, int replySeq, Long categoryId, Long memberId, String title, String ipAddr, Date regDate) {
        String sql = "";
        return 0;
    }
}
