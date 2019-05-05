package com.example.qichaoqun.amerilink.presenter.ipresenter;

import com.example.qichaoqun.amerilink.bean.ChoiceInfor;

/**
 * @author qichaoqun
 * @date 2018/10/9
 */
public interface IListModePresenter {
    /**
     * 加载酒店列表信息
     * @param choiceInfor 酒店查询的条件
     */
    void loadHotelList(ChoiceInfor choiceInfor);
}
