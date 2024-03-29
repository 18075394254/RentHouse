package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;

import bean.User;
import utils.UserDatabase;


public class LogActivity extends Activity {
	private EditText et_name;
	private EditText et_pwd;
	private TextView forgetPwdText,registerText;
	UserDatabase helper;
	ArrayList<User> list;
	Boolean b=false;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
		setContentView(R.layout.activity_login_avt);

		et_name=(EditText) findViewById(R.id.edit_userName);
		et_pwd=(EditText) findViewById(R.id.edit_password);
		forgetPwdText= (TextView) findViewById(R.id.forgetPwd);
		registerText = (TextView) findViewById(R.id.register_text);
		ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo

		
		helper=new UserDatabase(this);
		list = helper.getAllUsers();


	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.but_login:
			
			//获取输入框中的信息
			String name=et_name.getText().toString();
			String pwd=et_pwd.getText().toString();
			User user=new User(name, pwd);
			//验证账号密码
			//判断账号密码是否为空
			if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(pwd)){
				
				Toast.makeText(LogActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
			}else {
				

				for(int i=0;i<list.size();i++){
					if(list.get(i).getName().equals(name) && list.get(i).getPwd().equals(pwd)){
						
						Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(LogActivity.this, MainActivity.class));
						SharedPreferences sp= getSharedPreferences("user",
								Activity.MODE_PRIVATE);
						editor=sp.edit();
						editor.putString("name",name);
						editor.putString("pwd",pwd);
						editor.commit();
						finish();
						b=true;
					}
				}
				if(b==false) {
					Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
				}
				}

			break;
		case R.id.register_text:
			
			startActivityForResult(new Intent(LogActivity.this, RegisterActivity.class), 0);
			finish();
			break;

		case R.id.forgetPwd:

			startActivityForResult(new Intent(LogActivity.this,ForgetPwdActivity.class), 0);
			finish();
			break;
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
//land
		}
		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
//port
		}
	}
}
