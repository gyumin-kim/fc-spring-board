package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import example.springboard.dto.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BoardDaoImpl implements BoardDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

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
        Map<String, ?> params = Collections.singletonMap("category_id", categoryId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public List<Board> getBoardListByMember(Long categoryId, Long memberId) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_MEMBER;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("category_id", categoryId);
        paramMap.put("m.id", memberId);
        return jdbcTemplate.query(sql, paramMap, rowMapper);
    }

    /**
     * 'board_body' 테이블의 제목(title)으로 검색한 결과
     * (제목을 불러오지는 않고 해당 글의 목록만 조회하는 메소드)
     */
    @Override
    public List<Board> getBoardListByTitle(Long categoryId, String title) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_TITLE;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("b.category_id", categoryId);
        paramMap.put("b.title", '%' + title + '%');
        return jdbcTemplate.query(sql, paramMap, rowMapper);
    }

    /**
     * 'board_body' 테이블의 내용(content)으로 검색한 결과
     * (내용을 불러오지는 않고 해당 글의 목록만 조회하는 메소드)
     */
    @Override
    public List<Board> getBoardListByContent(Long categoryId, String content) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_CONTENT;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("b.category_id", categoryId);
        paramMap.put("bb.content", '%' + content + '%');
        return jdbcTemplate.query(sql, paramMap, rowMapper);
    }

    /**
     * 'board_body' 테이블의 제목(title) 혹은 내용(content)으로 검색한 결과
     * (제목이나 내용을 불러오지는 않고 해당 글의 목록만 조회하는 메소드)
     */
    @Override
    public List<Board> getBoardListByTitleOrContent(Long categoryId, String titleOrContent) {
        // 제목과 내용 모두로 검색하는 쿼리문
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_TITLE_OR_CONTENT;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("b.category_id", categoryId);
        paramMap.put("bb.content", '%' + titleOrContent + '%');
        paramMap.put("b.title", '%' + titleOrContent + '%');
        return jdbcTemplate.query(sql, paramMap, rowMapper);
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
