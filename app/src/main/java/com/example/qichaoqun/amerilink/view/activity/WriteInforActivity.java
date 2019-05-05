package com.example.qichaoqun.amerilink.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.base.BaseActivity;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.OrderCheck;
import com.example.qichaoqun.amerilink.bean.Passenger;
import com.example.qichaoqun.amerilink.presenter.WriterInforPresenter;
import com.example.qichaoqun.amerilink.view.iview.IWriterInforView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class WriteInforActivity extends BaseActivity<IWriterInforView, WriterInforPresenter> implements IWriterInforView, AdapterView.OnItemSelectedListener {

    @BindView(R.id.write_tool_bar)
    Toolbar writeToolBar;
    @BindView(R.id.room_name)
    TextView roomName;
    @BindView(R.id.room_address)
    TextView roomAddress;
    @BindView(R.id.room_type)
    TextView roomType;
    @BindView(R.id.room_desc)
    TextView roomDesc;
    @BindView(R.id.in_date)
    TextView inDate;
    @BindView(R.id.out_date)
    TextView outDate;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.parent_control)
    LinearLayout parentControl;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.operator_edit)
    EditText operatorEdit;
    @BindView(R.id.tuan_number)
    EditText tuanNumber;
    @BindView(R.id.special_request)
    EditText specialRequest;
    @BindView(R.id.mail_edit)
    EditText mailEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.should_pay_money)
    TextView shouldPayMoney;
    @BindView(R.id.radio_1)
    RadioButton radio1;
    @BindView(R.id.radio_2)
    RadioButton radio2;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.layout_one)
    LinearLayout layoutOne;
    @BindView(R.id.write_scroll_view)
    ScrollView writeScrollView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.sure_button)
    Button sureButton;
    private LinearLayout mParentLayout;

    private ChoiceInfor mChoiceInfor = null;
    private String mHotelId = null;
    private String mRoomKey = null;
    private String mAddress = null;
    private OrderCheck mOrderCheck;
    private Intent mIntent1;

    @Override
    protected int getLayoutId() {
        return R.layout.write_infor_layout;
    }

    @Override
    protected void initView() {
        //获取传递过来的信息
        Intent intent = getIntent();
        mChoiceInfor = (ChoiceInfor) intent.getExtras().getSerializable("choice");
        mHotelId = intent.getStringExtra("hotel_id");
        mRoomKey = intent.getStringExtra("room_key");
        mAddress = intent.getStringExtra("room_address");

        writeToolBar.setTitle(getResources().getString(R.string.write_infor));
        setSupportActionBar(writeToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        writeToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mParentLayout = (LinearLayout)findViewById(R.id.parent_control);
        setPassengerInfor();
        mIntent1 = new Intent();
    }

    @Override
    protected void loadData() {
        if (isPrepare) {
            presenter.loadingInfor(mHotelId,mRoomKey,mChoiceInfor);
        }
    }

    @Override
    protected WriterInforPresenter getPresenter() {
        return new WriterInforPresenter();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        Toast.makeText(this, getString(R.string.no_content), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showOrderComplete(OrderCheck order) {
        mOrderCheck = order;
        setCard(order);
    }

    /**
     * 设置初始化订单上的各个卡片上的值
     * @param card 含有各种信息的对象
     */
    public void setCard(OrderCheck card) {
        roomName.setText(card.getRoom_list().get(0).getRoom_name().split("<br />")[0]);
        roomAddress.setText(mAddress);
        roomType.setText(getResources().getString(R.string.room_type)+card.getRoom_list().get(0).getRoom_type());
        roomDesc.setText(card.getRoom_list().get(0).getRoom_desc());
        inDate.setText(card.getRoom_list().get(0).getRates_and_cancellation_policies().get(0).getRates().get(0).getCheck_in());
        outDate.setText(card.getRoom_list().get(0).getRates_and_cancellation_policies().get(0).getRates().get(0).getCheck_out());
        sum.setText("$"+card.getRoom_list().get(0).getRates_and_cancellation_policies().get(0).getTotal_amount_after_tax());
        shouldPayMoney.setText("$"+card.getRoom_list().get(0).getRates_and_cancellation_policies().get(0).getTotal_amount_after_tax());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showComplete(List<?> models) {}

    /**
     * 动态生成组件,包括成人 数量，性别，姓和名
     * 儿童的 数量，性别，姓和名
     * 房间的数量
     */
    private void setPassengerInfor() {
        //获取房间数两和每个房间的成人数量
        int roomNumber = mChoiceInfor.getRoomAmount();
        int preAdult = mChoiceInfor.getAdultSum() + mChoiceInfor.getChildrenPre();

        //加载布局文件
        View oneAdultLayout = View.inflate(this, R.layout.one_adult_infor, null);
        View oneRoomLayout = View.inflate(this, R.layout.one_room_layout, null);
        LinearLayout adultsLayout = oneRoomLayout.findViewById(R.id.adults_infor_layout);
        //根据成人的数量在布局中添加成人的信息
        for (int i = 0; i < roomNumber; i++) {
            int child = 0;
            int adult = 0;
            for (int j = 0; j < preAdult; j++) {
                //得到已有布局，将布局添加到父控件中
                adultsLayout.addView(oneAdultLayout, -1);
                View view = adultsLayout.getChildAt(j);
                TextView adultText = (TextView) view.findViewById(R.id.adult_number);
                if (j < mChoiceInfor.getAdultSum()) {
                    adultText.setText(getResources().getString(R.string.adult) + String.valueOf(++adult));
                } else {
                    adultText.setText(getResources().getString(R.string.kids) + String.valueOf(++child));
                }
                Spinner spinner = (Spinner) view.findViewById(R.id.sex_choice);
                spinner.setOnItemSelectedListener(this);

                oneAdultLayout = View.inflate(this, R.layout.one_adult_infor, null);
                oneAdultLayout.setPadding(0, 10, 0, 10);
            }
            //将生成的组件添加到最上层组件中
            mParentLayout.addView(oneRoomLayout, i);
            TextView roomNumberText = (TextView) oneRoomLayout.findViewById(R.id.room_number);
            roomNumberText.setText(getResources().getString(R.string.room_number) + String.valueOf(i + 1));

            oneRoomLayout = View.inflate(this, R.layout.one_room_layout, null);
            adultsLayout = oneRoomLayout.findViewById(R.id.adults_infor_layout);
        }
    }

    /**
     * 动态的获取动态生成的信息组件中的内容
     * 并且将其封装在相对应的对象中
     */
    private void getPassengerInfor(){
        //得到父控件
        for(int i = 0;i < mParentLayout.getChildCount();i++){
            //得到一个房型中的客人的信息
            LinearLayout oneRoomLayout = (LinearLayout)mParentLayout.getChildAt(i);
            LinearLayout oneRoomPassengers = (LinearLayout)oneRoomLayout.findViewById(R.id.adults_infor_layout);
            for(int j = 0;j < oneRoomPassengers.getChildCount();j++){
                //得到房间中第一个客人的信息
                View view = (LinearLayout)oneRoomPassengers.getChildAt(j);
                TextView type = (TextView) view.findViewById(R.id.adult_number);
                Spinner spinner = (Spinner) view.findViewById(R.id.sex_choice);
                EditText lastName = (EditText) view.findViewById(R.id.adult_ming);
                EditText firstName = (EditText) view.findViewById(R.id.adult_xing);

                Passenger passenger = new Passenger();
                Log.i("type", "getPassengerInfor: "+type.getText());
                Log.i("spinner", "getPassengerInfor: "+spinner.getSelectedItem());
                Log.i("lastName", "getPassengerInfor: "+lastName.getText());
                Log.i("firstName", "getPassengerInfor: "+firstName.getText());
            }
        }
    }

    @OnClick({R.id.cancel_button, R.id.sure_button})
    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                this.finish();
                break;
            case R.id.sure_button:
                getPassengerInfor();
                mIntent1.setClass(this,PayInforActivity.class);
                startActivity(mIntent1);
                break;
            default:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
