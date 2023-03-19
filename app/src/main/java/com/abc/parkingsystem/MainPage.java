package com.abc.parkingsystem;


import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class MainPage extends AppCompatActivity {

    private ListView mlistview;
    private SearchView msearchview;
    private boolean connection;
    private connect2mysql conn;
    private String[] titles;
    private String[] positions;
    private PopupWindow mpopwindow;
    private TextView tv_pkl_name;
    private TextView tv_pkl_position;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setStatusBar();
        try {
            String sql = "SELECT * FROM parkinglot_info";
            conn = new connect2mysql();
            connection = conn.getConnection();
            ParkingInfoDoubleArray parkingInfoDoubleArray = null;
            parkingInfoDoubleArray = conn.getSearchParkingResult(sql);
            titles = parkingInfoDoubleArray.getNameArray();
            positions = parkingInfoDoubleArray.getPositionArray();
        } catch (SQLException e) {
            Log.d("ERROR","错误："+e.toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("ERROR","错误："+e.toString());
            e.printStackTrace();
        }
        msearchview = findViewById(R.id.msearchView);
        msearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    String sql = "SELECT * FROM parkinglot_info WHERE parkinglot_name LIKE '%"+s+"%' OR parkinglot_position LIKE '%"+s+"%'";
                    try {
                        ParkingInfoDoubleArray parkingInfoDoubleArray = conn.getSearchParkingResult(sql);
                        titles = parkingInfoDoubleArray.getNameArray();
                        positions = parkingInfoDoubleArray.getPositionArray();
                        mlistview = findViewById(R.id.mlistView);
                        MyBaseAdapter mAdapter = new MyBaseAdapter();
                        mlistview.setAdapter(mAdapter);
                        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            // 点击搜索出来的项后，弹出PopUpWindow进行下一步选择
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // i为点击项的position
                                switch (i){
                                    default:
                                        String pkl_name_str = (String) adapterView.getItemAtPosition(i); //获取了停车场的名字
                                        String pkl_position_str = positions[i]; //获取了停车场的定位
                                        showPopUpWindow(pkl_name_str,pkl_position_str);
                                }
                            }
                        });
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    mlistview.setAdapter(null);
                }
                return false;
            }
        });
//        mlistview = findViewById(R.id.mlistView);
//        MyBaseAdapter mAdapter = new MyBaseAdapter();
//        mlistview.setAdapter(mAdapter);
    }

    private void showPopUpWindow(String pkl_name, String pkl_position){
        View contentview = LayoutInflater.from(MainPage.this).inflate(R.layout.parkinginfo_popupwindow,null);
        mpopwindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mpopwindow.setContentView(contentview);
        tv_pkl_name = (TextView) contentview.findViewById(R.id.tv_show_pkl_name);
        tv_pkl_position = (TextView) contentview.findViewById(R.id.tv_show_pkl_position);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btn_book_now = (Button) contentview.findViewById(R.id.btn_book_now);
        // 立即预定按钮的点击事件，携带数据跳转到下一页
        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_pkl_name.setText(pkl_name);
        tv_pkl_position.setText(pkl_position);
        View rootview = LayoutInflater.from(MainPage.this).inflate(R.layout.activity_main_page,null);
        mpopwindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.theme_blue));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }
    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainPage.this,R.layout.search_parking_item,null);
            TextView name = (TextView) view.findViewById(R.id.tv_item_parkinglot_name);
            TextView parkinglot_position = (TextView) view.findViewById(R.id.tv_item_parkinglot_position);
            name.setText(titles[position]);
            parkinglot_position.setText(positions[position]);
            return view;
        }
    }
}