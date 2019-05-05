package com.example.qichaoqun.amerilink.view.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.base.BaseFragment;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.MapHotel;
import com.example.qichaoqun.amerilink.bean.MapInfor;
import com.example.qichaoqun.amerilink.bean.MoneyEvent;
import com.example.qichaoqun.amerilink.model.MapModel;
import com.example.qichaoqun.amerilink.presenter.MapModePresenter;
import com.example.qichaoqun.amerilink.view.activity.HotelActivity;
import com.example.qichaoqun.amerilink.view.iview.IMapModeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qichaoqun
 * @create 2018/10/10
 * @Describe
 */
public class MapModeFragment extends BaseFragment<IMapModeView, MapModePresenter> implements IMapModeView, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener {

    public static final int OVERRIDES = 20;
    @BindView(R.id.map_list_view)
    MapView mapListView;
    @BindView(R.id.map_progressbar)
    ProgressBar mapProgressbar;

    private BaiduMap mBaiduMap;
    private boolean isFirst = true;
    private ChoiceInfor mChoiceInfor;
    private MapHotel mMapHotel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.map_mode_fragment;
    }

    @Override
    protected void initView() {
        mChoiceInfor = (ChoiceInfor) getArguments().getSerializable("choice");
        mBaiduMap = mapListView.getMap();
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
    }

    @Override
    protected MapModePresenter getPresenter() {
        return new MapModePresenter();
    }

    @Override
    protected void loadingData() {
        if(isFirst && isPrepared){
            isFirst = false;
            presenter.loadingData(mChoiceInfor);
        }
    }

    @Override
    public void showLoading() {
        mapProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        Toast.makeText(getContext(),getString(R.string.no_content),Toast.LENGTH_SHORT).show();
        mapProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),getString(R.string.error),Toast.LENGTH_SHORT).show();
        mapProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showMapComplete(MapHotel map) {
        //数据加载完成设置覆盖物
        mapListView.setVisibility(View.VISIBLE);
        Log.i("加载到的酒店的名称为：：：", "showMapComplete: "+map.getHotels().get(0).getName());
        mMapHotel = map;
        setBaiduMap();
        mapProgressbar.setVisibility(View.GONE);
    }

    /**
     * 将酒店的信息加载在地图上
     */
    private void setBaiduMap() {
        if(mMapHotel.getHotels() != null && mMapHotel.getHotels().size() > 0){
            //得到相关的bean的集合
            List<MapInfor> list = new ArrayList<>();
            addList(list);
            addOver(list);
        }else{
            Toast.makeText(getContext(),getResources().getString(R.string.no_result),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 循环添加覆盖物
     * @param list
     */
    private void addList(List<MapInfor> list){
        for(int i = 0; i < OVERRIDES; i ++){
            list.add(new MapInfor(String.valueOf(mMapHotel.getHotels().get(i).getHotel_id()),Double.parseDouble(mMapHotel.getHotels().get(i).getLatitude())
                    ,Double.parseDouble(mMapHotel.getHotels().get(i).getLongitude())
                    ,mMapHotel.getHotels().get(i).getPic()
                    ,mMapHotel.getHotels().get(i).getName()));
        }
    }

    /**
     * 在地图上添加覆盖物
     * @param list 坐标的集合
     */
    private void addOver(List<MapInfor> list) {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for (MapInfor info : list) {
            // 经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.location_3);
            options = new MarkerOptions()
                    .position(latLng)
                    // 图标
                    .icon(bitmap)
                    .zIndex(12);
            marker = (Marker) mBaiduMap.addOverlay(options);

            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public void showComplete(List<?> models) { }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle extraInfo = marker.getExtraInfo();
        MapInfor info = (MapInfor) extraInfo.getSerializable("info");
        initInfoWindow(info,marker);
        return true;
    }

    /**
     * 点击覆盖物出现弹窗并显示相应的图片
     * @param info 覆盖物的信息对象
     * @param marker 覆盖物
     */
    private void initInfoWindow(final MapInfor info, Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.maker_layout, null);
        ImageView imageView = view.findViewById(R.id.map_image);
        TextView textView = view.findViewById(R.id.map_name);
        TextView textView1 = view.findViewById(R.id.daohang);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"导航被点击了",Toast.LENGTH_SHORT).show();
                //跳转到酒店详情页面
                Bundle bundle = new Bundle();
                bundle.putSerializable("choice",mChoiceInfor);
                Intent intent = new Intent(getContext(), HotelActivity.class);
                intent.putExtra("hotel_id",info.getHotel_id());
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
        Glide.with(getContext()).load(info.getImageUrl()).skipMemoryCache(false).placeholder(R.drawable.shibai).into(imageView);
        textView.setText(info.getHotelName());
        Log.i("酒店的名称为", "initInfoWindow: "+info.getHotelName());
        final LatLng latLng = marker.getPosition();
        //将地图上的经纬度转换成屏幕中实际的点
        Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
        //设置屏幕中点的Y轴坐标的偏移量
        p.y -= 47;
        //把修改后的屏幕的点有转换成地图上的经纬度对象
        LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

        InfoWindow infoWindow = new InfoWindow(view, ll, 10);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(infoWindow);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyEvent moneyEvent){

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapListView.onDestroy();
    }


}
