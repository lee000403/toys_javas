package PollsWithDBInTerminal.terminalpolls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.HashMap;

public class PollPeople {
    public void startPoll(Statement statement) throws SQLException {

        try {
            // 설문자 입력 쿼리
            String query = "SELECT USER_ID, USER\n" +
                    "FROM users AS T_USER ;\n";

            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("- 설문자 가능 명단(가입 완료)");

            // 설문자 number 변수 초기화 후 설문 참여자 번호 표시
            int number = 1; // 설문 참여자 번호를 나타내는 변수 초기값 1로 설정
            Connection connection = statement.getConnection();

            HashMap<Integer, String> userNumberMap = new HashMap<>();



            // 현재 행에서 "USER_NAME" 컬럼의 값을 도출 설문 참여자 이름
            while (resultSet.next()) {
                String userId = resultSet.getString("USER_ID");
                String userName = resultSet.getString("USER_NAME");
                // 설문참여자의 번호 (number)와 이름 (userName) 출력
                System.out.println(number + ". " + userName);
                // 설문참여자의 번호를 1씩 증가
                userNumberMap.put(number, userName);
                number++;
            }

            // 설문자 번호 입력 메시지 출력
            System.out.print("- 설문자 번호 입력 : ");
            Scanner in = new Scanner(System.in);
            // 메소드 사용 후 데이터 입력 받음
            String userId = in.nextLine();

            // 선택된 설문자의 번호와 이름 출력
            boolean validSelection = false;
            while (!validSelection) {
                if (userNumberMap.containsKey(userId)) {
                    System.out.println("선택된 설문자: " + userId + ". " + userNumberMap.get(userId));
                    validSelection = true;
                } else {
                    System.out.println("- Error - 확인 후 입력 필요");
                    System.out.print("- 설문자 번호 입력 : ");
                    userId = in.nextLine();
                }
            }

            System.out.println("-- 설문 시작");
            System.out.println("\t--> 아래 참조 : poll contents example");

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());

        }

    }
}