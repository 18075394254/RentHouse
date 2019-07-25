package wishadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.CollectionAdapterInfo;
import bean.LookHouseAdapterInfo;

/**
 * Created by user on 2019/7/25.
 */

public class LookHouseAdapter extends RecyclerView.Adapter<LookHouseAdapter.ViewHolder>{
    private List<LookHouseAdapterInfo> mDatas;
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
    public LookHouseAdapter(@NonNull List<LookHouseAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public LookHouseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookHouseAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.lookhouse_recyclerview, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(LookHouseAdapter.ViewHolder holder, int position) {
        // FiveAdapterInfo info = mDatas.get(position);


      /*  holder.tvInfoTitle.setText(info.title);
        holder.tvInfoPrice.setText(info.price);
        holder.tvInfoSize.setText(info.size);

        holder.ivInfo.setImageResource(R.mipmap.ic_launcher_round);*/
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
        ImageView ivInfo;
        Button add_btn,chat_btn;
        private OnItemClickListener mListener;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv2 = (TextView) itemView.findViewById(R.id.tv_2);
            tv3 = (TextView) itemView.findViewById(R.id.tv_3);
            tv4 = (TextView) itemView.findViewById(R.id.tv_4);
            tv5 = (TextView) itemView.findViewById(R.id.tv_5);
            tv6 = (TextView) itemView.findViewById(R.id.tv_6);
            tv7 = (TextView) itemView.findViewById(R.id.tv_7);
            tv8 = (TextView) itemView.findViewById(R.id.tv_8);
            tv9 = (TextView) itemView.findViewById(R.id.tv_9);
            tv10 = (TextView) itemView.findViewById(R.id.tv_10);

            ivInfo = (ImageView)itemView.findViewById(R.id.iv_imageView);
            add_btn = (Button)itemView.findViewById(R.id.add_btn);
            chat_btn = (Button)itemView.findViewById(R.id.chat_btn);
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
