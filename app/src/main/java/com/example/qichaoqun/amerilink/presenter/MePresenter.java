package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.base.BaseView;
import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.model.MeModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.IMePresenter;
import com.example.qichaoqun.amerilink.view.iview.IMeView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/6
 */
public class MePresenter extends BasePresenter<IMeView> implements IMePresenter{

    @Override
    public void onDestroy() {

    }
}
