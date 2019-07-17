package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.CubeInTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.DefaultTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;
import com.youth.banner.transformer.FlipVerticalTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.StackTransformer;
import com.youth.banner.transformer.TabletTransformer;
import com.youth.banner.transformer.ZoomInTransformer;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youth.banner.transformer.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import activity.MainActivity;
import adapter.GridAdapter;
import application.MyApplication;
import loader.GlideImageLoader;


public class ShouYeFragment extends Fragment implements OnBannerListener {

    private View mView;
    private SearchView mSearchView;
    LinearLayout cityLin,messageLin;
    Banner banner;
    private RecyclerView gridRecyclerView;
    private GridView mGridView;
    String[] titles = new String[]{"单间","整套","品牌公寓","月付房源","求租贴","我要发布","平台分类","免费看房"};
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();
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
       // initGridRecycler();
        //初始化GridView
        initGridView();

        return mView;
    }

    private void initGridView() {
        mGridView= mView.findViewById(R.id.gridView);
        onArrayListShow();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;

                    case 7:

                        break;
                }
            }
        });
    }

   /* private void initGridRecycler() {
        gridRecyclerView = mView.findViewById(R.id.gidRecyclerView);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        gridRecyclerView.setAdapter(new GridAdapter(getActivity(),titles));

    }*/

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

    private void onArrayListShow() {
        ArrayList<String> list1=new ArrayList<>();
        list1.add("单间");
        list1.add("整套");
        list1.add("品牌公寓");
        list1.add("月付房源");
        list1.add("求租贴");
        list1.add("我要发布");
        list1.add("平台分类");
        list1.add("免费看房");

        ArrayList<Integer> list2=new ArrayList<>();
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);
        list2.add(R.mipmap.log);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<list1.size();i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", list2.get(i));//添加图像资源的ID
            map.put("ItemText", list1.get(i));//按序号做ItemText
            lstImageItem.add(map);
        }
//生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), //没什么解释
                lstImageItem,//数据来源
                R.layout.item_recyclerviedw,//night_item的XML实现

                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.recyclerImage,R.id.recyclerText});
        mGridView.setNumColumns(4);
//添加并且显示
        mGridView.setAdapter(saImageItems);
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
}
