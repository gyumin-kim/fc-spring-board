package example.springboard.dao;

import example.springboard.dto.FileInfo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
@Component
public class FileUploadDaoImpl implements FileUploadDao {
    private SimpleJdbcInsert jdbcInsert;
    private NamedParameterJdbcTemplate jdbcTemplate;
    public FileUploadDaoImpl(DataSource dataSource){
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("file")
                                                            .usingGeneratedKeyColumns("idx");
    }
    //단순히 DB에 저장하는 일
    @Override
    public void addfile(FileInfo fileInfo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(fileInfo);
        jdbcInsert.execute(params);
    }
}
