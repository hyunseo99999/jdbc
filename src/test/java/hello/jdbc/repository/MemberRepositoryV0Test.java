package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    private MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        Member member = new Member("hi30", 50000);
        memberRepositoryV0.save(member);

        Member findByMember = memberRepositoryV0.findById(member.getMemeber_id());
        Assertions.assertEquals(member, findByMember);
    }

    @Test
    void update() throws SQLException {
        Member findByMember = memberRepositoryV0.findById("hi1");
        memberRepositoryV0.update(findByMember.getMemeber_id(), 1000);
        Member findByMember01 = memberRepositoryV0.findById("hi1");

        org.assertj.core.api.Assertions.assertThat(findByMember01.getMoney()).isEqualTo(1000);
    }

    @Test
    void delete() throws SQLException {
        memberRepositoryV0.delete("hi1");
    }
}
