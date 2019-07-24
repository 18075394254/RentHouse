
package shouyeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.ThreeAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerThreeAdapter extends RecyclerView.Adapter<RecyclerThreeAdapter.ViewHolder> {

    private List<ThreeAdapterInfo> mDatas;

    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view,int position);
    }

    //声明一个私有变量
    private OnItemClickListener mOnItemClickListener;

    //提供一个公共的方法
    public void setListener(OnItemClickListener mOnItemClickListener){
            this.mOnItemClickListener = mOnItemClickListener;
    }

    public RecyclerThreeAdapter( List<ThreeAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_recyclerviedw, null),mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThreeAdapterInfo info = mDatas.get(position);


        holder.tvInfoTitle.setText(info.title);

         holder.ivInfo.setImageResource(R.mipmap.ic_launcher_round);
        //Glide.with(MyApplication.getContext()).load(info.url).into(holder.ivInfo);

    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;
        TextView tvInfoTitle;
        ImageView ivInfo;
        public ViewHolder(View itemView,OnItemClickListener itemClickListener) {
            super(itemView);

            tvInfoTitle = (TextView) itemView.findViewById(R.id.recyclerText);
            ivInfo = (ImageView)itemView.findViewById(R.id.recyclerImage);

            this.mListener = itemClickListener;
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
