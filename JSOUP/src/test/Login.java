package test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class Login {  
	
	 private String url_root = "http://88.100.14.65/sinoiaaf/"; 
	 private String url_safecode = "http://88.100.14.65/sinoiaaf/pages/login/RandomNumUtil.jsp?d=" ; // ��֤��
	 private String url_Login = "http://88.100.14.65/sinoiaaf/checklogin/checkLoginInfo.do"; // ��¼
	 private Map<String, String> cookie;
//	 private String path = Login.class.getResource("/").getPath().replaceAll("%20", " ") + "safecode.png";//�洢��ά��ͼƬ·��
	 private String path = "D:\\queryCnoic\\" + "safecode.png";//�洢��ά��ͼƬ·��
	 /**
     * ������֤��
     * ����Cookie
     * @throws IOException
     */
    public void getSafeCode() throws IOException {
    	 //1.��ȡcookie
        Response responseRoot = Jsoup.connect(url_root).ignoreContentType(true).userAgent("Mozilla").method(Method.GET).execute();
        cookie = responseRoot.cookies();
    	//2.������֤��ͼƬ
    	long random = new Date().getTime();
    	String url_safecode_new = url_safecode + random;
        Connection con = Jsoup.connect(url_safecode_new);// ��ȡ����
        con.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// ����ģ�������
        Response response_SafeCode = con.ignoreContentType(true).method(Method.POST).cookies(cookie).execute();//�����cookie������֤��
        byte[] bytes = response_SafeCode.bodyAsBytes();
        Util.saveFile(path, bytes);
        System.out.println("������֤�뵽��" + path);
    }
    
    
    /**
     * ��¼�б���ϵͳ
     */
    public Map<String,String> initLogin() throws IOException {
    	//1. ��ȡ��֤��
    	getSafeCode();
       System.out.println("��������֤�룺");
    	Scanner scan = new Scanner(System.in);
        String code = scan.next();
        try {
        	//2. post�û� ģ���¼
            Map<String, String> data = new HashMap<String, String>();
            data.put("sysUserCode" , "HHBX0001");
            data.put("sysPassWord" , "AA123456789");
            data.put("random" , code);
            
            Connection con = Jsoup.connect(url_Login);// ��ȡ����
            con.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// ����ģ�������
            con.ignoreContentType(true).method(Method.POST).data(data).cookies(cookie).execute();
//            Response response = con.ignoreContentType(true).method(Method.POST).data(data).cookies(cookie).execute();
//            Document d1 = Jsoup.parse(response.body());
//            System.out.println("��¼�󷵻�document��Ϣ----" + d1.toString());
            for (String s : cookie.keySet()) {
                System.out.println("��¼ʱʹ�õ�cookie��Ϣ----"+ s + "-" + cookie.get(s));
            }
        } catch (IOException e) {

        }
        return cookie;
    }
    
    
    public static void main(String[] args) throws Exception {
    	Login login = new Login();
    	login.initLogin();
    }
}
