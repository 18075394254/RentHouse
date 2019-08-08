package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.DetailsAdapterInfo;
import bean.FiveAdapterInfo;
import decoration.SpacesItemDecoration;
import loader.GlideImageLoader;
import mainfragment.ShouYeFragment;
import shouyeadapter.DetailsRecyclerAdapter;
import shouyeadapter.RecyclerFiveAdapter;
import utils.MyNestedScrollView;

/**
 * Created by user on 2019/8/5.
 */

public class DetailsActivity extends AppCompatActivity implements OnBannerListener {

    private Banner banner;
    private RecyclerView mOneRecyclerView,mTwoRecyclerView,mThreeRecyclerView;
    private GridLayoutManager mLayoutManager;
    private DetailsRecyclerAdapter mDetailsAdapter;
    private ImageView phoneImg,gobackImage;
    private MapView mMapView = null;
    private TextView tv_tishi;
    
    private XRecyclerView mFourRecyclerView;
    private MyNestedScrollView nestedSV;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    private int times =0;
    private LinearLayout mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(DetailsActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_details);
        initBanner();

        initOneRecyclerView();

        initTwoRecyclerView();

        initThreeRecyclerView();
        
        initFourRecyclerView();

        initMapView();
        mRootView = (LinearLayout) findViewById(R.id.details_rootLin);

        phoneImg = (ImageView) findViewById(R.id.details_phone);
        phoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();

                intent.setAction("android.intent.action.CALL_BUTTON");

                startActivity(intent);
            }
        });

        gobackImage = (ImageView) findViewById(R.id.details_iv_goback);

        gobackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }


        });

        tv_tishi = (TextView) findViewById(R.id.details_tv_tishi);
        String string = "1.异性2合租需谨慎，夜间看房请找亲友陪同"+"\n"+
                "2.没完成合同前，不要交任何费用"+"\n"+
                "3.谨防以搬贴为名的推广人员，避免隐私泄露"+"\n"+
                "4.发现房源虚假或用户骚扰情况，请举报";
        tv_tishi.setText(string);

    }

    private void initFourRecyclerView() {
        mFourRecyclerView = (XRecyclerView) findViewById(R.id.details_FourRecyclerView);
        nestedSV = (MyNestedScrollView) findViewById(R.id.details_nestedSV);
        mFourRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mFourRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mFourRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mFourRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        //添加数据
        fiveInfos = new ArrayList<>();
        FiveAdapterInfo info = null;
        for (int i = 0; i < 10; i++) {
            info = new FiveAdapterInfo();
            // info.url = urls[i];
            fiveInfos.add(info);
        }

        //设置adapter的数据
        mFiveAdapter = new RecyclerFiveAdapter(fiveInfos,MyApplication.type1);
        mFourRecyclerView.setAdapter(mFiveAdapter);


        mLinearLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mFourRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mFourRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {
                Toast.makeText(DetailsActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

            }
        });

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
        final int itemLimit = 2;
        mFourRecyclerView.setPullRefreshEnabled(false);
        mFourRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                // fiveRecyclerView.refreshComplete();
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
                            if(mFourRecyclerView != null) {
                                mFourRecyclerView.loadMoreComplete();
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
                            if(mFourRecyclerView != null) {
                                mFourRecyclerView.setNoMore(true);
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
                    mFourRecyclerView.reload();
                }
            }
        });


    
    }

    private void initMapView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    private void initThreeRecyclerView() {

                  mThreeRecyclerView = (RecyclerView) findViewById(R.id.details_threeRecyclerview);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(DetailsActivity.this, 2, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<DetailsAdapterInfo> detailsInfos = new ArrayList<>();
        DetailsAdapterInfo info = null;

        String[] message1s = new String[]{"朝向: ","入住时间: ","楼层: ","电梯: ","用水: ","用电: ","燃气: ","采暖: "};
        String[] message2s = new String[]{"南","随时入住","2/4层","无","民水","民电","无","集体供暖"};
        for (int i = 0; i < message1s.length; i++) {
            info = new DetailsAdapterInfo();
            info.message1 = message1s[i];
            info.message2 = message2s[i];
            detailsInfos.add(info);
        }

        //设置adapter的数据
        mDetailsAdapter = new DetailsRecyclerAdapter(detailsInfos,MyApplication.type3);
        mThreeRecyclerView.setAdapter(mDetailsAdapter);
        mThreeRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mThreeRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

    }

    private void initTwoRecyclerView() {
        mTwoRecyclerView = (RecyclerView) findViewById(R.id.details_twoRecyclerview);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(DetailsActivity.this, 6, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<DetailsAdapterInfo> detailsInfos = new ArrayList<>();
        DetailsAdapterInfo info = null;


        for (int i = 0; i < 6; i++) {
            info = new DetailsAdapterInfo();
            info.message1 = "标签";
            detailsInfos.add(info);
        }

        //设置adapter的数据
        mDetailsAdapter = new DetailsRecyclerAdapter(detailsInfos,MyApplication.type2);
        mTwoRecyclerView.setAdapter(mDetailsAdapter);
        mTwoRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mTwoRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
    }

    private void initOneRecyclerView() {
        mOneRecyclerView = (RecyclerView) findViewById(R.id.details_oneRecyclerview);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(DetailsActivity.this, 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<DetailsAdapterInfo> detailsInfos = new ArrayList<>();
        DetailsAdapterInfo info = null;
        String[] message1s = new String[]{"房租","户型","面积"};
        String[] message2s = new String[]{"3500元/月","三室一厅一卫","96㎡"};
        for (int i = 0; i < message1s.length; i++) {
            info = new DetailsAdapterInfo();
            info.message1 = message1s[i];
            info.message2 = message2s[i];
            detailsInfos.add(info);
        }

        //设置adapter的数据
        mDetailsAdapter = new DetailsRecyclerAdapter(detailsInfos,MyApplication.type1);
        mOneRecyclerView.setAdapter(mDetailsAdapter);
        mOneRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mOneRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

    }

    //初始书ViewPager轮播
    private void initBanner() {
        banner = (Banner) findViewById(R.id.details_banner);
        banner.setImages(MyApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(DetailsActivity.this)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mRootView.setFocusable(true);

        mRootView.setFocusableInTouchMode(true);

        mRootView.requestFocus();

        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
