package com.example.qichaoqun.amerilink.model.imodel;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public interface IHotelModel {
    /**
     * 加载酒店详情的信息
     * @param hotelId 酒店的id
     * @param choiceInfor 用户选择的酒店信息
     * @param callBack 回调接口
     */
    void loadHotelData(String hotelId, ChoiceInfor choiceInfor, CallBack callBack);
}
