package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
    LPROD테이블에 새로운 데이터를 추가하시오.
    
    lprod_gu와 lprod_nm은 직접 입력 받아서 처리하고,
    lprod_id는 현재의 lprod_id에서 제일 큰 값보다 1크게 한다.
    
    입력 받은 lprod_gu가 이미 등록되어 있는 값이면 새로운 값으로 다시 입력 받아서 처리한다.
     
*/
public class JdbcTest05_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. DB 연결
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "JU", "java");

            boolean continueProgram = true;
            while (continueProgram) {
                // 3. lprod_id 최대값 조회
                String maxIdSql = "SELECT MAX(lprod_id) FROM lprod";
                pstmt = conn.prepareStatement(maxIdSql);
                rs = pstmt.executeQuery();
                int maxId = 0;
                if (rs.next()) {
                    maxId = rs.getInt(1);
                }
                int newId = maxId + 1;

                // 4. lprod_gu 입력 및 중복 체크
                String lprodGu;
                boolean isUnique;
                do {
                    System.out.print("추가할 Lprod_gu 입력 >> ");
                    lprodGu = scan.next();

                    String checkSql = "SELECT COUNT(*) FROM lprod WHERE lprod_gu = ?";
                    pstmt = conn.prepareStatement(checkSql);
                    pstmt.setString(1, lprodGu);
                    rs = pstmt.executeQuery();
                    rs.next();
                    isUnique = rs.getInt(1) == 0;

                    if (!isUnique) {
                        System.out.println("이미 존재하는 Lprod_gu입니다. 다시 입력해주세요.");
                    }
                } while (!isUnique);

                // 5. lprod_nm 입력
                System.out.print("Lprod_nm 입력 >> ");
                String lprodNm = scan.next();

                // 6. 새로운 데이터 삽입
                String insertSql = "INSERT INTO lprod (lprod_id, lprod_gu, lprod_nm) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, newId);
                pstmt.setString(2, lprodGu);
                pstmt.setString(3, lprodNm);

                int insertResult = pstmt.executeUpdate();

                if (insertResult > 0) {
                    System.out.println("새로운 데이터가 성공적으로 추가되었습니다.");
                } else {
                    System.out.println("데이터 추가에 실패했습니다.");
                }

                // 7. 계속 진행 여부 확인
                System.out.print("계속해서 데이터를 추가하시겠습니까? (Y/N) >> ");
                String answer = scan.next().trim().toLowerCase();
                if (!answer.equals("y")) {
                    continueProgram = false;
                    System.out.println("프로그램을 종료합니다.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 8. 자원 반납
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
            if (scan != null) scan.close();
        }
    }
}