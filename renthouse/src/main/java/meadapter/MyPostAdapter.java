package meadapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.renthouse.R;
import java.util.List;

import application.MyApplication;
import bean.MyPostAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    private int type;

    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view,int postion);
    }

    //声明一个私有变量
    private OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    private List<MyPostAdapterInfo> mDatas;

    public MyPostAdapter(@NonNull List<MyPostAdapterInfo> mDatas) {
        this.mDatas = mDatas;

    }

    public List<MyPostAdapterInfo> getmDatas() {
        return mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.mypost_recycler_item, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;

        ImageView ivInfo;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //ivInfo = (ImageView)itemView.findViewById(R.id.suggest_item_imageview);
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
