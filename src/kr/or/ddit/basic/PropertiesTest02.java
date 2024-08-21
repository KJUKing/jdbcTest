package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest02 {
    public static void main(String[] args) {
        // 읽어온 정보를 저장할 Properties객체 생성
        Properties prop = new Properties();

        // 읽어올 파일을 정보를 갖는 File 객체 생성
        File file = new File("res/kr/or/ddit/config/dbinfo.properties");
        FileInputStream fis = null;

        try {
            //파일을 읽어올 입력용 스트림 객체 생성
            fis = new FileInputStream(file);

            //입력용 스트림을 이용하여 파일 내용을 읽어와 Properties객체에 저장하기
            //load()메소드이용
            prop.load(fis);

            // 읽어온 정보 출력
            System.out.println("DRIVER : " + prop.getProperty("driver"));
            System.out.println("URL : " + prop.getProperty("url"));
            System.out.println("USERNAME : "  + prop.getProperty("user"));
            System.out.println("PASSWORD : " + prop.getProperty("pass"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
        }
    }
}
