package PollsWithDBInTerminal.terminalpolls;
import java.sql.*;

public class PollStatistics {
    public int users_count(Statement statement) throws SQLException {
        String query = "SELECT COUNT(T_USERS.USER_ID) AS CNT\n" + //
                "FROM USERS AS T_USERS";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println("-- 총 설문자 : " + resultSet.getInt("CNT"));
        }
        return 0;
    }
    public ResultSet question_count(Statement statement) throws SQLException {
        String query = "SELECT T_GROUP.QUESTION, T_GROUP.ANSWER_ID, T_GROUP.CNT, t_ans.ANSWER\n" + //
                "FROM (\n" + //
                "    SELECT QUESTION, ANSWER_ID, COUNT(ANSWER_ID) AS CNT\n" + //
                "    FROM statistics AS T_STATI\n" + //
                "    JOIN questions AS T_QUEST ON T_STATI.QUESTION_ID = T_QUEST.QUESTION_ID\n" + //
                "    GROUP BY QUESTION, ANSWER_ID\n" + //
                ") AS T_GROUP\n" + //
                "JOIN (\n" + //
                "    SELECT QUESTION, MAX(CNT) AS MAX_CNT\n" + //
                "    FROM (\n" + //
                "        SELECT QUESTION, ANSWER_ID, COUNT(ANSWER_ID) AS CNT\n" + //
                "        FROM statistics AS T_STATI\n" + //
                "        JOIN questions AS T_QUEST ON T_STATI.QUESTION_ID = T_QUEST.QUESTION_ID\n" + //
                "        GROUP BY QUESTION, ANSWER_ID\n" + //
                "    ) AS T_SUBGROUP\n" + //
                "    GROUP BY QUESTION\n" + //
                ") AS T_MAX \n" + //
                "ON T_GROUP.QUESTION = T_MAX.QUESTION AND T_GROUP.CNT = T_MAX.MAX_CNT\n" + //
                "join answers as t_ans\n" + //
                "on T_GROUP.ANSWER_ID = t_ans.ANSWER_ID\n" + //
                ";";
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println("-- 문항 내에서 최대 갯수 번호");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("T_GROUP.QUESTION") 
            + "    --> " + resultSet.getString("t_ans.ANSWER"));
        }
        return resultSet;
    }
    public ResultSet answer_count(Statement statement) throws SQLException {
        String query = "SELECT T_ANS.ANSWER, COUNT(T_ANS.ANSWER_ID) AS CNT\n" + //
                "FROM statistics AS T_STATI\n" + //
                "JOIN answers AS T_ANS\n" + //
                "ON T_STATI.ANSWER_ID = T_ANS.ANSWER_ID\n" + //
                "GROUP BY T_ANS.ANSWER_ID\n" + //
                ";";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("T_ANS.ANSWER") + "    --> " + resultSet.getString("CNT"));
        }
        return resultSet;
    }

}
