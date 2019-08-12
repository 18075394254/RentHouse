package mefragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.example.user.renthouse.R;

import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import bean.SuggestAdapterInfo;
import bean.ThreeAdapterInfo;
import decoration.SpacesItemDecoration;
import meadapter.SuggestAdapter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.complaint_suggest,container,false);


        initRecyclerView();
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
            info.url = urls[i];
            infos.add(info);
        }

        //设置adapter的数据
        mSuggestAdapter = new SuggestAdapter(infos);
        mRecyclerView .setAdapter(mSuggestAdapter );
        mRecyclerView .setLayoutManager(mLayoutManager);

        //添加ItemDecoration，item之间的间隔
        int leftRight = dip2px(15);
        int topBottom = 0;
        mRecyclerView .addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        mSuggestAdapter.setItemClickListener(new SuggestAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {
                //Toast.makeText(getActivity(),"位置 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getActivity(), RecommendActivity.class));
                if (position == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(new String[] { "拍照", "从手机相册里选择" }, new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (which == 0) // 拍照
                            {
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, MyApplication.IMAGE_CAMERA);
                            }
                            else if (which == 1) // 从手机相册选择
                            {
                                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(picture, MyApplication.IMAGE_PHONE);


                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
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
        Toast.makeText(getActivity(),"requestCode  = "+requestCode,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(),"resultCode  = "+data,Toast.LENGTH_SHORT).show();
        Uri uri = data.getData();
        if(uri == null){
            //use bundle to get data
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap  photo = (Bitmap) bundle.get("data"); //get bitmap
                //spath :生成图片取个名字和路径包含类型
                //saveImage(Bitmap photo, String spath);
            } else {
                Toast.makeText(getActivity(), "获取图片错误", Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            //to do find the path of pic by uri
            Log.i("suggest","uri = "+uri);
            SuggestAdapterInfo info = new SuggestAdapterInfo();

            info.url = uri.toString();
            infos.add(info);
            mSuggestAdapter.notifyDataSetChanged();
        }

        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }else{
                   /* Toast.makeText(getActivity(),"resultCode  = "+resultCode,Toast.LENGTH_SHORT).show();
                    Uri uri = data.getData();
                    SuggestAdapterInfo info = new SuggestAdapterInfo();
                    info.url = uri.toString();
                    infos.add(info);
                    mSuggestAdapter.notifyDataSetChanged();*/

                }
                break;

            default:
                break;
        }


    }
}
