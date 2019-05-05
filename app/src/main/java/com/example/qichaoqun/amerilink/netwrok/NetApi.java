package com.example.qichaoqun.amerilink.netwrok;

import com.example.qichaoqun.amerilink.bean.HotelInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.bean.MapHotel;
import com.example.qichaoqun.amerilink.bean.MyLocation;
import com.example.qichaoqun.amerilink.bean.OrderCheck;
import com.example.qichaoqun.amerilink.bean.RoomInfor;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public interface NetApi {

    /**
     * 根据位置获取相应的具体的位置的信息
     * @return 被观察者对象
     */
    @GET("/geocoder/v2/?output=json&pois=1&ak=P7LL2upV8iMw7ihHY8RawhXyDfYYNQGl&mcode=E8:23:40:59:89:88:CC:3A:2D:DC:2A:E9:62:FA:34:7B:4E:46:6B:CF;com.example.qichaoqun.amerilink")
    Observable<MyLocation> getMyLocation(@Query("location") String location);

    /**
     * 根据乘客信息获取相应的酒店的列表
     */
    @POST("/multiplesupplier/public/search/hotels")
    Observable<HotelList> getHotelList(@HeaderMap Map<String,String> map,@Body RequestBody requestBody);

    /**
     * 获取地图中的酒店
     * @param map 请求头
     * @param requestBody 请求体，携带的参数
     * @return 带有数据的对象
     */
    @POST("/content/public/multi_hotels")
    Observable<MapHotel> getMapHotel(@HeaderMap Map<String,String> map,@Body RequestBody requestBody);

    /**
     * 根据酒店的id获取酒店的详情信息
     * @param hotelId 酒店的id
     * @return 酒店的详情信息的bean
     */
    @GET("/content/public/single_hotel/{hotelId}")
    Observable<HotelInfor> getHotelInforById(@HeaderMap Map<String,String> map,@Path("hotelId") String hotelId);

    /**
     * 获取酒店的房型信息
     *
     * @param map         请求头
     * @param requestBody 请求体，携带的参数
     * @return 带有数据的bean对象
     */
    @POST("/multiplesupplier/public/search/room_availability")
    Observable<RoomInfor> getHotelRoomInfor(@HeaderMap Map<String, String> map, @Body RequestBody requestBody);

    /**
     * 加载用户的订单的信息
     * @param map 请求头
     * @param requestBody 请求体，携带的参数
     * @return 带有数据的bean对象
     */
    @POST("/multiplesupplier/public/booking/prebook")
    Observable<OrderCheck> getOrderInfor(@HeaderMap Map<String, String> map, @Body RequestBody requestBody);
}
