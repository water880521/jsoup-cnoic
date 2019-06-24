package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.json.JSONObject;

public class Util {
    /**
     * 将字节流转换成文件
     * 
     * @param filename
     * @param data
     * @throws Exception
     */
    public static void saveFile(String filename, byte[] data) {

        if (data != null) {
            String filepath = filename;
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
       
    }
    /**
     * 判断中保信返回数据是否是json格式
     * 
     * @param filename
     * @param data
     * @throws Exception
     */
    public static boolean isJson(String content) {
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}