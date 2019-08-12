package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import mainfragment.WishFragment;
import mefragment.ComplaintFragment;
import mefragment.PublicityFragment;
import mefragment.SuggestFragment;
import shouyeadapter.MyFragmentAdapter;
import wishfragment.CollectionFragment;
import wishfragment.LookHouseFragment;

/**
 * Created by user on 2019/8/12.
 */

public class ComplaintActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"投诉", "建议", "投诉公示"};
    SuggestFragment mSuggestFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ComplaintActivity.this, R.color.tab_textColorSelect);

        setContentView(R.layout.activity_complaint);

       // initViewPagerAndTabLayout();
        //实例化
        mViewPager = (ViewPager) findViewById(R.id.activity_complaint_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_complaint_tablayout);
        //页面，数据源
        list = new ArrayList<>();
        list.add(new ComplaintFragment());
        list.add(new SuggestFragment());
        list.add(new PublicityFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager(), list, titles);
        mViewPager.setAdapter(adapter);
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPagerAndTabLayout() {
        //实例化
        mTabLayout = (TabLayout) findViewById(R.id.activity_complaint_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_complaint_viewpager);


        //页面，数据源
        list = new ArrayList<>();
        mSuggestFragment = new SuggestFragment();
        list.add(new ComplaintFragment());
        list.add(mSuggestFragment);
        list.add(new PublicityFragment());
        //ViewPager的适配器
        //viewpager加载adapter
        adapter = new MyAdapter(getSupportFragmentManager(), list, titles);
        mViewPager.setAdapter(adapter);

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
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            //通过相应的布局文件获取view
            View view = View.inflate(this, R.layout.complaint_textitem, null);
            TextView tv = (TextView) view.findViewById(R.id.complaint_text_item);
            //设置tab栏文字
            tv.setText(titles[i]);
           // tab.setText()
            //给tab设置view
            tab.setCustomView(view);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, String[] titles) {
            super(fragmentManager);

        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(ComplaintActivity.this," ComplaintActivity resultCode  = "+resultCode,Toast.LENGTH_SHORT).show();
        /*然后在碎片中调用重写的onActivityResult方法*/
      //  mSuggestFragment.onActivityResult(requestCode, resultCode, data);
        adapter.getItem(1).onActivityResult(requestCode, resultCode, data);
    }
}
