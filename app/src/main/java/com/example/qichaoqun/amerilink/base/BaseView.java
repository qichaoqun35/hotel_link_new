package com.example.qichaoqun.amerilink.base;

import java.util.List;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public interface BaseView {
    /**
     * 显示当前系统正在加载数据
     */
    void showLoading();

    /**
     * 显示加载到的数据为空
     */
    void showEmpty();

    /**
     * 显示加载数据出错
     */
    void showError();

    /**
     * 加载数据成功，并且成功返回数据
     * @param models 成功返回的数据对象
     */
    void showComplete(List<?> models);
}
