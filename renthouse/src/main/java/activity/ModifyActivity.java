package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.renthouse.R;

import application.MyApplication;

/**
 * Created by user on 2019/8/15.
 */

public class ModifyActivity extends AppCompatActivity {
    private Button exitLoginBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ModifyActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_modify);

        exitLoginBtn = (Button) findViewById(R.id.modify_btn_exitLogin);
        exitLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ModifyActivity.this,LogActivity.class));
            }
        });
    }
}
