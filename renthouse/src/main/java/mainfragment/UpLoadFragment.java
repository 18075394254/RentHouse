package mainfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.user.renthouse.R;

import myinterface.IBackInterface;

/**
 * Created by user on 2019/7/16.
 */

public class UpLoadFragment extends Fragment {
    private View mView;
    private WebView mWebView;
    private String url;
    private IBackInterface backInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_upload,container,false);
        mWebView = mView.findViewById(R.id.webview);
        backInterface = (IBackInterface)getActivity();
        backInterface.setSelectedFragment(this);//将fragment传递到Activity中
        url = "file:///android_asset/attestation1/index.html";

        loadLocalHtml(url);



        return mView;
    }


    /**
     * 用于返回是否需要实现监听
     */
    public void onBackPressed() {
       /* if(判断条件) {
            return true;//监听back键，用于处理自己的点击事件
        }
        return false;//原生back效果*/
        if (mWebView.canGoBack()) {
            mWebView.goBack();//返回上一页面

        }
    }
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
     public void loadLocalHtml(String url){
                 WebSettings ws = mWebView.getSettings();
                 ws.setJavaScriptEnabled(true);//开启JavaScript支持
                    mWebView.setWebViewClient(new WebViewClient(){
             @Override
             public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                 //重写此方法，用于捕捉页面上的跳转链接
                                 if ("http://start/".equals(url)){
                                         //在html代码中的按钮跳转地址需要同此地址一致
                                         Toast.makeText(getActivity(), "开始体验", Toast.LENGTH_SHORT).show();
                                        //finish();
                                    }
                                return true;
                             }
         });
                 mWebView.loadUrl(url);

    }

}
