package com.example.qichaoqun.amerilink.view.iview;

import com.example.qichaoqun.amerilink.base.BaseView;
import com.example.qichaoqun.amerilink.bean.MyLocation;

import java.util.List;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public interface ISearchView {
    /**
     * 获取用户的具体位置
     * @param myLocation 带有位置信息的对象
     */
    void getLocationSuccess(MyLocation myLocation);

    /**
     * 获取位置信息失败
     */
    void getLocationError();

    /**
     * 获取顶部的图片
     * @param list 图片的集合
     */
    void getTopImageSuccess(List<String> list);

    /**
     * 获取图片信息失败
     */
    void getTopImageError();
}
