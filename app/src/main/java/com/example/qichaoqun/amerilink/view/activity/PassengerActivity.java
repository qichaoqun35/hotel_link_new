package com.example.qichaoqun.amerilink.view.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.bean.PassengersInfor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/9/8
 */
public class PassengerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.passenger_toolbar)
    Toolbar passengerToolbar;
    @BindView(R.id.room_reduce)
    TextView roomReduce;
    @BindView(R.id.room_amount)
    TextView roomAmount;
    @BindView(R.id.room_add)
    TextView roomAdd;
    @BindView(R.id.adult_reduce)
    TextView adultReduce;
    @BindView(R.id.adult_amount)
    TextView adultAmount;
    @BindView(R.id.adult_add)
    TextView adultAdd;
    @BindView(R.id.young_reduce)
    TextView youngReduce;
    @BindView(R.id.young_amount)
    TextView youngAmount;
    @BindView(R.id.yong_add)
    TextView yongAdd;
    @BindView(R.id.children_reduce)
    TextView childrenReduce;
    @BindView(R.id.children_amount)
    TextView childrenAmount;
    @BindView(R.id.children_add)
    TextView childrenAdd;
    @BindView(R.id.is_early)
    Switch isEarly;
    @BindView(R.id.layout_one)
    LinearLayout layoutOne;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.sure_button)
    Button sureButton;
    private Toolbar mToolbar;


    private int mAllRommAmount;
    private int mAdultAmount1;
    private int mYoungAmount1;
    private int mChildrenAmount1;
    private int mAllPassengerAmount;
    private boolean mIsHaveEarly;
    private Typeface mIconfont;

    private void findViews() {
        mAllPassengerAmount = 1;
        //获取有多少个房间
        mAllRommAmount = Integer.parseInt(String.valueOf(roomAmount.getText()));
        //获取有多少个成人
        mAdultAmount1 = Integer.parseInt(String.valueOf(adultAmount.getText()));
        //获取有多少个少年
        mYoungAmount1 = Integer.parseInt(String.valueOf(youngAmount.getText()));
        //获取有多少个儿童
        mChildrenAmount1 = Integer.parseInt(String.valueOf(childrenAmount.getText()));
        //设置图片的显示
        mIconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        roomReduce.setTypeface(mIconfont);
        roomAdd.setTypeface(mIconfont);
        adultReduce.setTypeface(mIconfont);
        adultAdd.setTypeface(mIconfont);
        youngReduce.setTypeface(mIconfont);
        yongAdd.setTypeface(mIconfont);
        childrenReduce.setTypeface(mIconfont);
        childrenAdd.setTypeface(mIconfont);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_layout);
        ButterKnife.bind(this);
        setToolBar();
        findViews();
    }


    private void setToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.passenger_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.passenger_infor));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 发送广播回传数据
     */
    private void sendPassengersEvent() {
        mAllPassengerAmount = mAllRommAmount * (mAdultAmount1 + mChildrenAmount1 + mYoungAmount1);
        PassengersInfor passengersInfor = new PassengersInfor(mAllRommAmount, mAllPassengerAmount, mAdultAmount1, mYoungAmount1, mChildrenAmount1, mIsHaveEarly);
        passengersInfor.setList(getChildrenAge());
        EventBus.getDefault().post(passengersInfor);
        finish();
    }

    private void childrenAdd() {
        mChildrenAmount1++;
        childrenAmount.setText(mChildrenAmount1 + "");
        setChildrenAge(mAllRommAmount, mChildrenAmount1);
    }

    /**
     * 动态的生成控件，用于记录儿童的年龄信息
     */
    private void setChildrenAge(int roomAmount, int childrenAmount) {
        int n = 0;
        View oneChildrenLayout = View.inflate(this, R.layout.one_children_layout, null);
        View oneRoomChildLayout = View.inflate(this, R.layout.one_room_child_layout, null);
        LinearLayout linearLayout = oneRoomChildLayout.findViewById(R.id.children_layout);
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parent_layout);
        if (parentLayout.getChildCount() > 0) {
            parentLayout.removeAllViews();
        }
        for (int i = 0; i < roomAmount; i++) {
            int m = 0;
            TextView roomCount = (TextView) oneRoomChildLayout.findViewById(R.id.room_count);
            roomCount.setText(getResources().getString(R.string.room_number) + String.valueOf(++n));
            for (int j = 0; j < childrenAmount; j++) {
                linearLayout.addView(oneChildrenLayout);
                TextView textView = (TextView) oneChildrenLayout.findViewById(R.id.children_count);
                textView.setText(getResources().getString(R.string.kids) + String.valueOf(++m));
                Spinner spinner = (Spinner) oneChildrenLayout.findViewById(R.id.children_age);
                spinner.setOnItemSelectedListener(this);
                oneChildrenLayout.setPadding(0, 10, 0, 10);
                oneChildrenLayout = View.inflate(this, R.layout.one_children_layout, null);
            }
            parentLayout.addView(oneRoomChildLayout);
            oneRoomChildLayout = View.inflate(this, R.layout.one_room_child_layout, null);
            linearLayout = oneRoomChildLayout.findViewById(R.id.children_layout);
        }
    }

    /**
     * 用于获取儿童动态组件的信息
     */
    private List<String> getChildrenAge() {
        List<String> list = null;
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parent_layout);
        if (parentLayout.getChildCount() > 0) {
            list = new ArrayList<>();
            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                LinearLayout oneRoom = (LinearLayout) parentLayout.getChildAt(i);
                LinearLayout childrenLayout = (LinearLayout) oneRoom.findViewById(R.id.children_layout);
                for (int j = 0; j < childrenLayout.getChildCount(); j++) {
                    LinearLayout oneChildren = (LinearLayout) childrenLayout.getChildAt(j);
                    Spinner spinner = (Spinner) oneChildren.findViewById(R.id.children_age);
                    list.add((String) spinner.getSelectedItem());
                    Log.i("选中的房间的儿童年龄是：：", "getChildrenAge: " + spinner.getSelectedItem());
                }
            }
            return list;
        }
        return null;
       /* LinearLayout children = (LinearLayout) findViewById(R.id.children_layout);
        List<String> list = null;
        if(children.getChildCount() > 0){
            list  = new ArrayList<>();
            for (int i = 0; i < children.getChildCount(); i++) {
                View view = children.getChildAt(i);
                Spinner spinner = (Spinner)view.findViewById(R.id.children_age);
                Log.i("得到的值是多少：：：", "getChildrenAge: "+spinner.getSelectedItem());
                list.add((String) spinner.getSelectedItem());
            }
            return list;
        }*/
    }

    private void childrenReduce() {
        if (mChildrenAmount1 != 0 && mAllPassengerAmount != 0) {
            mChildrenAmount1--;
            childrenAmount.setText(mChildrenAmount1 + "");
            setChildrenAge(mAllRommAmount, mChildrenAmount1);
        } else {
            Toast.makeText(this, "不能再减了哦。。。", Toast.LENGTH_SHORT).show();
        }
    }

    private void yongAdd() {
        mYoungAmount1++;
        youngAmount.setText(mYoungAmount1 + "");
    }

    private void yongReduce() {
        if (mYoungAmount1 != 0 && mAllPassengerAmount != 0) {
            mYoungAmount1--;
            youngAmount.setText(mYoungAmount1 + "");
        } else {
            Toast.makeText(this, "不能再减了哦。。。", Toast.LENGTH_SHORT).show();
        }
    }

    private void adultAdd() {
        mAdultAmount1++;
        adultAmount.setText(mAdultAmount1 + "");
    }

    private void adultReduce() {
        if (mAdultAmount1 != 0 && mAllPassengerAmount != 0) {
            mAdultAmount1--;
            adultAmount.setText(mAdultAmount1 + "");
        } else {
            Toast.makeText(this, "不能再减了哦。。。", Toast.LENGTH_SHORT).show();
        }
    }

    private void roomAdd() {
        mAllRommAmount++;
        roomAmount.setText(mAllRommAmount + "");
        roomAmount.setText(mAllRommAmount + "");
        setChildrenAge(mAllRommAmount, mChildrenAmount1);
    }

    private void roomReduce() {
        if (mAllRommAmount != 1) {
            mAllRommAmount--;
            roomAmount.setText(mAllRommAmount + "");
            roomAmount.setText(mAllRommAmount + "");
            setChildrenAge(mAllRommAmount, mChildrenAmount1);
        } else {
            Toast.makeText(this, "不能再减了哦。。。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick({R.id.room_reduce, R.id.room_add, R.id.adult_reduce, R.id.adult_add, R.id.young_reduce, R.id.yong_add, R.id.children_reduce, R.id.children_add, R.id.is_early, R.id.sure_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.room_reduce:
                //房间数的减少
                roomReduce();
                break;
            case R.id.room_add:
                //房间数的增加
                roomAdd();
                break;
            case R.id.adult_reduce:
                //成人数量的减少
                adultReduce();
                break;
            case R.id.adult_add:
                //成人数量的增加
                adultAdd();
                break;
            case R.id.young_reduce:
                //少年数量的减少
                yongReduce();
                break;
            case R.id.yong_add:
                //少年数量的增加
                yongAdd();
                break;
            case R.id.children_reduce:
                //儿童数量的减少
                childrenReduce();
                break;
            case R.id.children_add:
                //儿童数量的增加
                childrenAdd();
                break;
            case R.id.is_early:
                //是否含早
                if (isEarly.isChecked()) {
                    mIsHaveEarly = true;
                } else {
                    mIsHaveEarly = false;
                }
                break;
            case R.id.sure_button:
                sendPassengersEvent();
                break;
            default:
        }
    }
}
