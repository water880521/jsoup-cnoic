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
	 private String url_safecode = "http://88.100.14.65/sinoiaaf/pages/login/RandomNumUtil.jsp?d=" ; // 验证码
	 private String url_Login = "http://88.100.14.65/sinoiaaf/checklogin/checkLoginInfo.do"; // 登录
	 private Map<String, String> cookie;
//	 private String path = Login.class.getResource("/").getPath().replaceAll("%20", " ") + "safecode.png";//存储二维码图片路径
	 private String path = "D:\\queryCnoic\\" + "safecode.png";//存储二维码图片路径
	 /**
     * 下载验证码
     * 保存Cookie
     * @throws IOException
     */
    public void getSafeCode() throws IOException {
    	 //1.获取cookie
        Response responseRoot = Jsoup.connect(url_root).ignoreContentType(true).userAgent("Mozilla").method(Method.GET).execute();
        cookie = responseRoot.cookies();
    	//2.保存验证码图片
    	long random = new Date().getTime();
    	String url_safecode_new = url_safecode + random;
        Connection con = Jsoup.connect(url_safecode_new);// 获取连接
        con.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Response response_SafeCode = con.ignoreContentType(true).method(Method.POST).cookies(cookie).execute();//必须带cookie请求验证码
        byte[] bytes = response_SafeCode.bodyAsBytes();
        Util.saveFile(path, bytes);
        System.out.println("保存验证码到：" + path);
    }
    
    
    /**
     * 登录中保信系统
     */
    public Map<String,String> initLogin() throws IOException {
    	//1. 获取验证码
    	getSafeCode();
       System.out.println("请输入验证码：");
    	Scanner scan = new Scanner(System.in);
        String code = scan.next();
        try {
        	//2. post用户 模拟登录
            Map<String, String> data = new HashMap<String, String>();
            data.put("sysUserCode" , "HHBX0001");
            data.put("sysPassWord" , "AA123456789");
            data.put("random" , code);
            
            Connection con = Jsoup.connect(url_Login);// 获取连接
            con.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
            con.ignoreContentType(true).method(Method.POST).data(data).cookies(cookie).execute();
//            Response response = con.ignoreContentType(true).method(Method.POST).data(data).cookies(cookie).execute();
//            Document d1 = Jsoup.parse(response.body());
//            System.out.println("登录后返回document信息----" + d1.toString());
            for (String s : cookie.keySet()) {
                System.out.println("登录时使用的cookie信息----"+ s + "-" + cookie.get(s));
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
