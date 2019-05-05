package com.example.qichaoqun.amerilink.presenter.ipresenter;

import com.example.qichaoqun.amerilink.bean.ChoiceInfor;

/**
 * @author qichaoqun
 * @date 2018/10/11
 */
public interface IWriterInforPresenter {
    /**
     * 加载用户选中的房型的相关信息
     * @param hotelId 房间id
     * @param roomKey 房间的特殊生成的key
     * @param choiceInfor 用户选择的条件
     */
    void loadingInfor(String hotelId, String roomKey, ChoiceInfor choiceInfor);
}
