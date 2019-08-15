package application;

/**
 * Created by user on 2019/7/17.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.user.renthouse.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.mark.multiimage.core.PreferencesUtils;


public class MyApplication extends Application {
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();
    public static int H,W;
    public static Application app;
    private static Context context;
    public static int type1 = 1;
    public static int type2 = 2;
    public static int type3 = 3;
    public static int IMAGE_PHONE = 0;
    public static int IMAGE_CAMERA = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        app=this;
        getScreen(this);

        String[] urls = getResources().getStringArray(R.array.url);

        List list = Arrays.asList(urls);
        images = new ArrayList(list);

        PreferencesUtils.init(this, "multiimage");

    }
    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H=dm.heightPixels;
        W=dm.widthPixels;
    }

    public static Context getContext(){
        return context;
    }

    /** 获取屏幕宽度 */
    public static int getWindowWidth() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width=wm.getDefaultDisplay().getWidth();
        Log.i("ooo", "width = " + width);
        return width;
    }
    /** 获取屏幕高度 */
    @SuppressWarnings("deprecation")
    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height=wm.getDefaultDisplay().getHeight();
        Log.i("ooo", "height = " + height);
        return height;
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                //顶部状态栏
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}