
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
import bean.FourAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerFourAdapter extends RecyclerView.Adapter<RecyclerFourAdapter.ViewHolder> {

    private List<FourAdapterInfo> mDatas;

    //定义一个接口
    public interface OnItemClickListener{
        void setOnitemClickListener(View view, int position);
    }

    //声明一个私有变量
    private OnItemClickListener mItemClickListener;

    //提供一个公共的方法 设置接口
    public void setListener(OnItemClickListener onItemClickListener){
        this.mItemClickListener = onItemClickListener;

    }

    public RecyclerFourAdapter( List<FourAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.four_recyclerview, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FourAdapterInfo info = mDatas.get(position);


        holder.tvInfoTitle.setText(info.title);
        holder.tvInfoPrice.setText(info.price);
        holder.tvInfoSize.setText(info.size);

          holder.ivInfo.setImageResource(R.mipmap.ic_launcher_round);
        //Glide.with(MyApplication.getContext()).load(info.url).into(holder.ivInfo);

    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;
        TextView tvInfoTitle,tvInfoPrice,tvInfoSize;
        ImageView ivInfo;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            tvInfoTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvInfoPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvInfoSize = (TextView) itemView.findViewById(R.id.tv_size);
            ivInfo = (ImageView)itemView.findViewById(R.id.iv_imageView);

            this.mListener = onItemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        mListener.setOnitemClickListener(view,getPosition());
                    }
                }
            });
        }
    }
}
