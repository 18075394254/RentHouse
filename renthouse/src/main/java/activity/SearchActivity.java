package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.AdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.SearchRecyclerAdapter;
import shouyeadapter.SearchRecyclerTwoAdapter;

/**
 * Created by user on 2019/7/30.
 */

public class SearchActivity  extends AppCompatActivity {
    private ImageView iv_goback;
    private SearchView mSearchView;
    private TextView tv_search;
    private XRecyclerView mRecyclerView_hot,mRecyclerView_his;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchRecyclerAdapter mRecyclerAdapter;
    private SearchRecyclerTwoAdapter mRecyclerTwoAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(SearchActivity.this,R.color.tab_textColorSelect);
        setContentView(R.layout.activity_search);
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        mSearchView = (SearchView) findViewById(R.id.search_searchView);
        tv_search = (TextView) findViewById(R.id.tv_search);

        initOneRecyclerView();
        initTwoRecyclerView();


        iv_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    //第一个RecyclerView
    private void initOneRecyclerView() {
        mRecyclerView_hot = (XRecyclerView) findViewById(R.id.search_hot_recycler);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<String> oneInfos = new ArrayList<>();
        AdapterInfo info = null;
        for (int i = 0; i < 8; i++) {

            oneInfos.add("标签");
        }

        //设置adapter的数据
        mRecyclerAdapter = new SearchRecyclerAdapter(oneInfos);
        mRecyclerView_hot.setAdapter(mRecyclerAdapter);
        mRecyclerView_hot.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView_hot.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mRecyclerAdapter.setItemClickListener(new SearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                Toast.makeText(SearchActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView_hot.setLoadingMoreEnabled(false);
        mRecyclerView_hot.setPullRefreshEnabled(false);
    }

    //第二个RecyclerView
    private void initTwoRecyclerView() {
        mRecyclerView_his = (XRecyclerView) findViewById(R.id.search_his_recycler);
        //设置每行的列数
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //添加数据
        List<String> oneInfos = new ArrayList<>();

        for (int i = 0; i < 8; i++) {

            oneInfos.add("搜索名称");
        }

        //设置adapter的数据
        mRecyclerTwoAdapter = new SearchRecyclerTwoAdapter(oneInfos);
        mRecyclerView_his.setAdapter(mRecyclerTwoAdapter);
        mRecyclerView_his.setLayoutManager(mLinearLayoutManager
        );

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        //mRecyclerView_his.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mRecyclerTwoAdapter.setItemClickListener(new SearchRecyclerTwoAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                Toast.makeText(SearchActivity.this,"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView_his.setLoadingMoreEnabled(false);
        mRecyclerView_his.setPullRefreshEnabled(false);
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
