package com.example.qichaoqun.amerilink.presenter.ipresenter;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public interface ISearchPresenter {

    /**
     * 根据经纬度获取具体位置的信息
     * @param latitude 纬度
     * @param longitude 经度
     */
    void loadLocationData(double latitude,double longitude);

    /**
     * 获取顶部的轮播图片信息
     */
    void getTopImage();
}
