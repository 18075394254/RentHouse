package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import application.MyApplication;
import bean.FiveAdapterInfo;
import bean.MyPostAdapterInfo;
import decoration.SpacesItemDecoration;
import meadapter.MyPostAdapter;
import shouyeadapter.RecyclerFiveAdapter;

/**
 * Created by user on 2019/8/26.
 */

public class MyPostActivity extends AppCompatActivity{

    private ImageView backImage;
    private XRecyclerView mXRecyclerView;
    private ArrayList<MyPostAdapterInfo> infos;
    private MyPostAdapter mypostAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    public int refreshTime = 0;
    int times = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(MyPostActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_mypost);
        initRecyclerView();

        backImage = (ImageView)findViewById(R.id.mypost_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        mXRecyclerView = findViewById(R.id.mypost_recycler);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        infos = new ArrayList<>();
        MyPostAdapterInfo info = null;
        for (int i = 0; i < 4; i++) {
            info = new MyPostAdapterInfo();
            // info.url = urls[i];
            infos.add(info);
        }

        //设置adapter的数据
        mypostAdapter = new MyPostAdapter(infos);
        mXRecyclerView.setAdapter(mypostAdapter);


        mLinearLayoutManager = new LinearLayoutManager(MyPostActivity.this);

        mXRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mXRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
/*//添加Android自带的分割线
        mXRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));*/
        mypostAdapter.setItemClickListener(new MyPostAdapter.OnItemClickListener() {

            @Override
            public void setOnItemClickListener(View view, int postion) {
                    startActivity(new Intent(MyPostActivity.this,MyPostDetailsActivity.class));
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

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        // collectionInfos.clear();
                        MyPostAdapterInfo info = null;
                        for(int i = 0; i < itemLimit ;i++){
                            info = new MyPostAdapterInfo();
                            // info.url = urls[i];
                            infos.add(info);
                        }
                        mypostAdapter.notifyDataSetChanged();
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
                            // collectionInfos.clear();
                            MyPostAdapterInfo info = null;
                            for(int i = 0; i < itemLimit ;i++){
                                info = new MyPostAdapterInfo();
                                // info.url = urls[i];
                                infos.add(info);
                            }
                            mypostAdapter.notifyDataSetChanged();
                            if( mXRecyclerView != null)
                                mXRecyclerView.refreshComplete();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            // collectionInfos.clear();
                            MyPostAdapterInfo info = null;
                            for(int i = 0; i < itemLimit ;i++){
                                info = new MyPostAdapterInfo();
                                // info.url = urls[i];
                                infos.add(info);
                            }
                            mypostAdapter.notifyDataSetChanged();
                            if( mXRecyclerView != null)
                                mXRecyclerView.refreshComplete();
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
