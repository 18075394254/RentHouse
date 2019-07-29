package activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import myinterface.IBackInterface;
import shouyeadapter.MyFragmentAdapter;
import mainfragment.MeFragment;
import mainfragment.ShouYeFragment;
import mainfragment.TieFragment;
import mainfragment.UpLoadFragment;
import mainfragment.WishFragment;

public class MainActivity extends AppCompatActivity {
    public TabLayout mTabLayout;
    ViewPager mViewPager;
    private Fragment fragment; //用于传递监听back键的fragment
    String[] tab_titles = new String[]{"首页","心愿单","发布","求租贴","我的"};
    int[] tab_imgs = new int[]{R.drawable.tab_shouye,R.drawable.tab_wish,R.drawable.tab_upload,
    R.drawable.tab_tie,R.drawable.tab_me};
    List<Fragment> mFragments = new ArrayList<>();
    UpLoadFragment upLoadFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(MainActivity.this,R.color.tab_textColorSelect);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);

        //添加Fragment到list中
        upLoadFragment = new UpLoadFragment();
        mFragments.add(new ShouYeFragment());
        mFragments.add(new WishFragment());
        mFragments.add(upLoadFragment);
        mFragments.add(new TieFragment());
        mFragments.add(new MeFragment());

        initView();
    }

    /**
     * 初始化各控件
     */
    private void initView(){

        //viewpager加载adapter
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), mFragments, tab_titles));
        //viewPager事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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
        //TabLayout加载viewpager
        //一行代码和ViewPager联动起来，简单粗暴。
        mTabLayout.setupWithViewPager(mViewPager);
        Drawable d = null;
        for (int i = 0; i < mTabLayout.getTabCount(); i++){
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
        /*    //通过相应的布局文件获取view
            View view = View.inflate(this, R.layout.tab_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.tab_imageView);
            TextView tv = (TextView) view.findViewById(R.id.tab_textView);
            //设置tab栏文字
            tv.setText(tab_titles[i]);
            //设置tab图片
            iv.setImageResource(tab_imgs[i]);
            //给tab设置view
            tab.setCustomView(view);*/

           switch (i){
                case 0:
                    d = getResources().getDrawable(R.drawable.tab_shouye);
                    break;
                case 1:
                    d = getResources().getDrawable(R.drawable.tab_wish);
                    break;
                case 2:
                    d = getResources().getDrawable(R.drawable.tab_upload);
                    break;
                case 3:
                    d = getResources().getDrawable(R.drawable.tab_tie);
                    break;
                case 4:
                    d = getResources().getDrawable(R.drawable.tab_me);
                    break;
            }
            tab.setIcon(d);
        }
    }




}
