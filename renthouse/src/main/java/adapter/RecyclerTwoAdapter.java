package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.renthouse.R;

import java.util.List;

import application.MyApplication;
import bean.AdapterInfo;
import bean.TwoAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerTwoAdapter extends RecyclerView.Adapter<RecyclerTwoAdapter.ViewHolder> {

    private List<TwoAdapterInfo> mDatas;
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view,int position);
    }
    //定义一个私有变量
    private OnItemClickListener mItemClickListener;

    //提供一个共有的方法
    public void setItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    public RecyclerTwoAdapter( List<TwoAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerTwoAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.recyclertwo_item, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TwoAdapterInfo info = mDatas.get(position);


        holder.tvInfoTitle.setText(info.title);
        holder.tvInfoMiaoshu.setText(info.miaoshu);
        holder.ivInfo.setImageResource(R.mipmap.ic_launcher_round);
        //Glide.with(MyApplication.getContext()).load(info.url).into(holder.ivInfo);

    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;
        TextView tvInfoTitle,tvInfoMiaoshu;
        ImageView ivInfo;
        public ViewHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfoMiaoshu = (TextView) itemView.findViewById(R.id.tab_miaoshu);
            tvInfoTitle = (TextView) itemView.findViewById(R.id.tab_title);
            ivInfo = (ImageView)itemView.findViewById(R.id.tab_imageView);
            this.mListener = onItemClickListener;
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
}
