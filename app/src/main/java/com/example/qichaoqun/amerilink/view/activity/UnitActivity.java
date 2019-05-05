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
 * @date 2018/10/9
 */
public class UnitActivity extends AppCompatActivity {
    @BindView(R.id.unit_toolbar)
    Toolbar unitToolbar;
    @BindView(R.id.unit_list_view)
    RecyclerView unitListView;

    private Utils mUtils = null;
    private ArrayList<String> mList = null;
    private LinearLayoutManager mLinearLayoutManager;
    private LanguageAdapter mLanguageAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_layout);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void initView() {
        //设置toolbar
        unitToolbar.setTitle(getString(R.string.unit_setting));
        setSupportActionBar(unitToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        unitToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //配置recyclerview
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        unitListView.setLayoutManager(mLinearLayoutManager);
        unitListView.setItemAnimator(new DefaultItemAnimator());
        //初始化工具类
        mUtils = new Utils(this);
        mList = new ArrayList<>();
    }

    private void loadData() {
        mList.add("KM");
        mList.add("Mail");
        mLanguageAdapter = new LanguageAdapter(this, mList);
        unitListView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setItemClickListener(new LanguageAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //显示语言更改提示框
                showUnitDialog(position);
            }
        });
    }

    private void showUnitDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tip))
                .setMessage(getString(R.string.unit_infor))
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //保存货币类型的设置
                        setUnitType(position);
                    }
                })
                .show();
    }

    /**
     * 对于货币类型进行设置
     *
     * @param position 用户选中的货币类型的下标
     */
    private void setUnitType(int position) {
        //判断该设置是否已经设置过
        String currentMoney = mUtils.getSetting(Consts.DANWEI);
        if (currentMoney.equals(mList.get(position))) {
            Toast.makeText(this, getString(R.string.unit_tip), Toast.LENGTH_SHORT).show();
        } else {
            //保存设置
            mUtils.setSetting(Consts.DANWEI, mList.get(position));
            //发送广播通知前台
            EventBus.getDefault().post(new Setting(Consts.DANWEI, mList.get(position)));
            finish();
        }
    }
}
