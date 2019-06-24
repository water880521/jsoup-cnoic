package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


 /**
  * 读取按人查询表格
  * @author Administrator
  *
  */

public class QueryPersonOld {
    /**
     * 读取excel
     * @作者 
       @类说明
     */
	
    public static List<String> getIdCard() throws FileNotFoundException, IOException {
    	File file = new File("D:\\queryCnoic\\person.xlsx");
       List<String> cardNoList = new ArrayList<String>();
       FileInputStream is = new FileInputStream(file); // 文件流
	   Workbook book = null;
	try {
		book = WorkbookFactory.create(is);// 读取excel表格为workbook类
	} catch (EncryptedDocumentException e) {
		e.printStackTrace();
	} catch (InvalidFormatException e) {
		e.printStackTrace();
	} 
	   Sheet rs = book.getSheetAt(0);//获取第一个sheet
	   int rsRows = rs.getPhysicalNumberOfRows(); // 获取总行数
	   for (int i = 1; i < rsRows; i++) {//i=1表示，从第二行开始
		   Row row = rs.getRow(i);//获取当前行
			Cell cell1 = row.getCell(3);//获取第四列
			String cardNo = null;
			if (cell1 != null) {
				cell1.setCellType(cell1.CELL_TYPE_STRING);
				cardNo = cell1.getStringCellValue();
				cardNoList.add(cardNo);
			}
	   }
	    book.close(); 
		is.close();
    return cardNoList;
    }
    
    /**
     * 更新excel
     * @作者 
       @类说明
     */
    public static void UpdateQueryPerson (List<Integer> idCardResult) throws IOException {
        File file = new File("D:\\queryCnoic\\person.xlsx");
        FileInputStream is = new FileInputStream(file); // 文件流
 	   Workbook book = null;
 	try {
 		book = WorkbookFactory.create(is);// 读取excel表格为workbook类
 	} catch (EncryptedDocumentException e) {
 		e.printStackTrace();
 	} catch (InvalidFormatException e) {
 		e.printStackTrace();
 	} 
 	   Sheet rs = book.getSheetAt(0);//获取第一个sheet
 	   for (int i = 0; i < idCardResult.size(); i++) {
 		   int j = idCardResult.get(i).intValue();//需要更新的行
 		   Row row = rs.getRow(j);//获取需要更新的行
 		   Cell cell4 = row.getCell(4);//获取需要更新的行、第五列
 		   cell4.setCellValue(1);
 	   }
 	            // 修改excel表格
 				// 首先要创建一个原始Excel文件的输出流对象！
 				FileOutputStream excelFileOutPutStream = new FileOutputStream("D:\\queryCnoic\\result\\person.xlsx");
 				// 将最新的 Excel 文件写入到文件输出流中，更新文件信息！
 				book.write(excelFileOutPutStream);
 				 // 执行 flush 操作， 将缓存区内的信息更新到文件上
 				excelFileOutPutStream.flush();
 				// 使用后，及时关闭这个输出流对象， 好习惯，再强调一遍！
 				excelFileOutPutStream.close();
 	            book.close(); 
 	            is.close();
 	            excelFileOutPutStream.close();
     }
    
    /**
     * 按人查询
     * 
     * **/
    public static int query(String idCard , Map<String,String> cookie) throws Exception {
    	int allCount = 0;
    	String  checkPerson; 
    	//2.1. 模拟进行查询-调用中保信校验接口
    	  Connection con0 = Jsoup.connect("http://88.100.14.65/sinoiaaf/independent/checkIndependentPersonInfo.do");
    	  Response query0 = con0.ignoreContentType(true).method(Method.POST).data("credentialNo",idCard).cookies(cookie).execute();
    	  JSONObject jSONObject_check = JSONObject.fromObject(query0.parse().text());
    	  System.out.println("校验接口返回值---------"+jSONObject_check.getString("msg"));
    	  checkPerson = jSONObject_check.getString("msg");
    	  
try {
	if(checkPerson!=null && checkPerson.equals("00")) {
		return 1;
//		  Connection con1 = Jsoup.connect("http://88.100.14.65/sinoiaaf/independent/queryIndependentPerson.do");
//		  Response query1 = con1.ignoreContentType(true).method(Method.POST).data(
//			        "indepentDayTime","" , 
//					"sysCompanyType","04" , 
//					"credentialNo",idCard).cookies(cookie).execute();
//		  
//		  Connection con = Jsoup.connect("http://88.100.14.65/sinoiaaf/independent/queryPersonPage.do");
//	    Response query = con.ignoreContentType(true).method(Method.POST).data(
//					        "credentialNo",idCard , 
//							"name","" , 
//							"companyCode","HHBX" , 
//							"time","" , 
//							"sysCompanyType","04" , 
//							"compAreaCode","000000" , 
//							"pageNo","1" , 
//							"flag","03").cookies(cookie).execute();
//	    System.out.println("按人查询身份证---------"+idCard);
//	    JSONObject jSONObject_query = JSONObject.fromObject(query0.parse().text());
//	    allCount  = Integer.parseInt(jSONObject_query.getString("allCount"));
	}
	else {
		return 0;
		  /*Connection con = Jsoup.connect("http://88.100.14.65/sinoiaaf/independent/queryRiskPerson.do");
	       Response query;
			query = con.ignoreContentType(true).method(Method.POST).data(
					        "indepentDayTime","" , 
					        "sysCompanyType","04" , 
					        "credentialNo",idCard 
							).cookies(cookie).execute();*/
	}
} catch (Exception e) {
	e.printStackTrace();
	return 0;
}

    }
    
    public static void main(String[] args) throws Exception {
//    	query("37060219840331107X"); 
    }
}

    