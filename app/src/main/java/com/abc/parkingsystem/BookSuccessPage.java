package com.abc.parkingsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookSuccessPage extends AppCompatActivity implements View.OnClickListener {
    boolean save = true;
    private TextView Download;
    private EditText Name;
    private ImageView Quit;
    private View view;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_success_page);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // 从上一个页面获取数据
        Bundle bundle = getIntent().getExtras();
        String pkp_num = bundle.getString("pkp_num");
        String pkp_in_time = bundle.getString("pkp_in_time");
        String pkl_position = bundle.getString("pkl_position");

        Download=findViewById(R.id.download);
        Download.setOnClickListener(this);
//        Download.setOnClickListener();

        Quit=findViewById(R.id.quit);
        Quit.setOnClickListener(this);

        Name=findViewById(R.id.pc_name);
//        String Dates=getData();
//        Name.setText(Dates);

        EditText pc_carlocate = findViewById(R.id.pc_carlocate);
        EditText pc_carstarttime = findViewById(R.id.pc_carstarttime);
        EditText pc_locate = findViewById(R.id.pc_locate);

        pc_carlocate.setText(pkp_num);
        pc_carstarttime.setText(pkp_in_time);
        pc_locate.setText(pkl_position);

    }
    //连接数据库
    private Connection connect() {
        // MySQL连接信息
        String url = "jdbc:mysql://10.0.2.2:3306/parking";
        String user = "root";
        String password = "dir99";
        Connection conn = null;
        try {
            // 连接到MySQL数据库
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    //获取数据
    private String getData() {
        String result = "";
        try {
            // 连接到MySQL数据库
            Connection conn = connect();
            // 执行SQL查询
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM parkingcode");
            // 处理查询结果
            while (rs.next()) {
                String pkl_name = rs.getString("pkl_name");
                String carlocate = rs.getString("carlocate");
                String car_name = rs.getString("car_name");
                String phone = rs.getString("phone");
                String carstyle = rs.getString("carstyle");
                String carid = rs.getString("carid");
                String paystand = rs.getString("paystand");
                String datestart = rs.getString("datestart");
                String pkl_locate = rs.getString("pkl_locate");

                //姓名，电话，车型号，车牌号等。然后split分割，再去存储。
                result += carlocate+"\n";
            }
            // 关闭连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quit:
            {finish();}
            case R.id.download:

                View views = findViewById(R.id.layout_to_save);
                Bitmap bitmap = Bitmap.createBitmap(views.getWidth(), views.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                views.draw(canvas);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "my_layout.png");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    if(save){
                        Toast.makeText(BookSuccessPage.this, "图片保存成功", Toast.LENGTH_SHORT).show();
                        save=false;
                    }
                    else
                        Toast.makeText(BookSuccessPage.this, "图片已下载", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
        }
    }
}