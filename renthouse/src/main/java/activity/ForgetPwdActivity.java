package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;

import application.MyApplication;
import bean.User;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import utils.UserDatabase;

/**
 * Created by user on 2019/8/21.
 */

public class ForgetPwdActivity extends AppCompatActivity{
    private EditText forget_edit_phone,forget_edit_inputcode,forget_edit_inputPwd,forget_edit_inputPwdagain;
    private Button btn_commit;
    private TextView forget_text_validate;
    UserDatabase helper;
    ArrayList<User> list;
    Boolean b=true;
    String phone = null;
    String inputcode = null;
    String inputPwd =null;
    String inputPwdAgain =null;
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
                forget_text_validate.setText(time+"后重新获取");
            }else if (what == 1){
                forget_text_validate.setEnabled(true);
                forget_text_validate.setText("获取验证码");
                flag = true;

            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//v7包下去除标题栏代码：
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(ForgetPwdActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_forgetpwd);

        forget_edit_phone = (EditText)findViewById(R.id.forget_edit_phone);
        forget_edit_inputcode = (EditText)findViewById(R.id.forget_edit_inputcode);
        forget_edit_inputPwd = (EditText)findViewById(R.id.forget_edit_inputPwd);
        forget_edit_inputPwdagain = (EditText)findViewById(R.id.forget_edit_inputPwdagain);
        forget_text_validate = (TextView)findViewById(R.id.forget_text_validate);
        btn_commit = (Button)findViewById(R.id.btn_commit);

        helper=new UserDatabase(this);
        list = helper.getAllUsers();

        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void click(View v){

        phone = forget_edit_phone.getText().toString();
        inputcode = forget_edit_inputcode.getText().toString();
        inputPwd = forget_edit_inputPwd.getText().toString();
        inputPwdAgain = forget_edit_inputPwdagain.getText().toString();
        switch (v.getId()) {

            case R.id.btn_commit:

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(inputcode) || TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(inputPwdAgain)) {

                    Toast.makeText(ForgetPwdActivity.this, "填入的信息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (inputPwd.equals(inputPwdAgain)) {
                        // 提交验证码，其中的code表示验证码，如“1357”
                        SMSSDK.submitVerificationCode("86", phone, inputcode);
                    }else{
                        Toast.makeText(ForgetPwdActivity.this, "两次密码设置不一致，请重新设置", Toast.LENGTH_SHORT).show();

                    }
                }


                break;

            case R.id.forget_iv_goback:
                finish();
                break;
            case R.id.register_text_validate:
                if (!TextUtils.isEmpty(phone)){
                    //sendCode(MyApplication.getContext());
                    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                    SMSSDK.getVerificationCode("86", phone);
                    //Toast.makeText(RegisterActivity.this, "正在获取验证码...", Toast.LENGTH_SHORT).show();
                    forget_text_validate.setEnabled(false);
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
                    Toast.makeText(ForgetPwdActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }




                break;
            default:
                break;
        }
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
                            Toast.makeText(ForgetPwdActivity.this, "验证完成", Toast.LENGTH_SHORT).show();
                            helper.add(phone, inputPwd);
                            startActivity(new Intent(ForgetPwdActivity.this,LogActivity.class));
                            finish();
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Toast.makeText(ForgetPwdActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
