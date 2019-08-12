package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.MyInfoAdapterInfo;
import decoration.SpacesItemDecoration;
import meadapter.MyInFoRecyclerAdapter;

/**
 * Created by user on 2019/8/12.
 */

public class MyInfoActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    List<MyInfoAdapterInfo> mDatas;
    private MyInFoRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(MyInfoActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_myinfo);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.myinfo_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20,0));

        mDatas = new ArrayList<>();
        mDatas.add(new MyInfoAdapterInfo("头像","",""));
        mDatas.add(new MyInfoAdapterInfo("昵称","那小子真帅",""));
        mDatas.add(new MyInfoAdapterInfo("性别","男",""));
        mDatas.add(new MyInfoAdapterInfo("生日","1992-10-17",""));
        mDatas.add(new MyInfoAdapterInfo("手机号","18075394254",""));
        mDatas.add(new MyInfoAdapterInfo("积分","1000",""));
        mDatas.add(new MyInfoAdapterInfo("微信号","wp591026264cyy",""));
        mDatas.add(new MyInfoAdapterInfo("职业","IT",""));
        mDatas.add(new MyInfoAdapterInfo("兴趣爱好","电子竞技",""));
         adapter = new MyInFoRecyclerAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new MyInFoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int postion) {

            }
        });


    }
}
