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

public class SearchRecyclerAdapter  extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>{
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view, int postion);
    }

    //声明一个私有变量
    private SearchRecyclerAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(SearchRecyclerAdapter.OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    private List<String> mDatas;

    public SearchRecyclerAdapter(@NonNull List<String> mDatas) {
        this.mDatas = mDatas;


    }


    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SearchRecyclerAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.searchact_item, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder holder, int position) {

        holder.tvInfo.setText(mDatas.get(position));


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SearchRecyclerAdapter.OnItemClickListener mListener;
        TextView tvInfo;
        ImageView ivInfo;
        public ViewHolder(View itemView, SearchRecyclerAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.his_searchname);
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
