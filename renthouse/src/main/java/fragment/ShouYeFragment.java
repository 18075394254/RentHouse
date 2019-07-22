package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerFourAdapter;
import adapter.RecyclerOneAdapter;
import adapter.RecyclerThreeAdapter;
import adapter.RecyclerTwoAdapter;
import application.MyApplication;
import bean.AdapterInfo;
import bean.FourAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;
import decoration.SpacesItemDecoration;
import loader.GlideImageLoader;


public class ShouYeFragment extends Fragment implements OnBannerListener {

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

    private RecyclerView gridRecyclerView,twoRecyclerView,threeRecyclerView,fourRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerOneAdapter mOneAdapter;
    private RecyclerTwoAdapter mTwoAdapter;
    private RecyclerThreeAdapter mThreeAdapter;
    private RecyclerFourAdapter mFourAdapter;

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

        return mView;
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
        mSearchView = mView.findViewById(R.id.searchView);
        mSearchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getActivity(),"搜索框点击了",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mSearchView.setQueryHint("输入地址区域名字搜索");
        mSearchView.setFocusable(false);
      //  mSearchView.setEnabled(false);
       // mSearchView.clearFocus();
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


    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

}
