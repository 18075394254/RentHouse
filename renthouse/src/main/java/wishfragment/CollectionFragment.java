package wishfragment;

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

public class CollectionFragment extends Fragment {
    private View mView;
    private RecyclerView.LayoutManager mLayoutManager;

    private CollectionRecyclerAdapter mCollectionAdapter;
    List<CollectionAdapterInfo> collectionInfos;
    private XRecyclerView collectionRecyclerView;

    private int refreshTime = 0;
    private int times = 0;
    private LinearLayoutManager mLinearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.collection_fragment,container,false);

        //初始化RecyclerView
        initRecyclerView();
        return mView;
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        {

            collectionRecyclerView =  mView.findViewById(R.id.collectionRecyclerView);
            collectionRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            collectionRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            collectionRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

            collectionRecyclerView
                    .getDefaultRefreshHeaderView()
                    .setRefreshTimeVisible(true);
            //添加数据
            collectionInfos = new ArrayList<>();
            /*CollectionAdapterInfo info = null;
            for (int i = 0; i < 2; i++) {
                info = new CollectionAdapterInfo();
                // info.url = urls[i];
                collectionInfos.add(info);
            }*/

            //设置adapter的数据
            mCollectionAdapter = new CollectionRecyclerAdapter(collectionInfos);
            collectionRecyclerView.setAdapter(mCollectionAdapter);

            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            collectionRecyclerView.setLayoutManager(mLinearLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
            collectionRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

            mCollectionAdapter.setListener(new CollectionRecyclerAdapter.OnItemClickListener() {
                @Override
                public void setOnitemClickListener(View view, int position) {
                   // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), DetailsActivity.class));
                }
            });

            final int itemLimit = 2;

            // When the item number of the screen number is list.size-2,we call the onLoadMore
            collectionRecyclerView.setLimitNumberToCallLoadMore(2);

            collectionRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    refreshTime ++;
                    times = 0;
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                           // collectionInfos.clear();
                            for(int i = 0; i < itemLimit ;i++){
                                collectionInfos.add(new CollectionAdapterInfo());
                            }
                            mCollectionAdapter.notifyDataSetChanged();
                            if(collectionRecyclerView != null)
                                collectionRecyclerView.refreshComplete();
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
                                    collectionInfos.add(new CollectionAdapterInfo());
                                }
                                if(collectionRecyclerView != null) {
                                    collectionRecyclerView.loadMoreComplete();
                                    mCollectionAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                for(int i = 0; i < itemLimit ;i++){
                                    collectionInfos.add(new CollectionAdapterInfo());
                                }
                                if(collectionRecyclerView != null) {
                                    collectionRecyclerView.setNoMore(true);
                                    mCollectionAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    }
                    times ++;
                }
            });


            collectionRecyclerView.refresh();
        }
    }


    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
