package example.springboard.dao;

import example.springboard.dto.Member;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoImpl implements MemberDao {
    private static final Log log = LogFactory.getLog(MemberDaoImpl.class);
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    // Constructor Injection
    public MemberDaoImpl(DataSource dataSource) {
        log.info("MemberDaoImpl()");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                            .withTableName("member")
                            .usingGeneratedKeyColumns("id");
    }

    /**
     * 회원가입
     */
    @Override
    public Long insertMember(Member member) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    /**
     * 게시글 조회(제목, 내용, 제목+내용) 시 작성자 이름(name)을 가져오기 위한 메소드
     */
    @Override
    public String selectNameById(Long id) {
        String sql = "SELECT name FROM member WHERE id = :member_id";
        try {
            Map<String, ?> params = Collections.singletonMap("member_id", id);
            return jdbcTemplate.queryForObject(sql, params, String.class);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 로그인 화면에서 사용자가 입력한 name을 바탕으로 password를 가져옴
     * 가져온 password를 사용자가 입력한 password와 service에서 비교
     */
    @Override
    public String selectPasswordByName(String name) {
        String sql = "SELECT password FROM member WHERE name = :name";
        try{
            // Board에 값을 담아주는 규칙을 가지고 있는 RowMapper를 만든다.
            // Board.class정보를 읽어들여서 프로퍼티의 이름을 가지고 칼럼에 담아주는 규칙을 만든다.
            // readCount라는 프로퍼티가 있으면 read_count라는 칼럼의 값을 담아준다.
            Map<String, ?> params = Collections.singletonMap("name", name);
            // 한건 or null 을 조회할 때는 queryForObject
            // 첫번째 파라미터 : sql
            // 두번째 파라미터 : 바인딩할 Map
            // 세번째 파라미터 : Mapper (칼럼을 DTO에 담아주기위한 규칙)
            return jdbcTemplate.queryForObject(sql, params, String.class);
        }catch(Exception ex){
            return null;
        }
    }

    /**
     * 회원정보 보기 및 수정 용도
     */
    @Override
    public Member selectMemberByEmail(String email) {
        String sql = "SELECT id, name, email, password FROM member WHERE email = :email";
        try {
            RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
            Map<String, ?> params = Collections.singletonMap("email", email);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Member selectMemberById(Long id) {
        String sql = "SELECT id, name, email, password FROM member WHERE id = :id";
        try {
            RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
            Map<String, ?> params = Collections.singletonMap("id", id);
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (Exception ex) {
            return null;
        }
    }

    //    @Override
//    public Member selectMemberByName(String name) {
//        String sql = "SELECT id, name, email, password FROM member WHERE name = :name";
//        try {
//            RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
//            Map<String, ?> params = Collections.singletonMap("name", name);
//            return jdbcTemplate.queryForObject(sql, params, rowMapper);
//        } catch (Exception ex) {
//            return null;
//        }
//    }

    /**
     * 회원정보 수정
     */
    // 회원정보 수정 메소드 -> SET id = :id 를 빼자
    @Override
    public int updateMember(Member member) {
        String sql = "UPDATE member SET id = :id, name = :name, email = :email, password = :password " +
                "WHERE id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Member> selectAllMember() {
        String sql = "SELECT id, name, email, password FROM member";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Member.class));
    }

    @Override
    public int updateMemberPermission(Member member, int[] permissions) {
        // 해당 member의 permission 정보를 모두 지운 뒤, 매개변수로 받은 permissions을 다시 추가한다.
        String sql = "DELETE FROM member_permission WHERE member_id = :member_id";
        Map<String, ?> params = Collections.singletonMap("member_id", member.getId());
        jdbcTemplate.update(sql, params);

        for (int permission : permissions) {
            // permission이 0이면 admin.jsp에서 해당 권한에 체크하지 않았다는 의미
            if (permission == 0)    continue;

            sql = "INSERT INTO member_permission (member_id, permission_id) VALUES (:id, :permission_id)";
            Map<String, Object> map = new HashMap<>();
            map.put("id", member.getId());
            map.put("permission_id", permission);
            jdbcTemplate.update(sql, map);
        }

        return 0;
    }
}
