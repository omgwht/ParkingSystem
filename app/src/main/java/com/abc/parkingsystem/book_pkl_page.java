package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class book_pkl_page extends AppCompatActivity implements View.OnClickListener {

    private connect2mysql conn;
    private Boolean connection;
    private ImageView iv_pkp_0;
    private ImageView iv_pkp_1;
    private ImageView iv_pkp_2;
    private ImageView iv_pkp_3;
    private ImageView iv_pkp_4;
    private ImageView iv_pkp_5;
    private pkpStateInfoArray pkp_info_arr;
    private String pkl_name;
    private TextView tv_show_remain_num;
    private ImageView[] iv_arr;

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pkl_page);
        setStatusBar();

        Bundle bundle = getIntent().getExtras();
        pkl_name = bundle.getString("pkl_name");
        String pkl_position = bundle.getString("pkl_position");
        TextView tv_pkl_name = findViewById(R.id.tv_pkl_name);
        tv_pkl_name.setText(pkl_name);
        tv_show_remain_num = findViewById(R.id.tv_show_remain_num);
        iv_pkp_0 = findViewById(R.id.iv_pkp_0);
        iv_pkp_1 = findViewById(R.id.iv_pkp_1);
        iv_pkp_2 = findViewById(R.id.iv_pkp_2);
        iv_pkp_3 = findViewById(R.id.iv_pkp_3);
        iv_pkp_4 = findViewById(R.id.iv_pkp_4);
        iv_pkp_5 = findViewById(R.id.iv_pkp_5);
        iv_arr = new ImageView[]{iv_pkp_0,iv_pkp_1,iv_pkp_2,iv_pkp_3,iv_pkp_4,iv_pkp_5};

        refreshBookPage();

        iv_pkp_0.setOnClickListener(this);
        iv_pkp_1.setOnClickListener(this);
        iv_pkp_2.setOnClickListener(this);
        iv_pkp_3.setOnClickListener(this);
        iv_pkp_4.setOnClickListener(this);
        iv_pkp_5.setOnClickListener(this);
    }

    private void refreshBookPage(){
        try {
            conn = new connect2mysql();
            connection = conn.getConnection();
            int remain_num = conn.getPkpRemainNum("SELECT COUNT(*) FROM parkingport_info WHERE pkl_name='"+ pkl_name +"' AND pkp_state=0");
            tv_show_remain_num.setText("剩余车位"+remain_num+"个");
            String sql = "SELECT * FROM parkingport_info WHERE pkl_name = '"+ pkl_name +"'";
            pkp_info_arr = conn.getParkingPortInfo(sql);
            String[] pkp_num = pkp_info_arr.getPkp_num();
            int[] pkp_info_intarr = pkp_info_arr.getPkp_state();
            int last_pkp_num = 0;
            for(int i = 0; i < pkp_info_intarr.length; i++){
                if(pkp_info_intarr[i] == 0){
                    last_pkp_num++;
                }
            }
            String[] pkp_name_arr = pkp_info_arr.getPkl_name();
            for(int i = 0; i < pkp_info_intarr.length; i++){
                int pkp_state_num = (int) pkp_info_intarr[i];
                ImageView iv_pkp_i = iv_arr[i];

                if(pkp_state_num == 0){ // 车位空
                    iv_pkp_i.setTag("0"+"."+i+"."+pkp_num[i]+"."+pkl_name+"."+last_pkp_num); //第一位表示占用状态,第二位表示索引，第三部分表示车位号,第四部分表示停车场名，最后一部分表示剩余车位数
                    iv_pkp_i.setImageDrawable(getResources().getDrawable(R.mipmap.null_pk_icon));
                    iv_pkp_i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }else if(pkp_state_num == 1){ // 车位占用
                    iv_pkp_i.setTag("1"+"."+i+"."+pkp_num[i]+"."+pkl_name+"."+last_pkp_num); //第一位表示占用状态
                    iv_pkp_i.setImageDrawable(getResources().getDrawable(R.mipmap.car_icon));
                    iv_pkp_i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        /*
        * 利用id.getTag()获取 车位占用信息，车位号索引，车位号码，停车场名称，剩余车位数 信息
        * */
        switch (view.getId()){
            case R.id.iv_pkp_0:
                String tag_info0 = iv_pkp_0.getTag().toString();
                String[] tagInfo_arr0 = tag_info0.split("\\.");
                if(tagInfo_arr0[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr0[3],tagInfo_arr0[4],tagInfo_arr0[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_pkp_1:
                String tag_info1 = iv_pkp_1.getTag().toString();
                String[] tagInfo_arr1 = tag_info1.split("\\.");
                if(tagInfo_arr1[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr1[3],tagInfo_arr1[4],tagInfo_arr1[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_pkp_2:
                String tag_info2 = iv_pkp_2.getTag().toString();
                String[] tagInfo_arr2 = tag_info2.split("\\.");
                if(tagInfo_arr2[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr2[3],tagInfo_arr2[4],tagInfo_arr2[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_pkp_3:
                String tag_info3 = iv_pkp_3.getTag().toString();
                String[] tagInfo_arr3 = tag_info3.split("\\.");
                if(tagInfo_arr3[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr3[3],tagInfo_arr3[4],tagInfo_arr3[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_pkp_4:
                String tag_info4 = iv_pkp_4.getTag().toString();
                String[] tagInfo_arr4 = tag_info4.split("\\.");
                if(tagInfo_arr4[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr4[3],tagInfo_arr4[4],tagInfo_arr4[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_pkp_5:
                String tag_info5 = iv_pkp_5.getTag().toString();
                String[] tagInfo_arr5 = tag_info5.split("\\.");
                if(tagInfo_arr5[0].equals(0+"")){
                    showBookPopUpWindow(tagInfo_arr5[3],tagInfo_arr5[4],tagInfo_arr5[2],true);
                }else{
                    Toast.makeText(book_pkl_page.this,"该车位已占用，请选择其他车位！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showBookPopUpWindow(String pkl_name, String last_pkp_num, String pkp_num, boolean in_state_change_to){
        View contentView = LayoutInflater.from(book_pkl_page.this).inflate(R.layout.book_pkl_popup,null);
        PopupWindow mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopWindow.setContentView(contentView);
        TextView tv_pkl_name = (TextView) contentView.findViewById(R.id.tv_show_pkl_name);
        TextView tv_last_pkp_num = (TextView) contentView.findViewById(R.id.tv_show_pkl_remain);
        TextView tv_pkp_num = (TextView) contentView.findViewById(R.id.tv_pkp_num);
        Button btn_sure_to_book = (Button) contentView.findViewById(R.id.btn_book_now);
        btn_sure_to_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean book_state = conn.changePkpState(in_state_change_to,pkl_name,pkp_num,null,null,null);
                    if(book_state){
                        Toast.makeText(book_pkl_page.this,"车位预定成功!",Toast.LENGTH_SHORT).show();
                        refreshBookPage();
                    }else{
                        Toast.makeText(book_pkl_page.this,"车位预定失败，请重试",Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        tv_pkl_name.setText(pkl_name);
        tv_last_pkp_num.setText("剩余"+last_pkp_num+"个");
        tv_pkp_num.setText(pkp_num);
        View rootView = LayoutInflater.from(book_pkl_page.this).inflate(R.layout.activity_book_pkl_page,null);
        mPopWindow.setAnimationStyle(R.style.popupwindowStyle);
        mPopWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }
}