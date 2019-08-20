package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.user.renthouse.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import application.MyApplication;
import bean.FiveAdapterInfo;
import shouyeadapter.MyFragmentAdapter;
import shouyeadapter.RecyclerFiveAdapter;
import shouyeadapter.RecyclerOneAdapter;
import shouyefragment.HotFragment;
import shouyefragment.OtherFragment;
import utils.MyNestedScrollView;

/**
 * Created by user on 2019/8/20.
 */

public class ApartmentSourceActivity extends AppCompatActivity{

    String[] tab_titles = new String[]{"热门房型","其他门店"};
    private TabLayout mTabLayout;
    private ArrayList<Fragment> list;
    private ViewPager viewPager;
    private ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ApartmentSourceActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_apartmet_source);
        //initTablayoutView();
        initViewPager();

        backImage = (ImageView)findViewById(R.id.apartment_source_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViewPager() {
        //实例化
        viewPager = (ViewPager)findViewById(R.id.apartment_source_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.apartment_source_tablayout);
        //页面，数据源
        list = new ArrayList<>();
        list.add(new HotFragment());
        list.add(new OtherFragment());

        //viewpager加载adapter
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), list, tab_titles));

        //绑定
        mTabLayout.setupWithViewPager(viewPager);
    }



}
