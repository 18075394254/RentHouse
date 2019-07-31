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

public class SearchRecyclerTwoAdapter  extends RecyclerView.Adapter<SearchRecyclerTwoAdapter.ViewHolder>{
    //定义一个接口
    public interface OnItemClickListener{
        void setOnItemClickListener(View view, int postion);
    }

    //声明一个私有变量
    private SearchRecyclerTwoAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法
    public void setItemClickListener(SearchRecyclerTwoAdapter.OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    private List<String> mDatas;

    public SearchRecyclerTwoAdapter(@NonNull List<String> mDatas) {
        this.mDatas = mDatas;


    }


    @Override
    public SearchRecyclerTwoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SearchRecyclerTwoAdapter.ViewHolder(View.inflate(parent.getContext(), R.layout.search_his_item, null),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerTwoAdapter.ViewHolder holder, int position) {

        holder.tvInfo.setText(mDatas.get(position));


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SearchRecyclerTwoAdapter.OnItemClickListener mListener;
        TextView tvInfo;
        ImageView ivInfo;
        public ViewHolder(View itemView, SearchRecyclerTwoAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.text_search_his);
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
