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
    public int answer_count(Statement statement) throws SQLException {
        String query = "SELECT COUNT(T_STATI.ANSWER_ID) AS CNT\n" + //
                "FROM statistics AS T_STATI\n" + //
                "GROUP BY T_STATI.ANSWER_ID\n" + //
                "ORDER BY T_STATI.ANSWER_ID";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            System.out.println(" --> " + resultSet.getInt("CNT") + "개");
        }
        return 0;
    }
}
