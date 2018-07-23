package com.echeck.echeck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";

    private ImageView iv_back;
    private EditText et_phone;
    private EditText et_secret;
    private Button bt_register;
    private String phone, secret;


    final OkHttpClient client = new OkHttpClient();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String ReturnMessage = (String) msg.obj;
                Log.i("获取的返回信息", ReturnMessage);
                final Person person = new Gson().fromJson(ReturnMessage, Person.class);
                final String AA = person.getStatus();
                /***
                 * 在此处可以通过获取到的Msg值来判断
                 * 给出用户提示注册成功 与否，以及判断是否用户名已经存在
                 */
                Log.i("MSGhahahha", AA);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //设置沉浸式状态栏
        setStatusBar();

        //初始化控件
        initView();

        //点击响应事件
        initOnClickEvent();

        //触摸响应事件
        initOnTouchEvent();
    }

    /*
    向服务器发送请求
     */
    private void  postRequest(String phone, String secret){
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .add("secret", secret)
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url("http://119.29.167.150:8088/webtest2/1")
                .post(formBody)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    response = client.newCall(request).execute();
//                    Toast.makeText(RegisterActivity.this, response.body().string(), Toast.LENGTH_LONG);
                    Log.d(TAG, "onResponse: "+response.body().string());


//                    if (response.isSuccessful()) {
//                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
//                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
//
//                    } else {
//                        throw new IOException("Unexpected code:" + response);
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /*
    初始化控件
     */
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.bt_register_back);
        bt_register = (Button) findViewById(R.id.bt_register);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_secret = (EditText) findViewById(R.id.et_secret);
    }


    /*
    点击响应事件
     */
    private void initOnClickEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击向服务器发送请求
//        bt_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                phone = et_phone.getText().toString().trim();
//                secret = et_secret.getText().toString().trim();
//
//                //向服务器发送请求
//                postRequest(phone, secret);
//            }
//        });

        //点击注册按钮

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString().trim();
                secret = et_secret.getText().toString().trim();

                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterActivity.this, "请输入登录手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phone.length() != 11){
                    Toast.makeText(RegisterActivity.this, "手机号位数错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phone.matches("[1][3578].+")){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(secret)){
                    Toast.makeText(RegisterActivity.this, "请输入登录密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExistPhone(phone)){
                    Toast.makeText(RegisterActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    //保存账号密码到SharePreferences中
                    saveRegisterInfo(phone, secret);

                    //返回账号到HomeActivity中
                    Intent data = new Intent();
                    data.putExtra("phone", phone);
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }


    //判断账号是否已存在
    private boolean isExistPhone(String phone) {
        boolean has_phone = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spSecret = sp.getString(phone, "");

        if (!TextUtils.isEmpty(spSecret)){
            has_phone = true;
        }

        return has_phone;
    }

    //将密码保存到SharePreferences中
    private void saveRegisterInfo(String phone, String secret) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        editor.putString(phone, secret);
        //提交修改
        editor.commit();
    }

    /*
    触摸响应事件
     */
    private void initOnTouchEvent() {
        bt_register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_register.setBackground(getResources().getDrawable(R.drawable.bt_onclick_register_shape));
//                        bt_register.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_register.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
//                        bt_register.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /*
    设置沉浸式状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
