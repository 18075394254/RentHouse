package utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2019/7/17.
 */
//设置RecyclerView网格布局每个item之间的间距
public class SpaceItemDecorationUtils extends RecyclerView.ItemDecoration {
    //间距大小
    private int space;

    public SpaceItemDecorationUtils(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            //item是第一个的时候不设置间距
            outRect.left = 0;
        }else {
            outRect.left = space;
        }
    }
}
