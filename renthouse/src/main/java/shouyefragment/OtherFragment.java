package shouyefragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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

import activity.ListActivity;
import activity.OtherApartmentDetailsActivity;
import bean.TwoAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.ApartmentSourceRecyclerAdapter;

/**
 * Created by user on 2019/8/20.
 */

public class OtherFragment extends android.support.v4.app.Fragment {
        private View mView;
    private XRecyclerView mXRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ApartmentSourceRecyclerAdapter mAdapter;
    private int times = 0;
    List<TwoAdapterInfo> twoInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.otherfragment,container,false);
        //初始化RecyclerView
        initRecyclerView();

        return mView;
    }

    private void initRecyclerView() {
        mXRecyclerView= mView.findViewById(R.id.otherfragment_XRecyclerView);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        //添加数据
        twoInfos = new ArrayList<>();
        TwoAdapterInfo info = null;

        for (int i = 0; i < 5; i++) {
            info = new TwoAdapterInfo();
            twoInfos.add(info);
        }

        //设置adapter的数据
        mAdapter = new ApartmentSourceRecyclerAdapter(twoInfos);
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setLayoutManager(mLayoutManager);
        mXRecyclerView.setPullRefreshEnabled(false);
        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(10);
        int topBottom = 0;
        mXRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        for(int i = 0; i < 2 ;i++){
                            twoInfos.add(new TwoAdapterInfo());
                        }
                        if(mXRecyclerView != null) {
                            mXRecyclerView.loadMoreComplete();
                            mAdapter.notifyDataSetChanged();
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
                            for(int i = 0; i < 2 ;i++){
                                twoInfos.add(new TwoAdapterInfo());
                            }
                            if(mXRecyclerView != null) {
                                mXRecyclerView.loadMoreComplete();
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for(int i = 0; i < 2 ;i++){
                                twoInfos.add(new TwoAdapterInfo());
                            }
                            if(mXRecyclerView != null) {
                                mXRecyclerView.loadMoreComplete();
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });
        mAdapter.setItemClickListener(new ApartmentSourceRecyclerAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                //Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), OtherApartmentDetailsActivity.class));
            }
        });
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
