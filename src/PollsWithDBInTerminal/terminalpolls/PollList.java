package PollsWithDBInTerminal.terminalpolls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class PollList {
    public String pollList(Statement statement, Connection connection, String user_id) throws SQLException {
        Scanner in = new Scanner(System.in);
        String query;
        ResultSet resultSet;
        String query2;
        ResultSet resultSet2;
        // 문제 받는 쿼리
        query = "SELECT QUESTION, QUESTION_ID\n" + //
                "FROM questions AS T_QUEST";
        resultSet = statement.executeQuery(query);

        HashMap<String, String> ans_ans = new HashMap<String, String>();
        HashMap<String, String> que_ans = new HashMap<String, String>();
        int answer;
        // 질문과 답항을 출력
        Statement statement2 = connection.createStatement();
        query = "DELETE \n" + //
                "FROM statistics\n" +
                "WHERE USER_ID = '" + user_id + "'";
        int count3 = statement2.executeUpdate(query);
        while (resultSet.next()) {
            System.out.println();
            statement = connection.createStatement();
            System.out.println(resultSet.getString("QUESTION"));

            query2 = "SELECT ANSWER, ANSWER_ID\n" + //
                    "FROM answers AS T_ANSWE";
            resultSet2 = statement.executeQuery(query2);
            statement = connection.createStatement();
            int number = 1;

            while (resultSet2.next()) {
                System.out.print(resultSet2.getString("ANSWER"));
                ans_ans.put(String.valueOf(number), resultSet2.getString("ANSWER_ID"));
                number++;
            }
            System.out.println();
            System.out.print("답 입력 : ");
            answer = in.nextInt();
            if (answer > 4) {
                System.out.println("잘못 입력 하였습니다. \n" + "처음으로 돌아 갑니다. ");
                break;
            }
            Commons commons = new Commons();
            String statPk = commons.generateUUID();
            query = "INSERT INTO statistics\n" + //
                    "(USER_ID, QUESTION_ID, ANSWER_ID, STATISTICS_ID)\n" + //
                    "value\n" + //
                    "('" + user_id + "', '" + resultSet.getString("QUESTION_ID") + "', '"
                    + ans_ans.get(String.valueOf(answer)) + "', '" + statPk + "')";
            int count4 = statement.executeUpdate(query);

        }
        return null;
    }
}
