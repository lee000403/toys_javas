package PollsWithDBInTerminal.terminalpolls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PollList {
    public String pollList(Statement statement) throws SQLException {
        try {
            Scanner myObj = new Scanner(System.in);
            String query;
            ResultSet resultSet;
            String query2;
            ResultSet resultSet2;
       
            // 문제 받는 쿼리
            query = "SELECT QUESTION\n" + //
                    "FROM questions\n" + //
                    ";";
            resultSet = statement.executeQuery(query);

            // 답항 뽑는 쿼리
            query2 = " SELECT ANSWER\n" + //
                    "FROM answers;";

            // 질문과 답항을 출력
            while (resultSet.next()) {
                System.out.println(resultSet.getString("QUESTION"));

                resultSet2 = statement.executeQuery(query2);
                while (resultSet2.next()) {
                    
                    System.out.print(resultSet2.getString("ANSWER"));
                }
                System.out.println(); //줄바꿈
                System.out.print("답 : " );
                int first = myObj.nextInt(); 
                System.out.println( first);

                

                
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;

    }
}
