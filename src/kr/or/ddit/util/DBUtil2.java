package kr.or.ddit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// jdbc드라이버를 로딩하고 connection객체를 생성하여 반환하는 메소드로 구성된 class 만들기
// (dbinfo.properties파일의 내용으로 설정하기)
public class DBUtil2 {
    static Properties prop; //Properties객체 변수 선언



    static{
        prop = new Properties();
        File file = new File("res/kr/or/ddit/config/dbinfo.properties");
        FileInputStream fis = null;

        try{
            fis = new FileInputStream(file); // 파일 내용을 읽어올 properties객체에 저장하기
            prop.load(fis);

//            Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(prop.getProperty("driver"));

        } catch (Exception e) {
            System.out.println("드라이버 로딩실패!!");
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
//            conn = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe", "JU", "java");
                conn = DriverManager.getConnection(
                        prop.getProperty("url"),
                        prop.getProperty("user"),
                        prop.getProperty("pass"));
        }catch (SQLException e) {
            System.out.println("DB 연결 실패!!");
            e.printStackTrace();
        }
        return conn;
    }
}
