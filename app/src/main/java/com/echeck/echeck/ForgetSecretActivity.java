package com.echeck.echeck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
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
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetSecretActivity extends AppCompatActivity {
    private ImageView bt_forgetSecret_back;
    private Button bt_resetSecret;
    private Button bt_get_code;
    private EditText et_phone, et_code, et_new_secret;
    EventHandler eventHandler;
    private String phone, code, new_secret;
    //    private static boolean flag=false;
    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();

    int  b =2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_secret);

        //初始化SMSSDK
        MobSDK.init(this);

        //初始化EventHandler
        initHandler();

        //沉浸式状态栏
        setStatusBar();

        //初始化控件
        initView();

        //点击响应事件
        initOnClickEvent();

        //触摸响应事件
        initOnTouchEvent();
    }

    /*
    初始化handler
     */
    private void initHandler() {
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
//                        flag = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(ForgetSecretActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event != SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        Toast.makeText(ForgetSecretActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgetSecretActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }


    private void initView() {
        bt_forgetSecret_back = (ImageView) findViewById(R.id.bt_forgetSecret_back);
        bt_resetSecret = (Button) findViewById(R.id.bt_resetSecret);
        bt_get_code = (Button) findViewById(R.id.bt_get_code);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_new_secret = (EditText) findViewById(R.id.et_new_secret);

    }



    /*
    点击响应事件
     */
    private void initOnClickEvent() {
        bt_forgetSecret_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ForgetSecretActivity.this, "请输入登录手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phone.length() != 11){
                    Toast.makeText(ForgetSecretActivity.this, "手机号位数错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!phone.matches("[1][3578].+")){
                    Toast.makeText(ForgetSecretActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SMSSDK.getVerificationCode("86", phone);
                    Toast.makeText(ForgetSecretActivity.this, "已发送验证码至: " + phone, Toast.LENGTH_SHORT).show();
//                    bt_get_code.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
//                    bt_get_code.setTextColor(getResources().getColor(R.color.white));
                    new Thread(new ForgetSecretActivity.MyCountDownTimer()).start();
                }
            }
        });

        bt_resetSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString().trim();
                code = et_code.getText().toString().trim();
                new_secret = et_new_secret.toString().trim();
//                Toast.makeText(ForgetSecretActivity.this, "new _secret: " + new_secret, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ForgetSecretActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }else if (phone.length() != 11){
                    Toast.makeText(ForgetSecretActivity.this, "手机号位数错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!phone.matches("[1][3578].+")) {
                    Toast.makeText(ForgetSecretActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(code)){
                    Toast.makeText(ForgetSecretActivity.this, "请输入您的验证码", Toast.LENGTH_SHORT).show();
                }else if (code.length() != 4){
                    Toast.makeText(ForgetSecretActivity.this, "您的验证码位数不对", Toast.LENGTH_SHORT).show();
                }
                else {
                    SMSSDK.submitVerificationCode("86", phone, code);
                    SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(phone, "123");
                    editor.apply();
                    SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
                    Log.d("ForgetSecretActivity", "onClick: "+sharedPreferences.getString(phone, ""));

//                    if (sp.getString(phone, "") == new_secret) {
                    Toast.makeText(ForgetSecretActivity.this, "重置密码成功" +"\n" + "newsecret: " + sp.getString(phone, ""), Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(ForgetSecretActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    /*
    触摸响应事件
     */
    private void initOnTouchEvent() {
        bt_resetSecret.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_resetSecret.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
                        bt_resetSecret.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_resetSecret.setBackground(getResources().getDrawable(R.drawable.bt_shape));
                        bt_resetSecret.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        bt_get_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_get_code.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
                        bt_get_code.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_get_code.setBackground(getResources().getDrawable(R.drawable.bt_shape));
                        bt_get_code.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable{

        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        bt_get_code.setClickable(false);
                        bt_get_code.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
                        bt_get_code.setTextColor(getResources().getColor(R.color.white));
                        bt_get_code.setText(T + "s后获取");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    bt_get_code.setClickable(true);
                    bt_get_code.setBackground(getResources().getDrawable(R.drawable.bt_shape));
                    bt_get_code.setTextColor(getResources().getColor(R.color.colorPrimary));
                    bt_get_code.setText("重新获取");
                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }
    //销毁SMS
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
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
}
