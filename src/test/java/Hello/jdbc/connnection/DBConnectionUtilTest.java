package Hello.jdbc.connnection;

import Hello.jdbc.connection.DBConnectionUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//jdbc 표준 connection 인터페이스
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static Hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class DBConnectionUtilTest {
    @Test
    void connection(){
        Connection connection = DBConnectionUtil.getConnection();
        Assertions.assertThat(connection).isNotNull();
        //class org.h2.jdbc.JdbcConnection -> //jdbc 표준 connection 인터페이스 구현체
    }
    @Test
    void dataSourceDriverManger() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(driverManagerDataSource);
    }

    @Test
    void dataSoucrceConnectionPool() throws Exception{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(20);
        dataSource.setPoolName("MyPool");
        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    void useDataSource(DataSource dataSource) {
        try {
            Connection con = dataSource.getConnection();
            Connection con2 = dataSource.getConnection();

            log.info("class {} , con{}", con.getClass(), con);
            log.info("class {} , con{}", con2.getClass(), con2);
            Assertions.assertThat(con).isEqualTo(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
