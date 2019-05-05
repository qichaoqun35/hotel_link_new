package com.example.qichaoqun.amerilink.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.adapter.LanguageAdapter;
import com.example.qichaoqun.amerilink.base.BaseActivity;
import com.example.qichaoqun.amerilink.presenter.LanguagePresenter;
import com.example.qichaoqun.amerilink.utils.ActManger;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;
import com.example.qichaoqun.amerilink.view.iview.ILanguageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/10/7
 */
public class LanguageActivity extends AppCompatActivity {
    @BindView(R.id.language_toolbar)
    Toolbar languageToolbar;
    @BindView(R.id.language_list_view)
    RecyclerView languageListView;

    private LinearLayoutManager mLinearLayoutManager = null;
    private LanguageAdapter mLanguageAdapter = null;
    private Utils mUtils = null;
    private List<String> mList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_layout);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    /**
     * 初始化视图和布局
     */
    private void initView() {
        //设置toolbar
        languageToolbar.setTitle(getString(R.string.language_setting));
        setSupportActionBar(languageToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        languageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //配置recyclerview
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        languageListView.setLayoutManager(mLinearLayoutManager);
        languageListView.setItemAnimator(new DefaultItemAnimator());
        mUtils = new Utils(this);
        mList = new ArrayList<>();
    }


    /**
     * 设置相关的基本的数据
     */
    private void loadData() {
        //添加基本语言的类型
        mList.add(Consts.CHINESE);
        mList.add(Consts.ENGLISH);
        mLanguageAdapter = new LanguageAdapter(this, mList);
        languageListView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setItemClickListener(new LanguageAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //显示语言更改提示框
                showLanguageDialog(position);
            }
        });
    }

    /**
     * 显示更换语言的dialog
     */
    private void showLanguageDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tip))
                .setMessage(getString(R.string.language_infor))
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //更换语言
                        setLanguage(mList.get(position));
                    }
                })
                .show();
    }

    /**
     * 设置语言，根据得到的语言来进行设置
     * @param language 语言类型
     */
    private void setLanguage(String language) {
        String currentLanguage = mUtils.getSetting(Consts.LANGUAGE);
        if(language.equals(currentLanguage)){
            Toast.makeText(this,getString(R.string.language_tip),Toast.LENGTH_SHORT).show();
        }else{
            if(Consts.CHINESE.equals(language)){
                mUtils.setSetting(Consts.LANGUAGE,Consts.CHINESE);
                setLanguage(Consts.CHINESE_CODE,true);
            }else if(Consts.ENGLISH.equals(language)){
                mUtils.setSetting(Consts.LANGUAGE,Consts.ENGLISH);
                setLanguage(Consts.ENGLISH_CODE,true);
            }
        }
    }

    /**
     * 设置本地语言
     */
    private void setLanguage(String language,boolean flag){
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,displayMetrics);
        //可以设置为finish最上层的Activity
        ActManger actManger = ActManger.getAppManger();
        actManger.finishActivity();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
