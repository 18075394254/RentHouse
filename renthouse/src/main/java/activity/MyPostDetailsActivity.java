package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.user.renthouse.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.MyPostDetailsAdapterInfo;
import decoration.SpacesItemDecoration;
import loader.GlideImageLoader;
import meadapter.MyPostDetailsAdapter;

/**
 * Created by user on 2019/8/26.
 */

public class MyPostDetailsActivity extends AppCompatActivity implements OnBannerListener {
    private Banner banner;
    ListView mListView;
    ArrayAdapter<String> adapter;
    String[] datas={"标题：标题内容","预算：这是预算金额","性别限制：女","入住时间：2018-07-08可入住","位置：位置信息"};

    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyPostDetailsAdapter mPostDeatailsAdapter;
    private ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(MyPostDetailsActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_mypost_details);

        initBanner();
        initListView();
        initRecyclerView();

        backImage = (ImageView) findViewById(R.id.post_details_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.mypost_details_recycler);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

            //设置每行的列数
            //    mLayoutManager = new GridLayoutManager(DetailsActivity.this, 2, GridLayoutManager.VERTICAL, false);
            //添加数据
            List<MyPostDetailsAdapterInfo> detailsInfos = new ArrayList<>();
            MyPostDetailsAdapterInfo info = null;

            for (int i = 0; i < 8; i++) {
                info = new MyPostDetailsAdapterInfo();
               
                detailsInfos.add(info);
            }

            //设置adapter的数据
            mPostDeatailsAdapter = new MyPostDetailsAdapter(detailsInfos);
            mRecyclerView.setAdapter(mPostDeatailsAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);

            //添加ItemDecoration，item之间的间隔
            int leftRight = dip2px(15);
            int topBottom = 5;
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

            mPostDeatailsAdapter.setItemClickListener(new MyPostDetailsAdapter.OnItemClickListener() {
                @Override
                public void setOnItemClickListener(View view, int postion) {

                }
            });

    }


    private void initListView() {
        mListView = (ListView) findViewById(R.id.mypost_details_listView);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        mListView.setAdapter(adapter);
    }

    //初始书ViewPager轮播
    private void initBanner() {
        banner = (Banner) findViewById(R.id.mypost_details_banner);
        banner.setImages(MyApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(MyPostDetailsActivity.this)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
     /*   banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);*/
    }

    @Override
    public void OnBannerClick(int position) {

    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

}
