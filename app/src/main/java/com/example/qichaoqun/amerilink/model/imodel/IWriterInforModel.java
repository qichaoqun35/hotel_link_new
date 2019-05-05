package com.example.qichaoqun.amerilink.model.imodel;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.OrderCheck;

/**
 * @author qichaoqun
 * @date 2018/10/11
 */
public interface IWriterInforModel {
    /**
     * 加载用户选中的房型的相关信息
     * @param hotelId 房间id
     * @param roomKey 房间的特殊生成的key
     * @param choiceInfor 用户选择的条件
     * @param checkCallBack 回调的接口
     */
    void loadingInfor(String hotelId, String roomKey, ChoiceInfor choiceInfor, CallBack<OrderCheck> checkCallBack);
}
