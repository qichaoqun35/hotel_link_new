package com.example.qichaoqun.amerilink.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.base.BaseFragment;
import com.example.qichaoqun.amerilink.presenter.OrderPresenter;
import com.example.qichaoqun.amerilink.view.iview.IOrderView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class OrderFragment extends BaseFragment<IOrderView, OrderPresenter> implements IOrderView {

    @BindView(R.id.my_tool_bar)
    Toolbar myToolBar;
    @BindView(R.id.my_pull_refresh)
    SwipeRefreshLayout myPullRefresh;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.order_fragment_layout;
    }

    @Override
    protected void initView() {
        myPullRefresh.setColorSchemeColors(getResources().getColor(R.color.main_color));
    }

    @Override
    protected OrderPresenter getPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected void loadingData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showComplete(List<?> models) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object object){

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
