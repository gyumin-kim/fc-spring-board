package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import example.springboard.dto.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    /**
     * 특정 category의 모든 글을 불러옴
     */
    @Override
    public List<Board> getBoardListAll(Long categoryId) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_ALL;
        Map<String, ?> params = Collections.singletonMap("category_id", categoryId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    /**
     * 'board_body' 테이블의 작성자(member)로 검색한 결과
     * (작성자로 해당 글의 목록을 조회하되, 작성자를 불러오지는 않는다)
     */
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
     * (제목으로 해당 글의 목록을 조회하되, 제목을 불러오지는 않는다)
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
     * (내용으로 해당 글의 목록을 조회하되, 내용을 불러오지는 않는다)
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
     * (제목 혹은 내용으로 해당 글의 목록을 조회하되, 제목이나 내용을 불러오지는 않는다)
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

    /**
     * Board(글) 객체를 데이터베이스에 INSERT
     */
    @Override
    public int addBoard(Board board) {
        // 'is_deleted'는 생략(DEFAULT 0)
        // BeanPropertySqlParameterSource를 사용할 경우
        // 'is_deleted'가 NULL로 insert되는 문제가 있어 NamedParameterJdbcTemplate 사용
        String sql = BoardDaoSqls.SAVE_BOARD;
        SqlParameterSource beanProps = new BeanPropertySqlParameterSource(board);
        return jdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("origin_id", board.getOriginId())
                .addValue("depth", board.getDepth())
                .addValue("reply_seq", board.getReplySeq())
                .addValue("category_id", board.getCategoryId())
                .addValue("member_id", board.getMemberId())
                .addValue("title", board.getTitle())
                .addValue("ip_addr", board.getIpAddr()));
    }

    @Override
    public int updateBoard(Board board) {
        String sql = BoardDaoSqls.UPDATE_BOARD;
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        return jdbcTemplate.update(sql, params);
    }

//    @Override
//    public int addBoardReply(Long id, Long originId, int depth, int replySeq, Long categoryId, Long memberId, String title, String ipAddr, Date regDate) {
//        String sql = "";
//        return 0;
//    }

    // SELECT origin_id, depth, reply_seq FROM board WHERE id = :id
    @Override
    public Board getBoardInfoForReply(Long id) {
        String sql = BoardDaoSqls.GET_BOARD_INFO_FOR_REPLY;
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        Map<String, ?> parmas = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(sql, parmas, rowMapper);
    }

    // UPDATE board SET reply_seq = reply_seq + 1 WHERE origin_id = :origin_id AND reply_seq > :reply_seq
    @Override
    public int updateBoardForReply(Board board) {
        String sql = BoardDaoSqls.UPDATE_BOARD_FOR_REPLY;
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        return jdbcTemplate.update(sql, params);
    }
}
