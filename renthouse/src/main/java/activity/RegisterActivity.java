package activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;

import application.MyApplication;
import bean.User;
import utils.UserDatabase;


public class RegisterActivity extends AppCompatActivity {
	private EditText et_name;
	private EditText et_pwd;
	private EditText et_pwd2;
	UserDatabase helper;
	ArrayList<User> list;
	Boolean b=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//v7包下去除标题栏代码：
		getSupportActionBar().hide();
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MyApplication.setWindowStatusBarColor(RegisterActivity.this,R.color.tab_textColorSelect);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
		setContentView(R.layout.activity_register_avt);

		helper=new UserDatabase(this);
		list = helper.getAllUsers();
		
	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.btn_register:

			String name=et_name.getText().toString();
			String pwd=et_pwd.getText().toString();
			String pwd2=et_pwd2.getText().toString();
			if(!pwd.equals(pwd2)){
				Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新设置", Toast.LENGTH_SHORT).show();
			}else {

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getName().equals(name)) {
						Toast.makeText(this, "此用户名已被注册，请重新输入", Toast.LENGTH_SHORT).show();
						b = false;
					}
				}

				if (b) {
					if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {

						Toast.makeText(RegisterActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
					} else {
						if (name != null) {
							helper.add(name, pwd);
						}

						Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

						startActivity(new Intent(RegisterActivity.this, LogActivity.class));

						finish();
					}

				}
			}
			break;

			case R.id.register_iv_goback:
				finish();
				break;

		default:
			break;
		}
	}
}
