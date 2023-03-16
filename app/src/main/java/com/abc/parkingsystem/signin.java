package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class signin extends AppCompatActivity implements View.OnClickListener {

    private boolean connection;
    private connect2mysql conn;
    private EditText edit_user_name;
    private EditText edit_password;
    private CheckBox rememberme;
    private CheckBox remember_me;
    private Boolean remember_me1;
    private Map userMap;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        boolean load_flag = getSharedPrefsData(this);
        setStatusBar();

        TextView goto_register = (TextView) findViewById(R.id.goto_resister);
        goto_register.setOnClickListener(this);
        Button btn_signin = findViewById(R.id.btn_login);
        btn_signin.setOnClickListener(this);
        edit_user_name = findViewById(R.id.edit_user_name);
        edit_password = findViewById(R.id.edit_password);
        rememberme = findViewById(R.id.ckb_remember_me);
        remember_me = findViewById(R.id.ckb_remember_me);
        if(load_flag)
        loadSharedPrefsData(this);
    }

//    protected void setStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
//            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
//            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色
//        }
//    }
protected void setStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.theme_blue));//设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
    }
}

private void loadSharedPrefsData(Context context){
    String user_name = (String) userMap.get("user_name");
    String password = (String) userMap.get("password");
    Boolean remember_me_sign = (Boolean) userMap.get("remember_me");
    edit_user_name.setText(user_name);
    edit_password.setText(password);
    remember_me.setChecked(remember_me_sign);
}

private boolean getSharedPrefsData(Context context){
        SharedPreferences sp = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        String user_name = sp.getString("user_name",null);
        String password = sp.getString("password",null);
        Boolean remember_me1 = sp.getBoolean("remember_me",false);
        userMap = new HashMap();
        userMap.put("user_name",user_name);
        userMap.put("password",password);
        userMap.put("remember_me",remember_me1);
        Log.d("DATA","数据为："+ userMap.values());
        return remember_me1;
}

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goto_resister:
                Intent intent = new Intent(this,register.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                String user_name = edit_user_name.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                boolean remember = rememberme.isChecked();

                if(user_name.length()==0||password.length()==0){
                    Toast.makeText(this,"请将用户名和密码填写完整",Toast.LENGTH_LONG).show();
                }else{
                    //开始与数据库中账号密码对比
                    //建立与数据库的连接
                    try {
                        conn = new connect2mysql();
                        connection = conn.getConnection();
                        if(connection){
                            Log.d("SUCCESS","连接成功");
                            String signin_sql = "SELECT COUNT(*) FROM user_info WHERE user_name='"+user_name+"' AND user_password='"+password+"'";
                            try {
                                boolean signin_flag = conn.selectcountFromDB(signin_sql);
                                if(signin_flag){
                                    Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                                    if(remember){ //选择记住我
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_info",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user_name",user_name);
                                        editor.putString("password",password);
                                        editor.putBoolean("remember_me",remember);
                                        editor.commit();
                                    }else{
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_info",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user_name",null);
                                        editor.putString("password",null);
                                        editor.putBoolean("remember_me",false);
                                        editor.commit();
                                    }
                                    getSharedPrefsData(this);
                                    Intent goto_mainPage = new Intent(this,MainPage.class);
                                    startActivity(goto_mainPage);
                                    finish();
                                }else{
                                    Toast.makeText(this,"用户名或密码输入错误",Toast.LENGTH_LONG).show();
                                }
                            } catch (SQLException e) {
                                Log.d("ERROR","SQL错误"+e.toString());
                            } catch (ClassNotFoundException e) {
                                Log.d("ERROR","ClassNotFound错误"+e.toString());
                            }
                        }else{
                            Toast.makeText(this,"数据库连接失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (ClassNotFoundException e) {
                        Log.d("ERROR","ClassNotFound错误"+e.toString());
                    } catch (SQLException e) {
                        Log.d("ERROR","SQL错误"+e.toString());
                    }
                }
                break;
        }


    }

}
