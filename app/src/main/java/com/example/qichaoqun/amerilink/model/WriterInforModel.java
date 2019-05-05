package com.example.qichaoqun.amerilink.model;

import android.util.Log;

import com.example.qichaoqun.amerilink.base.CallBack;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.OrderCheck;
import com.example.qichaoqun.amerilink.model.imodel.IWriterInforModel;
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
 * @date 2018/10/11
 */
public class WriterInforModel implements IWriterInforModel {

    @Override
    public void loadingInfor(String hotelId, String roomKey, ChoiceInfor choiceInfor, final CallBack<OrderCheck> checkCallBack) {
        String baseUrl = "https://testopenapi.aichotels-service.com";
        String reqUrl = "/multiplesupplier/public/booking/prebook";
        RequestBody requestBody = RequestBody.create(Consts.JSON,setJson(hotelId,roomKey,choiceInfor));
        GetNetApi.getNetApi(baseUrl)
                .getOrderInfor(ToolsUtils.getHeaderMap("POST",reqUrl),requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderCheck>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        checkCallBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(OrderCheck value) {
                        checkCallBack.onNext(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkCallBack.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 校验订单，发送数据进行订单的校验
     * @param mHotelId
     * @param mRoomKey
     * @param choiceInfor
     * @return json字符串
     */
    private String setJson(String mHotelId,String mRoomKey,ChoiceInfor choiceInfor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hotel_id",mHotelId);
            jsonObject.put("check_in",choiceInfor.getInDate());
            jsonObject.put("check_out",choiceInfor.getOutDate());
            jsonObject.put("room_number",choiceInfor.getRoomAmount());
            jsonObject.put("adult_number",choiceInfor.getAdultSum());
            jsonObject.put("kids_number",choiceInfor.getChildrenPre());
            jsonObject.put("room_key",mRoomKey);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("json设置数据失败", "setJson: "+"json设置数据失败");
        }
        return null;
    }
}
