package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
                    Toast.makeText(this,"??????????????????????????????", Toast.LENGTH_LONG).show();
                }else{
                    if(!password_text.equals(confirm_password_text)){
                        Toast.makeText(this,"????????????????????????????????????",Toast.LENGTH_LONG).show();
                    }
                    else{  //?????????????????????????????????????????????
                        String insert_sql = "INSERT INTO user_info VALUES('"+user_name_text+"','"+password_text+"','"+phone_num_text+"');";
                        try {
                            conn = new connect2mysql();
                            connection = conn.getConnection();
                            boolean register_flag = conn.insertToDB(insert_sql);
                            if(register_flag){
                                Toast.makeText(this,"???????????????",Toast.LENGTH_LONG).show();
                            }
                        } catch (SQLException e) {
                            Log.d("ERROR",e.toString());
                            Toast.makeText(this,"????????????",Toast.LENGTH_LONG).show();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.tv_goto_signin:
                Intent intent = new Intent(this,signin.class);
                startActivity(intent);
                break;
        }
    }
}