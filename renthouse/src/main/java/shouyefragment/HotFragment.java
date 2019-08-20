package shouyefragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import activity.BrandApartmentActivity;
import activity.DetailsActivity;
import application.MyApplication;
import bean.CollectionAdapterInfo;
import bean.FiveAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.RecyclerFiveAdapter;
import wishadapter.CollectionRecyclerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import activity.DetailsActivity;
import bean.CollectionAdapterInfo;
import bean.CollectionAdapterInfo;
import decoration.SpacesItemDecoration;
import listener.EndLessOnScrollListener;
import wishadapter.CollectionRecyclerAdapter;

/**
 * Created by admin on 2019/7/23.
 */

public class HotFragment extends Fragment {
    private View mView;
    private ArrayList<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;

    private int refreshTime = 0;
    private int times = 0;
    private LinearLayoutManager mLinearLayoutManager;

    private XRecyclerView mXRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hot_fragment,container,false);

        //初始化RecyclerView
        initRecyclerView();
        return mView;
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        {
            mXRecyclerView = mView.findViewById(R.id.hot_fragment_RecyclerView);
            mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

           /* mXRecyclerView
                    .getDefaultRefreshHeaderView()
                    .setRefreshTimeVisible(true);*/
            //添加数据
            fiveInfos = new ArrayList<>();
            FiveAdapterInfo info = null;
            for (int i = 0; i < 10; i++) {
                info = new FiveAdapterInfo();
                // info.url = urls[i];
                fiveInfos.add(info);
            }

            //设置adapter的数据
            mFiveAdapter = new RecyclerFiveAdapter(fiveInfos, MyApplication.type5);
            mXRecyclerView.setAdapter(mFiveAdapter);


            mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };

            mXRecyclerView.setLayoutManager(mLinearLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
            mXRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

            mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
                @Override
                public void setOnitemClickListener(View view, int position) {
                    // Toast.makeText(RoomSourceActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), DetailsActivity.class));
                }
            });

            // When the item number of the screen number is list.size-2,we call the onLoadMore
            //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
            final int itemLimit = 2;

            mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                fiveInfos.add(new FiveAdapterInfo());
                            }
                            if(mXRecyclerView != null) {
                                mXRecyclerView.loadMoreComplete();
                                mFiveAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }

                @Override
                public void onLoadMore() {
                    Log.e("aaaaa","call onLoadMore");
                    if(times < 2){
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                for(int i = 0; i < itemLimit ;i++){
                                    fiveInfos.add(new FiveAdapterInfo());
                                }
                                if(mXRecyclerView != null) {
                                    mXRecyclerView.loadMoreComplete();
                                    mFiveAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                for(int i = 0; i < itemLimit ;i++){
                                    fiveInfos.add(new FiveAdapterInfo());
                                }
                                if(mXRecyclerView != null) {
                                    mXRecyclerView.setNoMore(true);
                                    mFiveAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    }
                    times ++;
                }
            });

        }
    }


    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
