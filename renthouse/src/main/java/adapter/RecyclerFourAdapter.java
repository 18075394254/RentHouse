
package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.renthouse.R;

import java.util.List;

import application.MyApplication;
import bean.AdapterInfo;
import bean.FourAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerFourAdapter extends RecyclerView.Adapter<RecyclerFourAdapter.ViewHolder> {

    private List<FourAdapterInfo> mDatas;

    public RecyclerFourAdapter(@NonNull List<FourAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }

    public List<FourAdapterInfo> getmDatas() {
        return mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.four_recyclerview, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FourAdapterInfo info = mDatas.get(position);


        holder.tvInfoTitle.setText(info.title);
        holder.tvInfoPrice.setText(info.price);
        holder.tvInfoSize.setText(info.size);

          holder.ivInfo.setImageResource(R.mipmap.ic_launcher_round);
        //Glide.with(MyApplication.getContext()).load(info.url).into(holder.ivInfo);

    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvInfoTitle,tvInfoPrice,tvInfoSize;
        ImageView ivInfo;
        public ViewHolder(View itemView) {
            super(itemView);

            tvInfoTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvInfoPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvInfoSize = (TextView) itemView.findViewById(R.id.tv_size);
            ivInfo = (ImageView)itemView.findViewById(R.id.iv_imageView);
        }
    }
}
