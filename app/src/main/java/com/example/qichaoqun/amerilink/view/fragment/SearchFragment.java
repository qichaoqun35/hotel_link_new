package com.example.qichaoqun.amerilink.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.base.BaseFragment;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.MyLocation;
import com.example.qichaoqun.amerilink.bean.PassengersInfor;
import com.example.qichaoqun.amerilink.netwrok.GlideImageLoader;
import com.example.qichaoqun.amerilink.presenter.SearchPresenter;
import com.example.qichaoqun.amerilink.utils.ActManger;
import com.example.qichaoqun.amerilink.utils.ToolsUtils;
import com.example.qichaoqun.amerilink.utils.Utils;
import com.example.qichaoqun.amerilink.view.activity.HotelListActivity;
import com.example.qichaoqun.amerilink.view.activity.PassengerActivity;
import com.example.qichaoqun.amerilink.view.iview.ISearchView;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class SearchFragment extends BaseFragment<ISearchView, SearchPresenter> implements ISearchView {

    private static final String TAG = "SearchFragment";
    //@BindView(R.id.search_banner)
    //Banner searchBanner;
    @BindView(R.id.top_search)
    TextView topSearch;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.city_text)
    TextView cityText;
    @BindView(R.id.location_image)
    LinearLayout locationImage;
    @BindView(R.id.layout_search_one)
    LinearLayout layoutSearchOne;
    @BindView(R.id.in_date)
    TextView inDate;
    @BindView(R.id.out_date)
    TextView outDate;
    @BindView(R.id.total_day)
    TextView totalDay;
    @BindView(R.id.time_choice_layout)
    LinearLayout timeChoiceLayout;
    @BindView(R.id.total_room)
    TextView totalRoom;
    @BindView(R.id.total_adult)
    TextView totalAdult;
    @BindView(R.id.total_child)
    TextView totalChild;
    @BindView(R.id.choice_passenger_layout)
    LinearLayout choicePassengerLayout;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.location)
    TextView location;
    Unbinder unbinder;
    /**
     * 标记是否为第一次加载数据，如果是就会加载数据
     * 如果不是就不会在重新进行数据的加载
     */
    private boolean isFirst = true;

    private String city = "Monrovia";
    private double latitude = 34.1400;
    private double longitude = -118.0152;
    private int mAllRommAmount = 1;
    private int mAllPassengerAmount = 1;
    private int mAdultAmount1 = 1;
    private int mYoungAmount1 = 0;
    private int mChildrenAmount1 = 0;
    private boolean mIsHaveEarly = false;
    private List<String> mList = null;

    private Utils mUtils = null;
    private Dialog mDateDialog = null;
    private Intent mIntent;
    private int i = 0;
    private ChoiceInfor mChoiceInfor;

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment_layout;
    }

    @Override
    protected void initView() {
        //设置城市的初始化值
        cityText.setText(city);
        //设置当前的年月日
        inDate.setText(ToolsUtils.getCurrentTime());
        outDate.setText(ToolsUtils.getAfterDayTime());
        //设置共有多少晚
        setTotalNight();
        //设置共有多少个房间多少个成人
        totalRoom.setText(String.valueOf(mAllRommAmount));
        //设置共有多少个成人
        totalAdult.setText(String.valueOf(mAllRommAmount * (mAdultAmount1 + mYoungAmount1)));
        //设置有多少个儿童
        totalChild.setText(String.valueOf(mChildrenAmount1));
        //初始化工具类
        mUtils = new Utils(getContext());
        //初始化图片
        Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "iconfont2.ttf");
        location.setTypeface(iconfont);
        //初始化搜索按钮点击事件
        searchHotel();
        //将当前activity加入Activity管理器进行管理
        ActManger actManger = ActManger.getAppManger();
        actManger.addActivity((Activity) getContext());
        mIntent = new Intent();
        mChoiceInfor = new ChoiceInfor();
    }

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void loadingData() {
        if (isFirst & isPrepared) {
            isFirst = false;
            //加载图片数据信息
            //presenter.getTopImage();
        }
    }


    @OnClick({R.id.top_search, R.id.city_text, R.id.location_image, R.id.in_date, R.id.out_date, R.id.choice_passenger_layout, R.id.search_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_search:
                break;
            case R.id.city_text:
                break;
            case R.id.location_image:
                //获取用户位置信息
                getMyLocation();
                break;
            case R.id.in_date:
                //获取选择的入住时间
                showDateDialog(DateUtil.getDateForString(ToolsUtils.getCurrentTime()), "in");
                break;
            case R.id.out_date:
                //获取选择的退房时间
                showDateDialog(DateUtil.getDateForString(ToolsUtils.getAfterDayTime()), "out");
                break;
            case R.id.choice_passenger_layout:
                mIntent.setClass(getContext(), PassengerActivity.class);
                startActivity(mIntent);
                break;
            default:
        }
    }

    /**
     * 点击搜索按钮实现酒店的搜索,防止按钮重复点击
     */
    private void searchHotel() {
        RxView.clicks(searchButton).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        mChoiceInfor.setCity(city);
                        mChoiceInfor.setCityLatitude(String.valueOf(latitude));
                        mChoiceInfor.setCityLongitude(String.valueOf(longitude));
                        mChoiceInfor.setInDate(inDate.getText()+"");
                        mChoiceInfor.setOutDate(outDate.getText()+"");
                        mChoiceInfor.setPassengerAmount(mAllPassengerAmount);
                        mChoiceInfor.setRoomAmount(mAllRommAmount);
                        mChoiceInfor.setAdultPre(mAdultAmount1);
                        mChoiceInfor.setYongPre(mYoungAmount1);
                        mChoiceInfor.setChildrenPre(mChildrenAmount1);
                        mChoiceInfor.setHaveEarly(mIsHaveEarly);
                        mChoiceInfor.setList(mList);
                        //跳转到酒店信息的页面
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("choice", mChoiceInfor);
                        mIntent.setClass(getContext(),HotelListActivity.class);
                        mIntent.putExtras(bundle);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPassengerEvent(PassengersInfor passengersInfor) {
        Log.i(TAG, "onPassengerEvent: "+"广播中的内容执行了。。。。。");
        mAllRommAmount = passengersInfor.getAllRommAmount();
        Log.i("event 中的房间的数量为：：", "onEventCity: " + passengersInfor.getAllRommAmount());
        mAllPassengerAmount = passengersInfor.getAllPassengerAmount();
        mAdultAmount1 = passengersInfor.getAdultAmount1();
        mYoungAmount1 = passengersInfor.getYoungAmount1();
        mChildrenAmount1 = passengersInfor.getChildrenAmount1();
        mIsHaveEarly = passengersInfor.getHaveEatly();
        mList = passengersInfor.getList();
        totalRoom.setText(String.valueOf(mAllRommAmount));
        totalAdult.setText(String.valueOf(mAllRommAmount * (mAdultAmount1 + mYoungAmount1)));
        totalChild.setText(String.valueOf(mAllRommAmount * mChildrenAmount1));
    }

    @Override
    public void getLocationSuccess(MyLocation myLocation) {
        Log.i(TAG, "getLocationSuccess: " + myLocation.getResult().getFormatted_address());
        //更新城市和经纬度信息
        latitude = myLocation.getResult().getLocation().getLat();
        longitude = myLocation.getResult().getLocation().getLng();
        cityText.setText(myLocation.getResult().getAddressComponent().getCity());
    }

    @Override
    public void getLocationError() {
        Toast.makeText(getContext(), getString(R.string.location_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTopImageSuccess(List<String> list) {
        //设置顶部轮播图
        //searchBanner.setImages(list).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    public void getTopImageError() {
        Toast.makeText(getContext(), getString(R.string.empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取用户当前的位置
     */
    public void getMyLocation() {
        Location location = mUtils.beginLocation();
        presenter.loadLocationData(location.getLatitude(), location.getLongitude());
    }

    private void showDateDialog(List<Integer> date, final String way) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getContext());
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                //此处将获取到的日期
                if ("in".equals(way)) {
                    inDate.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                            + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                } else {
                    outDate.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                            + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                }
                //设置共有几个晚上
                setTotalNight();
            }

            @Override
            public void onCancel() {
            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        //设置显示的最小的时间点，均为今天
        builder.setMinYear(DateUtil.getYear());
        builder.setMinMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMinDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        mDateDialog = builder.create();
        mDateDialog.show();
    }

    /**
     * 设置共有多少个晚上
     */
    private void setTotalNight() {
        String inTime[] = inDate.getText().toString().split("-");
        String outTime[] = outDate.getText().toString().split("-");
        int inData = ToolsUtils.getDataFormat(inTime);
        int outData = ToolsUtils.getDataFormat(outTime);
        if (outData > inData) {
            int nights = outData - inData;
            totalDay.setText(String.valueOf(nights));
        }
    }

}
