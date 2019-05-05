package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.model.ListModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.IListModePresenter;
import com.example.qichaoqun.amerilink.view.iview.IListModView;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/9
 */
public class ListModePresenter extends BasePresenter<IListModView> implements IListModePresenter{

    private ListModel mListModel = null;

    public ListModePresenter(){
        mListModel = new ListModel();
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void loadHotelList(ChoiceInfor choiceInfor) {
        mView.showLoading();
        mListModel.getHotelList(choiceInfor, new CallBack<HotelList>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
            }

            @Override
            public void onNext(HotelList hotelList) {
                mView.getHotelList(hotelList);
            }

            @Override
            public void onError() {
                mView.showError();
            }
        });
    }
}
