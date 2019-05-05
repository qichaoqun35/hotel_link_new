package com.example.qichaoqun.amerilink.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author qichaoqun
 * @create 2018/10/5
 * @Describe 今天开始对该项目的重构，这是第一个启动页面
 * 主要是权限的申请和界面的跳转，主要申请了存储、位置、相机等权限
 * 当然还有其他的操作，比如获取系统的字体，获取应用的设置等等。
 * 这些功能的实现主要应用的是 Rxjava 框架和申请权限的框架 RxPermission
 */
public class LauncherActivity extends Activity {

    /**
     * 所有需要的权限类型
     */
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //动态获取权限
        getPermission();
    }

    /**
     * 申请完权限跳转到主页面
     */
    private void toMainActivity() {
        //start:事件序列的起始点，这里从0开始
        //count: 总的事件的数量，这里只需要发送一次，因此总的事件的数量是 1
        //initialDelay: 距离第一次返送间隔的时间
        //period: 间隔的时间（第一次发送除外）
        //time_unit:事件单位，这里使用的是秒
        Observable.intervalRange(0,1,2,3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    /**
     * 动态获取权限
     */
    public void getPermission() {
        final Utils utils = new Utils(this);
        RxPermissions rxPermissions = new RxPermissions(LauncherActivity.this);
        rxPermissions.request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if(granted){
                    //权限全部申请成功
                    //判断是否时第一次使用，如果是将初始化系统的各种设置
                    if(utils.getSetting(Consts.LANGUAGE) == null){
                        //初始化各种设置
                        utils.setSettings();
                    }
                    //设置语言
                    setLanguage(utils);
                    //跳到主页面去
                    toMainActivity();
                }else{
                    //权限申请不成功，弹窗提示退出
                    showDialog();
                }
            }
        });
    }

    /**
     * 设置系统的语言
     * @param utils 工具类
     */
    private void setLanguage(Utils utils) {
        //获取系统的语言设置
        String currentLanguage = utils.getSetting(Consts.LANGUAGE);
        //自动设置称用户设置过的语言
        if(Consts.CHINESE.equals(currentLanguage)){
            setLanguage(Consts.CHINESE_CODE);
        }else if(Consts.ENGLISH.equals(currentLanguage)){
            setLanguage(Consts.ENGLISH_CODE);
        }
    }

    /**
     * 弹出对话框提示，往设置中给与权限否则应用程序退出
     */
    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tip))
                .setMessage(getString(R.string.permission_tip))
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LauncherActivity.this.finish();
                    }
                })
                .show();
        //设置点击dialog外的屏幕，该dialog不会消失
        alertDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置语言
     * @param language 语言的代码编号
     */
    private void setLanguage(String language){
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,displayMetrics);
    }

}
