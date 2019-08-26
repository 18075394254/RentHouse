package mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import activity.BrowseActivity;
import activity.CollectionRecordsActivity;
import activity.ComplaintActivity;
import activity.ContactActivity;
import activity.CreditActivity;
import activity.MessageActivity;
import activity.MessageDetailsActivity;
import activity.ModifyActivity;
import activity.MyInfoActivity;
import activity.MyPostActivity;
import activity.MyPublishActivity;
import bean.MeAdapterInfoOne;
import bean.MeAdapterInfoTwo;
import decoration.SpacesItemDecoration;
import meadapter.MeRecyclerOneAdapter;
import meadapter.MeRecyclerTwoAdapter;

/**
 * Created by user on 2019/7/16.
 */

public class MeFragment extends Fragment {
    private View mView;
    private RecyclerView myOneRecyclerView;
    private RecyclerView myTwoRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MeRecyclerOneAdapter mOneAdapter;
    private MeRecyclerTwoAdapter mTwoAdapter;
    private RelativeLayout mRelativeLayout;

    String[] titles = new String[]{"信用认证","我的帖子","我的发布"};
    String[] titles2 = new String[]{"消息中心","浏览记录","我的收藏","关于我们","投诉建议","联系客服","账号设置"};
    int[] imgs = new int[]{R.drawable.mycertify, R.drawable.mypost, R.drawable.myissue};
    int[] imgs2 = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me,container,false);

        //初始化第一个RecyclerView
        initRecyclerView();
        //初始化第二个RecyclerView
        initTwoRecyclerView();

        mRelativeLayout = mView.findViewById(R.id.relativelayout_info);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
            }
        });
        return mView;
    }

    //初始化第二个RecyclerView
    private void initTwoRecyclerView() {
        myTwoRecyclerView = (RecyclerView) mView.findViewById(R.id.me_recyclerview_two);
        //设置每行的列数
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //添加数据
        List<MeAdapterInfoTwo> adapterInfos = new ArrayList<>();

        MeAdapterInfoTwo info = null;
        for (int i = 0; i < titles2.length; i++) {
            info = new MeAdapterInfoTwo();
            info.icon = imgs2[i];
            info.title = titles2[i];
            info.gotoimg = R.drawable.gotome;
            adapterInfos.add(info);
        }

        //设置adapter的数据
        mTwoAdapter = new MeRecyclerTwoAdapter(adapterInfos);
        myTwoRecyclerView.setAdapter(mTwoAdapter);
        myTwoRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        myTwoRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mTwoAdapter.setItemClickListener(new MeRecyclerTwoAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
               // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), MessageActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), BrowseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), CollectionRecordsActivity.class));

                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), MessageDetailsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), ComplaintActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), ContactActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), ModifyActivity.class));
                        break;
                }
            }
        });
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        myOneRecyclerView = (RecyclerView) mView.findViewById(R.id.me_recyclerview_one);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<MeAdapterInfoOne> adapterInfos = new ArrayList<>();

        MeAdapterInfoOne info = null;
        for (int i = 0; i < titles.length; i++) {
            info = new MeAdapterInfoOne();
            info.message = titles[i];
            info.img = imgs[i];
            adapterInfos.add(info);
        }

        //设置adapter的数据
        mOneAdapter = new MeRecyclerOneAdapter(adapterInfos);
        myOneRecyclerView.setAdapter(mOneAdapter);
        myOneRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        myOneRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mOneAdapter.setItemClickListener(new MeRecyclerOneAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
               // Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), CreditActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), MyPostActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), MyPublishActivity.class));
                        break;

                }

            }
        });
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());

    }
}
