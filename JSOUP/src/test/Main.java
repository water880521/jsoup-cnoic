package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	   public static void main(String[] args) throws Exception {
		    Login login = new Login();
		    Map<String,String> cookie = new HashMap<String,String>();
	    	System.out.println("请输入您要查询的维度，从车架号查询为1，从车牌号查询为2，从人查询为3，从手机查询为4：");
	     	Scanner scan = new Scanner(System.in);
	        String queryCondition = scan.next();
	      try {
	    	        //1.1 按车架号查询
			    if(queryCondition!=null && queryCondition.equals("1")) {
			    	List<String> cheJiaList = QueryCarCheJia.getCheJia(); 
			    	//1.2模拟登录
			    	cookie = login.initLogin();
			    	//1.3 读取excel/更新excel
			    	List<Map<String,Object>> cheJiaResult = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < cheJiaList.size(); i++) {
						int allCount = QueryPerson.query(cheJiaList.get(i),cookie);
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("row", i+1);
						map.put("allCount", allCount);
						cheJiaResult.add(map);
			      }
					QueryCarCheJia.UpdateCheJia(cheJiaResult);
					//1.4 模拟退出系统
					Logout logout = new Logout();
			    	logout.logout(cookie);
			    }
			    
			    
			        //2.1 按车牌号查询
			    if(queryCondition!=null && queryCondition.equals("1")) {
			    	List<String> chePaiList = QueryCarChePai.getChePai(); 
			    	//2.2模拟登录
			    	cookie = login.initLogin();
			    	//2.3 读取excel/更新excel
			    	List<Map<String,Object>> chePaiResult = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < chePaiList.size(); i++) {
						int allCount = QueryPerson.query(chePaiList.get(i),cookie);
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("row", i+1);
						map.put("allCount", allCount);
						chePaiResult.add(map);
			      }
					QueryCarChePai.UpdateChePai(chePaiResult);
					//2.4 模拟退出系统
					Logout logout = new Logout();
			    	logout.logout(cookie);
			    }
			    
			    
			    
				    //3.1 按人查询
			    if(queryCondition!=null && queryCondition.equals("3")) {
			    	List<String> idCardList = QueryPerson.getIdCard(); 
			    	//3.2模拟登录
			    	cookie = login.initLogin();
			    	//3.3 读取excel/更新excel
			    	List<Map<String,Object>> idCardResult = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < idCardList.size(); i++) {
						int allCount = QueryPerson.query(idCardList.get(i),cookie);
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("row", i+1);
						map.put("allCount", allCount);
						idCardResult.add(map);
			      }
					QueryPerson.UpdateQueryPerson(idCardResult);
					//3.4 模拟退出系统
					Logout logout = new Logout();
			    	logout.logout(cookie);
			    }
			    
			    
			    
			    
			        //4.1 按手机查询，获取要查询的身份证 读取excel
			    if(queryCondition!=null && queryCondition.equals("4")) {
			    	List<String> phoneList = QueryPhone.getPhone(); 
			    	//4.2模拟登录
			    	cookie = login.initLogin();
			    	//4.3 读取excel/更新excel
			    	List<Map<String,Object>> phoneResult = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < phoneList.size(); i++) {
						int allCount = QueryPhone.query(phoneList.get(i),cookie);
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("row", i+1);
						map.put("allCount", allCount);
						phoneResult.add(map);
			      }
					QueryPhone.UpdatePhone(phoneResult);
					//4.4. 模拟退出系统
					Logout logout = new Logout();
			    	logout.logout(cookie);
			    }
		} catch (Exception e) {
			Logout logout = new Logout();
			logout.logout(cookie);
			e.printStackTrace();
		}
 }
}
