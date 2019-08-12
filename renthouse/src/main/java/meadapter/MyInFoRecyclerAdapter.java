package meadapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.MyInfoAdapterInfo;

/**
 * Created by user on 2019/8/12.
 */

public class MyInFoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    private List<MyInfoAdapterInfo> mDatas;
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view, int postion);
    }

    //声明一个私有变量
    private OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public MyInFoRecyclerAdapter(@NonNull List<MyInfoAdapterInfo> mDatas) {
        this.mDatas = mDatas;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        if(ONE_ITEM == viewType){
            View v = mInflater.inflate(R.layout.myinfo_recycler_item,parent,false);
            holder = new OneViewHolder(v,mItemClickListener);
        }else{
            View v = mInflater.inflate(R.layout.myinfo_recycler_itemtwo,parent,false);
            holder = new TwoViewHolder(v,mItemClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OneViewHolder){
            ((OneViewHolder) holder).myinfo_item_tv_title.setText(mDatas.get(position).title);
        }else {
            if (position == 4){
               // 用****替换手机号码中间4位
                String mobile = mDatas.get(position).info;
                String maskNumber = mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length());
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_title.setText(mDatas.get(position).title);
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_info.setText(maskNumber);
                ((TwoViewHolder) holder).myinfo_itemtwo_iv_goback.setVisibility(View.INVISIBLE);
            }else if(position == 5){
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_title.setText(mDatas.get(position).title);
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_info.setText(mDatas.get(position).info);
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_info.setTextColor(Color.GREEN);
                ((TwoViewHolder) holder).myinfo_itemtwo_iv_goback.setVisibility(View.INVISIBLE);
            }else{
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_title.setText(mDatas.get(position).title);
                ((TwoViewHolder) holder).myinfo_itemtwo_tv_info.setText(mDatas.get(position).info);
            }

        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return ONE_ITEM;
        }else{
            return TWO_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


}

class OneViewHolder extends RecyclerView.ViewHolder{
    private MyInFoRecyclerAdapter.OnItemClickListener mListener;
    TextView myinfo_item_tv_title;
    ImageView myinfo_item_iv_icon,myinfo_item_iv_goback;
    public OneViewHolder(View itemView, MyInFoRecyclerAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        myinfo_item_tv_title = (TextView) itemView.findViewById(R.id.myinfo_item_tv_title);
        myinfo_item_iv_icon = itemView.findViewById(R.id.myinfo_item_iv_icon);
        myinfo_item_iv_goback = itemView.findViewById(R.id.myinfo_item_iv_goback);
        mListener = onItemClickListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.setOnItemClickListener(view,getPosition());
                }
            }
        });
    }
}
class TwoViewHolder extends RecyclerView.ViewHolder{
    private MyInFoRecyclerAdapter.OnItemClickListener mListener;
    TextView myinfo_itemtwo_tv_title,myinfo_itemtwo_tv_info;
    ImageView myinfo_itemtwo_iv_goback;
    public TwoViewHolder(View itemView, MyInFoRecyclerAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        myinfo_itemtwo_tv_title = (TextView) itemView.findViewById(R.id.myinfo_itemtwo_tv_title);
        myinfo_itemtwo_tv_info = (TextView) itemView.findViewById(R.id.myinfo_itemtwo_tv_info);
        myinfo_itemtwo_iv_goback = itemView.findViewById(R.id.myinfo_itemtwo_iv_goback);
        mListener = onItemClickListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.setOnItemClickListener(view,getPosition());
                }
            }
        });
    }
}
