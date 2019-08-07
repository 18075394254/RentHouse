package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import shouyeadapter.RoomSourceAdapter;
import utils.MyNestedScrollView;

public class RoomSourceActivity extends AppCompatActivity {

    public SearchView mSearchView;
    private ImageView backImage;
    private RoomSourceAdapter mRoomSourceOneAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mOneRecyclerView;
    private XRecyclerView mTwoRecyclerView;
    private MyNestedScrollView nestedSV;
    private LinearLayoutManager mLinearLayoutManager;
    List<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    LinearLayout searchRootView;
    private int times = 0;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> listStrings;
    private TabLayout mOneTabLayout,mTwoTabLayout;
    String[] tab_titles_one = new String[]{"全部","单间","整套","公寓","月付"};
    String[] tab_titles_two = new String[]{"地铁","区域","租金","排序"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(RoomSourceActivity.this,R.color.tab_textColorSelect);
        setContentView(R.layout.activity_roomsource);

        initSearchView();

        initTablayoutView();

        initOneRecyclerView();

        initTwoRecyclerView();

        backImage = (ImageView) findViewById(R.id.iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomSourceActivity.this.finish();
            }
        });
    }

    private void initTwoRecyclerView() {

        mTwoRecyclerView = (XRecyclerView) findViewById(R.id.roomsource_twoRecyclerView);
                nestedSV = (MyNestedScrollView) findViewById(R.id.roomsource_nestedSV);
        mTwoRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mTwoRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mTwoRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mTwoRecyclerView
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
                mFiveAdapter = new RecyclerFiveAdapter(fiveInfos,MyApplication.type3);
        mTwoRecyclerView.setAdapter(mFiveAdapter);


                mLinearLayoutManager = new LinearLayoutManager(RoomSourceActivity.this, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };

        mTwoRecyclerView.setLayoutManager(mLinearLayoutManager);

                //添加ItemDecoration，item之间的间隔
                int leftRight = dip2px(15);
                int topBottom = 0;
        mTwoRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

                mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
                    @Override
                    public void setOnitemClickListener(View view, int position) {
                       // Toast.makeText(RoomSourceActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RoomSourceActivity.this, DetailsActivity.class));
                    }
                });

                // When the item number of the screen number is list.size-2,we call the onLoadMore
                //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
                final int itemLimit = 2;
        mTwoRecyclerView.setPullRefreshEnabled(false);
        mTwoRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                                    if(mTwoRecyclerView != null) {
                                        mTwoRecyclerView.loadMoreComplete();
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
                                    if(mTwoRecyclerView != null) {
                                        mTwoRecyclerView.setNoMore(true);
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
                            mTwoRecyclerView.reload();
                        }
                    }
                });


    }

    private void initOneRecyclerView() {
        //第四个RecyclerView

        mOneRecyclerView = (RecyclerView)findViewById(R.id.roomsource_oneRecyclerView);
            //设置每行的列数
            mLayoutManager = new GridLayoutManager(RoomSourceActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
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
        mRoomSourceOneAdapter = new RoomSourceAdapter(fourInfos);
            mOneRecyclerView.setAdapter(mRoomSourceOneAdapter);
            mOneRecyclerView.setLayoutManager(mLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 0;
        mOneRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mRoomSourceOneAdapter.setItemClickListener(new RoomSourceAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                Toast.makeText(RoomSourceActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initTablayoutView() {
      mOneTabLayout = (TabLayout) findViewById(R.id.roomsource_onetablayout);
        mTwoTabLayout = (TabLayout) findViewById(R.id.roomsource_twotablayout);
        for (int i =0;i < tab_titles_one.length;i++){
            mOneTabLayout.addTab(mOneTabLayout.newTab().setText(tab_titles_one[i]));
        }
        for (int i =0;i < tab_titles_two.length;i++){
            mTwoTabLayout.addTab(mTwoTabLayout.newTab().setText(tab_titles_two[i]));
        }
        //TabLayout的事件
        mOneTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void initSearchView() {

        searchRootView = (LinearLayout) findViewById(R.id.rootSearchView);
        mSearchView = (SearchView) findViewById(R.id.roomsource_searchView);
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
