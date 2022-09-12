package Hello.jdbc.MemberService;

import Hello.jdbc.connection.MemberServiceV1;
import Hello.jdbc.domain.Member;
import Hello.jdbc.domain.MemberRepositoryV1;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static Hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class MemberServiceV1Test {
    String mem1 = "mmmm1";
    String mem2 = "mmmm2";
    private MemberServiceV1 memberServiceV1;
    private MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void before(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername(USERNAME);
        hikariDataSource.setPassword(PASSWORD);
        memberRepositoryV1 = new MemberRepositoryV1(hikariDataSource);
        memberServiceV1 = new MemberServiceV1(memberRepositoryV1);
    }

    @AfterEach
    void after() throws SQLException{
        memberRepositoryV1.DeleteMember(mem1);
        memberRepositoryV1.DeleteMember(mem2);
    }


    @Test
    @DisplayName("정상이체")
    void accountTransfer(){
        Member memberA = new Member(mem1, 50);
        Member memberB = new Member(mem2, 1000);

        try {
            //given
            memberRepositoryV1.InsertMember(memberA);
            memberRepositoryV1.InsertMember(memberB);

            //when
            //돈이 부족한 상태에서 송금 시도 시 트랜잭션 에러 발생생
           Assertions.assertThatThrownBy(() -> memberServiceV1.SendMoney(memberA.getMemberId(), memberB.getMemberId(), 1000)).isInstanceOf(IllegalStateException.class);

            //then
            Member findA = memberRepositoryV1.GetMemberByName(memberA.getMemberId());
            Member findB = memberRepositoryV1.GetMemberByName(memberB.getMemberId());
            log.info("{} {}",findA.getMemberId(),findA.getMoney());
            log.info("{} {}",findB.getMemberId(),findB.getMoney());

            //Thread.sleep(1000);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
