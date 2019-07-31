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

import bean.AdapterInfo;

/**
 * Created by user on 2019/7/31.
 */

public class ModelRecyclerViewAdapter extends RecyclerView.Adapter<ModelRecyclerViewAdapter.ViewHolder>{
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view, int postion);
    }

    //声明一个私有变量
    private ModelRecyclerViewAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(ModelRecyclerViewAdapter.OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    private List<AdapterInfo> mDatas;

    public ModelRecyclerViewAdapter(@NonNull List<AdapterInfo> mDatas) {
        this.mDatas = mDatas;
        for (int i= 0; i < mDatas.size();i++){
            Log.i("one ",mDatas.get(i).toString());
        }

    }

    public List<AdapterInfo> getmDatas() {
        return mDatas;
    }

    @Override
    public ModelRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ModelRecyclerViewAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.item_recyclerviedw, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ModelRecyclerViewAdapter.ViewHolder holder, int position) {
        AdapterInfo info = mDatas.get(position);
        Log.i("one = ","mDatas.get(position) = "+mDatas.get(position).toString());

        holder.tvInfo.setText(info.message);
        holder.ivInfo.setImageResource(info.img);

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ModelRecyclerViewAdapter.OnItemClickListener mListener;
        TextView tvInfo;
        ImageView ivInfo;
        public ViewHolder(View itemView, ModelRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.recyclerText);
            ivInfo = (ImageView)itemView.findViewById(R.id.recyclerImage);
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
