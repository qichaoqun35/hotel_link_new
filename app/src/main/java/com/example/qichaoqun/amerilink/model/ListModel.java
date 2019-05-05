package com.example.qichaoqun.amerilink.model;

import android.util.Log;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.model.imodel.IListModel;
import com.example.qichaoqun.amerilink.netwrok.GetNetApi;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.ToolsUtils;
import com.example.qichaoqun.amerilink.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class ListModel implements IListModel {



    @Override
    public void getHotelList(ChoiceInfor choiceInfor, final CallBack<HotelList> callBack) {
        String reqUrl = "/multiplesupplier/public/search/hotels";
        String baseUrl = "https://testopenapi.aichotels-service.com";
        RequestBody requestBody = RequestBody.create(Consts.JSON,getJson(choiceInfor));
        GetNetApi.getNetApi(baseUrl)
                .getHotelList(ToolsUtils.getHeaderMap("POST",reqUrl),requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotelList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(HotelList value) {
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

    /**
     * 获取需要提交到服务器中的内容
     * @param choiceInfor 酒店查询的条件
     * @return json字符串
     */
    public String getJson(ChoiceInfor choiceInfor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hotel_ids", "");
            jsonObject.put("latitude", choiceInfor.getCityLatitude());
            Log.i("json解析中总的城市的纬度", "getJson: "+choiceInfor.getCityLatitude());
            jsonObject.put("longitude", choiceInfor.getCityLongitude());
            jsonObject.put("radius", 20);
            jsonObject.put("check_in", choiceInfor.getInDate());
            Log.i("json解析中总的入住时间", "getJson: "+choiceInfor.getInDate());
            jsonObject.put("check_out", choiceInfor.getOutDate());
            jsonObject.put("room_number", choiceInfor.getRoomAmount());
            jsonObject.put("adult_number", choiceInfor.getAdultSum());
            Log.i("json解析中总的成人数", "getJson: "+choiceInfor.getAdultSum());
            jsonObject.put("kids_number", choiceInfor.getChildrenPre());
            jsonObject.put("light", 0);
            return jsonObject.toString().trim();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
