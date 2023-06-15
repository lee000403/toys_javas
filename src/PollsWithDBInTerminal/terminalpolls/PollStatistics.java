package PollsWithDBInTerminal.terminalpolls;

import java.sql.*;

public class PollStatistics {
    public ResultSet users_count(Statement statement) throws SQLException { 
        // 유저_ID 개수 세는 함수
        String query = "select count(group_cnt.user_id_cnt) as CNT\n" + //
                "from (\n" + //
                "SELECT count(user_id) as user_id_cnt\n" + //
                "FROM statistics\n" + //
                "group by user_id\n" + //
                ") as group_cnt";
        ResultSet resultSet = statement.executeQuery(query);
        // 설문자 명수 계산 LOOP
        while (resultSet.next()) {
            System.out.println("-- 총 설문자 : " + resultSet.getInt("CNT"));
        }
        return resultSet;
    }

    public ResultSet question_count(Statement statement, Connection connection) throws SQLException {
        // 문항 개수 함수
        String query = "SELECT QUESTION_ID\n" + //
                "FROM questions AS T_QUEST";
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println("-- 문항 내에서 최대 갯수 번호");
        // 문항 출력 LOOP
        while (resultSet.next()) {
            String que = resultSet.getString("QUESTION_ID");
            // 각 질문마다 최대 문항 번호 출력 DML
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
            // 질문 및 최대값 문항 출력
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("END_CNT.QUESTION")
                        + "    --> " + resultSet2.getString("T_ANSWE.ANSWER"));
            }
        }
        return resultSet;
    }

    public ResultSet answer_count(Statement statement) throws SQLException {
        // 문항 개수 출력 함수
        String query = "SELECT T_ANS.ANSWER, COUNT(T_ANS.ANSWER_ID) AS CNT\n" + //
                "FROM statistics AS T_STATI\n" + //
                "JOIN answers AS T_ANS\n" + //
                "ON T_STATI.ANSWER_ID = T_ANS.ANSWER_ID\n" + //
                "GROUP BY T_ANS.ANSWER_ID\n" + //
                ";";
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println();
        System.out.println("-- 답항 중심");
        // 최대 문항 개수 출력 LOOP
        while (resultSet.next()) {
            System.out.println(resultSet.getString("T_ANS.ANSWER") + "    --> " + resultSet.getString("CNT"));
        }
        return resultSet;
    }

}
