package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.AdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerOneAdapter extends RecyclerView.Adapter<RecyclerOneAdapter.ViewHolder> {

    private List<AdapterInfo> mDatas;

    public RecyclerOneAdapter(@NonNull List<AdapterInfo> mDatas) {
        this.mDatas = mDatas;
        for (int i= 0; i < mDatas.size();i++){
            Log.i("one ",mDatas.get(i).toString());
        }

    }

    public List<AdapterInfo> getmDatas() {
        return mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_recyclerviedw, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

        TextView tvInfo;
        ImageView ivInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.recyclerText);
            ivInfo = (ImageView)itemView.findViewById(R.id.recyclerImage);
        }
    }
}
