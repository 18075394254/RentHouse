package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.FiveAdapterInfo;
import bean.RoomSouarceInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.RecyclerFiveAdapter;
import shouyeadapter.RecyclerOneAdapter;
import shouyeadapter.RoomSourceAdapter;
import utils.MyNestedScrollView;

/**
 * Created by user on 2019/8/20.
 */

public class BrandApartmentActivity extends AppCompatActivity{
    private RecyclerOneAdapter mOneAdapter;
    private RecyclerView mRecyclerView;
    private XRecyclerView mXRecyclerView;
    private MyNestedScrollView nestedSV;
    private LinearLayoutManager mLinearLayoutManager;
    private RoomSourceAdapter mRoomSourceOneAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout searchRootView;
    public SearchView mSearchView;
    String[] tab_titles_two = new String[]{"地铁","区域","租金","排序"};
    private TabLayout mTabLayout;
    private ArrayList<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    private int times = 0;
    private ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(BrandApartmentActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_apartment);
        initSearchView();
        initOneRecyclerView();
        initTablayoutView();
        initTwoRecyclerView();
        backImage = (ImageView)findViewById(R.id.apartment_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initSearchView() {

        searchRootView = (RelativeLayout) findViewById(R.id.apartment_searchRoot);
        mSearchView = (SearchView) findViewById(R.id.apartment_searchView);
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
        Log.i("room","id = "+id);
        TextView textView = (TextView) mSearchView.findViewById(id);
        if (textView != null) {
            textView.setTextSize(15);//字体、提示字体大小
            textView.setHintTextColor(Color.GRAY);//提示字体颜色
        }
        mSearchView.setIconified(false);//一开始处于展开状态
        mSearchView.setEnabled(false);
    }
    private void initOneRecyclerView() {


          mRecyclerView = (RecyclerView)findViewById(R.id.apartment_recyclerview);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(BrandApartmentActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        //添加数据
        List<RoomSouarceInfo> fourInfos = new ArrayList<>();
        RoomSouarceInfo info = null;
        String[] urls = getResources().getStringArray(R.array.url4);

        for (int i = 0; i < urls.length; i++) {
            info = new RoomSouarceInfo();

            info.url = urls[i];
            fourInfos.add(info);
        }

        //设置adapter的数据
        mRoomSourceOneAdapter = new RoomSourceAdapter(fourInfos,MyApplication.type1);
        mRecyclerView.setAdapter(mRoomSourceOneAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mRoomSourceOneAdapter.setItemClickListener(new RoomSourceAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                //Toast.makeText(RoomSourceActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BrandApartmentActivity.this,ApartmentSourceActivity.class));
            }
        });

    }
    private void initTablayoutView() {
        mTabLayout = (TabLayout) findViewById(R.id.apartment_tablayout);


        for (int i =0;i < tab_titles_two.length;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(tab_titles_two[i]));
        }
        //TabLayout的事件
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }
        });

      /*  for (int i = 0; i < mOneTabLayout.getTabCount(); i++){
            TabLayout.Tab tab = mOneTabLayout.getTabAt(i);
            //通过相应的布局文件获取view
            View view = View.inflate(this, R.layout.tab_roomsource_item, null);
            TextView tv = (TextView) view.findViewById(R.id.tab_textView);
            //设置tab栏文字
            tv.setText(tab_titles[i]);
            }*/


    }
    private void initTwoRecyclerView() {

        mXRecyclerView = (XRecyclerView) findViewById(R.id.apartment_twoRecyclerView);
        nestedSV = (MyNestedScrollView) findViewById(R.id.apratment_nestedSV);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mXRecyclerView
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
        mFiveAdapter = new RecyclerFiveAdapter(fiveInfos, MyApplication.type5);
        mXRecyclerView.setAdapter(mFiveAdapter);


        mLinearLayoutManager = new LinearLayoutManager(BrandApartmentActivity.this, LinearLayoutManager.VERTICAL, false) {
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
                startActivity(new Intent(BrandApartmentActivity.this, DetailsActivity.class));
            }
        });

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
        final int itemLimit = 2;
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        searchRootView.setFocusable(true);

        searchRootView.setFocusableInTouchMode(true);

        searchRootView.requestFocus();
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
