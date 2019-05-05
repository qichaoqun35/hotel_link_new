package com.example.qichaoqun.amerilink.base;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/6
 */
public interface CallBack<T> {

    /**
     * 绑定Dispose
     * @param disposable
     */
    void onSubscribe(Disposable disposable);

    /**
     * 加载成功后返回数据
     * @param t 数据
     */
    void onNext(T t);

    /**
     * 显示错误
     */
    void onError();

}
