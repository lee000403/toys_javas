package PollsWithDBInTerminal.terminalpolls;

import java.sql.*;
import java.util.Scanner;
import java.util.HashMap;

public class PollPeople {
    public String users(Statement statement, Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        // 설문자 입력 쿼리
        String query = "SELECT USER_ID, USER\n" +
                "FROM users AS T_USER ;\n";

        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("- 설문자 가능 명단(가입 완료)");

        // 설문자 number 변수 초기화 후 설문 참여자 번호 표시
        int number = 1; // 설문 참여자 번호를 나타내는 변수 초기값 1로 설정
        String user_id;
        int user_number;
        HashMap<Integer, String> userNumberMap = new HashMap<>();

        // 현재 행에서 "USER_NAME" 컬럼의 값을 도출 설문 참여자 이름
        while (resultSet.next()) {
            String userId = resultSet.getString("USER_ID");
            String userName = resultSet.getString("USER");
            // 설문참여자의 번호 (number)와 이름 (userName) 출력
            System.out.print(number + ", " + userName + " ");
            // 설문참여자의 번호를 1씩 증가
            userNumberMap.put(number, userId);
            number++;
        }
        resultSet.close();
        System.out.println();
        // 설문자 번호 입력 LOOP
        while (true) {
            System.out.print("- 설문자 번호 입력 : ");
            user_number = in.nextInt();
            query = "SELECT COUNT(*) AS CNT\n" +
                    "FROM USERS";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query);
            // 선택 입력 외 값 입력시 BREAK문 작동 
            if (resultSet2.next()) {
                String user_num = resultSet2.getString("CNT");
                if (user_number > Integer.parseInt(user_num)) {
                    System.out.println("-Error- 확인후 입력 필요");
                } else {
                    break;
                }
            }
        }
        // USER_ID 값 얻기
        user_id = userNumberMap.get(user_number);
        System.out.println("-- 설문 시작");
        System.out.println("\t--> 아래 참조 : poll contents example");
        return user_id;
    }
}