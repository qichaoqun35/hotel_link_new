package com.example.qichaoqun.amerilink.model.imodel;

import com.example.qichaoqun.amerilink.base.CallBack;

/**
 * @author qichaoqun
 * @date 2018/10/6
 */
public interface ISearchMode {
    /**
     * 根据经纬度获取具体位置的信息
     * @param latitude 纬度
     * @param longitude 经度
     * @param callBack 回调接口
     */
    void loadLocationData(double latitude, double longitude, CallBack callBack);

    /**
     * 获取顶部的轮播图片信息
     * @param callBack 回调的接口
     */
    void getTopImage(CallBack callBack);
}
