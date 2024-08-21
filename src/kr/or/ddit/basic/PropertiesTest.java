package kr.or.ddit.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) {
        Properties prop = new Properties();


        // 저장할 properties파일 정보를 갖는 File객체 생성
        File file = new File("res/kr/or/ddit/config/test.properties");
        FileOutputStream fos = null;
        try {
            // 저장할 내용을 Properties객체에 추가하기
            prop.setProperty("name", "홍길동");
            prop.setProperty("age", String.valueOf(30));
            prop.setProperty("addr", "대전시");
            prop.setProperty("tel", "010-1234-4568");

            //출력용 스트림 객체 생성
            fos = new FileOutputStream(file);
            prop.store(fos, "이 내용은 주석입니다");
            System.out.println("출력 작업 완료..");

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
