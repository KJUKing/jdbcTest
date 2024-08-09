package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest01 {
	/*
		- JDBC (Java DataBase Connectivity)라이브러리를 이용하여 DB자료 처리하기
		
		- JDBC 처리 순서
		1. 드라이버 로딩 ==> 라이브러리를 사용할 수 있게 메모리로 읽어 들이는 작업
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		2. DB시스템에 접속하기 ==> 접속이 완료되면 Connection객체 생성된다.
			DriverManager.getConnection()메서드를 이용한다.
			
		3. 질의 ==> SQL문장을 DB서버로 보내서 결과를 얻어온다.
			(Statement객체 또는 PreparedStatement개체를 이용하여 작업한다.)
			
		4. 결과 처리 작업 ==> 질의 결과를 받아서 원하는 작업을 수행한다.
			1) SQL문이 select문일 경우 ==> select한 결과가 ResultSet객체에 저장되어 반환된다.
			2) SQL문이 select문이 아닐 경우(insert문, update문, delete문...) 
					==> 작업에 성공한 레코드 수가 반환된다. 
		
		5. 사용했던 자원 반납하기
			==> 각 단계에서 생성된 객체를 close()메서드를 이용하여 닫아 준다.
		
		
	*/
	public static void main(String[] args) {
		// DB작업에 필요한 객체 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB에 접속  ==> Connection객체 생성
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", 
					"JSJ91", "java");
			
			// 3. 질의
			
			// 3-1. SQL문 작성
			String sql = "select * from lprod";
			
			// 3-2. Statement객체 생성 ==> 질의를 처리하는 객체 생성
			stmt = conn.createStatement();
			
			// 3-3. SQL문를 서버로 보내서 결과를 얻어온다.
			//		(실행할 SQL문이 select문이기 때문에 실행 결과가 ResultSet객체에 저장되어 반환된다.)
			rs = stmt.executeQuery(sql);
			
			// 4. 결과 처리하기  ==> 한 레코드씩 화면 출력하기
			System.out.println("== 처리 결과 출력 ==");
			
			// Result객체에 저장된 데이터를 차례로 꺼내오려면 반복문과 next()메서드를 이용하여 처리한다.
			// next() 메서드 ==> ResultSet객체의 데이터를 가리키는 포인터를 다음번째 레코드로 이동시키고
			//					그 곳에 데이터가 있으면 true를 반환하고 없으면 false를 반환한다.
			while(rs.next()) {
				// 데이터 가져오기
				// 방법1) ResultSet객체.get자료형이름("컬럼명 또는 alias명")
				// 방법2) ResultSet객체.get자료형이름(컬럼번호)  ==> 컬럼 번호는 1부터 시작...
				System.out.println("lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu : " + rs.getString(2));
				System.out.println("lprod_nm : " + rs.getString("LPROD_NM"));
				System.out.println("----------------------------------------");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// 5. 자원 반납
			try{ if(rs!=null) rs.close(); }catch(SQLException e) {}
			try{ if(stmt!=null) stmt.close(); }catch(SQLException e) {}
			try{ if(conn!=null) conn.close(); }catch(SQLException e) {}
		}
		
		

	}

}
