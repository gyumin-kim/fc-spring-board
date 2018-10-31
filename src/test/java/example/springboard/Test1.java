package example.springboard;

import example.springboard.dto.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public class Test1 {

    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    @Test
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
}
