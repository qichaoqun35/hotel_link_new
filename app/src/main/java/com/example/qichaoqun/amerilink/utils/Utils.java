package com.example.qichaoqun.amerilink.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class Utils {

    private Context mContext = null;
    public Utils(Context context){
        mContext = context;
    }

    public Location beginLocation() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //获得位置服务
        String provider = judgeProvider(lm);
        //有位置提供器的情况
        if (provider != null) {
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return lm.getLastKnownLocation(provider);
        } else {
            //不存在位置提供器的情况
            Toast.makeText(mContext, "不存在位置提供器的情况", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            //网络定位
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            //GPS定位
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(mContext, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 获取系统的各项设置
     * @param setting 设置
     * @return 设置的内容
     */
    public String getSetting(String setting){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("settings",Context.MODE_PRIVATE);
        return sharedPreferences.getString(setting,null);
    }

    /**
     * 初始化系统的各种设置
     */
    public void setSettings(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language","简体中文");
        editor.putString("money","USD($)");
        editor.putString("danwei","KM");
        editor.commit();
    }

    public void setSetting(String setting,String content){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(setting,content);
        editor.commit();
    }


}
