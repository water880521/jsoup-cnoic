package test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import net.sf.json.JSONObject;
/**
 * 使用Jsoup模拟登出
 *
 * 
 * **/
public class Logout {
	
    public void logout(Map<String,String> cookie) throws Exception {
        Connection con = Jsoup.connect("http://88.100.14.65/sinoiaaf/login/loginOut.do");
        con.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Response logOut = con.ignoreContentType(true).method(Method.POST).cookies(cookie).execute();
        // 打印退出成功后的信息
        System.out.println("打印退出成功后的信息------------------" + logOut.parse().text());
    }
    
    public static void main(String[] args) throws Exception {
        Map<String, String> cookie = new HashMap<>();    
        cookie.put("JSESSIONID" , "QXYmRUshInNSiWgcbfhJ63lfPf4QR-eGsL-d34PiPcxXT2OGEkA7!-1607029483");
    	Logout logout = new Logout();
        logout.logout(cookie);
    }
}