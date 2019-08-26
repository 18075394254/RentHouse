package activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.user.renthouse.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.CreditAdapterInfo;
import cn.com.mark.multiimage.core.ImageMainActivity;
import decoration.SpacesItemDecoration;
import meadapter.CreditAdapter;
import utils.FileUtil;

/**
 * Created by user on 2019/8/26.
 */

public class CreditActivity extends AppCompatActivity{
    private RecyclerView mRecyclerViewOne,mRecyclerViewTwo,mRecyclerViewThree;
    private GridLayoutManager mLayoutManager;
    List<CreditAdapterInfo> infos;
    private CreditAdapter mCreditAdapter;
    private static  int CAMERA_CODE = 10086;//相机
    private static  int PHOTO_CODE = 10010;//相册
    int[] images = new int[]{R.drawable.credit,R.drawable.cradexample,R.drawable.contract,R.drawable.wordcard};
    private ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApplication.setWindowStatusBarColor(CreditActivity.this,R.color.tab_textColorSelect);

        setContentView(R.layout.activity_credit);
        initOneRecyclerView();
        initTwoRecyclerView();
        initThreeRecyclerView();

        backImage = (ImageView)findViewById(R.id.credit_iv_goback);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initThreeRecyclerView() {
        mRecyclerViewThree = (RecyclerView) findViewById(R.id.credit_recyclerthree);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(CreditActivity.this, 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        infos = new ArrayList<>();
        CreditAdapterInfo info = null;
            info = new CreditAdapterInfo();
            info.image = images[0];
            infos.add(info);
        info = new CreditAdapterInfo();
        info.image = images[2];
        infos.add(info);

        //设置adapter的数据
        mCreditAdapter = new CreditAdapter(infos);
        mRecyclerViewThree.setAdapter(mCreditAdapter );
        mRecyclerViewThree.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 15;
        mRecyclerViewThree.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mCreditAdapter.setItemClickListener(new CreditAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                if(position == 0) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(CreditActivity.this);
                    builder.setItems(new String[]{"拍照", "从手机相册里选择"}, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) // 拍照
                            {

                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA_CODE);

                            } else if (which == 1) // 从手机相册选择
                            {
                                Intent intent = new Intent(CreditActivity.this, ImageMainActivity.class);
                                intent.putExtra("action-original", true);
                                intent.putExtra("MAX_SEND", 3 - infos.size());
                                startActivityForResult(intent, PHOTO_CODE);

                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }else{

                }
            }
        });
    }

    private void initTwoRecyclerView() {
        mRecyclerViewTwo = (RecyclerView) findViewById(R.id.credit_recyclertwo);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(CreditActivity.this, 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        infos = new ArrayList<>();
        CreditAdapterInfo info = null;


            info = new CreditAdapterInfo();
            info.image = images[0];
            infos.add(info);

        info = new CreditAdapterInfo();
        info.image = images[1];
        infos.add(info);

        info = new CreditAdapterInfo();
        info.image = images[3];
        infos.add(info);


        //设置adapter的数据
        mCreditAdapter = new CreditAdapter(infos);
        mRecyclerViewTwo.setAdapter(mCreditAdapter );
        mRecyclerViewTwo.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 15;
        mRecyclerViewTwo.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mCreditAdapter.setItemClickListener(new CreditAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                if(position == 0) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(CreditActivity.this);
                    builder.setItems(new String[]{"拍照", "从手机相册里选择"}, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) // 拍照
                            {

                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA_CODE);

                            } else if (which == 1) // 从手机相册选择
                            {
                               Intent intent = new Intent(CreditActivity.this, ImageMainActivity.class);
                                intent.putExtra("action-original", true);
                                intent.putExtra("MAX_SEND", 3 - infos.size());
                                startActivityForResult(intent, PHOTO_CODE);

                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }else{

                }
            }
        });
    }

    //初始化RecyclerView
    private void initOneRecyclerView() {
        mRecyclerViewOne = (RecyclerView) findViewById(R.id.credit_recyclerone);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(CreditActivity.this, 2, GridLayoutManager.VERTICAL, false);
        //添加数据
        infos = new ArrayList<>();
        CreditAdapterInfo info = null;

        for (int i = 0; i < 2; i++) {
            info = new CreditAdapterInfo();
            info.image = images[0];
            infos.add(info);
        }

        //设置adapter的数据
        mCreditAdapter = new CreditAdapter(infos);
        mRecyclerViewOne.setAdapter(mCreditAdapter );
        mRecyclerViewOne.setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 15;
        mRecyclerViewOne.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mCreditAdapter.setItemClickListener(new CreditAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreditActivity.this);
                    builder.setItems(new String[]{"拍照", "从手机相册里选择"}, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) // 拍照
                            {

                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA_CODE);

                            } else if (which == 1) // 从手机相册选择
                            {
                                Intent intent = new Intent(CreditActivity.this, ImageMainActivity.class);
                                intent.putExtra("action-original", true);
                                intent.putExtra("MAX_SEND", 1);
                                startActivityForResult(intent, PHOTO_CODE);

                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();

            }
        });
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    Log.i("suggest", "requestCode = " + requestCode);
    Log.i("suggest", "resultCode = " + resultCode);

    if (resultCode == Activity.RESULT_OK) {
        if (requestCode == CAMERA_CODE) {
            String imagePath = FileUtil.getHeadPicPath(CreditActivity.this)
                    + FileUtil.SUGGESTION_SAVED_PICTURE_NAME;
//                Bitmap bitmap = ImageUtil.readPictureDegreeToForwordBitmap(imagePath);
//                String userHeadPic = ImageUtil.saveCroppedImage(FileUtil.getHeadPicPath(this), bitmap);
            Log.i("suggest", "imagePath = " + imagePath);
            CreditAdapterInfo info = new CreditAdapterInfo();

            infos.add(info);
            Log.i("suggest", "infos.size = " + infos.size());
            mCreditAdapter.notifyDataSetChanged();

        } else if (requestCode == PHOTO_CODE) {
            String imagePath = "";
            ArrayList<Uri> images = data.getParcelableArrayListExtra("result");
            for (int i = 0; i < images.size(); i++) {
                imagePath = images.get(i).getPath();
                Log.i("suggest", "imagePath2 = " + imagePath);
                CreditAdapterInfo info = new CreditAdapterInfo();
                info.url = imagePath;
                infos.add(info);
            }
            mCreditAdapter.notifyDataSetChanged();

        }
    }


}

    /**
     * 判断摄像头是否可用
     * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好，
     * 以后有更好的方法的话可适当替换
     *
     * https://blog.csdn.net/jm_beizi/article/details/51728495
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 相机权限获取
     */
    private void cameraPromissionAllowed() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                FileUtil.getHeadPicPath(CreditActivity.this), FileUtil.SUGGESTION_SAVED_PICTURE_NAME)));
        startActivityForResult(intent, CAMERA_CODE);



    }
}

