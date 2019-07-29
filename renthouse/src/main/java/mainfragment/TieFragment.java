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

/**
 * Created by user on 2019/7/16.
 */

public class TieFragment extends Fragment {
    private View mView;
    private WebView mWebView;
    private String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tie,container,false);
        mWebView = mView.findViewById(R.id.webview);

        readHtmlFormAssets();
        return mView;
    }

    private void readHtmlFormAssets(){
        WebSettings webSettings =  mWebView.getSettings();

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        mWebView.setBackgroundColor(Color.TRANSPARENT);//  WebView 背景透明效果，不知道为什么在xml配置中无法设置？
        mWebView.loadUrl("file:///android_asset/index.html");
}
}
