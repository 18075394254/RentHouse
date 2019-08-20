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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.FiveAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.RecyclerFiveAdapter;
import utils.MyNestedScrollView;

/**
 * Created by user on 2019/8/20.
 */

public class OtherApartmentDetailsActivity extends AppCompatActivity {
    private XRecyclerView mXRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    List<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    private int times = 0;
    TextView titleText;
    private ImageView backImage;
    private int refreshTime = 0;
    private MyNestedScrollView nestedSV;
    private LinearLayout mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(OtherApartmentDetailsActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_other_apartment_details);
        mRootView = (LinearLayout) findViewById(R.id.linRootView);
        initRecyclerView();
    }
    private void initRecyclerView() {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.other_apartment_details_recyclerView);
        nestedSV = (MyNestedScrollView) findViewById(R.id.other_apratment_details_nestedSV);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mXRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        fiveInfos = new ArrayList<>();
        FiveAdapterInfo info = null;
        for (int i = 0; i < 8; i++) {
            info = new FiveAdapterInfo();
            // info.url = urls[i];
            fiveInfos.add(info);
        }

        //设置adapter的数据
        mFiveAdapter = new RecyclerFiveAdapter(fiveInfos, MyApplication.type4);
        mXRecyclerView.setAdapter(mFiveAdapter);



        mLinearLayoutManager = new LinearLayoutManager(OtherApartmentDetailsActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
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
                startActivity(new Intent(OtherApartmentDetailsActivity.this, DetailsActivity.class));
            }
        });

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
        final int itemLimit = 2;
        //设置下拉刷新bu可用
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
              /*  //refresh data here
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

                }, 1000);*/
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

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // Toast.makeText(getActivity(),"滑动到底了，需要加载数据",Toast.LENGTH_SHORT).show();
                    mXRecyclerView.reload();
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        mRootView.setFocusable(true);

        mRootView.setFocusableInTouchMode(true);

        mRootView.requestFocus();
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
