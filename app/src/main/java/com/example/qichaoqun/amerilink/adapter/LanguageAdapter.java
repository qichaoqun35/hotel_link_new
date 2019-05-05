package com.example.qichaoqun.amerilink.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.qichaoqun.amerilink.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @date 2018/10/7
 */
public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    private Context mContext = null;
    private List<String> mList = null;
    private int index = -1;
    private MyItemClickListener mItemClickListener = null;

    public LanguageAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.text.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(index);
                }
                notifyDataSetChanged();
            }
        });
        if(index == position){
            holder.radio.setChecked(true);
        }else{
            holder.radio.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.radio)
        RadioButton radio;
        @BindView(R.id.text)
        TextView text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

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
