package wishfragment;

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

import bean.CollectionAdapterInfo;
import bean.LookHouseAdapterInfo;
import decoration.SpacesItemDecoration;
import listener.EndLessOnScrollListener;
import wishadapter.CollectionRecyclerAdapter;
import wishadapter.LookHouseAdapter;

/**
 * Created by admin on 2019/7/23.
 */

public class LookHouseFragment extends Fragment {

    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<LookHouseAdapterInfo> lookHouseInfos;
    private LinearLayoutManager mLinearLayoutManager;
    private LookHouseAdapter mLookHouseAdapter;
    private int refreshTime = 0;
    private int times = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.lookhouse_fragment,container,false);
        mRecyclerView = mView.findViewById(R.id.lookHouseRecyclerView);

        initRecyclerView();
        return mView;
    }

    private void initRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        lookHouseInfos = new ArrayList<>();
        LookHouseAdapterInfo info = null;
        for (int i = 0; i < 5; i++) {
            info = new LookHouseAdapterInfo();
            // info.url = urls[i];
            lookHouseInfos.add(info);
        }

        //设置adapter的数据
        mLookHouseAdapter = new LookHouseAdapter(lookHouseInfos);
        mRecyclerView.setAdapter(mLookHouseAdapter);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mLookHouseAdapter.setListener(new LookHouseAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {
                Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

            }
        });
        final int itemLimit = 5;

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        mRecyclerView.setLimitNumberToCallLoadMore(2);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        lookHouseInfos.clear();
                        for(int i = 0; i < itemLimit ;i++){
                            lookHouseInfos.add(new LookHouseAdapterInfo());
                        }
                        mLookHouseAdapter.notifyDataSetChanged();
                        if(mRecyclerView != null)
                            mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                Log.e("aaaaa","call onLoadMore");
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                lookHouseInfos.add(new LookHouseAdapterInfo());
                            }
                            if(mRecyclerView != null) {
                                mRecyclerView.loadMoreComplete();
                                mLookHouseAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                lookHouseInfos.add(new LookHouseAdapterInfo());
                            }
                            if(mRecyclerView != null) {
                                mRecyclerView.setNoMore(true);
                                mLookHouseAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });


        mRecyclerView.refresh();

}

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}

