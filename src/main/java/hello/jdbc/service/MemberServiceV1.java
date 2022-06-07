package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV0;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV0 memberRepositoryV0;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositoryV0.findById(fromId);
        Member toMember = memberRepositoryV0.findById(toId);

    }

}
