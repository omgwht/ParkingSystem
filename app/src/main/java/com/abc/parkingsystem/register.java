package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class register extends AppCompatActivity implements View.OnClickListener {


    private EditText user_name;
    private EditText password;
    private EditText confirm_password;
    private EditText phone_num;
    private EditText check_num;
    private Button goto_next;
    private connect2mysql conn;
    private boolean connection;
    private TextView go_back_to_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBar();

        goto_next = findViewById(R.id.btn_register);
        goto_next.setOnClickListener(this);
        user_name = findViewById(R.id.edit_user_name);
        password = findViewById(R.id.edit_password);
        confirm_password = findViewById(R.id.edit_confirm_password);
        phone_num = findViewById(R.id.edit_phone_num);
        check_num = findViewById(R.id.edit_check_num);

        go_back_to_signin = findViewById(R.id.tv_goto_signin);
        go_back_to_signin.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                String user_name_text = user_name.getText().toString().trim();
                String password_text = password.getText().toString().trim();
                String confirm_password_text = confirm_password.getText().toString().trim();
                String phone_num_text = phone_num.getText().toString().trim();
                String check_num_text = check_num.getText().toString().trim();
                if(user_name_text.length()==0||password_text.length()==0||confirm_password_text.length()==0||
                        phone_num_text.length()==0||check_num_text.length()==0){
                    Toast.makeText(this,"请将所有选项填写完整", Toast.LENGTH_LONG).show();
                }else{
                    if(!password_text.equals(confirm_password_text)){
                        Toast.makeText(this,"两次输入密码不同，请重试",Toast.LENGTH_LONG).show();
                    }
                    else{  //校验完成，执行注册，写入数据库
                        String insert_sql = "INSERT INTO user_info VALUES('"+user_name_text+"','"+password_text+"','"+phone_num_text+"');";
                        try {
                            conn = new connect2mysql();
                            connection = conn.getConnection();
                            if(connection){
                                boolean register_flag = conn.insertToDB(insert_sql);
                                if(register_flag){
                                    Toast.makeText(this,"注册成功！",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(this,"数据库连接失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            Log.d("ERROR",e.toString());
                            Toast.makeText(this,"注册失败",Toast.LENGTH_LONG).show();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.tv_goto_signin:
                Intent intent = new Intent(this,signin.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}