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

import adapter.RecyclerAdapter;
import application.MyApplication;
import bean.AdapterInfo;
import decoration.SpacesItemDecoration;
import loader.GlideImageLoader;


public class ShouYeFragment extends Fragment implements OnBannerListener {

    private View mView;
    private SearchView mSearchView;
    LinearLayout cityLin,messageLin;
    Banner banner;
    private RecyclerView gridRecyclerView;
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

    private RecyclerView rv_content;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

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
        //初始化网格状RecyclerView
        initGridRecycler();
       // setStatusColor();
        chooseLayoutManager(4);
        initRvContent();
        return mView;
    }



    private void initGridRecycler() {
        rv_content = (RecyclerView) mView.findViewById(R.id.gidRecyclerView);
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
        mSearchView.setFocusable(false);
        mSearchView.setQueryHint("输入地址区域名字搜索");
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


    private List<AdapterInfo> getAdapterInfos() {
        List<AdapterInfo> infos = new ArrayList<>();
        AdapterInfo info = null;
        for (int i = 0; i < titles.length; i++) {
            info = new AdapterInfo();
            info.message = titles[i];
            info.img = imgs[i];
            infos.add(info);
        }
        return infos;
    }


    private void chooseLayoutManager(int count) {

                mLayoutManager = new GridLayoutManager(getActivity(), count, GridLayoutManager.VERTICAL, true);
                final GridLayoutManager manager = (GridLayoutManager) mLayoutManager;
               /* manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position % (manager.getSpanCount() + 1) == 0 ? manager.getSpanCount() : 1;
                    }
                });*/
    }

    private void initRvContent() {
        mAdapter = new RecyclerAdapter(getAdapterInfos());
        rv_content.setAdapter(mAdapter);
        rv_content.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;

        rv_content.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        //rv_content.addItemDecoration(new SpacesItemDecoration(dip2px(1), dip2px(1), Color.BLUE));
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

}
