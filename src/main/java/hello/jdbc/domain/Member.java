package hello.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {

    private String memeber_id;

    private int money;

    public Member() {

    }
}
