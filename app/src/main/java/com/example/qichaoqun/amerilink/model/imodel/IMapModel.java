package com.example.qichaoqun.amerilink.model.imodel;

import android.telecom.Call;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.MapHotel;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public interface IMapModel {
    /**
     * 获取地图上的酒店的信息
     * @param choiceInfor 用户选择的条件
     * @param callBack 回调接口
     */
    void loadingData(ChoiceInfor choiceInfor, CallBack<MapHotel> callBack);
}
