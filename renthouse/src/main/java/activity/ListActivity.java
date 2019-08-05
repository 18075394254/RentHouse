package activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import application.MyApplication;
import bean.FiveAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.RecyclerFiveAdapter;
import utils.MyNestedScrollView;

/**
 * Created by user on 2019/8/5.
 */

public class ListActivity extends AppCompatActivity {
    String[] tab_titles = new String[]{"地铁","区域","租金","排序"};
    private TabLayout mTabLayout;
    private XRecyclerView mRecyclerView;
    private MyNestedScrollView nestedSV;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<FiveAdapterInfo> fiveInfos;
    private RecyclerFiveAdapter mFiveAdapter;
    private int times =0;
    private ImageView mBackImage,mCardImage;
    private LinearLayout mRootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ListActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_list);
        initTablayoutView();
        initTwoRecyclerView();
        mBackImage = (ImageView) findViewById(R.id.list_iv_goback);
        mCardImage = (ImageView) findViewById(R.id.list_cardimageView);
        mRootView = (LinearLayout) findViewById(R.id.list_rootLin);

        Glide.with(MyApplication.getContext()).
                load("http://pic30.photophoto.cn/20140313/0040039490056765_b.jpg").
                placeholder(R.mipmap.card).error(R.mipmap.card).into(mCardImage);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void initTwoRecyclerView() {

        mRecyclerView = (XRecyclerView) findViewById(R.id.list_RecyclerView);
        nestedSV = (MyNestedScrollView) findViewById(R.id.list_nestedSV);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView
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
        mRecyclerView.setAdapter(mFiveAdapter);


        mLinearLayoutManager = new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mFiveAdapter.setListener(new RecyclerFiveAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {
                Toast.makeText(ListActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();

            }
        });

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        //  fiveRecyclerView.setLimitNumberToCallLoadMore(2);
        final int itemLimit = 2;
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                            if(mRecyclerView != null) {
                                mRecyclerView.loadMoreComplete();
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
                            if(mRecyclerView != null) {
                                mRecyclerView.setNoMore(true);
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
                    mRecyclerView.reload();
                }
            }
        });


    }
    private void initTablayoutView() {

        mTabLayout = (TabLayout) findViewById(R.id.list_tablayout);

        for (int i =0;i < tab_titles.length;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(tab_titles[i]));
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
