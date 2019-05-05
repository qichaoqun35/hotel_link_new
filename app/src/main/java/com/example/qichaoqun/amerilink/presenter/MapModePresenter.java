package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.MapHotel;
import com.example.qichaoqun.amerilink.model.MapModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.IMapPresenter;
import com.example.qichaoqun.amerilink.view.iview.IMapModeView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class MapModePresenter extends BasePresenter<IMapModeView> implements IMapPresenter{

    private MapModel mMapModel = null;
    public MapModePresenter(){
        mMapModel = new MapModel();
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void loadingData(ChoiceInfor choiceInfor) {
        mView.showLoading();
        mMapModel.loadingData(choiceInfor, new CallBack<MapHotel>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
            }

            @Override
            public void onNext(MapHotel mapHotel) {
                mView.showMapComplete(mapHotel);
            }

            @Override
            public void onError() {
                mView.showError();
            }
        });
    }
}
