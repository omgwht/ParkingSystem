package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class signin extends AppCompatActivity implements View.OnClickListener {

    private boolean connection;
    private connect2mysql conn;
    private EditText edit_user_name;
    private EditText edit_password;
    private CheckBox rememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        TextView goto_register = (TextView) findViewById(R.id.goto_resister);
        goto_register.setOnClickListener(this);
        Button btn_signin = findViewById(R.id.btn_login);
        btn_signin.setOnClickListener(this);
        edit_user_name = findViewById(R.id.edit_user_name);
        edit_password = findViewById(R.id.edit_password);
        rememberme = findViewById(R.id.ckb_remember_me);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goto_resister:
                Intent intent = new Intent(this,register.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String user_name = edit_user_name.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                CheckBox remember_me = findViewById(R.id.ckb_remember_me);
                boolean remember = rememberme.isChecked();
                if(remember){ //选择记住我
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name",user_name);
                    editor.putString("password",password);
                    editor.putBoolean("remember_me",remember);
                    editor.commit();
                }
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
                        }
                    } catch (ClassNotFoundException e) {
                        Log.d("ERROR","ClassNotFound错误"+e.toString());
                    } catch (SQLException e) {
                        Log.d("ERROR","SQL错误"+e.toString());
                    }
                    String signin_sql = "SELECT COUNT(*) FROM user_info WHERE user_name='"+user_name+"' AND user_password='"+password+"'";
                    try {
                        boolean signin_flag = conn.selectcountFromDB(signin_sql);
                        if(signin_flag){
                            Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(this,"用户名或密码输入错误",Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        Log.d("ERROR","SQL错误"+e.toString());
                    } catch (ClassNotFoundException e) {
                        Log.d("ERROR","ClassNotFound错误"+e.toString());
                    }
                }
                break;
        }


    }

}
