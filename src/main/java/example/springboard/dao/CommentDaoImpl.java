package example.springboard.dao;

import example.springboard.controller.LoginController;
import example.springboard.dto.Comment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
        SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
        return jdbcInsert.execute(params);
    }

    @Override
    public List<Comment> getCommentList(Long boardId) {
        // Comment : id가 아닌 member name이 보여져야 함.(SQL 수정 필요)
        String sql = "SELECT c.id, c.parent_comment_id, c.seq, m.id, c.content, c.ip_addr, c.reg_date " +
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
    public int deleteComment(Long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        String sql = "UPDATE comment SET is_deleted = 1 WHERE id = :id";
        return jdbcTemplate.update(sql, params);
    }
}
