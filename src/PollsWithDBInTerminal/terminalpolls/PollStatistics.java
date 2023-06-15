package PollsWithDBInTerminal.terminalpolls;

import java.sql.*;

public class PollStatistics {
    public ResultSet users_count(Statement statement) throws SQLException {
        String query = "select count(group_cnt.user_id_cnt) as CNT\n" + //
                "from (\n" + //
                "SELECT count(user_id) as user_id_cnt\n" + //
                "FROM statistics\n" + //
                "group by user_id\n" + //
                ") as group_cnt";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println("-- 총 설문자 : " + resultSet.getInt("CNT"));
        }
        return resultSet;
    }

    public ResultSet question_count(Statement statement, Connection connection) throws SQLException {
        String query = "SELECT QUESTION_ID\n" + //
                "FROM questions AS T_QUEST";
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println("-- 문항 내에서 최대 갯수 번호");
        while (resultSet.next()) {
            String que = resultSet.getString("QUESTION_ID");

            query = "SELECT T_ANSWE.ANSWER, END_CNT.QUESTION\n" + //
                    "FROM (select t_quest.QUESTION_ID, t_quest.QUESTION, MAX(group_cnt.CNT) AS MAX_CNT\n" + //
                    "\t\tfrom (\n" + //
                    "\t\t\tselect ANSWER_ID, QUESTION_ID , count(*) as cnt\n" + //
                    "\t\t\tfrom statistics\n" + //
                    "\t\t\tWHERE QUESTION_ID = '"+que+"'\n" + //
                    "\t\t\tgroup by ANSWER_ID, QUESTION_ID\n" + //
                    "\t\t) as group_cnt\n" + //
                    "\t\tjoin questions as t_quest\n" + //
                    "\t\ton t_quest.QUESTION_ID = group_cnt.QUESTION_ID\n" + //
                    "\t\tjoin answers as t_answe\n" + //
                    "\t\ton t_answe.ANSWER_ID = group_cnt.ANSWER_ID\n" + //
                    "        GROUP BY group_cnt.QUESTION_ID, t_quest.QUESTION\n" + //
                    "\t) AS END_CNT\n" + //
                    "JOIN (\n" + //
                    "\tselect ANSWER_ID, QUESTION_ID , count(*) as cnt\n" + //
                    "\tfrom statistics\n" + //
                    "\tgroup by ANSWER_ID, QUESTION_ID\n" + //
                    ") AS T_QUE\n" + //
                    "ON END_CNT.MAX_CNT = T_QUE.CNT AND END_CNT.QUESTION_ID = T_QUE.QUESTION_ID\n" + //
                    "JOIN answers AS T_ANSWE\n" + //
                    "ON T_QUE.ANSWER_ID = T_ANSWE.ANSWER_ID\n" + //
                    "LIMIT 1\n";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query);
            
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("END_CNT.QUESTION")
                        + "    --> " + resultSet2.getString("T_ANSWE.ANSWER"));
            }
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
        System.out.println();
        System.out.println("-- 답항 중심");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("T_ANS.ANSWER") + "    --> " + resultSet.getString("CNT"));
        }
        return resultSet;
    }

}
