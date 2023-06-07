package com.abc.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

public class Car extends AppCompatActivity {
    private EditText Car_type;
    private EditText Car_num;
    private EditText Car_else;
    private TextView Car_add;
    private BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mycar);

        Car_add=findViewById(R.id.car_add);
        Car_type=findViewById(R.id.car_type);
        Car_num=findViewById(R.id.car_num);
        Car_else=findViewById(R.id.car_else);

        Car_add.setOnClickListener(this::Onclick);


        bottomNavigationView = findViewById(R.id.btnNavView);
        bottomNavigationView.setSelectedItemId(R.id.ibtn_bottom_2);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ibtn_bottom_1:
                        // 首页
                        Intent intent_main_page = new Intent(getApplicationContext(),MainPage.class);
                        startActivity(intent_main_page);
                        finish();
                        Log.i("INFO","底部导航栏1点击成功");
                        return true;
                    case R.id.ibtn_bottom_2:
                        // 我的车辆
                        Intent intent_car = new Intent(getApplicationContext(),Car.class);
                        startActivity(intent_car);
                        finish();
                        Log.i("INFO","底部导航栏2点击成功");
                        return true;
                    case R.id.ibtn_bottom_3:
                        // 车友圈
                        Intent intent_friendCircle = new Intent(getApplicationContext(),FriendCircleMain.class);
                        startActivity(intent_friendCircle);

                        Log.i("INFO","底部导航栏3点击成功");
                        return true;
                    case R.id.ibtn_bottom_4:
                        Log.i("INFO","底部导航栏4点击成功");
                        Intent intent_mine = new Intent(getApplicationContext(),Mine.class);
                        startActivity(intent_mine);
                        finish();
                        return true;
                }
                return false;
            }
        });



    }

    private void Onclick(View view) {
        switch (view.getId()){
            case R.id.car_add:
                car_info car=getinfo();
                Bundle b=new Bundle();

                Intent intent=new Intent(Car.this,Mycar.class);//设置切换对应activity
                b.putSerializable("car",(Serializable) car);
                intent.putExtras(b);
                startActivity(intent);

        }


    }


    //获取信息，存入car中
    public  car_info getinfo(){
        car_info car=new car_info();
        car.setType(Car_type.getText().toString());
        car.setNum(Car_num.getText().toString());
        car.setAnother(Car_else.getText().toString());
        return car;
    }


}