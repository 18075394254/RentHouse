package activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.HashMap;

import application.MyApplication;
import bean.User;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import utils.UserDatabase;


public class RegisterActivity extends AppCompatActivity {
	private EditText register_edit_phone;
	private EditText register_edit_inputcode;
	private EditText register_edit_inputPwd;
	private TextView register_text_validate;
	private RadioButton mRadioButton;
	UserDatabase helper;
	ArrayList<User> list;
	Boolean b=true;
	String phone = null;
	String inputcode = null;
	String inputPwd =null;
	public boolean flag = true;
	int time = 60;
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			Log.i("register","what = "+what);
			if (what == 0 ){

				int time = msg.arg1;
				register_text_validate.setText(time+"后重新获取");
			}else if (what == 1){
				register_text_validate.setEnabled(true);
				register_text_validate.setText("获取验证码");
				flag = true;

			}

		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//v7包下去除标题栏代码：
		getSupportActionBar().hide();
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MyApplication.setWindowStatusBarColor(RegisterActivity.this,R.color.tab_textColorSelect);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
		setContentView(R.layout.activity_register_avt);
		register_edit_phone = (EditText) findViewById(R.id.register_edit_phone);
		register_edit_inputcode = (EditText) findViewById(R.id.register_edit_inputcode);
		register_edit_inputPwd = (EditText) findViewById(R.id.register_edit_inputPwd);
		register_text_validate = (TextView) findViewById(R.id.register_text_validate);
		mRadioButton = (RadioButton) findViewById(R.id.radioButton);

		helper=new UserDatabase(this);
		list = helper.getAllUsers();

		// 注册一个事件回调，用于处理SMSSDK接口请求的结果
		SMSSDK.registerEventHandler(eventHandler);


	}
	public void click(View v){

		phone=register_edit_phone.getText().toString();
		inputcode=register_edit_inputcode.getText().toString();
		inputPwd=register_edit_inputPwd.getText().toString();

		switch (v.getId()) {

		case R.id.btn_register:

					if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(inputcode) || TextUtils.isEmpty(inputPwd)) {

						Toast.makeText(RegisterActivity.this, "填入的信息不能为空", Toast.LENGTH_SHORT).show();
					} else {
						if (mRadioButton.isSelected()) {
							// 提交验证码，其中的code表示验证码，如“1357”
							SMSSDK.submitVerificationCode("86", phone, inputcode);
						}else{
							Toast.makeText(RegisterActivity.this, "请勾选已阅读用户协议", Toast.LENGTH_SHORT).show();

						}
					}


			break;

			case R.id.register_iv_goback:
				finish();
				break;
			case R.id.register_text_validate:
			if (!TextUtils.isEmpty(phone)){
				//sendCode(MyApplication.getContext());
				// 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
				SMSSDK.getVerificationCode("86", phone);
				//Toast.makeText(RegisterActivity.this, "正在获取验证码...", Toast.LENGTH_SHORT).show();
				register_text_validate.setEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (flag) {
							time--;

							Log.i("register","time = "+time);
							if (time != 0) {
								Message message = new Message();
								message.what = 0;
								message.arg1 = time;
								mHandler.sendMessage(message);

							} else {
								Message message = new Message();
								message.what = 1;
								mHandler.sendMessage(message);
								flag = false;
								time = 60;

							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();

			}else{
				Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
			}




				break;
		default:
			break;
		}
	}

	public void sendCode(Context context) {
		RegisterPage page = new RegisterPage();
		//如果使用我们的ui，没有申请模板编号的情况下需传null
		page.setTempCode(null);
		page.setRegisterCallback(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 处理成功的结果
					HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
					String country = (String) phoneMap.get("country"); // 国家代码，如“86”
					String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
					// TODO 利用国家代码和手机号码进行后续的操作
				} else{
					// TODO 处理错误的结果
				}
			}
		});
		page.show(context);
	}
	// 使用完EventHandler需注销，否则可能出现内存泄漏
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterEventHandler(eventHandler);
		flag = false;
	}
	EventHandler eventHandler = new EventHandler() {
		public void afterEvent(int event, int result, Object data) {
			// afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
			Message msg = new Message();
			msg.arg1 = event;
			msg.arg2 = result;
			msg.obj = data;
			new Handler(Looper.getMainLooper(), new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					int event = msg.arg1;
					int result = msg.arg2;
					Object data = msg.obj;
					//回调完成
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						//提交验证码成功

						if (result == SMSSDK.RESULT_COMPLETE) {
							// TODO 处理验证码验证通过的结果
							// 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
							Toast.makeText(RegisterActivity.this, "验证完成", Toast.LENGTH_SHORT).show();
							helper.add(phone, inputPwd);
							startActivity(new Intent(RegisterActivity.this,LogActivity.class));
							finish();
						} else {
							// TODO 处理错误的结果
							((Throwable) data).printStackTrace();
							Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
						}
					}else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
						//获取验证码成功

					}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
						//返回支持发送验证码的国家列表

					}
					// TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
					return false;
				}
			}).sendMessage(msg);
		}
	};

}
