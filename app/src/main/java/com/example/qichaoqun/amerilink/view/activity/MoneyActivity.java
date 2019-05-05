package com.example.qichaoqun.amerilink.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.adapter.LanguageAdapter;
import com.example.qichaoqun.amerilink.bean.Setting;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun 
 * @create 2018/10/8
 * @Describe 
 */
public class MoneyActivity extends AppCompatActivity {
    @BindView(R.id.money_toolbar)
    Toolbar moneyToolbar;
    @BindView(R.id.money_list_view)
    RecyclerView moneyListView;
    private LinearLayoutManager mLinearLayoutManager;
    private Utils mUtils = null;
    private ArrayList<String> mList = null;
    private LanguageAdapter mLanguageAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_layout);
        ButterKnife.bind(this);
        
        initView();
        loadData();
    }
    
    private void initView() {
        //设置toolbar
        moneyToolbar.setTitle(getString(R.string.money_setting));
        setSupportActionBar(moneyToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        moneyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //配置recyclerview
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moneyListView.setLayoutManager(mLinearLayoutManager);
        moneyListView.setItemAnimator(new DefaultItemAnimator());
        //初始化工具类
        mUtils = new Utils(this);
        mList = new ArrayList<>();
    }
    
    private void loadData() {
        mList.add("USD($)");
        mList.add("CAD(CA$)");
        mList.add("EUR");
        mList.add("JPY(￥)");
        mList.add("CNY(CN￥)");
        mList.add("BRL(R$)");
        mList.add("INR(Rs)");
        mLanguageAdapter = new LanguageAdapter(this, mList);
        moneyListView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setItemClickListener(new LanguageAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //显示语言更改提示框
                showMoneyDialog(position);
            }
        });
    }
    
    private void showMoneyDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tip))
                .setMessage(getString(R.string.money_infor))
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //保存货币类型的设置
                        setMoneyType(position);
                    }
                })
                .show();
    }

    /**
     * 对于货币类型进行设置
     * @param position 用户选中的货币类型的下标
     */
    private void setMoneyType(int position) {
        //判断该设置是否已经设置过
        String currentMoney = mUtils.getSetting(Consts.MONEY);
        if(currentMoney.equals(mList.get(position))){
            Toast.makeText(this,getString(R.string.money_tip),Toast.LENGTH_SHORT).show();
        }else{
            //保存设置
            mUtils.setSetting(Consts.MONEY,mList.get(position));
            //发送广播通知前台
            EventBus.getDefault().post(new Setting(Consts.MONEY,mList.get(position)));
            finish();
        }
    }
}
