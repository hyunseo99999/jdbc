package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    private MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        Member member = new Member("hi10", 50000);
        memberRepositoryV0.save(member);

        Member findByMember = memberRepositoryV0.findById(member.getMemeber_id());
        Assertions.assertEquals(member, findByMember);

    }
}
