package example.springboard.dao;

import example.springboard.dto.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;


    public  CommentDaoImpl(DataSource dataSource){
        System.out.println("CommentDaoImpl()");
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("comment");
    }
    @Override
    public List<Comment> getCommentList(int parentCommentId) {
        String sql = "SELECT c.id, c.parent_comment_id, c.seq, m.id, c.content, c.ip_addr, c.reg_date \n" +
                "FROM comment AS c INNER JOIN member AS m ON c.member_id = m.id\n" +
                "WHERE board_id = :board_id \n" +
                "ORDER BY parent_comment_id DESC, seq ASC";
        try{
            RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
            return jdbc.query(sql,rowMapper);
        }catch (Exception ex){
            System.out.println("리스트를 가져오지 못하였습니다.");
            return null;
        }
    }

    @Override
    public int addComment(Comment comment) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
        return insertAction.execute(params);
    }

    @Override
    public int updateComment(Comment comment) {
        String sql = "UPDATE comment SET content = :content, reg_date = :reg_date, ip_addr = :ip_addr \n" +
                "WHERE member_id = :member_id and id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
        return jdbc.update(sql,params);
    }

    @Override
    public int deleteComment(Long id) {
        Map<String, Long> params = Collections.singletonMap("id",id);
        String sql = "UPDATE comment SET is_deleted = 1 WHERE id = :id";
        return jdbc.update(sql,params);
    }
}
