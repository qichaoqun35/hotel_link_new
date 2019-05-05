package com.example.qichaoqun.amerilink.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.utils.ActManger;
import com.example.qichaoqun.amerilink.view.fragment.MeFragment;
import com.example.qichaoqun.amerilink.view.fragment.OrderFragment;
import com.example.qichaoqun.amerilink.view.fragment.SearchFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @create 2018/10/5
 * @Describe 这里是主页面，主要有三个功能模块，分别是搜索界面，订单界面还有我的设置页面
 * 这三个页面都是由fragment完成，最后实现fragment的动态添加用于显示不同的页面
 */
public class MainActivity extends AppCompatActivity {

    private static final int SEARCH_PAGER = 0;
    private static final int ORDER_PAGER = 1;
    private static final int ME_PAGER = 2;
    public static final int INTERVAL_TIME = 1000;

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    /**
     * 获取底部导航栏的监听事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ameri_search:
                    setFragment(SEARCH_PAGER);
                    return true;
                case R.id.ameri_order:
                    setFragment(ORDER_PAGER);
                    return true;
                case R.id.ameri_me:
                    setFragment(ME_PAGER);
                    return true;
                default:
            }
            return false;
        }
    };
    private SearchFragment mSearchFragment = null;
    private OrderFragment mOrderFragment = null;
    private MeFragment mMeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        //设置底部导航栏的监听事件
        bottomNavigation.setOnNavigationItemSelectedListener(mListener);
        //最先加载的页面是第一个
        setFragment(SEARCH_PAGER);
    }


    /**
     * 创建Fragment
     *
     * @param flag 标识符，根据不同的内容区创建不同的Fragment
     *             使用此方法完美解决，导航栏闪退的问题，和视频中fragment的使用问题
     *             推荐使用下面的这种方法去动态的创建fragment
     */
    public void setFragment(int flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //隐藏所有的fragment
        hiddenAllFragment(transaction);
        switch (flag) {
            case SEARCH_PAGER:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                    transaction.add(R.id.frame_layout, mSearchFragment);
                } else {
                    transaction.show(mSearchFragment);
                }
                break;
            case ORDER_PAGER:
                if (mOrderFragment == null) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.frame_layout, mOrderFragment);
                } else {
                    transaction.show(mOrderFragment);
                }
                break;
            case ME_PAGER:
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    transaction.add(R.id.frame_layout, mMeFragment);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏当前显示的fragment如果显示则会隐藏起来
     * @param transaction fragment管理器
     */
    private void hiddenAllFragment(FragmentTransaction transaction) {
        if (mSearchFragment != null) {
            transaction.hide(mSearchFragment);
        }
        if (mOrderFragment != null) {
            transaction.hide(mOrderFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    /**
     * 连续点击两下退出
     */
    long lastTime;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastTime > INTERVAL_TIME){
            lastTime = currentTime;
            Toast.makeText(MainActivity.this, getString(R.string.exit_tip),Toast.LENGTH_LONG).show();
        }else{
            finish();
        }
    }
}
