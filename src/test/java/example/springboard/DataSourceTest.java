package example.springboard;

import example.springboard.config.ApplicationConfig;
import example.springboard.dao.MemberDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceTest {
    private static final Log log = LogFactory.getLog(DataSourceTest.class);

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DataSource ds = ac.getBean(DataSource.class);
        Connection conn = null;
        try {
            conn = ds.getConnection();
            if(conn != null)
                log.info("db 접속 성공!");
        }catch(Exception ex) {
            ex.printStackTrace();
        }finally {
            if(conn != null) {
                try {
                    conn.close(); // 커넥션을 되돌려준다.
                } catch (SQLException e) {
                }
            }
        } // finally
    }

}
