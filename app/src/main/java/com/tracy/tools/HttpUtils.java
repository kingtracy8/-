package com.tracy.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by trcay on 2017/5/22.
 */
public class HttpUtils {

    /*
    *   用来访问网络获得json字符串并把其转换成String类型
    */

    public static String getJsonContent(String url_path) {

        try {
            URL url = new URL(url_path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            int code = conn.getResponseCode();
            if (code == 200) {
                return changeInputStream(conn.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    //把输入流转换成String类型并返回

    private static String changeInputStream(InputStream is) {

        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = is.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
