package activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.user.renthouse.R;

import application.MyApplication;

/**
 * Created by user on 2019/7/30.
 */

public class SearchActivity  extends AppCompatActivity {
    private ImageView iv_goback;
    private SearchView mSearchView;
    private TextView tv_search;
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

        iv_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
