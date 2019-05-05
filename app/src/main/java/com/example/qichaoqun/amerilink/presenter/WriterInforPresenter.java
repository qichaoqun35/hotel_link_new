package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.OrderCheck;
import com.example.qichaoqun.amerilink.model.WriterInforModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.IWriterInforPresenter;
import com.example.qichaoqun.amerilink.view.iview.IWriterInforView;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/11
 */
public class WriterInforPresenter extends BasePresenter<IWriterInforView> implements IWriterInforPresenter{
    private WriterInforModel mWriterInforModel = null;
    public WriterInforPresenter(){
        mWriterInforModel = new WriterInforModel();
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void loadingInfor(String hotelId, String roomKey, ChoiceInfor choiceInfor) {
        mView.showLoading();
        mWriterInforModel.loadingInfor(hotelId, roomKey, choiceInfor, new CallBack<OrderCheck>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
            }

            @Override
            public void onNext(OrderCheck orderCheck) {
                mView.showOrderComplete(orderCheck);
            }

            @Override
            public void onError() {
                mView.showError();
            }
        });
    }
}
