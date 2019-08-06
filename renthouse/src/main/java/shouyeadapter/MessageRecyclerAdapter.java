package shouyeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import java.util.List;

import bean.FourAdapterInfo;
import bean.MessageAdapterInfo;

/**
 * Created by user on 2019/8/6.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {

    private List<MessageAdapterInfo> mDatas;

    //定义一个接口
    public interface OnItemClickListener{
        void setOnitemClickListener(View view, int position);
    }

    //声明一个私有变量
    private MessageRecyclerAdapter.OnItemClickListener mItemClickListener;

    //提供一个公共的方法 设置接口
    public void setListener(MessageRecyclerAdapter.OnItemClickListener onItemClickListener){
        this.mItemClickListener = onItemClickListener;

    }

    public MessageRecyclerAdapter( List<MessageAdapterInfo> mDatas) {
        this.mDatas = mDatas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.message_item, null),mItemClickListener);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MessageRecyclerAdapter.OnItemClickListener mListener;
        TextView tvInfoTitle,tvInfoContent,tvInfoDate;
        ImageView iv_icon;
        public ViewHolder(View itemView, MessageRecyclerAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tvInfoTitle = (TextView) itemView.findViewById(R.id.message_title);
            tvInfoContent = (TextView) itemView.findViewById(R.id.message_content);
            tvInfoDate = (TextView) itemView.findViewById(R.id.message_time);
            iv_icon = (ImageView)itemView.findViewById(R.id.message_image);

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
