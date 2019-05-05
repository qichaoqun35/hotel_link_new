package com.example.qichaoqun.amerilink.model;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.MyLocation;
import com.example.qichaoqun.amerilink.model.imodel.ISearchMode;
import com.example.qichaoqun.amerilink.netwrok.GetNetApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qichaoqun
 * @date 2018/10/6
 */
public class SearchModel implements ISearchMode {

    @Override
    public void loadLocationData(double latitude, double longitude, final CallBack callBack) {
        GetNetApi.getNetApi().getMyLocation(latitude+","+longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(MyLocation value) {
                        callBack.onNext(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getTopImage(CallBack callBack) {
        List<String> list = new ArrayList<>();
        list.add("https://img.aichotels-content.com/public/hotels/1058/113134/supplier/LAXMVDT_DoubleTree_by_Hilton_Hotel_Monrovia_Pasadena_Area_H.jpg");
        list.add("https://img.aichotels-content.com/public/hotels/1058/113134/supplier/LAXMVDT_DoubleTree_by_Hilton_Hotel_Monrovia_Pasadena_Area_A.jpg");
        list.add("https://img.aichotels-content.com/public/hotels/1058/113134/supplier/LAXMVDT_DoubleTree_by_Hilton_Hotel_Monrovia_Pasadena_Area_B.jpg");
        list.add("https://img.aichotels-content.com/public/hotels/1058/113134/supplier/LAXMVDT_DoubleTree_by_Hilton_Hotel_Monrovia_Pasadena_Area_C.jpg");
        list.add("https://img.aichotels-content.com/public/hotels/1058/113134/supplier/LAXMVDT_DoubleTree_by_Hilton_Hotel_Monrovia_Pasadena_Area_D.jpg");
        callBack.onNext(list);
    }
}
