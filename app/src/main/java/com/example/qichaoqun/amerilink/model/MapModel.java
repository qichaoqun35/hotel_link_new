package com.example.qichaoqun.amerilink.model;

import android.util.Log;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.bean.MapHotel;
import com.example.qichaoqun.amerilink.model.imodel.IMapModel;
import com.example.qichaoqun.amerilink.netwrok.GetNetApi;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class MapModel implements IMapModel {


    @Override
    public void loadingData(ChoiceInfor choiceInfor, final CallBack<MapHotel> callBack) {
        String baseUrl = "https://testopenapi.aichotels-service.com";
        String reqUrl = "/content/public/multi_hotels";
        RequestBody requestBody = RequestBody.create(Consts.JSON,setJson(choiceInfor));
        GetNetApi.getNetApi(baseUrl)
                .getMapHotel(ToolsUtils.getHeaderMap("POST",reqUrl),requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MapHotel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(MapHotel value) {
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


    public String setJson(ChoiceInfor choiceInfor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hotel_ids", "");
            jsonObject.put("latitude", Double.parseDouble(choiceInfor.getCityLatitude()));
            jsonObject.put("longitude", Double.parseDouble(choiceInfor.getCityLongitude()));
            jsonObject.put("radius", "20");
            Log.i("json字符串为：：", "setSignJson: "+jsonObject.toString());
            return jsonObject.toString().trim();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
