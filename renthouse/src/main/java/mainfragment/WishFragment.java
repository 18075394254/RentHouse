package mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import wishfragment.CollectionFragment;
import wishfragment.LookHouseFragment;

/**
 * Created by user on 2019/7/16.
 */

public class WishFragment extends Fragment {
    private View mView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"收藏房源", "看房行程"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_wish,container,false);
        //实例化
        viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) mView.findViewById(R.id.tablayout);
        //页面，数据源
        list = new ArrayList<>();
        list.add(new CollectionFragment());
        list.add(new LookHouseFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);

        return mView;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
