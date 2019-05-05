package com.example.qichaoqun.amerilink.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.qichaoqun.amerilink.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/10/11
 */
public class PayInforActivity extends AppCompatActivity {
    @BindView(R.id.pay_infor_bar)
    Toolbar payInforBar;
    @BindView(R.id.sure_pay)
    Button surePay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_infor_layout);
        ButterKnife.bind(this);
        setToolBar();
    }

    private void setToolBar() {
        payInforBar= (Toolbar) findViewById(R.id.pay_infor_bar);
        payInforBar.setTitle(getResources().getString(R.string.pay_infor));
        setSupportActionBar(payInforBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        payInforBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.sure_pay)
    public void clickListener(){
        Intent intent = new Intent(this,OrderSuccess.class);
        startActivity(intent);
    }
}
