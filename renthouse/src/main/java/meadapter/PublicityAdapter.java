package meadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.PublicityAdapterInfo;

/**
 * Created by user on 2019/7/25.
 */

public class PublicityAdapter extends RecyclerView.Adapter<PublicityAdapter.ViewHolder>{
    private List<PublicityAdapterInfo> mDatas;
    //定义一个接口
    public interface OnItemClickListener{
        void setOnitemClickListener(View view, int position);
    }

    //声明一个私有变量
    private PublicityAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法 设置接口
    public void setListener(PublicityAdapter.OnItemClickListener onItemClickListener){
        this.mItemClickListener = onItemClickListener;

    }
    public PublicityAdapter(@NonNull List<PublicityAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public PublicityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublicityAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.publicity_recyclerview, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(PublicityAdapter.ViewHolder holder, int position) {
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
        TextView tv_name,tv_time,tv_content,tv_type,tv_details,tv_result;

        private PublicityAdapter.OnItemClickListener mListener;
        public ViewHolder(View itemView, PublicityAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.publicity_tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.publicity_tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.publicity_tv_content);
            tv_type = (TextView) itemView.findViewById(R.id.publicity_tv_type);
            tv_details = (TextView) itemView.findViewById(R.id.publicity_tv_details);
            tv_result = (TextView) itemView.findViewById(R.id.publicity_tv_result);

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
