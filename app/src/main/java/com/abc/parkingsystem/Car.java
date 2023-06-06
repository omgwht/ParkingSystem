package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class Car extends AppCompatActivity {
    private EditText Car_type;
    private EditText Car_num;
    private EditText Car_else;
    private TextView Car_add;


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