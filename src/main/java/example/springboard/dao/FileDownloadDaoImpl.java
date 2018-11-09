package example.springboard.dao;

import example.springboard.dto.FileInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
@Repository
public class FileDownloadDaoImpl implements FileDownloadDao {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    public FileDownloadDaoImpl(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public FileInfo download(Long boardIdx) {
        String sql = "select idx,board_idx,original_file_name,stored_file_name,file_size from file where board_idx = :board_idx and del_gb = 0";
        RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);
        Map<String, ?> params = Collections.singletonMap("board_idx",boardIdx);
        return  jdbcTemplate.queryForObject(sql,params,rowMapper);
    }
}
