package Hello.jdbc.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MemberRepositoryV0Test {
    MemberRepositoryV0 repo = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        log.info("{}", repo.GetMemberByName("hi1"));
    }


}
