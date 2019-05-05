package com.example.qichaoqun.amerilink.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.adapter.HotelListAdapter;
import com.example.qichaoqun.amerilink.base.BaseFragment;
import com.example.qichaoqun.amerilink.bean.ChoiceInfor;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.bean.MoneyEvent;
import com.example.qichaoqun.amerilink.bean.Setting;
import com.example.qichaoqun.amerilink.presenter.ListModePresenter;
import com.example.qichaoqun.amerilink.view.activity.HotelActivity;
import com.example.qichaoqun.amerilink.view.iview.IListModView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @author qichaoqun 
 * @create 2018/10/10
 * @Describe 
 */
public class ListModeFragment extends BaseFragment<IListModView, ListModePresenter> implements IListModView {

    @BindView(R.id.hotel_list)
    RecyclerView hotelList;
    @BindView(R.id.no_content_text)
    TextView noContentText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ChoiceInfor mChoiceInfor;

    private boolean isFirst = true;
    private static final String TAG = "ListModeFragment";
    private Bundle mBundle = null;
    private Intent mIntent = null;
    private HotelListAdapter mHotelListAdapter = null;
    private HotelList mHotelList = null;

    @Override
    protected int getLayoutId() {
        return R.layout.list_mode_fragment;
    }

    @Override
    protected void initView() {
        //获取传递过来的值
        mChoiceInfor = (ChoiceInfor) getArguments().getSerializable("choice");
        //初始化recyclerview
        hotelList.setLayoutManager(new LinearLayoutManager(getContext()));
        //对recyclerview设置分割线
        hotelList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        hotelList.setItemAnimator(new DefaultItemAnimator());

        mBundle = new Bundle();
        mIntent = new Intent();
    }

    @Override
    protected ListModePresenter getPresenter() {
        return new ListModePresenter();
    }

    @Override
    protected void loadingData() {
        if(isFirst && isPrepared){
            isFirst = false;
            presenter.loadHotelList(mChoiceInfor);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        progressBar.setVisibility(View.GONE);
        noContentText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),getString(R.string.error),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete(List<?> models) {

    }

    @Override
    public void getHotelList(final HotelList list) {
        //设值recyclerview的adapter
        mHotelList = list;
        mHotelListAdapter = new HotelListAdapter(getContext(),list.getHotel_list());
        hotelList.setAdapter(mHotelListAdapter);
        mHotelListAdapter.setItemClickListener(new HotelListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mBundle.putSerializable("choice",mChoiceInfor);
                mIntent.setClass(getContext(),HotelActivity.class);
                mIntent.putExtras(mBundle);
                mIntent.putExtra("hotel_id", String.valueOf(list.getHotel_list().get(position).getHotel_id()));
                getContext().startActivity(mIntent);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MoneyEvent moneyEvent){
        if(mHotelList.getHotel_list() != null && mHotelList.getHotel_list().size() > 0){
            hotelList.setAdapter(mHotelListAdapter);
        }
    }
}
