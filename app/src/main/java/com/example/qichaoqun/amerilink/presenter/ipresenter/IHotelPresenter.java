package com.example.qichaoqun.amerilink.presenter.ipresenter;

import com.example.qichaoqun.amerilink.bean.ChoiceInfor;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public interface IHotelPresenter {

    /**
     * 加载单个酒店的信息
     * @param id 酒店的id
    *  @param choiceInfor 用户选择的酒店的条件
     */
    void loadHotelData(String id, ChoiceInfor choiceInfor);
}
