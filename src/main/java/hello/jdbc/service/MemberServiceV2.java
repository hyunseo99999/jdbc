package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;


    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            bizLogic(connection, fromId, toId, money);
            connection.commit();
        } catch (Exception ex) {
            connection.rollback();
            throw new IllegalStateException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (Exception e) {
                    log.error("error", e);
                }
            }
        }
    }

    private void bizLogic(Connection connection, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(connection, fromId);
        Member toMember = memberRepository.findById(connection, toId);

        memberRepository.update(connection, fromMember.getMemeber_id(), fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(connection, toMember.getMemeber_id(), toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemeber_id().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

}
