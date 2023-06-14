package PollsWithDBInTerminal.terminalpolls;

import java.sql.*;
import java.util.Scanner;

public class PollPeople {
    public void startPoll(Statement statement) throws SQLException {

        try {
            String query = "SELECT U.USER_ID AS USERID, U.USER, U.GENDER, U.AGE, Q.QUESTION_ID AS QUESTIONID, Q.QUESTION, A.ANSWER_ID AS ANSWERID, A.ANSWER\n"
                    +
                    "FROM USERS AS U\n" +
                    "INNER JOIN QUESTIONS AS Q ON U.USER_ID = Q.QUESTION_ID\n" +
                    "INNER JOIN ANSWERS AS A ON Q.QUESTION_ID = A.ANSWER_ID";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("- 설문자 가능 명단(가입 완료)");
            int number = 1;
            while (resultSet.next()) {
                String userName = resultSet.getString("USER_NAME");
                System.out.println(number + ". " + userName);
                number++;
            }

            System.out.print("- 설문자 번호 입력 : ");
            Scanner in = new Scanner(System.in);
            int userNumber = in.nextInt();

            while (userNumber <= 0 || userNumber >= number) {
                System.out.println("- Error - 확인 후 입력 필요");
                System.out.print("- 설문자 번호 입력 : ");
                userNumber = in.nextInt();
            }

            System.out.println("-- 설문 시작");
            System.out.println("\t--> 아래 참조 : poll contents example");

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}