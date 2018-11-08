package example.springboard.dao;

import example.springboard.dto.Comment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class CommentDaoImpl implements CommentDao {
    private static final Log log = LogFactory.getLog(CommentDaoImpl.class);
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    // Constructor Injection
    public CommentDaoImpl(DataSource dataSource){
        log.info("CommentDaoImpl()");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("comment");
    }

    @Override
    public int addComment(Comment comment) {
        String sql = "INSERT INTO comment (id, board_id, parent_comment_id, seq, member_id, content, ip_addr, reg_date) " +
                "VALUES (null, :board_id, :parent_comment_id, :seq, :member_id, :content, :ip_addr, NOW());";
        return jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                .addValue("board_id", comment.getBoardId())
                .addValue("parent_comment_id", comment.getParentCommentId())
                .addValue("seq", comment.getSeq())
                .addValue("member_id", comment.getMemberId())
                .addValue("content", comment.getContent())
                .addValue("ip_addr", comment.getIpAddr()));
    }

    @Override
    public List<Comment> getCommentList(Long boardId) {
        String sql = "SELECT c.id, c.parent_comment_id, c.seq, m.id AS member_id, m.name, c.content, c.ip_addr, c.reg_date, c.is_deleted " +
                "FROM comment AS c INNER JOIN member AS m ON c.member_id = m.id " +
                "WHERE board_id = :board_id " +
                "ORDER BY parent_comment_id DESC, seq ASC";
        try{
            RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
            Map<String, ?> params = Collections.singletonMap("board_id", boardId);
            return jdbcTemplate.query(sql, params, rowMapper);
        }catch (Exception ex){
            log.info("리스트를 가져오지 못하였습니다.");
            return null;
        }
    }

    @Override
    public int updateComment(Comment comment) {
        String sql = "UPDATE comment SET content = :content, reg_date = :regdate, ip_addr = :ipAddr " +
                "WHERE member_id = :memberId and id = :id;";
//        Map<String,Object> map = new HashMap<>();
//        map.put("content",comment.getContent());
//        map.put("ip_addr",comment.getIpAddr());
//        map.put("id",comment.getId());
//        map.put("member_id",comment.getMemberId());
//        map.put("reg_date",comment.getRegdate());
        SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int updateCommentId(Long id) {
        String sql = "UPDATE comment SET parent_comment_id = :parent_comment_id WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("parent_comment_id", id);
        map.put("id", id);
        return jdbcTemplate.update(sql, map);
    }

    @Override
    public int deleteComment(Long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        String sql = "UPDATE comment SET is_deleted = 1 WHERE id = :id";
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public Long selectLastId(int count) {
        String sql = "SELECT id FROM comment ORDER BY id DESC LIMIT 0, :count";
        try {
            Map<String, ?> params = Collections.singletonMap("count", count);
            return jdbcTemplate.queryForObject(sql, params, Long.class);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 특정 id를 가진 댓글에 달린 대댓글의 개수를 구하는 메소드
     */
    @Override
    public int selectChildCommentCount(Long id) {
        String sql = "SELECT COUNT(*) FROM comment WHERE parent_comment_id = :id";
        Map<String, Long> params = Collections.singletonMap("id", id);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
