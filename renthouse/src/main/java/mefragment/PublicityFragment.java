package mefragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import bean.PublicityAdapterInfo;
import decoration.SpacesItemDecoration;
import meadapter.PublicityAdapter;

/**
 * Created by user on 2019/8/12.
 */

public class PublicityFragment extends Fragment {
    private View mView;
    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<PublicityAdapterInfo> publicityInfos;
    private LinearLayoutManager mLinearLayoutManager;
    private PublicityAdapter mPublicityAdapter;
    private int refreshTime = 0;
    private int times = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.complaint_publicity,container,false);
        mRecyclerView = mView.findViewById(R.id.complaint_publicity_RecyclerView);

        initRecyclerView();
        return  mView;
    }
    private void initRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        publicityInfos = new ArrayList<>();
        PublicityAdapterInfo info = null;
        for (int i = 0; i < 5; i++) {
            info = new PublicityAdapterInfo();
            // info.url = urls[i];
            publicityInfos.add(info);
        }

        //设置adapter的数据
        mPublicityAdapter = new PublicityAdapter(publicityInfos);
        mRecyclerView.setAdapter(mPublicityAdapter);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mPublicityAdapter.setListener(new PublicityAdapter.OnItemClickListener() {
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
                        publicityInfos.clear();
                        for(int i = 0; i < itemLimit ;i++){
                            publicityInfos.add(new PublicityAdapterInfo());
                        }
                        mPublicityAdapter.notifyDataSetChanged();
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
                                publicityInfos.add(new PublicityAdapterInfo());
                            }
                            if(mRecyclerView != null) {
                                mRecyclerView.loadMoreComplete();
                                mPublicityAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                publicityInfos.add(new PublicityAdapterInfo());
                            }
                            if(mRecyclerView != null) {
                                mRecyclerView.setNoMore(true);
                                mPublicityAdapter.notifyDataSetChanged();
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


