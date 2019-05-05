package com.example.qichaoqun.amerilink.view.iview;

import com.example.qichaoqun.amerilink.base.BaseView;
import com.example.qichaoqun.amerilink.bean.HotelList;

/**
 * @author qichaoqun
 * @date 2018/10/9
 */
public interface IListModView extends BaseView {

    /**
     * 获取酒店的列表
     * @param hotelList 带有节点信息的bean
     */
    void getHotelList(HotelList hotelList);
}
