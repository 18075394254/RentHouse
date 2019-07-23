package fragment;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerFiveAdapter;
import adapter.RecyclerFourAdapter;
import adapter.RecyclerOneAdapter;
import adapter.RecyclerThreeAdapter;
import adapter.RecyclerTwoAdapter;
import application.MyApplication;
import bean.AdapterInfo;
import bean.FiveAdapterInfo;
import bean.FourAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;
import decoration.SpacesItemDecoration;
import listener.EndLessOnScrollListener;
import loader.GlideImageLoader;


public class ShouYeFragment extends Fragment implements OnBannerListener ,SwipeRefreshLayout.OnRefreshListener{

    private View mView;
    private SearchView mSearchView;
    LinearLayout cityLin,messageLin;
    Banner banner;
    private GridView mGridView;
    String[] titles = new String[]{"单间","整套","品牌公寓","月付房源","求租贴","我要发布","平台分类","免费看房"};
    int[] imgs = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight};
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();

    public static final int LINEAR_VERTICAL = 1;
    public static final int LINEAR_HORIZONTAL = 2;
    public static final int GRID_VERTICAL = 3;
    public static final int GRID_HORIZONTAL = 4;
    public static final int STAGGER_VERTICAL = 5;
    public static final int STAGGER_HORIZONTAL = 6;

    private int type;

    private RecyclerView gridRecyclerView,twoRecyclerView,threeRecyclerView,fourRecyclerView,fiveRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerOneAdapter mOneAdapter;
    private RecyclerTwoAdapter mTwoAdapter;
    private RecyclerThreeAdapter mThreeAdapter;
    private RecyclerFourAdapter mFourAdapter;
    private RecyclerFiveAdapter mFiveAdapter;
    List<FiveAdapterInfo> fiveInfos;

    LinearLayout searchRootView;

    private SwipeRefreshLayout mRefreshLayout;
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

        return mView;
    }
    //第五个RecyclerView
    private void initFiveRecyclerView() {
        {
            mRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.layout_swipe_refresh);
            fiveRecyclerView = (RecyclerView) mView.findViewById(R.id.fiveRecyclerView);
            //设置每行的列数
            //mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
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

            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            fiveRecyclerView.setLayoutManager(mLinearLayoutManager);
            //监听SwipeRefreshLayout.OnRefreshListener
            mRefreshLayout.setOnRefreshListener(this);

            /**
             * 监听addOnScrollListener这个方法，新建我们的EndLessOnScrollListener
             * 在onLoadMore方法中去完成上拉加载的操作
             * */
            fiveRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
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
            fiveRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
            fiveRecyclerView.addOnScrollListener(new EndLessOnScrollListener(new LinearLayoutManager(getActivity())) {
                @Override
                public void onLoadMore(int currentPage) {
                    loadMoreData();
                }
            });

            mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
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
            fiveInfos.add(new FiveAdapterInfo());
            mFiveAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 重写SwipeRefreshLayout.OnRefreshListener的OnRefresh方法
     * 在这里面去做下拉刷新的操作
     */
    @Override
    public void onRefresh() {
        updateData();
        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
        mFiveAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    private void updateData(){
        //我在List最前面加入一条数据
        fiveInfos.add(new FiveAdapterInfo());
    }
    //第四个RecyclerView
    private void initFourRecyclerView() {
        fourRecyclerView = (RecyclerView) mView.findViewById(R.id.fourRecyclerView);
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
        threeRecyclerView = (RecyclerView) mView.findViewById(R.id.threeRecyclerView);
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
        twoRecyclerView= mView.findViewById(R.id.twoRecyclerView);
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
        gridRecyclerView = (RecyclerView) mView.findViewById(R.id.gidRecyclerView);
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
        banner = mView.findViewById(R.id.banner);
        banner.setImages(MyApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    //初始化标题栏上城市名和消息两个LinearLayout布局
    private void initLayout() {
        cityLin = mView.findViewById(R.id.cityLin);
        messageLin = mView.findViewById(R.id.messageLin);
    }

    //初始化SearchView
    private void initSearchView() {
        searchRootView = mView.findViewById(R.id.searchRootView);
        mSearchView = mView.findViewById(R.id.searchView);
        mSearchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               // Toast.makeText(getActivity(),"搜索框点击了",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //设置输入字体颜色
        if(mSearchView == null) { return;}
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        //textView.setTextColor(Color.RED);//字体颜色
        textView.setTextSize(15);//字体、提示字体大小
        textView.setHintTextColor(Color.GRAY);//提示字体颜色
        searchRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"搜索框点击了",Toast.LENGTH_SHORT).show();
            }
        });
        mSearchView.setEnabled(false);
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

        searchRootView.setFocusable(true);

        searchRootView.setFocusableInTouchMode(true);

        searchRootView.requestFocus();
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
