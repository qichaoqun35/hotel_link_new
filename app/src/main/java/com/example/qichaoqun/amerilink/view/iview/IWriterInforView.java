package com.example.qichaoqun.amerilink.view.iview;

import com.example.qichaoqun.amerilink.base.BaseView;
import com.example.qichaoqun.amerilink.bean.OrderCheck;

/**
 * @author qichaoqun
 * @date 2018/10/11
 */
public interface IWriterInforView extends BaseView {
    /**
     * 订单的内容加载完成
     * @param orderCheck 带有相关的订单信息的bean
     */
    void showOrderComplete(OrderCheck orderCheck);
}
