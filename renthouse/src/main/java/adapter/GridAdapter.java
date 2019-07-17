package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import application.MyApplication;

/**
 * Created by user on 2019/7/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.Holder> {
    private Context mContext;
    private String[] stringList;
    private int leftRight;
    private int topBottom;
    private int spanCount;

    public  GridAdapter(Context mContext, String[] list/*, int spanCount, int leftRight, int topBottom*/){
        this.mContext = mContext;
        stringList = list;
      /*  this.spanCount = spanCount;
        this.leftRight = leftRight;
        this.topBottom = topBottom;*/

    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerviedw, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.imageView.setImageResource(R.mipmap.log);
        holder.textView.setText(stringList[position]);

       /* //获取屏幕宽度
        int screenWidth = MyApplication.getWindowWidth();
        //获取单张图片宽度
        int itemImgWidth = (screenWidth - leftRight * (spanCount + 1)) / spanCount;
        //设置图片宽高
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.width = itemImgWidth;
        params.height = itemImgWidth;
        holder.imageView.setLayoutParams(params);*/

    }

    @Override
    public int getItemCount() {
        return stringList.length;
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.recyclerImage);
            textView = (TextView)itemView.findViewById(R.id.recyclerText);
        }
    }

}
