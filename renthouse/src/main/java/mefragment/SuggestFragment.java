package mefragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.renthouse.R;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.SuggestAdapterInfo;
import cn.com.mark.multiimage.core.ImageMainActivity;
import decoration.SpacesItemDecoration;
import meadapter.SuggestAdapter;
import utils.FileUtil;

/**
 * Created by user on 2019/8/12.
 */

public class SuggestFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private SuggestAdapter mSuggestAdapter;
    //ArrayList<Uri> uriList = new ArrayList<>();
    List<SuggestAdapterInfo> infos;

    private Button mButton;
    private static  int CAMERA_CODE = 10086;//相机
    private static  int PHOTO_CODE = 10010;//相册

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.complaint_suggest,container,false);


        initRecyclerView();

        mButton = mView.findViewById(R.id.complaint_suggest_tijiao);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return  mView;
    }
    //初始化RecyclerView
    private void initRecyclerView() {
        mRecyclerView = mView.findViewById(R.id.complaint_suggest_recycler);
        //设置每行的列数
        mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        //添加数据
        infos = new ArrayList<>();
        SuggestAdapterInfo info = null;
        String[] urls = getResources().getStringArray(R.array.url3);
        String[] titles = getResources().getStringArray(R.array.threetitle);
        for (int i = 0; i < 1; i++) {
            info = new SuggestAdapterInfo();
            //info.url = urls[i];
            infos.add(info);
        }

        //设置adapter的数据
        mSuggestAdapter = new SuggestAdapter(infos);
        mRecyclerView .setAdapter(mSuggestAdapter );
        mRecyclerView .setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 15;
        mRecyclerView .addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mSuggestAdapter.setItemClickListener(new SuggestAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                if(position == 0) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(new String[]{"拍照", "从手机相册里选择"}, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) // 拍照
                            {
                            /*Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera, MyApplication.IMAGE_CAMERA);*/
                            /*if (isCameraCanUse()) {
                                cameraPromissionAllowed();
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage("无法使用相机，请开启相机权限后重试")
                                        .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }*/
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA_CODE);

                            } else if (which == 1) // 从手机相册选择
                            {
                          /*  Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(picture, MyApplication.IMAGE_PHONE);*/
                                Intent intent = new Intent(getActivity(), ImageMainActivity.class);
                                intent.putExtra("action-original", true);
                                intent.putExtra("MAX_SEND", 10 - infos.size());
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
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Toast.makeText(getActivity(),"requestCode  = "+requestCode,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(),"resultCode  = "+data,Toast.LENGTH_SHORT).show();
        Log.i("suggest","requestCode = "+requestCode);
        Log.i("suggest","resultCode = "+ resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                String imagePath = FileUtil.getHeadPicPath(getActivity())
                        + FileUtil.SUGGESTION_SAVED_PICTURE_NAME;
//                Bitmap bitmap = ImageUtil.readPictureDegreeToForwordBitmap(imagePath);
//                String userHeadPic = ImageUtil.saveCroppedImage(FileUtil.getHeadPicPath(this), bitmap);
                Log.i("suggest","imagePath = "+ imagePath);
                SuggestAdapterInfo info = new SuggestAdapterInfo();
                info.url = imagePath;
                infos.add(info);
                Log.i("suggest", "infos.size = " + infos.size());
                mSuggestAdapter.notifyDataSetChanged();

            } else if (requestCode == PHOTO_CODE) {
                String imagePath = "";
                ArrayList<Uri> images = data.getParcelableArrayListExtra("result");
                for (int i = 0; i < images.size(); i++) {
                    imagePath = images.get(i).getPath();
                    Log.i("suggest","imagePath2 = "+ imagePath);
                    SuggestAdapterInfo info = new SuggestAdapterInfo();

                    info.url = imagePath;
                    infos.add(info);
                }
                mSuggestAdapter.notifyDataSetChanged();

            }
        }
      /*  switch (requestCode) {
            //拍照相片
            case 1:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }else{
                    Uri uri = data.getData();
                    Log.i("suggest", "uri = " + uri);
                    if (uri == null) {
                        //use bundle to get data
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                            //spath :生成图片取个名字和路径包含类型
                            //saveImage(Bitmap photo, String spath);
                            Log.i("suggest", "photo = " + photo);
                        } else {
                            Toast.makeText(getActivity(), "获取图片错误", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        //to do find the path of pic by uri
                        Log.i("suggest", "uri = " + uri);
                        SuggestAdapterInfo info = new SuggestAdapterInfo();

                        info.url = uri.toString();
                        infos.add(info);
                        Log.i("suggest", "infos.size = " + infos.size());
                        mSuggestAdapter.notifyDataSetChanged();
                    }

                }
                break;
            //相册相片
            case 0:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }else {

                    Uri uri = data.getData();
                    if (uri == null) {
                        //use bundle to get data
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                            //spath :生成图片取个名字和路径包含类型
                            //saveImage(Bitmap photo, String spath);
                        } else {
                            Toast.makeText(getActivity(), "获取图片错误", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        //to do find the path of pic by uri
                        Log.i("suggest", "uri = " + uri);
                        SuggestAdapterInfo info = new SuggestAdapterInfo();

                        info.url = uri.toString();
                        infos.add(info);
                        Log.i("suggest", "infos.size = " + infos.size());
                        mSuggestAdapter.notifyDataSetChanged();
                    }
                    break;
                }
            default:
                break;
          }
                */



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
                FileUtil.getHeadPicPath(getActivity()), FileUtil.SUGGESTION_SAVED_PICTURE_NAME)));
        startActivityForResult(intent, CAMERA_CODE);



    }
}
