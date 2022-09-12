package Hello.jdbc.connection;


//Repository -> 실제 DB CRUD 작업
//Service -> 트랜잭션(기능) 단위 작업

import Hello.jdbc.domain.Member;
import Hello.jdbc.domain.MemberRepositoryV1;

import java.sql.SQLException;

public class MemberServiceV1 {
    MemberRepositoryV1 memberRepositoryV1;

    public MemberServiceV1(MemberRepositoryV1 memberRepositoryV1) {
        this.memberRepositoryV1 = memberRepositoryV1;
    }

    public void SendMoney(String from, String to, int money) throws Exception{
        try {
            Member sender = memberRepositoryV1.GetMemberByName(from);
            Member receiver = memberRepositoryV1.GetMemberByName(to);

            if(sender.getMoney() >= money) {
                memberRepositoryV1.UpdateMember(from, sender.getMoney() - money);
                memberRepositoryV1.UpdateMember(to, receiver.getMoney() + money);
            }else
                throw new IllegalStateException("잔액이 부족합니다");
        } catch (SQLException e) {
            throw e;
        }
    }
}
