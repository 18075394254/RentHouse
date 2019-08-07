package shouyeadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.FiveAdapterInfo;

/**
 * Created by user on 2019/7/22.
 */

public class RecyclerFiveAdapter extends RecyclerView.Adapter<RecyclerFiveAdapter.ViewHolder>{

    private List<FiveAdapterInfo> mDatas;
    private int type;
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
    public RecyclerFiveAdapter(@NonNull List<FiveAdapterInfo> mDatas,int type) {
        this.mDatas = mDatas;
        this.type = type;
    }

    public List<FiveAdapterInfo> getmDatas() {
        return mDatas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.five_recyclerview, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        TextView shouye_five_text_name,shouye_five_text_whole,shouye_five_text_oneroom,shouye_five_text_starNum,
                shouye_five_text_oneroom2,shouye_five_text_landlord,shouye_five_text_address,shouye_five_text_label1
                ,shouye_five_text_label2,shouye_five_text_label3,shouye_five_text_price,shouye_five_text_updatetime;
        ImageView ivInfo,shouye_five_iv_icon;
        private OnItemClickListener mListener;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            shouye_five_text_name = (TextView) itemView.findViewById(R.id.shouye_five_text_name);
            shouye_five_text_whole = (TextView) itemView.findViewById(R.id.shouye_five_text_whole);
            shouye_five_text_oneroom = (TextView) itemView.findViewById(R.id.shouye_five_text_oneroom);
            shouye_five_text_starNum = (TextView) itemView.findViewById(R.id.shouye_five_text_starNum);
            shouye_five_text_oneroom2 = (TextView) itemView.findViewById(R.id.shouye_five_text_oneroom2);
            shouye_five_text_landlord = (TextView) itemView.findViewById(R.id.shouye_five_text_landlord);
            shouye_five_text_address = (TextView) itemView.findViewById(R.id.shouye_five_text_address);
            shouye_five_text_label1 = (TextView) itemView.findViewById(R.id.shouye_five_text_label1);
            shouye_five_text_label2 = (TextView) itemView.findViewById(R.id.shouye_five_text_label2);
            shouye_five_text_label3 = (TextView) itemView.findViewById(R.id.shouye_five_text_label3);
            shouye_five_text_price = (TextView) itemView.findViewById(R.id.shouye_five_text_price);
            shouye_five_text_updatetime = (TextView) itemView.findViewById(R.id.shouye_five_text_updatetime);

            ivInfo = (ImageView)itemView.findViewById(R.id.iv_imageView);
            shouye_five_iv_icon = (ImageView)itemView.findViewById(R.id.shouye_five_iv_icon);

            if (type == 1){

            }else if (type == 2){
                shouye_five_text_price.setVisibility(View.VISIBLE);
            }else{
                shouye_five_text_name.setVisibility(View.VISIBLE);
                shouye_five_iv_icon.setVisibility(View.VISIBLE);
                shouye_five_text_updatetime.setVisibility(View.VISIBLE);
            }

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
