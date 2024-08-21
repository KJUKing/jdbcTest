package kr.or.ddit.basic;

import java.util.ResourceBundle;

/*
    ResourceBundle객체 => 파일의 확장자가 '.properties'인 파일의 내용을 일어과
        key값과 value값을 분리해서 정보를 갖는 객체
 */
public class ResourceBundleTest {
    public static void main(String[] args) {
        // ResourceBundle객체를 이용하여 '.properties'파일 읽어오기

        // ResourceBundle객체 생성
        // 읽어올 파일을 지정할때 패키지명.파일명만 지정하고 확장자는 지정하지않는다.
        // 이유 확장자는 무조건 properties이기떄문에 다른게 있다는가정은없다
        ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.config.dbinfo");

        System.out.println("DRIVER " + bundle.getString("driver"));
        System.out.println("URL " + bundle.getString("url"));
        System.out.println("USER " + bundle.getString("user"));
        System.out.println("PASS" + bundle.getString("pass"));


    }
}
