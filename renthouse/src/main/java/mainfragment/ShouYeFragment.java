package mainfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

import activity.SearchActivity;
import application.MyApplication;
import bean.AdapterInfo;
import bean.FiveAdapterInfo;
import bean.FourAdapterInfo;
import bean.FiveAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;
import decoration.SpacesItemDecoration;
import listener.EndLessOnScrollListener;
import loader.GlideImageLoader;
import shouyeadapter.RecyclerFiveAdapter;
import shouyeadapter.RecyclerFourAdapter;
import shouyeadapter.RecyclerOneAdapter;
import shouyeadapter.RecyclerThreeAdapter;
import shouyeadapter.RecyclerTwoAdapter;
import utils.MyNestedScrollView;


public class ShouYeFragment extends Fragment implements OnBannerListener{

    private View mView;
    //private SearchView mSearchView;
    private TextView mSearchView;

    LinearLayout cityLin,messageLin;
    private MyNestedScrollView nestedSV;
    Banner banner;
    private GridView mGridView;
    String[] titles = new String[]{"单间","整套","品牌公寓","月付房源","求租贴","我要发布","平台分类","免费看房"};
    int[] imgs = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight};
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();

    public static final int LINEAR_VERTICAL = 1;
    public static final int LINEAR_HORIZONTAL = 2;
    public static final int GRID_VERTICAL = 3;
    public static final int GRID_HORIZONTAL = 4;
    public static final int STAGGER_VERTICAL = 5;
    public static final int STAGGER_HORIZONTAL = 6;

    private int type;

    private RecyclerView gridRecyclerView,twoRecyclerView,threeRecyclerView,fourRecyclerView;
    private XRecyclerView fiveRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerOneAdapter mOneAdapter;
    private RecyclerTwoAdapter mTwoAdapter;
    private RecyclerThreeAdapter mThreeAdapter;
    private RecyclerFourAdapter mFourAdapter;
    private RecyclerFiveAdapter mFiveAdapter;
    List<FiveAdapterInfo> fiveInfos;

    LinearLayout searchRootView;

    private int refreshTime = 0;
    private int times = 0;

    private LinearLayoutManager mLinearLayoutManager;

    public void initData(){
        transformers.add(DefaultTransformer.class);
       /* transformers.add(AccordionTransformer.class);
        transformers.add(BackgroundToForegroundTransformer.class);
        transformers.add(ForegroundToBackgroundTransformer.class);
        transformers.add(CubeInTransformer.class);//兼容问题，慎用
        transformers.add(CubeOutTransformer.class);
        transformers.add(DepthPageTransformer.class);
        transformers.add(FlipHorizontalTransformer.class);
        transformers.add(FlipVerticalTransformer.class);
        transformers.add(RotateDownTransformer.class);
        transformers.add(RotateUpTransformer.class);
        transformers.add(ScaleInOutTransformer.class);
        transformers.add(StackTransformer.class);
        transformers.add(TabletTransformer.class);
        transformers.add(ZoomInTransformer.class);
        transformers.add(ZoomOutTranformer.class);
        transformers.add(ZoomOutSlideTransformer.class);*/
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shouye,container,false);
        //初始化SearchView
        initSearchView();
        //初始化标题栏上城市名和消息两个LinearLayout布局
        initLayout();
        //初始书ViewPager轮播
        initBanner();
        //设置轮播图的样式
        initData();
        //初始化第一个RecyclerView
        initOneRecyclerView();

        //初始化第二个RecyclerView
        initTwoRecyclerView();

        //初始化第三个RecyclerView
        initThreeRecyclerView();

        //初始化第四个RecyclerView
        initFourRecyclerView();

        //初始化第五个RecyclerView
        initFiveRecyclerView();

        //防止ScrollView与RecyclerView滑动冲突
        avoidSlideError();


        return mView;
    }
    //防止ScrollView与RecyclerView滑动冲突
    private void avoidSlideError() {
        gridRecyclerView.setNestedScrollingEnabled(false);
        twoRecyclerView.setNestedScrollingEnabled(false);
        threeRecyclerView.setNestedScrollingEnabled(false);
        fourRecyclerView.setNestedScrollingEnabled(false);
        fiveRecyclerView.setNestedScrollingEnabled(false);
    }

    //第五个RecyclerView
    private void initFiveRecyclerView() {
        {

            fiveRecyclerView = mView.findViewById(R.id.shouye_fiveRecyclerView);
            nestedSV = mView.findViewById(R.id.shouye_nestedSV);
            fiveRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            fiveRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            fiveRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

            fiveRecyclerView
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
            mFiveAdapter = new RecyclerFiveAdapter(fiveInfos);
            fiveRecyclerView.setAdapter(mFiveAdapter);


            mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            fiveRecyclerView.setLayoutManager(mLinearLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
            fiveRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

            mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
                @Override
                public void setOnitemClickListener(View view, int position) {
                    Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

                }
            });

            // When the item number of the screen number is list.size-2,we call the onLoadMore
          //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
            final int itemLimit = 2;
            fiveRecyclerView.setPullRefreshEnabled(false);
            fiveRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                                if(fiveRecyclerView != null) {
                                    fiveRecyclerView.loadMoreComplete();
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
                                if(fiveRecyclerView != null) {
                                    fiveRecyclerView.setNoMore(true);
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
                    fiveRecyclerView.reload();
             }
            }
        });

            
        }
    }

    //第四个RecyclerView
    private void initFourRecyclerView() {
        fourRecyclerView = (RecyclerView) mView.findViewById(R.id.shouye_fourRecyclerView);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        //添加数据
        List<FourAdapterInfo> fourInfos = new ArrayList<>();
        FourAdapterInfo info = null;
        String[] urls = getResources().getStringArray(R.array.url4);
        String[] titles = getResources().getStringArray(R.array.fourtitle);
        String[] prices = getResources().getStringArray(R.array.fourprice);
        String[] sizes = getResources().getStringArray(R.array.foursize);
        for (int i = 0; i < titles.length; i++) {
            info = new FourAdapterInfo();
            info.title = titles[i];
            info.price = prices[i];
            info.size = sizes[i];
            info.url = urls[i];
            fourInfos.add(info);
        }

        //设置adapter的数据
        mFourAdapter = new RecyclerFourAdapter(fourInfos);
        fourRecyclerView.setAdapter(mFourAdapter);
        fourRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        fourRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mFourAdapter.setListener(new RecyclerFourAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {
                Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

            }
        });
    }

    //第三个RecyclerView
    private void initThreeRecyclerView() {
        threeRecyclerView = (RecyclerView) mView.findViewById(R.id.shouye_threeRecyclerView);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<ThreeAdapterInfo> threeInfos = new ArrayList<>();
        ThreeAdapterInfo info = null;
        String[] urls = getResources().getStringArray(R.array.url3);
        String[] titles = getResources().getStringArray(R.array.threetitle);
        for (int i = 0; i < titles.length; i++) {
            info = new ThreeAdapterInfo();
            info.title = titles[i];
            info.url = urls[i];
            threeInfos.add(info);
        }

        //设置adapter的数据
        mThreeAdapter = new RecyclerThreeAdapter(threeInfos);
        threeRecyclerView.setAdapter(mThreeAdapter);
        threeRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        threeRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mThreeAdapter.setListener(new RecyclerThreeAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //第二个RecyclerView
    private void initTwoRecyclerView() {
        twoRecyclerView= mView.findViewById(R.id.shouye_twoRecyclerView);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<TwoAdapterInfo> twoInfos = new ArrayList<>();
        TwoAdapterInfo info = null;
        String[] urls = getResources().getStringArray(R.array.url2);
        String[] miaoshu = getResources().getStringArray(R.array.twomiaoshu);
        String[] titles = getResources().getStringArray(R.array.twotitle);
        for (int i = 0; i < miaoshu.length; i++) {
            info = new TwoAdapterInfo();
            info.title = titles[i];
            info.miaoshu = miaoshu[i];
            info.url = urls[i];
            twoInfos.add(info);
        }

        //设置adapter的数据
        mTwoAdapter = new RecyclerTwoAdapter(twoInfos);
        twoRecyclerView.setAdapter(mTwoAdapter);
        twoRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(10);
        int topBottom = 0;
        twoRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mTwoAdapter.setItemClickListener(new RecyclerTwoAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //第一个RecyclerView
    private void initOneRecyclerView() {
        gridRecyclerView = (RecyclerView) mView.findViewById(R.id.shouye_gidRecyclerView);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<AdapterInfo> oneInfos = new ArrayList<>();
        AdapterInfo info = null;
        for (int i = 0; i < titles.length; i++) {
            info = new AdapterInfo();
            info.message = titles[i];
            info.img = imgs[i];
            oneInfos.add(info);
        }

        //设置adapter的数据
        mOneAdapter = new RecyclerOneAdapter(oneInfos);
        gridRecyclerView.setAdapter(mOneAdapter);
        gridRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        gridRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

       mOneAdapter.setItemClickListener(new RecyclerOneAdapter.OnItemClickListener() {
           @Override
           public void setOnItemClickListener(View view, int position) {
               Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
           }
       });
    }

    //初始书ViewPager轮播
    private void initBanner() {
        banner = mView.findViewById(R.id.shouye_banner);
        banner.setImages(MyApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    //初始化标题栏上城市名和消息两个LinearLayout布局
    private void initLayout() {
        cityLin = mView.findViewById(R.id.shouye_cityLin);
        messageLin = mView.findViewById(R.id.shouye_messageLin);
    }

    //初始化SearchView
    private void initSearchView() {
       // searchRootView = mView.findViewById(R.id.shouye_searchRootView);
        mSearchView = mView.findViewById(R.id.shouye_searchView);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(),"你点击了："+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
