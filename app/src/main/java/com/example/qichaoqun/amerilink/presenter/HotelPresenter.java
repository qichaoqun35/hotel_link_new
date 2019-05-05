package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.model.HotelModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.IHotelPresenter;
import com.example.qichaoqun.amerilink.view.iview.IHotelView;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class HotelPresenter extends BasePresenter<IHotelView> implements IHotelPresenter {

    private HotelModel mHotelModel = null;
    public HotelPresenter(){
        mHotelModel = new HotelModel();
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void loadHotelData(String id, ChoiceInfor choiceInfor) {
        mView.showLoading();
        mHotelModel.loadHotelData(id, choiceInfor, new CallBack() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
            }

            @Override
            public void onNext(Object o) {
                //mView.showComplete(o);
                mView.showLoadComplete(o);
            }

            @Override
            public void onError() {
                mView.showError();
            }
        });
    }
}
