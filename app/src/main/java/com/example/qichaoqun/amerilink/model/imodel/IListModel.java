package com.example.qichaoqun.amerilink.model.imodel;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public interface IListModel {
    /**
     * 获取酒店的价格列表
     * @param choiceInfor 酒店查询的条件
     * @param callBack 用于网络请求后的接口回调
     */
    void getHotelList(ChoiceInfor choiceInfor, CallBack<HotelList> callBack);
}
