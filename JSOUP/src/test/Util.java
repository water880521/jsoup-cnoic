package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.json.JSONObject;

public class Util {
    /**
     * ���ֽ���ת�����ļ�
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
     * �ж��б��ŷ��������Ƿ���json��ʽ
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