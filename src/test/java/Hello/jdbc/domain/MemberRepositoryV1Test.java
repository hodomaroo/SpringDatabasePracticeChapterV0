package Hello.jdbc.domain;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.*;

import javax.sql.DataSource;
import java.sql.SQLException;

import static Hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class MemberRepositoryV1Test {
    MemberRepositoryV1 repo;

    @BeforeEach
    void beforeEach(){
        //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername(USERNAME);
        hikariDataSource.setPassword(PASSWORD);

        repo = new MemberRepositoryV1(hikariDataSource);
    }

    @Test
    void crud() throws Exception {
        String name = "10000";
        repo.InsertMember(new Member(name, 2000));
        log.info("{}",repo.GetMemberByName(name));
        repo.UpdateMember(name,1000);
        log.info("{}",repo.GetMemberByName(name));
        repo.DeleteMember(name);
        Thread.sleep(1000);
    }
}
