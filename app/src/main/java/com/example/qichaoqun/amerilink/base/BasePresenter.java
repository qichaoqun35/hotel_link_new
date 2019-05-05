package com.example.qichaoqun.amerilink.base;

import android.content.Context;
import android.util.Log;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public abstract class BasePresenter<V> {

    /**
     * 得到 v 层视图接口的引用
     */
    protected V mView;
    protected Context mContext = null;
    protected Disposable mDisposable = null;


    /**
     * 提供 v 层与 p 层相绑定视图的接口方法
     */
    public void attachView(Context context,V view){
        mContext = context;
        mView = view;
    }

    /**
     * 提供视图和p层相解绑的接口
     */
    public void dettachView(){
        mView = null;
    }

    /**
     * 提供销毁的方式，当该presenter需要销毁时
     */
    abstract public void onDestroy();

    /**
     * 当页面退出时销毁被观察者以及被观察者所进行的活动
     */
    protected void unSubscribe(){
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
            Log.i("销毁方法调用了：：：", "unSubscribe: ");
        }
    }

}
