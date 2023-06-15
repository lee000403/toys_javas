package PollsWithDBInTerminal;

import java.sql.*;
import java.util.Scanner;

import PollsWithDBInTerminal.terminalpolls.PollList;
import PollsWithDBInTerminal.terminalpolls.PollPeople;
import PollsWithDBInTerminal.terminalpolls.PollStatistics;

public class PollsWithDB {
    public static void main(String[] args) {
        try {
            // sql (url, user, password 변수 저장)
            String url = "jdbc:mysql://127.0.0.1:3306/db_polls";
            String user = "root";
            String password = "!yojulab*";

            // sql 연결
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("DB연결 성공\n");
            // statement : edit 생성
            Statement statement = connection.createStatement();

            // query 및 resultSet 변수 미리 생성
            String query;
            String query2;
            ResultSet resultSet;
            ResultSet resultSet2;

            // Scanner 인스턴스 및 Scanner 변수 : in 설정.
            Scanner in = new Scanner(System.in);

            // 함수 인스턴스화
            PollStatistics pollStatistics = new PollStatistics();
            PollList pollList = new PollList();
            PollPeople pollPeople = new PollPeople();

            String key = " "; // key 에 변수 초기화
            System.out.println("------ 작동 key ------");
            System.out.println("(E)xit : 종료");
            System.out.println("(P)oll : 설문 시작");
            System.out.println("(S)tatistic : 설문 통계");
            while (!(key.equals("E") || key.equals("Exit"))) {
                System.out.print("선택 입력 : ");
                key = in.nextLine(); // key 입력 받기
                

                // P 랑 Poll 입력시 안에 if 문 실행
                if (key.equals("P") || key.equals("Poll")) {
                    System.out.println();
                    String user_id = pollPeople.users(statement, connection);
                    String answers = pollList.pollList(statement, connection, user_id);
                }

                // S 랑 Statistic 입력시 else if 문 실행
                else if (key.equals("S") || key.equals("Statistic")) {
                    ResultSet user_c = pollStatistics.users_count(statement);
                    ResultSet question_c = pollStatistics.question_count(statement, connection);
                    ResultSet answer_c = pollStatistics.answer_count(statement);
                }
                // 그 외 입력 값에 대한 처리.
                else {
                    System.out.println("잘못 입력. 입력값 : " + key);
                }
            }
            System.out.println("----- 설문 종료 ------");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
