package com.example.qichaoqun.amerilink.model;

import android.util.Log;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelInfor;
import com.example.qichaoqun.amerilink.bean.RoomInfor;
import com.example.qichaoqun.amerilink.model.imodel.IHotelModel;
import com.example.qichaoqun.amerilink.netwrok.GetNetApi;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
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
public class HotelModel implements IHotelModel {


    @Override
    public void loadHotelData(String hotelId, ChoiceInfor choiceInfor, final CallBack callBack) {
        //获取根据酒店id来得到的酒店的信息
        String baseUrl = "https://testopenapi.aichotels-service.com";
        String reqUrl = "/multiplesupplier/public/search/room_availability";
        Observable<HotelInfor> observableId = GetNetApi.getNetApi(baseUrl).getHotelInforById(ToolsUtils.getHeaderMap("GET","/content/public/single_hotel/"+hotelId),hotelId);
        RequestBody requestBody = RequestBody.create(Consts.JSON,setJson(hotelId,choiceInfor));
        Observable<RoomInfor> observableRoom = GetNetApi.getNetApi(baseUrl).getHotelRoomInfor(ToolsUtils.getHeaderMap("POST",reqUrl),requestBody);
        //使用合并操作符，合并两次网络请求
        Observable.merge(observableId,observableRoom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(Object value) {
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

    private String setJson(String hotelId,ChoiceInfor choiceInfor){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hotel_id",hotelId);
            jsonObject.put("check_in",choiceInfor.getInDate());
            jsonObject.put("check_out",choiceInfor.getOutDate());
            jsonObject.put("room_number",choiceInfor.getRoomAmount());
            jsonObject.put("adult_number",choiceInfor.getAdultSum());
            jsonObject.put("kids_number",choiceInfor.getChildrenPre());
            return jsonObject.toString().trim();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
