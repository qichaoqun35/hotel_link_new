package com.example.qichaoqun.amerilink.presenter;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.base.BasePresenter;
import com.example.qichaoqun.amerilink.bean.MyLocation;
import com.example.qichaoqun.amerilink.model.SearchModel;
import com.example.qichaoqun.amerilink.presenter.ipresenter.ISearchPresenter;
import com.example.qichaoqun.amerilink.view.iview.ISearchView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class SearchPresenter extends BasePresenter<ISearchView> implements ISearchPresenter {

    private static final String TAG = "SearchPresenter";

    private SearchModel mSearchModel = null;
    public SearchPresenter(){
        mSearchModel = new SearchModel();
    }

    @Override
    public void loadLocationData(double latitude,double longitude) {
        //调用m层数据的加载
        mSearchModel.loadLocationData(latitude, longitude, new CallBack<MyLocation>() {
            @Override
            public void onNext(MyLocation location) {
                mView.getLocationSuccess(location);
            }

            @Override
            public void onError() {
                mView.getLocationError();
            }

            @Override
            public void onSubscribe(Disposable disposable) {
                mDisposable = disposable;
            }
        });
    }

    /**
     * 获取轮播图的图片的相关信息
     * 正常的话应该是通过网络进行加载，这里先这样写，因为没有请求的接口
     */
    @Override
    public void getTopImage() {
        mSearchModel.getTopImage(new CallBack<List<String>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(List<String> list) {
                mView.getTopImageSuccess(list);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }
}
