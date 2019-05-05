package com.example.qichaoqun.amerilink.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.bean.HotelList;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @date 2018/10/10
 */
public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.MyViewHolder> {

    public static final String STAR_ONE = "1";
    public static final String STAR_TWO = "2";
    public static final String STAR_THREE = "3";
    public static final String STAR_FOUR = "4";
    public static final String STAR_FIVE = "5";
    private Context mContext = null;
    private List<HotelList.HotelListBean> mList = null;
    private MyItemClickListener mItemClickListener = null;

    public HotelListAdapter(Context context, List<HotelList.HotelListBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hotel_list_item, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        //加载酒店图片
        Glide.with(mContext)
                .load(mList.get(position).getThumbnail())
                .placeholder(R.drawable.load_image)
                .skipMemoryCache(false)
                .thumbnail(0.1f)
                .into(myViewHolder.hotelImage);
        myViewHolder.hotelName.setText(mList.get(position).getHotel_name());

        //设置酒店星级
        float statNo = mList.get(position).getStar();
        String starSo = String.valueOf(statNo).substring(0,1);
        Log.i("酒店的星级为：：：", "onBindViewHolder: "+starSo);
        myViewHolder.starsText.setText(starSo);
        setStarts(myViewHolder,starSo);

        //设置酒店地址
        if (mList.get(position).getAddress() == null) {
            myViewHolder.addressText.setText(mContext.getResources().getString(R.string.no_result));
        } else {
            myViewHolder.addressText.setText(mList.get(position).getAddress());
        }
        myViewHolder.distanceText.setText(String.valueOf(mList.get(position).getDistance()).substring(0, 4) + " km");
        //设置酒店的价格
        if (-1 == (int) mList.get(position).getNight_rate_before_tax()) {
            myViewHolder.moneyText.setText(mContext.getResources().getString(R.string.no_result));
        } else {
            //获取用户选择的货币类型，根据货币类型设置相应的价格
            myViewHolder.moneyText.setText(getHotelMoney(mList.get(position).getNight_rate_before_tax()).substring(0, 4));
        }

        //为条目设置监听动作
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hotel_image)
        ImageView hotelImage;
        @BindView(R.id.hotel_name)
        TextView hotelName;
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
        @BindView(R.id.stars_text)
        TextView starsText;
        @BindView(R.id.address_text)
        TextView addressText;
        @BindView(R.id.distance_text)
        TextView distanceText;
        @BindView(R.id.money_text)
        TextView moneyText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 设置酒店的星级
     * @param star 星级
     */
    private void setStarts(MyViewHolder myViewHolder,String star) {
        switch (star) {
            case STAR_ONE:
                myViewHolder.starOne.setVisibility(View.VISIBLE);
                break;
            case STAR_TWO:
                myViewHolder.starOne.setVisibility(View.VISIBLE);
                myViewHolder.starTwo.setVisibility(View.VISIBLE);
                break;
            case STAR_THREE:
                myViewHolder.starOne.setVisibility(View.VISIBLE);
                myViewHolder.starTwo.setVisibility(View.VISIBLE);
                myViewHolder.starThree.setVisibility(View.VISIBLE);
                break;
            case STAR_FOUR:
                myViewHolder.starOne.setVisibility(View.VISIBLE);
                myViewHolder.starTwo.setVisibility(View.VISIBLE);
                myViewHolder.starThree.setVisibility(View.VISIBLE);
                myViewHolder.starFour.setVisibility(View.VISIBLE);
                break;
            case STAR_FIVE:
                myViewHolder.starOne.setVisibility(View.VISIBLE);
                myViewHolder.starTwo.setVisibility(View.VISIBLE);
                myViewHolder.starThree.setVisibility(View.VISIBLE);
                myViewHolder.starFour.setVisibility(View.VISIBLE);
                myViewHolder.starFive.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 获取和设置货币的类型
     * @param moneyAmount 钱数
     * @return 多少钱
     */
    private String getHotelMoney(double moneyAmount) {
        //获取用户设置的货币类型
        Utils utils = new Utils(mContext);
        String money = utils.getSetting(Consts.MONEY);
        switch (money) {
            case "USD($)":
                return "$" + String.valueOf(moneyAmount);
            case "CAD(CA$)":
                break;
            case "EUR":
                return String.valueOf(moneyAmount);
            case "JPY(￥)":
                return String.valueOf(moneyAmount);
            case "CNY(CN￥)":
                return "￥" + String.valueOf(moneyAmount * 6.8234);
            case "BRL(R$)":
                return String.valueOf(moneyAmount);
            case "INR(Rs)":
                return String.valueOf(moneyAmount);
                default:
        }
        return null;
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
