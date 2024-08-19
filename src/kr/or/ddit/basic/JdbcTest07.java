package kr.or.ddit.basic;

import kr.or.ddit.util.DBUtil;

import java.sql.*;
import java.util.Scanner;

/**
    회원을 관리하는 프로그램을 작성하시오. (MYMEMBER 테이블 이용)

    아래 메뉴의 기능을 모두 구현하시오. (CRUD기능)
    메뉴 예시)
        == 작업 선택 ==
        1. 자료 추가    -> insert
        2. 자료 삭제    -> delete
        3. 자료 수정    -> update
        4. 전체 자료 출력-> select
        0. 작업 끝
      =================

    조건)
    1. 자료 추가에서 '회원ID'는 중복되지 않는다.(중복되면 다시 입력받는다.)
    2. 자료 삭제에서 '회원ID'를 입력 받아서 처리한다.
    3. 자료 수정에서 '회원ID'는 변경되지 않는다.

 */
public class JdbcTest07 {
    public static void main(String[] args) {



        Scanner scan = new Scanner(System.in);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();

            System.out.println("== 작업 선택 ==");
            System.out.println("1. 자료 추가");
            System.out.println("2. 자료 삭제");
            System.out.println("3. 자료 수정");
            System.out.println("4. 전체 자료 출력");
            System.out.println();
            System.out.print("수행할 작업 번호 >> ");
            int num = scan.nextInt();
            scan.nextLine(); // nextInt 후 개행 문자 처리

            if (num == 1) {
                System.out.println("회원 추가 페이지입니다");

                System.out.print("id 입력");
                String id = scan.nextLine();

                // ID 중복 체크
                String checkSql = "select count(*) from mymember where MEM_ID = ?";
                pstmt = conn.prepareStatement(checkSql);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("이미 존재하는 회원 ID입니다 다른 ID를 입력해주십시오");
                } else {
                    System.out.println();
                    System.out.print("pw 입력");
                    String pass = scan.nextLine();
                    System.out.println();
                    System.out.print("name 입력");
                    String name = scan.nextLine();
                    System.out.println();
                    System.out.print("tel 입력");
                    String tel = scan.nextLine();
                    System.out.println();
                    System.out.print("address 입력");
                    String addr = scan.nextLine();
                    System.out.println();

                    String sql = "insert into MYMEMBER(MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR) values(?,?,?,?,?)";

                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, id);
                    pstmt.setString(2, pass);
                    pstmt.setString(3, name);
                    pstmt.setString(4, tel);
                    pstmt.setString(5, addr);

                    int result = pstmt.executeUpdate();
                    if (result == 1) {
                        System.out.println("회원정보가 성공적으로 추가됨");
                    }else{
                        System.out.println("회원정보 추가 실패");
                    }
                }

            }

            if (num == 2) {
                System.out.println("회원 삭제 페이지입니다");

                System.out.println("삭제하려는 회원 ID를 입력하시오");
                String id = scan.nextLine();
                System.out.println();

                String checkSql = "select count(*) from mymember where MEM_ID = ?";
                pstmt = conn.prepareStatement(checkSql);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    String sql = "delete from mymember where MEM_ID = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, id);

                    int result = pstmt.executeUpdate();
                    if (result > 0) {
                        System.out.println("회원정보가 성공적으로 삭제됨");
                    } else {
                        System.out.println("회원정보 삭제 실패");
                    }
                } else {
                    System.out.println("없는 id 정보입니다.");
                }

            }

            if (num == 3) {
                System.out.println("회원 수정 페이지입니다");

                System.out.println("수정하려는 회원 ID를 입력하시오");
                String id = scan.nextLine();
                System.out.println();

                // ID 중복 체크
                String checkSql = "select count(*) from mymember where MEM_ID = ?";
                pstmt = conn.prepareStatement(checkSql);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();

                if (rs.next()) {

                    System.out.println("회원정보 발견 성공");
                    System.out.println("수정할 회원정보를 기입하시오");

                    System.out.print("pw 입력");
                    String pass = scan.nextLine();
                    System.out.println();
                    System.out.print("name 입력");
                    String name = scan.nextLine();
                    System.out.println();
                    System.out.print("tel 입력");
                    String tel = scan.nextLine();
                    System.out.println();
                    System.out.print("address 입력");
                    String addr = scan.nextLine();
                    System.out.println();

                    pstmt.close();

                    String sql = "update mymember SET mem_pass = ?, mem_name = ?, mem_tel = ?, mem_addr = ?\n" +
                            "WHERE mem_id = ?";


                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, pass);
                    pstmt.setString(2, name);
                    pstmt.setString(3, tel);
                    pstmt.setString(4, addr);
                    pstmt.setString(5, id);
                    int updateCount = pstmt.executeUpdate();
                    if (updateCount == 1) {
                        System.out.println("회원 정보 수정 성공");
                    } else {
                        System.out.println("회원 정보 수정 실패");
                    }
                }else{
                    System.out.println("회원정보 발견 실패");
                }

            }

            if (num == 4) {
                System.out.println("전체 정보 조회 페이지입니다");

                String sql = "select * from mymember";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                System.out.println("\t=== 검색 결과 ===");
                System.out.println("------------------------------------------------------");
                while(rs.next()) {
                    String memId = rs.getString("MEM_ID");
                    String memPass = rs.getString("MEM_PASS");
                    String memName = rs.getString("MEM_NAME");
                    String memTel = rs.getString("MEM_TEL");
                    String memAddr = rs.getString("MEM_ADDR");

                    System.out.println(memId + ", " + memPass + ", " + memName + ", " + memTel + ", " + memAddr);

                }
                System.out.println("------------------------------------------------------");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) try { rs.close();  }catch(SQLException e) {}
            if(pstmt!=null) try { pstmt.close();  }catch(SQLException e) {}
            if(conn!=null) try { conn.close();  }catch(SQLException e) {}
        }
    }

}
