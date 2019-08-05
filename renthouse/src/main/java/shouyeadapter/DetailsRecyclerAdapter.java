
package shouyeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.DetailsAdapterInfo;
import bean.DetailsAdapterInfo;
import bean.ThreeAdapterInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 下午1:33
 * 邮箱：mail@hezhilin.cc
 */

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder> {
    private List<DetailsAdapterInfo> mDatas;
    private int type = 0;

    public DetailsRecyclerAdapter( List<DetailsAdapterInfo> mDatas,int type) {
        this.mDatas = mDatas;
        this.type = type;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == 1) {
            return new ViewHolder(View.inflate(parent.getContext(), R.layout.details_onerecycler_item, null));
        }else if(type == 2){
            return new ViewHolder(View.inflate(parent.getContext(), R.layout.details_tworecycler_item, null));
        }else{
            return new ViewHolder(View.inflate(parent.getContext(), R.layout.details_threerecycler_item, null));
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailsAdapterInfo info = mDatas.get(position);
        if (type == 1){
            holder.tv_message1.setText(info.message1);
            holder.tv_message2.setText(info.message2);
        }else if(type == 2){
            holder.tv_message1.setText(info.message1);
        }else{
            holder.tv_message1.setText(info.message1);
            holder.tv_message2.setText(info.message2);
        }


    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_message1,tv_message2;

        public ViewHolder(View itemView) {
            super(itemView);
        if (type == 1){
            tv_message1 = (TextView) itemView.findViewById(R.id.details_item_tv_message1);
            tv_message2 = (TextView)itemView.findViewById(R.id.details_item_tv_message2);

        }else if(type ==2){
            tv_message1 = (TextView) itemView.findViewById(R.id.details_tv_label);
        }else{
            tv_message1 = (TextView) itemView.findViewById(R.id.details_tv1_info);
            tv_message2 = (TextView)itemView.findViewById(R.id.details_tv2_info);
        }


        }
    }
}
