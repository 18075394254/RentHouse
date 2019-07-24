package wishfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import bean.CollectionAdapterInfo;
import decoration.SpacesItemDecoration;
import listener.EndLessOnScrollListener;
import wishadapter.CollectionRecyclerAdapter;

/**
 * Created by admin on 2019/7/23.
 */

public class CollectionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View mView;
    private RecyclerView.LayoutManager mLayoutManager;

    private CollectionRecyclerAdapter mCollectionAdapter;
    List<CollectionAdapterInfo> fiveInfos;
    private RecyclerView collectionRecyclerView;

    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.collection_fragment,container,false);

        //初始化RecyclerView
        initFiveRecyclerView();
        return mView;
    }

    //第五个RecyclerView
    private void initFiveRecyclerView() {
        {
            mRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.layout_swipe_refresh);
            collectionRecyclerView = (RecyclerView) mView.findViewById(R.id.collectionRecyclerView);

            //添加数据
            fiveInfos = new ArrayList<>();
            CollectionAdapterInfo info = null;
            for (int i = 0; i < 5; i++) {
                info = new CollectionAdapterInfo();
                // info.url = urls[i];
                fiveInfos.add(info);
            }

            //设置adapter的数据
            mCollectionAdapter = new CollectionRecyclerAdapter(fiveInfos);
            collectionRecyclerView.setAdapter(mCollectionAdapter);

            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            collectionRecyclerView.setLayoutManager(mLinearLayoutManager);
            //监听SwipeRefreshLayout.OnRefreshListener
            mRefreshLayout.setOnRefreshListener(this);

            /**
             * 监听addOnScrollListener这个方法，新建我们的EndLessOnScrollListener
             * 在onLoadMore方法中去完成上拉加载的操作
             * */
            collectionRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    loadMoreData();
                }
            });
            //这个是下拉刷新出现的那个圈圈要显示的颜色
            mRefreshLayout.setColorSchemeResources(
                    R.color.colorRed,
                    R.color.colorYellow,
                    R.color.colorGreen
            );

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
            collectionRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
            collectionRecyclerView.addOnScrollListener(new EndLessOnScrollListener(new LinearLayoutManager(getActivity())) {
                @Override
                public void onLoadMore(int currentPage) {
                    loadMoreData();
                }
            });

            mCollectionAdapter.setListener(new CollectionRecyclerAdapter.OnItemClickListener() {
                @Override
                public void setOnitemClickListener(View view, int position) {
                    Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    //每次上拉加载的时候，给RecyclerView的后面添加了5条数据数据
    private void loadMoreData(){
        for (int i =0; i < 5; i++){
            fiveInfos.add(new CollectionAdapterInfo());
            mCollectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        for (int i =0; i <2; i++){
            fiveInfos.add(new CollectionAdapterInfo());

        }
        mCollectionAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
