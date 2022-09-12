package Hello.jdbc;

import Hello.jdbc.connection.MemberServiceV1;
import Hello.jdbc.domain.Member;
import Hello.jdbc.domain.MemberRepositoryV1;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.SQLException;

import static Hello.jdbc.connection.ConnectionConst.*;

@SpringBootTest
class JdbcApplicationTests {


}
