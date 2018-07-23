package com.echeck.echeck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Button bt_login;
    private TextView tv_register;
    private TextView tv_forget;
    private EditText et_phone;
    private EditText et_secret;
    private String phone, secret, spSecret;
    private Button bt_sms_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //初始化控件
        initViews();

        //点击响应事件
        initOnClickEvent();

        //触摸响应事件
        initOnTouchEvent();
    }


    /*
    初始化控件
     */
    private void initViews() {
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_secret = (EditText) findViewById(R.id.et_secret);
        bt_sms_login = (Button) findViewById(R.id.bt_sms_login);
    }

    /*
    点击事件响应
     */
    private void initOnClickEvent() {
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spSecret = readSecret(phone);

                phone = et_phone.getText().toString();
                secret = et_secret.getText().toString();

                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(HomeActivity.this, "请输入登录手机号", Toast.LENGTH_LONG).show();
                    return;
                }else if (phone.length() != 11){
                    Toast.makeText(HomeActivity.this, "手机号位数错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!phone.matches("[1][3578].+")){
                    Toast.makeText(HomeActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(secret)){
                    Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (secret.equals(spSecret)){
                    Toast.makeText(HomeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    saveLoginStatus(true, phone);
//                    Intent data = new Intent();
//                    //datad.putExtra( ); name , value ;
//                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//                    setResult(RESULT_OK,data);
                    startActivity(new Intent(HomeActivity.this, WriteInfoActivity.class));
                    return;
                }else if(spSecret!=null && !spSecret.equals(secret)){
                    Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(HomeActivity.this, "手机号未被注册", Toast.LENGTH_SHORT).show();
                }


            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ForgetSecretActivity.class);
                startActivity(intent);
            }
        });

        bt_sms_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SMSLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //根据手机号返回对应的密码
    private String readSecret(String phone) {
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(phone, "");
    }

    //保存登录状态
//    private void saveLoginStatus(boolean status, String phone) {
//        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器
//        SharedPreferences.Editor editor = sp.edit();
//        //存入boolean类型的登录状态
//        editor.putBoolean("isLogin", status);
//        //存入登录时的手机号
//        editor.putString("loginPhone", phone);
//        //提交修改
//        editor.commit();
//    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_phone 控件
                et_phone.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_secret.setSelection(userName.length());
            }
        }
    }



    /*
    触摸事件响应
     */
    private void initOnTouchEvent() {
        bt_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_login.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
                        bt_login.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_login.setBackground(getResources().getDrawable(R.drawable.bt_shape));
                        bt_login.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        bt_sms_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_sms_login.setBackground(getResources().getDrawable(R.drawable.bt_register_shape));
                        bt_sms_login.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_sms_login.setBackground(getResources().getDrawable(R.drawable.bt_shape));
                        bt_sms_login.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    /*
    访问服务器
     */
//    private void sendRequestWithOkHttp() {
//        Log.d(TAG, "sendRequestWithOkHttp: ");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.d(TAG, "run: ");
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("http://xs.eqask.com:8060/Home/Toy/toyList")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
////                    parseJSONWithGSON(responseData);
//                    Log.d("HomeActivity", "执行try");
//                }catch (IOException e){
//                    e.printStackTrace();
//                    Log.d("HomeActivity", "执行catch");
//                }
//            }
//        }).start();
//        Log.d(TAG, "sendRequestWithOkHttp: ");
//
////        OkHttpClient client = new OkHttpClient();
////        Request request = new Request.Builder()
////                .url("http://xs.eqask.com:8060/Home/Toy/toyList")
////                .build();
////        client.newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, IOException e) {
////                Log.d(TAG, "onFailure: "+e);
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) throws IOException {
////                Log.d(TAG, "onResponse: "+response.body().string());
////            }
////        });
//    }

//    private void parseJSONWithGSON (String jsonData) {
//        Gson gson = new Gson();
//        JsonBean jsonBean = gson.fromJson(jsonData, JsonBean.class);
//
//        Log.d("HomeActivity", "phone is " + jsonBean.getMessage());
//
//    }

}
