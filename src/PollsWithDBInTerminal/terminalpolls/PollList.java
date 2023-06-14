package PollsWithDBInTerminal.terminalpolls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class PollList {
    public String pollList(Statement statement) throws SQLException {
        try {
            Scanner myObj = new Scanner(System.in);
            String query;
            ResultSet resultSet;
            String query2;
            ResultSet resultSet2;
            String q = "QUESTION";
            // 문제 받는 쿼리
            query = "SELECT QUESTION, ANSWER\n" + //
                    "FROM answers  AS T_ANSWE\n" + //
                    "JOIN questions AS T_QUEST\n" + //
                    "ORDER BY QUESTION, ANSWER";
            resultSet = statement.executeQuery(query);

            HashMap <String, String>que_ans = new HashMap<String, String>();
            int num =1;
            // 질문과 답항을 출력
            while (resultSet.next()) {
                if (num%4 == 1) {
                    System.out.println(resultSet.getString("'"+q+"'"));
                }
                System.out.print(resultSet.getString("ANSWER") + ". ");
                if (num%4 == 0) {
                    System.out.println();
                    System.out.print("답 : " );
                    int first = myObj.nextInt();
                    que_ans.put(q, String.valueOf(first));
                }
                
                num++;
                
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;

    }
}
