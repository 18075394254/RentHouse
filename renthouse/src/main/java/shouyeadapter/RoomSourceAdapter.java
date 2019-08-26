package shouyeadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.RoomSouarceInfo;

/**
 * Created by user on 2019/8/2.
 */

public class RoomSourceAdapter extends RecyclerView.Adapter<RoomSourceAdapter.ViewHolder>{

    int type = 0;
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view, int position);
    }

    //声明一个私有变量
    private RoomSourceAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(RoomSourceAdapter.OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public RoomSourceAdapter(@NonNull List<RoomSouarceInfo> mDatas,int type) {
        this.mDatas = mDatas;
        this.type = type;

    }

    private List<RoomSouarceInfo> mDatas;
    

    public List<RoomSouarceInfo> getmDatas() {
        return mDatas;
    }

    @Override
    public RoomSourceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RoomSourceAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.room_onerecycler_item, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RoomSourceAdapter.ViewHolder holder, int position) {
        RoomSouarceInfo info = mDatas.get(position);
        Log.i("one = ","mDatas.get(position) = "+mDatas.get(position).toString());
        if (type == 0) {
            holder.ivInfo.setImageResource(R.mipmap.background);
        }else{
            holder.ivInfo.setImageResource(R.mipmap.mofang);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoomSourceAdapter.OnItemClickListener mListener;

        ImageView ivInfo;
        public ViewHolder(View itemView, RoomSourceAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            ivInfo = (ImageView)itemView.findViewById(R.id.room_recyclerImage);
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
