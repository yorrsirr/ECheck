package com.echeck.echeck;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class WriteInfoActivity extends AppCompatActivity{
    public static final int TAKE_PHOTO =1;

    private EditText et_name;
    private Spinner sp_department;
    private ImageView bt_writeInfo_back;
    private ImageView iv_writeInfi_photo;
    private TextView tv_openCamera;
    private TextView tv_openAlbum;
    private Button bt_sure;

    private Uri imageUri;

    //用在Spanner里面的
    private List<CharSequence> departmentList = null;
    private ArrayAdapter<CharSequence> departmentAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_info);

        //设置沉浸式布局
        setStatusBar();

        //初始化控件
        initView();

        //点击响应事件
        initOnClickEvent();

        //触摸响应事件
        initOnTouchEvent();

        //初始化Spinner
        initSpinner();

    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /*
    初始化控件
     */
    private void initView(){
        bt_writeInfo_back = (ImageView) findViewById(R.id.bt_writeInfo_back);
        iv_writeInfi_photo = (ImageView) findViewById(R.id.iv_writeInfo_photo);
        bt_sure = (Button) findViewById(R.id.bt_sure);
    }

    /*
    点击响应事件
     */
    private void initOnClickEvent() {
        bt_writeInfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteInfoActivity.this.finish();
            }
        });

        iv_writeInfi_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，用于存储拍照后的图片
                File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(WriteInfoActivity.this, "com.example.cameraalbumtest.fileprovider1", outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                try {
                    //将拍摄的照片显示出来
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    iv_writeInfi_photo.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
            }
                break;
            default:
                break;

        }
    }

    /*
    触摸响应事件
     */
    private void initOnTouchEvent() {
        bt_sure.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_sure.setBackground(getResources().getDrawable(R.drawable.bt_onclick_register_shape));
//                        bt_sure.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_sure.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
//                        bt_sure.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /*
    初始化Spinner
     */
    private void initSpinner() {
        sp_department = (Spinner) findViewById(R.id.sp_department);

        departmentList = new ArrayList<CharSequence>();
        departmentList.add("行政部");
        departmentList.add("广州研发一部");
        departmentList.add("广州研发二部");
        departmentList.add("广州研发三部");
        departmentList.add("广州研发四部");
        departmentList.add("广州研发五部");
        departmentList.add("海外支持部");
        departmentList.add("请选择您所在的部门");

        departmentAdapter = new sampleArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentList);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_department.setAdapter(departmentAdapter);
        sp_department.setSelection(departmentList.size()-1, true);
    }
}
