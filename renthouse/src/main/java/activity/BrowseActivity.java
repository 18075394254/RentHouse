package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.FiveAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.RecyclerFiveAdapter;

/**
 * Created by user on 2019/8/19.
 */

public class BrowseActivity extends AppCompatActivity{
    private XRecyclerView mXRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    List<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    private int times = 0;
    private ImageView backImage;
    private int refreshTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(BrowseActivity.this,R.color.tab_textColorSelect);
        setContentView(R.layout.activity_browse_and_collection);
        
        initRecyclerView();

        backImage = findViewById(R.id.browse_and_collection_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        mXRecyclerView = findViewById(R.id.browse_and_collection_recyclerView);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        fiveInfos = new ArrayList<>();
        FiveAdapterInfo info = null;
        for (int i = 0; i < 4; i++) {
            info = new FiveAdapterInfo();
            // info.url = urls[i];
            fiveInfos.add(info);
        }

        //设置adapter的数据
        mFiveAdapter = new RecyclerFiveAdapter(fiveInfos,MyApplication.type4);
        mXRecyclerView.setAdapter(mFiveAdapter);


        mLinearLayoutManager = new LinearLayoutManager(BrowseActivity.this);

        mXRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mXRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {
                // Toast.makeText(RoomSourceActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BrowseActivity.this, DetailsActivity.class));
            }
        });

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
        final int itemLimit = 2;
        //设置下拉刷新可用
        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        // collectionInfos.clear();
                        FiveAdapterInfo info = null;
                        for(int i = 0; i < itemLimit ;i++){
                            info = new FiveAdapterInfo();
                            // info.url = urls[i];
                            fiveInfos.add(info);
                        }
                        mFiveAdapter.notifyDataSetChanged();
                        if( mXRecyclerView != null)
                            mXRecyclerView.refreshComplete();
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
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
