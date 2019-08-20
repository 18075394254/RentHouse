package mainfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import activity.BaiduMapTest;
import activity.BrandApartmentActivity;
import activity.DetailsActivity;
import activity.ListActivity;
import activity.MainActivity;
import activity.MessageActivity;
import activity.RecommendActivity;
import activity.RoomSourceActivity;
import activity.SearchActivity;
import application.MyApplication;
import bean.AdapterInfo;
import bean.FiveAdapterInfo;
import bean.FourAdapterInfo;
import bean.ThreeAdapterInfo;
import bean.TwoAdapterInfo;
import decoration.SpacesItemDecoration;
import loader.GlideImageLoader;
import myinterface.PermissionListener;
import shouyeadapter.RecyclerFiveAdapter;
import shouyeadapter.RecyclerFourAdapter;
import shouyeadapter.RecyclerOneAdapter;
import shouyeadapter.RecyclerThreeAdapter;
import shouyeadapter.RecyclerTwoAdapter;
import utils.MyNestedScrollView;
import utils.NetWorkUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import static activity.BaseActivity.requestRuntimePermissions;



public class ShouYeFragment extends Fragment implements OnBannerListener{

    private View mView;
    //private SearchView mSearchView;
    private TextView mSearchView;

    LinearLayout cityLin,messageLin;
    private MyNestedScrollView nestedSV;
    Banner banner;
    private GridView mGridView;
    private List<HotCity> hotCities;
    String[] titles = new String[]{"单间","整套","品牌公寓","月付房源","求租贴","我要发布","短租房源","家政服务"};
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
    private TextView shouye_city_Tv;

    public LocationClient mLocationClient = null; //初始化LocationClient类
    public MyLocationListener myListener = new MyLocationListener();

    private BDLocation BDlocation = null;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.INTERNET"};
    private SharedPreferences sp;
    String cityName = "";

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
        sp = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);;
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
        //定位当前位置
        initLoactions();

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
            mFiveAdapter = new RecyclerFiveAdapter(fiveInfos,MyApplication.type1);
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
                   // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), DetailsActivity.class));
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
               // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), BaiduMapTest.class));
                startActivity(new Intent(getActivity(), DetailsActivity.class));
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
                //Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), RecommendActivity.class));
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
                //Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), ListActivity.class));
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
              // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
               if (position == 2){
                   startActivity(new Intent(getActivity(), BrandApartmentActivity.class));
               }else if(position == 4) {

               }else if(position == 5){

               }else{
                   startActivity(new Intent(getActivity(), RoomSourceActivity.class));
               }

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
        shouye_city_Tv = mView.findViewById(R.id.shouye_city_Tv);
        messageLin = mView.findViewById(R.id.shouye_messageLin);
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        messageLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        cityLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPicker.from(getActivity())
                        .enableAnimation(true)
                        .setAnimationStyle(R.style.CustomAnim)
                        .setLocatedCity(null)
                        .setHotCities(null)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                shouye_city_Tv.setText(data.getName());
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("city", data.getName());
                                editor.commit();
                            }

                            @Override
                            public void onCancel() {
                               // Toast.makeText(getActivity(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {

                              //  Toast.makeText(getActivity(), "定位完成", Toast.LENGTH_SHORT).show();
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (BDlocation != null)
                                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(BDlocation.getCity(),BDlocation.getProvince(),BDlocation.getCountryCode()), LocateState.SUCCESS);
                                    }
                                }, 1500);
                            }
                        })
                        .show();
            }
        });
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

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的City赋值给txt
            /**
             *1.国家:location.getCountry()
             * 2.城市:location.getCity()
             * 3.区域(例：天河区)：location.getDistrict()
             * 4.地点(例：风信路)：location.getStreet()
             * 5.详细地址：location.getAddrStr()
             */
            final String cityname = location.getCity();
            Log.i("shouye","Name = "+location.getCity());
            Log.i("shouye","address = "+location.getAddrStr());
            mLocationClient.stop();
            BDlocation = location;
            Dialog alertDialog = new AlertDialog.Builder(getActivity()).
                    setTitle("当前城市定位到"+location.getCity()+",是否确认定位？").
                    setIcon(R.mipmap.log).
                    setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shouye_city_Tv.setText(cityname);

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("city", cityname);
                            editor.commit();

                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    create();
            if (cityname.equals(shouye_city_Tv.getText().toString())){

            }else{
                alertDialog.show();
            }



        }
        public void onReceivePoi(BDLocation arg0) {
        }
    }


    private void initLoactions(){

        cityName = sp.getString("city","上海");
        shouye_city_Tv.setText(cityName);
      /*  if (!NetWorkUtils.isMobileDataEnable(MyApplication.getContext())){
            Dialog alertDialog = new AlertDialog.Builder(getActivity()).
                    setTitle("当前没有网络，是否打开网络设置？").
                    setIcon(R.mipmap.log).
                    setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           NetWorkUtils.GoSetting(getActivity());
                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    create();
            alertDialog.show();
        }else {*/

            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            //声明LocationClient类
            mLocationClient = new LocationClient(MyApplication.getContext());
            mLocationClient.registerLocationListener(myListener); //注册监听函数
            setLocationOption(); //定义setLocationOption()方法
            mLocationClient.start(); //执行定位

            if (Build.VERSION.SDK_INT >= 23) {//判断当前系统是不是Android6.0
                requestRuntimePermissions(PERMISSIONS_STORAGE, new PermissionListener() {
                    @Override
                    public void granted() {
                        //权限申请通过
                    }

                    @Override
                    public void denied(List<String> deniedList) {
                        //权限申请未通过
                        for (String denied : deniedList) {
                            if (denied.equals("android.permission.ACCESS_FINE_LOCATION")) {

                                Toast.makeText(getActivity(), "定位失败，请检查是否打开定位权限！", Toast.LENGTH_SHORT).show();

                            } else if (denied.equals("android.permission.ACCESS_NETWORK_STATE")) {

                                Toast.makeText(getActivity(), "定位失败，请检查是否打开网络权限！", Toast.LENGTH_SHORT).show();

                            } else if (denied.equals("android.permission.CHANGE_WIFI_STATE")) {

                                Toast.makeText(getActivity(), "定位失败，请检查是否打开WiFi权限！", Toast.LENGTH_SHORT).show();

                            } else if (denied.equals("android.permission.ACCESS_FINE_LOCATION")) {

                                Toast.makeText(getActivity(), "定位失败，请检查是否打开定位权限！", Toast.LENGTH_SHORT).show();

                            } else if (denied.equals("android.permission.INTERNET")) {

                                Toast.makeText(getActivity(), "当前无网络信号！", Toast.LENGTH_SHORT).show();

                            }


                        }

                    }
                });
            }
      //  }

    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setAddrType("all");//返回定位结果包含地址信息
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        option.disableCache(true);//禁止启用缓存定位
        //option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);

    }
}
