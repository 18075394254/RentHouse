package mainfragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
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

import modelfragment.LazyFragment;
import myinterface.IBackInterface;

/**
 * Created by user on 2019/7/16.
 */

public class TieFragment extends LazyFragment {
    private View mView;
    private WebView mWebView;
    private String url;
    private IBackInterface backInterface;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tie,container,false);
        mWebView = mView.findViewById(R.id.webview);
        backInterface = (IBackInterface)getActivity();
        backInterface.setSelectedFragment(this);//将fragment传递到Activity中
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });
        readHtmlFormAssets();
        isPrepared = true;
        lazyLoad();
        return mView;
    }

    private void readHtmlFormAssets(){
        WebSettings webSettings =  mWebView.getSettings();

        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        //mWebView.setBackgroundColor(Color.TRANSPARENT);//  WebView 背景透明效果，不知道为什么在xml配置中无法设置？
        String url = "file:///android_asset/attestation1/index.html";

}
    /**
     * 用于返回是否需要实现监听
     */
    public boolean onBackPressed() {
        Toast.makeText(getActivity(),"cangoback = "+mWebView.canGoBack(),Toast.LENGTH_SHORT).show();
        if(mWebView.canGoBack()) {
            return true;//监听back键，用于处理自己的点击事件
        }
        return false;//原生back效果


    }
    public void onGoBack() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        }



    }


    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        if (mWebView != null)
            mWebView.loadUrl("http://www.zkzn.net");
    }


}
