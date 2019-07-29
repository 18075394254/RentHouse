package meadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.AdapterInfo;
import bean.MeAdapterInfoOne;
import bean.MeAdapterInfoTwo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class MeRecyclerTwoAdapter extends RecyclerView.Adapter<MeRecyclerTwoAdapter.ViewHolder> {

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


    private List<MeAdapterInfoTwo> mDatas;

    public MeRecyclerTwoAdapter(@NonNull List<MeAdapterInfoTwo> mDatas) {
        this.mDatas = mDatas;
        for (int i= 0; i < mDatas.size();i++){
            Log.i("one ",mDatas.get(i).toString());
        }

    }

    public List<MeAdapterInfoTwo> getmDatas() {
        return mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.me_recyclertwo, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeAdapterInfoTwo info = mDatas.get(position);
        Log.i("one = ","mDatas.get(position) = "+mDatas.get(position).toString());

        holder.tvInfo.setText(info.title);
        holder.ivInfoicon.setImageResource(info.icon);


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;
        TextView tvInfo;
        ImageView ivInfoicon,ivInfoGoto;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.title);
            ivInfoicon = (ImageView)itemView.findViewById(R.id.icon);
            ivInfoGoto = (ImageView)itemView.findViewById(R.id.gotomore);
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
}
