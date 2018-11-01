package example.springboard.dao;

import example.springboard.dto.Board;
import example.springboard.dto.BoardBody;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class BoardDaoImpl implements BoardDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

    // Constructor Injection
    public BoardDaoImpl(DataSource dataSource) {
        System.out.println("BoardDaoImpl()");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("id", "is_deleted");//여기서 "id"는 자동생성이여서 받아서 service에서 setter로 넣어야하지만 is_deleted는 default값이 있기 때문에 따로 값은 받지않는다.
}

    /**
     * 특정 category의 모든 글을 불러옴
     */
    @Override
    public List<Board> selectBoardListAll(Long categoryId) {
        String sql = BoardDaoSqls.GET_BOARD_LIST_ALL;
        Map<String, ?> params = Collections.singletonMap("category_id", categoryId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    /**
     * 'board_body' 테이블의 작성자(member)로 검색한 결과
     * (작성자로 해당 글의 목록을 조회하되, 작성자를 불러오지는 않는다)
     */
    @Override
    public List<Board> selectBoardListByMember(Long categoryId, Long memberId) {
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
    public List<Board> selectBoardListByTitle(Long categoryId, String title) {
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
    public List<Board> selectBoardListByContent(Long categoryId, String content) {
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
    public List<Board> selectBoardListByTitleOrContent(Long categoryId, String titleOrContent) {
        // 제목과 내용 모두로 검색하는 쿼리문
        String sql = BoardDaoSqls.GET_BOARD_LIST_BY_TITLE_OR_CONTENT;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("b.category_id", categoryId);
        paramMap.put("bb.content", '%' + titleOrContent + '%');
        paramMap.put("b.title", '%' + titleOrContent + '%');
        return jdbcTemplate.query(sql, paramMap, rowMapper);
    }

    // board 테이블의 속성(id)에 해당하는 board_body의 content를 가져오는 역할
    @Override
    public Board selectBoardDetail(Long id) {
        String sql = BoardDaoSqls.GET_BOARD_DETAIL;
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        Map<String, ?> params = Collections.singletonMap("b.id", id);
        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    /**
     * Board(글) 객체를 데이터베이스에 INSERT
     */
//    @Override
//    public int addBoard(Board board) {
//        // 'is_deleted'는 생략(DEFAULT 0)
//        // BeanPropertySqlParameterSource를 사용할 경우
//        // 'is_deleted'가 NULL로 insert되는 문제가 있어 NamedParameterJdbcTemplate.update() 사용
//        String sql = BoardDaoSqls.ADD_BOARD;
//        return jdbcTemplate.update(sql, new MapSqlParameterSource()
//                .addValue("origin_id", board.getOriginId())
//                .addValue("depth", board.getDepth())
//                .addValue("reply_seq", board.getReplySeq())
//                .addValue("category_id", board.getCategoryId())
//                .addValue("member_id", board.getMemberId())
//                .addValue("title", board.getTitle())
//                .addValue("ip_addr", board.getIpAddr()));
//    }

    // Board 객체를 DB에 INSERT, 새롭게 만들어진 Board 는 'is_delete = 0' 으로 넣어주어야 한다.
    @Override
    public Long insertBoard(Board board){
        String sql = BoardDaoSqls.ADD_BOARD;
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    // board_body 테이블에 입력받은 정보를 저장이 (addBoard와 같이 처리되어야 함)
    @Override
    public int insertBoardBody(Long id, String content) {
        String sql = BoardDaoSqls.ADD_BOARD_BODY;
        Map<String, Object> map = new HashMap<>();
        map.put("board_id", id);
        map.put("content", content);
        return jdbcTemplate.update(sql, map);
    }

    // 게시글을 수정하면 board table, board_body table 이 같이 수정되어야 한다.(updateBoardBody 와 같이 처리되어야 함)
    @Override
    public int updateBoard(Board board) {
        String sql = BoardDaoSqls.UPDATE_BOARD;
        Map<String, Object> map = new HashMap<>();
        map.put("title", board.getTitle());
        map.put("ip_addr", board.getIpAddr());
        map.put("member_id", board.getMemberId());
        map.put("id", board.getId());
        return jdbcTemplate.update(sql, map);
//        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
//        return jdbcTemplate.update(sql, params);
    }
    // 해당 글의 수정한 본문 내용과 id 값을 받아서 업데이트 한다. (updateBoard와 같이 처리되어야 함)
    @Override
    public int updateBoardBody(String content, Long id){
        String sql = BoardDaoSqls.UPDATE_BOARD_BODY;
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        map.put("id", id);
        return jdbcTemplate.update(sql, map);
    }

//    @Override
//    public int insertBoardReply(Long id, Long originId, int depth, int replySeq, Long categoryId, Long memberId, String title, String ipAddr, Date regDate) {
//        String sql = "";
//        return 0;
//    }

    // 답글을 달 때 부모글의 정보를 가져오는 역할
    // 실행 후 updateBoardForReply가 실행되어야 한다.
    @Override
    public Board selectBoardInfoForReply(Long id) {
        String sql = BoardDaoSqls.GET_BOARD_INFO_FOR_REPLY;
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        Map<String, ?> parmas = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(sql, parmas, rowMapper);
    }

    // 답글을 달 때 seq 값을 증가시켜주는 역할
    // 실행 후 부모글의 origin_id 값은 그대로 board에 저장하고 reply_seq 값과 depth 값은 1씩 증가시켜서 새로운 board에 저장한다.
    // selectBoardInfoForReply 와 updateBoardForReply, insertBoard는 하나의 트랜잭션이다??
    @Override
    public int updateBoardForReply(Board board) {
        String sql = BoardDaoSqls.UPDATE_BOARD_FOR_REPLY;
        Map<String, Object> map = new HashMap<>();
        map.put("origin_id", board.getOriginId());
        map.put("reply_seq", board.getReplySeq());
        return jdbcTemplate.update(sql, map);
    }

    // 'is_deleted'를 1로 설정
    // 목록 조회 시 답글이 있는 경우 "삭제된 글 입니다" / 답글이 없는 경우 UI에 표시되지 않도록 JavaScrip로 따로 구현
    @Override
    public int deleteBoard(Long id){
        String sql = BoardDaoSqls.DELETE_BOARD;
        Map<String, Long> map = Collections.singletonMap("id", id);
        return jdbcTemplate.update(sql, map);
    }
}
