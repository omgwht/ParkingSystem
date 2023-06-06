package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class data extends AppCompatActivity implements View.OnClickListener {

    private TextView park;
    private TextView place;
    private TextView time;
    private TextView btn_next;
    private TextView tv_num;
    private TextView tv_type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        park = findViewById(R.id.park);
        place = findViewById(R.id.place);
        time = findViewById(R.id.time);
        tv_num = findViewById(R.id.tv_num);
        tv_type = findViewById(R.id.tv_type);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_next:
                Intent intent = new Intent();
                intent.setClass(this,Mycar.class);
                Bundle bundle = new Bundle();
                bundle.putString("num",tv_num.getText().toString());
                bundle.putString("type",tv_type.getText().toString());
                bundle.putString("park",park.getText().toString());
                bundle.putString("place",place.getText().toString());
                bundle.putString("time",time.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}