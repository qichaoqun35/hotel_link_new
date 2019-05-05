package com.example.qichaoqun.amerilink.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    protected T presenter = null;
    protected boolean isPrepared = false;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getContext().getApplicationContext());
        //加载布局
        mView = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this, mView);
        //注册广播
        EventBus.getDefault().register(this);

        //得到 p 层的引用对象
        presenter = getPresenter();
        //将视图回调接口与 p 层相绑定
        presenter.attachView(getContext(), (V) this);
        //初始化控件
        initView();
        //标记视图初始化完成
        isPrepared = true;
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //加载数据
        loadingData();
    }

    /**
     * 获取布局的id值
     * @return int类型的布局的id值
     */
    abstract protected int getLayoutId();

    /**
     * 初始化fragment中的个中控件
     */
    abstract protected void initView();

    /**
     * 获取 p 层的引用实例
     * @return p 层的引用对象
     */
    abstract protected T getPresenter();

    /**
     * 采用懒加载进行加载数据
     */
    abstract protected void loadingData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dettachView();
        presenter.onDestroy();
    }
}
