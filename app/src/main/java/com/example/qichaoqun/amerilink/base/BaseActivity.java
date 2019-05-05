package com.example.qichaoqun.amerilink.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @date 2018/10/7
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T presenter = null;
    protected boolean isPrepare = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //获取p层引用
        presenter = getPresenter();
        //绑定v层接口视图
        presenter.attachView(this, (V) this);
        //初始化视图
        initView();
        isPrepare = true;
        //加载数据
        loadData();
    }

    /**
     * 获取布局的id
     * @return 布局的id
     */
    abstract protected int getLayoutId();

    /**
     * 初始化布局
     */
    abstract protected void initView();

    /**
     * 加载数据
     */
    abstract protected void loadData();

    /**
     * 获取 p 层引用
     * @return p层对象
     */
    abstract protected T getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.dettachView();
            presenter.onDestroy();
        }
    }
}
