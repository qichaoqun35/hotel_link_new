package com.example.qichaoqun.amerilink.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.adapter.MyFragmentAdapter;
import com.example.qichaoqun.amerilink.assistview.ViewPagerCompat;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.MoneyEvent;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;
import com.example.qichaoqun.amerilink.view.fragment.ListModeFragment;
import com.example.qichaoqun.amerilink.view.fragment.MapModeFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @create 2018/10/9
 * @Describe
 */
public class HotelListActivity extends AppCompatActivity {

    private static final String TAG = "HotelListActivity";
    @BindView(R.id.hotel_list_bar)
    Toolbar hotelListBar;
    @BindView(R.id.table_layout)
    TabLayout tableLayout;
    @BindView(R.id.view_pager)
    ViewPagerCompat viewPager;

    private ChoiceInfor mChoiceInfor = null;
    private ListModeFragment mListModeFragment = null;
    private MapModeFragment mMapModeFragment = null;
    private ArrayList<Fragment> mFragmentList = null;
    private ArrayList<String> mTitleList = null;
    private MyFragmentAdapter mMyFragmentAdapter = null;
    private Bundle mBundle;
    private Utils mUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_list_layout);
        ButterKnife.bind(this);
        setToolBar();

        Intent intent = getIntent();
        mChoiceInfor = (ChoiceInfor) intent.getExtras().getSerializable("choice");

        //设置顶部导航栏
        setTablelayout();

        mUtils = new Utils(this);
    }

    private void setToolBar() {
        hotelListBar.setTitle(getResources().getString(R.string.hotel_list));
        setSupportActionBar(hotelListBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        hotelListBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.usd:
                        mUtils.setSetting(Consts.MONEY,Consts.USD);
                        break;
                    case R.id.cad:
                        break;
                    case R.id.eur:
                        break;
                    case R.id.jpy:
                        break;
                    case R.id.cny:
                        mUtils.setSetting(Consts.MONEY,Consts.CNY);
                        break;
                    case R.id.brl:
                        break;
                    case R.id.inr:
                        break;
                    default:
                        break;
                }
                EventBus.getDefault().post(new MoneyEvent(""));
                Toast.makeText(HotelListActivity.this,getString(R.string.setting_success),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        hotelListBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool, menu);
        return true;
    }

    /**
     * 设置顶部滑动栏
     */
    private void setTablelayout() {

        //实现fragment、viewpager、tablelayout的三者相结合
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();

        mBundle = new Bundle();
        mBundle.putSerializable("choice",mChoiceInfor);
        mListModeFragment = new ListModeFragment();
        mListModeFragment.setArguments(mBundle);
        mMapModeFragment = new MapModeFragment();
        mMapModeFragment.setArguments(mBundle);

        mFragmentList.add(mListModeFragment);
        mFragmentList.add(mMapModeFragment);

        mTitleList.add(getString(R.string.list_mode));
        mTitleList.add(getString(R.string.map_mode));

        mMyFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        viewPager.setAdapter(mMyFragmentAdapter);
        //实现viewpager和tablelayout的相结合
        tableLayout.setupWithViewPager(viewPager);
    }
}
