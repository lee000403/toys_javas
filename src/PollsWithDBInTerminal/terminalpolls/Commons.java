package PollsWithDBInTerminal.terminalpolls;

import java.util.UUID;

public class Commons {
     public String generateUUID() { // statistic PK를 위한 UUID 함수 호출
        // UUID 값 리턴
        return UUID.randomUUID().toString();
    }
}
