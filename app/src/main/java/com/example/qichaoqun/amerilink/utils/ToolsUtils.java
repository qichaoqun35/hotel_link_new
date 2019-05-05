package com.example.qichaoqun.amerilink.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author qichaoqun
 * @date 2018/8/24
 */
public class ToolsUtils {
    /**
     * 获取当前的格式化日期
     *
     * @return 当前日期
     */
    public static String getCurrentTime() {
        return DateHelper.getDateTime_I(new Date());
    }

    /**
     * 获取后一天的日期
     */
    public static String getAfterDayTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(DateHelper.getAddDays(1));
    }

    public static void getDate(String str) {
        String data = Environment.getExternalStorageState();
        File sdPath = Environment.getExternalStorageDirectory();
        File file = new File(sdPath, "hello.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getlocation(double latitude, double longitude, final MyLocation myLocation) {
        String path = "http://api.map.baidu.com/geocoder/v2/?location=" + latitude + "," + longitude + "&output=json&pois=1&ak=6q2d2Gswxw8tmV1c2RFZLnje4f9BQuvu";
        //得到okhttpclient
        OkHttpClient httpClient = new OkHttpClient();
        //得到设置请求体
        Request.Builder requestBuilder = new Request.Builder()
                .url(path);
        requestBuilder.method("GET", null);
        //得到request
        final Request request = requestBuilder.build();

        //取得call
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("地图位置信息加载失败", "onResponse: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //Log.i("获取的地图的位置是多爱好", "onResponse: "+response.body().string());
                    myLocation.getMyLocation(response.body().string());
                }
            }
        });
    }

    public interface MyLocation{
        void getMyLocation(String str);
    }

    /**
     * 将字符数组转为int类型的数字
     * @param str 字符数组
     * @return int类型的变量
     */
    public static Integer getDataFormat(String[] str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
        }
        return Integer.valueOf(sb.toString());
    }

    /**
     * 获取用于网络请求的请求头
     * @param method 请求方式
     * @param reqUrl 请求url
     * @return header
     */
    public static Map<String,String> getHeaderMap(String method, String reqUrl){
        String date = getUtc.getUTCTimeStr();
        String secret = "0c67e1@2e4?KD5da6a4#5a2H60sd3";
        String token = Encryption.generateSignature(method,reqUrl,date,secret);

        Map<String,String> map = new HashMap<>();
        map.put("APIClientKey","qinggongyeschool");
        map.put("Date",getUtc.getUTCTimeStr());
        map.put("APIClientToken",token);
        return map;
    }
}
