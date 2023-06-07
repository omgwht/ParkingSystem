package com.abc.parkingsystem;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class Mycar extends AppCompatActivity implements View.OnClickListener {
    private ImageView re;
    private TextView Num;
    private TextView Type;
    private TextView tv_save;
    private String path;
    private TextView tv_park;
    private TextView tv_place;
    private TextView tv_time;
    private TextView tv_delete;
    private connect2mysql conn;
    private Map car_position_map;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycar);
        Num=findViewById(R.id.num);
        re=findViewById(R.id.re);
        Type=findViewById(R.id.type);
        tv_park = findViewById(R.id.tv_park);
        tv_place = findViewById(R.id.tv_place);
        tv_time = findViewById(R.id.tv_time);
        tv_save = findViewById(R.id.tv_save);
        tv_delete = findViewById(R.id.tv_delete);
        Bundle bundle = getIntent().getExtras();

        String num = bundle.getString("num");
        String type = bundle.getString("type");
        String park = bundle.getString("park");
        String place = bundle.getString("place");
        String time = bundle.getString("time");
        if(num == null){
            car_info car=(car_info) bundle.getSerializable("car");
            try {
                show_message(car);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Num.setText(num);
            Type.setText(type);
            tv_park.setText(park);
            tv_place.setText(place);
            tv_time.setText(time);
        }

        // 获取数据库连接
        conn = new connect2mysql();
        try {
            conn.getConnection();
            car_position_map = conn.getMyCarPositionThrowCarNum(num);
            String pkl_name = (String) car_position_map.get("pkl_name");
            String pkp_num = (String) car_position_map.get("pkp_num");
            String pkp_in_time = (String) car_position_map.get("pkp_in_time");

            tv_park.setText(pkl_name);
            tv_place.setText(pkp_num);
            tv_time.setText(pkp_in_time);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        re.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_delete.setOnClickListener(this);

    }




    public void show_message(car_info car) throws SQLException {
        // 根据数据库内容获取停车数据并展示


        Num.setText(car.num);
        Type.setText(car.type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re:
                finish();
                break;
            case R.id.tv_save:
                String num = Num.getText().toString();
                String type = Type.getText().toString();
                StringBuilder sb = new StringBuilder();
                sb.append("车牌号：").append(num);
                sb.append("型号：").append(type);
                String fileName = System.currentTimeMillis() + ".txt";
                String directory = null;
                //外部存储的私有空间
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                path = directory + File.separatorChar + fileName;
                //通过日志输出保存信息的文件的路径
                Log.d("final_homework",path);
                FileUtil.saveText(path,sb.toString());
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                Num.setText("暂无");
                Type.setText("暂无");
                tv_park.setText("暂无");
                tv_place.setText("暂无");
                tv_time.setText("暂无");
                break;
        }
    }

}
