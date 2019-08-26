package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import bean.MyPublishAdapterInfo;
import bean.MyPublishAdapterInfo;
import decoration.SpacesItemDecoration;
import meadapter.MyPublishRecyclerAdapter;
import wishadapter.CollectionRecyclerAdapter;

/**
 * Created by user on 2019/8/26.
 */

public class MyPublishActivity extends AppCompatActivity{
    private RecyclerView.LayoutManager mLayoutManager;

    private MyPublishRecyclerAdapter mMyPublishAdapter;
    List<MyPublishAdapterInfo> mypublishInfos;
    private XRecyclerView publishRecyclerView;

    private int refreshTime = 0;
    private int times = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView backImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(MyPublishActivity.this,R.color.tab_textColorSelect);

        initRecyclerView();
        backImage = (ImageView)findViewById(R.id.mypublish_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    //初始化RecyclerView
    private void initRecyclerView() {
        {

            publishRecyclerView = (XRecyclerView) findViewById(R.id.mypublish_xRecyclerView);
            publishRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            publishRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            publishRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

            publishRecyclerView
                    .getDefaultRefreshHeaderView()
                    .setRefreshTimeVisible(true);
            //添加数据
            mypublishInfos = new ArrayList<>();
            /*MyPublishAdapterInfo info = null;
            for (int i = 0; i < 2; i++) {
                info = new MyPublishAdapterInfo();
                // info.url = urls[i];
                mypublishInfos.add(info);
            }*/

            //设置adapter的数据
            mMyPublishAdapter = new MyPublishRecyclerAdapter(mypublishInfos);
            publishRecyclerView.setAdapter(mMyPublishAdapter);

            mLinearLayoutManager = new LinearLayoutManager(MyPublishActivity.this);
            publishRecyclerView.setLayoutManager(mLinearLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
            publishRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

           mMyPublishAdapter.setListener(new MyPublishRecyclerAdapter.OnItemClickListener() {
               @Override
               public void setOnitemClickListener(View view, int position) {
                   
               }
           });
            final int itemLimit = 2;

            // When the item number of the screen number is list.size-2,we call the onLoadMore
            publishRecyclerView.setLimitNumberToCallLoadMore(2);

            publishRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    refreshTime ++;
                    times = 0;
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            // mypublishInfos.clear();
                            for(int i = 0; i < itemLimit ;i++){
                                mypublishInfos.add(new MyPublishAdapterInfo());
                            }
                            mMyPublishAdapter.notifyDataSetChanged();
                            if(publishRecyclerView != null)
                                publishRecyclerView.refreshComplete();
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
                                    mypublishInfos.add(new MyPublishAdapterInfo());
                                }
                                if(publishRecyclerView != null) {
                                    publishRecyclerView.loadMoreComplete();
                                    mMyPublishAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                for(int i = 0; i < itemLimit ;i++){
                                    mypublishInfos.add(new MyPublishAdapterInfo());
                                }
                                if(publishRecyclerView != null) {
                                    publishRecyclerView.setNoMore(true);
                                    mMyPublishAdapter.notifyDataSetChanged();
                                }
                            }
                        }, 1000);
                    }
                    times ++;
                }
            });


            publishRecyclerView.refresh();
        }
    }


    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
