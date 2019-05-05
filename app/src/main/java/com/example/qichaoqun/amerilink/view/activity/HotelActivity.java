package com.example.qichaoqun.amerilink.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.adapter.OnItemClickListener;
import com.example.qichaoqun.amerilink.adapter.RoomAdapter;
import com.example.qichaoqun.amerilink.assistview.CustomLinearLayoutManager;
import com.example.qichaoqun.amerilink.base.BaseActivity;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelInfor;
import com.example.qichaoqun.amerilink.bean.RoomInfor;
import com.example.qichaoqun.amerilink.netwrok.GlideImageLoader;
import com.example.qichaoqun.amerilink.presenter.HotelPresenter;
import com.example.qichaoqun.amerilink.view.iview.IHotelView;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class HotelActivity extends BaseActivity<IHotelView, HotelPresenter> implements IHotelView {

    @BindView(R.id.hotel_infor_toolbar)
    Toolbar hotelInforToolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.hotel_infor_name)
    TextView hotelInforName;
    @BindView(R.id.hotel_infor_address)
    TextView hotelInforAddress;
    @BindView(R.id.hotel_infor_iphone)
    TextView hotelInforIphone;
    @BindView(R.id.hotel_infor_fix)
    TextView hotelInforFix;
    @BindView(R.id.star_one)
    ImageView starOne;
    @BindView(R.id.star_two)
    ImageView starTwo;
    @BindView(R.id.star_three)
    ImageView starThree;
    @BindView(R.id.star_four)
    ImageView starFour;
    @BindView(R.id.star_five)
    ImageView starFive;
    @BindView(R.id.hotel_infor_stars)
    TextView hotelInforStars;
    @BindView(R.id.hotel_infor_map)
    MapView hotelInforMap;
    @BindView(R.id.smoking_image)
    LinearLayout smokingImage;
    @BindView(R.id.food_image)
    LinearLayout foodImage;
    @BindView(R.id.hotel_infor_intro)
    TextView hotelInforIntro;
    @BindView(R.id.room_list)
    RecyclerView roomList;
    @BindView(R.id.hotel_infor_layout)
    ScrollView hotelInforLayout;
    @BindView(R.id.hotel_infor_progress)
    ProgressBar hotelInforProgress;

    private String mHotelId = null;
    private ChoiceInfor mChoiceInfor = null;
    private boolean isFirst = true;

    private static final int ONE_STAR = 1;
    private static final int TWO_STAR = 2;
    private static final int THREE_STAR = 3;
    private static final int FOUR_STAR = 4;
    private static final int ALL_STAR = 5;
    private LocationClient mLocationClient = null;
    private BaiduMap mBaiduMap = null;
    private HotelInfor mHotelInfor = null;
    private RoomInfor mRoomInfor = null;
    private Bundle mBundle;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.hotel_layout;
    }

    @Override
    protected void initView() {
        //获取上文传递过来的酒店的 id
        Intent intent1 = getIntent();
        //获取酒店的id
        mHotelId = intent1.getStringExtra("hotel_id");
        //获取用户选择的酒店的条件
        mChoiceInfor = (ChoiceInfor) intent1.getExtras().getSerializable("choice");
        setToolBar();

        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(this);
        customLinearLayoutManager.setScrollEnabled(false);
        roomList.setLayoutManager(customLinearLayoutManager);

        mBundle = new Bundle();
        mIntent = new Intent();
    }

    private void setToolBar() {
        hotelInforToolbar = (Toolbar) findViewById(R.id.hotel_infor_toolbar);
        hotelInforToolbar.setTitle(getResources().getString(R.string.hotel_infor));
        setSupportActionBar(hotelInforToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        hotelInforToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        if(isFirst && isPrepare){
            isFirst = false;
            presenter.loadHotelData(mHotelId,mChoiceInfor);
        }
    }

    @Override
    protected HotelPresenter getPresenter() {
        return new HotelPresenter();
    }

    @Override
    public void showLoading() {
        hotelInforProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        Toast.makeText(this,getString(R.string.empty),Toast.LENGTH_SHORT).show();
        hotelInforProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show();
        hotelInforProgress.setVisibility(View.GONE);
    }

    @Override
    public void showLoadComplete(Object object) {
        if(object instanceof HotelInfor){
            mHotelInfor = (HotelInfor) object;
            banner.setImages(mHotelInfor.getOtherimages()).setImageLoader(new GlideImageLoader()).start();
            setHotelInfor();
        }else{
            Log.i("方法执行记录", "showLoadComplete: "+"这个方法执行了：：：");
            mRoomInfor = (RoomInfor) object;
            //设置recyclerview的adapter
            RoomAdapter roomAdapter = new RoomAdapter(HotelActivity.this, mRoomInfor.getRoom_list());
            roomList.setAdapter(roomAdapter);
            //设置酒店的监听事件
            setRoomClickListener(roomAdapter);
            hotelInforProgress.setVisibility(View.GONE);
        }
    }

    private void setRoomClickListener(RoomAdapter roomAdapter) {
        roomAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onCancelClick(View view, int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(HotelActivity.this)
                        .setTitle(getResources().getString(R.string.room_cancel)).setMessage("取消政策")

                        .setPositiveButton(getResources().getString(R.string.sure),null)
                        .create();
                alertDialog.show();
            }

            @Override
            public void onBookingClick(View view, int position) {
                //房间的room_key
                mBundle.putSerializable("choice",mChoiceInfor);
                mIntent.setClass(HotelActivity.this,WriteInforActivity.class);
                mIntent.putExtra("hotel_id",mHotelId);
                mIntent.putExtra("room_key",mRoomInfor.getRoom_list().get(position).getRates_and_cancellation_policies().get(0).getRoom_key());
                mIntent.putExtra("room_address",mHotelInfor.getHotel_data().getAddress());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    /**
     * 设置酒店的各种信息
     */
    private void setHotelInfor() {
        hotelInforName.setText(mHotelInfor.getHotel_data().getName());
        hotelInforAddress.setText(mHotelInfor.getHotel_data().getAddress());
        hotelInforIphone.setText(mHotelInfor.getHotel_data().getPhone());
        hotelInforFix.setText(mHotelInfor.getHotel_data().getFax());
        //设置星级
        hotelInforStars.setText(String.valueOf(mHotelInfor.getHotel_data().getStar()) + getResources().getString(R.string.hotel_star));
        setStarts(mHotelInfor.getHotel_data().getStar());
        //设置酒店在地图上的位置
        setMap(mHotelInfor);
    }

    private void setMap(HotelInfor hotelInfor) {
        mLocationClient = new LocationClient(getApplicationContext());
        mBaiduMap = hotelInforMap.getMap();
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true,null));
        LatLng latLng = new LatLng(Double.parseDouble(hotelInfor.getHotel_data().getLatitude())
                ,Double.parseDouble(hotelInfor.getHotel_data().getLongitude()));
        //添加覆盖点
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.my_location);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap)
                .zIndex(12)
                .draggable(true);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    private void setStarts(int star) {
        switch (star) {
            case ONE_STAR:
                starOne.setVisibility(View.VISIBLE);
                break;
            case TWO_STAR:
                starOne.setVisibility(View.VISIBLE);
                starTwo.setVisibility(View.VISIBLE);
                break;
            case THREE_STAR:
                starOne.setVisibility(View.VISIBLE);
                starTwo.setVisibility(View.VISIBLE);
                starThree.setVisibility(View.VISIBLE);
                break;
            case FOUR_STAR:
                starOne.setVisibility(View.VISIBLE);
                starTwo.setVisibility(View.VISIBLE);
                starThree.setVisibility(View.VISIBLE);
                starFour.setVisibility(View.VISIBLE);
                break;
            case ALL_STAR:
                starOne.setVisibility(View.VISIBLE);
                starTwo.setVisibility(View.VISIBLE);
                starThree.setVisibility(View.VISIBLE);
                starFour.setVisibility(View.VISIBLE);
                starFive.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showComplete(List<?> models) { }

    @OnClick(R.id.hotel_infor_intro)
    public void onClick(){
        setDialog();
    }

    /**
     * 用来显示酒店信息的dialog
     */
    private void setDialog() {
        AlertDialog alertDialog = null;
        String intro = mHotelInfor.getHotel_data().getIntro();
        if(intro != null && intro != ""){
            intro = intro.replace("<p>","");
            intro = intro.replace("</p>","\n");
            intro = intro.replace("<b>","");
            intro = intro.replace("</b>","");
            intro = intro.replace("<br/>","\n");
            alertDialog = new AlertDialog.Builder(this)
                    .setMessage(intro)
                    .setPositiveButton(getResources().getString(R.string.sure),null)
                    .create();
        }else{
            alertDialog = new AlertDialog.Builder(this)
                    .setMessage(mHotelInfor.getHotel_data().getIntro())
                    .setPositiveButton(getResources().getString(R.string.no_result),null)
                    .create();
        }
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束轮播
        banner.stopAutoPlay();
        hotelInforMap.onDestroy();
    }
}
