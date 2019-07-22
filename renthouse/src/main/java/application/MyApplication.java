package application;

/**
 * Created by user on 2019/7/17.
 */
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.example.user.renthouse.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyApplication extends Application {
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();
    public static int H,W;
    public static Application app;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        app=this;
        getScreen(this);

        String[] urls = getResources().getStringArray(R.array.url);

        List list = Arrays.asList(urls);
        images = new ArrayList(list);

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
}