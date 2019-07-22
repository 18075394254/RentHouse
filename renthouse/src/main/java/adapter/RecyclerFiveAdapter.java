package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.FiveAdapterInfo;
import bean.FourAdapterInfo;

/**
 * Created by user on 2019/7/22.
 */

public class RecyclerFiveAdapter extends RecyclerView.Adapter<RecyclerFiveAdapter.ViewHolder>{

    private List<FiveAdapterInfo> mDatas;

    public RecyclerFiveAdapter(@NonNull List<FiveAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }

    public List<FiveAdapterInfo> getmDatas() {
        return mDatas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.five_recyclerview, null));
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
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
        ImageView ivInfo;
        public ViewHolder(View itemView) {
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
        }
    }
}
