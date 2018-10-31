package example.springboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;
@Configuration
@EnableTransactionManagement
public class DBProperties {
    // 2) 자기 자신을 참조하는 private static 변수
    private static DBProperties instance = new DBProperties();

    private String jdbcDriver;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    // 1) private한 생성자
    private DBProperties() {
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            jdbcDriver = prop.getProperty("dbDriver");
            dbUrl = prop.getProperty("dbUrl");
            dbUser = prop.getProperty("dbId");
            dbPassword = prop.getProperty("dbPassword");
            System.out.println("'application.properties'에서 값 읽어옴");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 3) public static한 메소드 (instance를 리턴)
    public static DBProperties getInstance() {
        return instance;
    }
    public String getJdbcDriver() {
        return jdbcDriver;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public String getDbUser() {
        return dbUser;
    }
    public String getDbPassword() {
        return dbPassword;
    }
}
