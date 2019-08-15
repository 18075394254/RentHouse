package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.MessageAdapterInfo;
import decoration.SpacesItemDecoration;
import shouyeadapter.MessageRecyclerAdapter;

/**
 * Created by user on 2019/8/15.
 */

public class ContactActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageRecyclerAdapter mMessageAdapter;
    private ImageView backImage;
    private Button onlineBtn,phoneBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ContactActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_contact);
        initRecyclerView();

        backImage = (ImageView) findViewById(R.id.contact_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        onlineBtn = (Button)findViewById(R.id.contact_online);
        phoneBtn = (Button)findViewById(R.id.contact_phone);


    }
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.contact_recyclerview);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        //设置每行的列数
        //    mLayoutManager = new GridLayoutManager(DetailsActivity.this, 2, GridLayoutManager.VERTICAL, false);
        //添加数据
        List<MessageAdapterInfo> detailsInfos = new ArrayList<>();
        MessageAdapterInfo info = null;

        for (int i = 0; i < 8; i++) {
            info = new MessageAdapterInfo();
            info.title="常见问题";
            info.content = "问题描述问题描述问题描述问题描述问题描述";
            info.dateTime = "";
            detailsInfos.add(info);
        }

        //设置adapter的数据
        mMessageAdapter = new MessageRecyclerAdapter(detailsInfos);
        mRecyclerView.setAdapter(mMessageAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 5;
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mMessageAdapter.setListener(new MessageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void setOnitemClickListener(View view, int position) {

            }
        });
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}