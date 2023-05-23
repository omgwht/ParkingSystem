package com.abc.parkingsystem;


import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.SQLException;

public class MainPage extends AppCompatActivity{

    private ListView mlistview;
    private SearchView msearchview;
    private boolean connection;
    private connect2mysql conn;
    private String[] titles;
    private String[] positions;
    private PopupWindow mpopwindow;
    private TextView tv_pkl_name;
    private TextView tv_pkl_position;
    private RelativeLayout re_layout_bottom;
    private MenuItem bottom_btn_1;
    private MenuItem bottom_btn_2;
    private MenuItem bottom_btn_3;
    private MenuItem bottom_btn_4;
    private TextView tv_show_pkl_remain;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setStatusBar();
        re_layout_bottom = (RelativeLayout) findViewById(R.id.re_layout_bottom);

        bottomNavigationView = findViewById(R.id.btnNavView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ibtn_bottom_1:
                        Log.i("INFO","底部导航栏1点击成功");
                        return true;
                    case R.id.ibtn_bottom_2:
                        Log.i("INFO","底部导航栏2点击成功");
                        return true;
                    case R.id.ibtn_bottom_3:
                        Log.i("INFO","底部导航栏3点击成功");
                        return true;
                    case R.id.ibtn_bottom_4:
                        Log.i("INFO","底部导航栏4点击成功");
                        return true;
                }
                return false;
            }
        });
        // 设置底部第一个按钮选中状态
//        bottom_btn_1 = findViewById(R.id.ibtn_bottom_1);
//        bottom_btn_2 = findViewById(R.id.ibtn_bottom_2);
//        bottom_btn_3 = findViewById(R.id.ibtn_bottom_3);
//        bottom_btn_4 = findViewById(R.id.ibtn_bottom_4);
//        bottom_btn_1.setPressed(true);
//        bottom_btn_1.setOnClickListener(this);
//        bottom_btn_2.setOnClickListener(this);
//        bottom_btn_3.setOnClickListener(this);
//        bottom_btn_4.setOnClickListener(this);
        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(39.906901,116.397972);

        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("Mine0停车场").snippet("北京市西城区西长安街1号"));
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
                        mapView.setVisibility(View.INVISIBLE);
                        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            private int pkp_remain_num;

                            // 点击搜索出来的项后，弹出PopUpWindow进行下一步选择
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // i为点击项的position
                                switch (i){
                                    default:
                                        String pkl_name_str = (String) adapterView.getItemAtPosition(i); //获取了停车场的名字
                                        try {
                                            pkp_remain_num = conn.getPkpRemainNum("SELECT COUNT(*) FROM parkingport_info WHERE pkl_name='"+pkl_name_str+"' AND pkp_state=0");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String pkl_position_str = positions[i]; //获取了停车场的定位

                                        Boolean show_popup_state = showPopUpWindow(pkl_name_str,pkl_position_str,pkp_remain_num);
                                        if(show_popup_state){
                                            msearchview.clearFocus();
                                        }
                                }
                            }
                        });
                    } catch (SQLException e) {
                        Log.d("ERROR","错误信息："+e.toString());
                    }
                }else{
                    mapView.setVisibility(View.VISIBLE);
                    mlistview.setAdapter(null);
                    msearchview.clearFocus();
//                    bottom_btn_1.setPressed(true);
                }
                return false;
            }
        });

    }

//    @Override
//    public void onClick(View view) {
////        switch (view.getId()){
////            case R.id.ibtn_bottom_1:
////                break;
////            case R.id.ibtn_bottom_2:
////                break;
////            case R.id.ibtn_bottom_3:
////                break;
////            case R.id.ibtn_bottom_4:
////                break;
//        }
//    }

    private boolean showPopUpWindow(String pkl_name, String pkl_position,int remain_num){
        View contentview = LayoutInflater.from(MainPage.this).inflate(R.layout.parkinginfo_popupwindow,null);
        mpopwindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mpopwindow.setContentView(contentview);
        tv_pkl_name = (TextView) contentview.findViewById(R.id.tv_show_pkl_name);
        tv_pkl_position = (TextView) contentview.findViewById(R.id.tv_show_pkl_position);
        tv_show_pkl_remain = (TextView) contentview.findViewById(R.id.tv_show_pkl_remain);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btn_book_now = (Button) contentview.findViewById(R.id.btn_book_now);
        // 立即预定按钮的点击事件，携带数据跳转到下一页
        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,book_pkl_page.class);
                Bundle bundle = new Bundle();
                bundle.putString("pkl_name",pkl_name);
                bundle.putString("pkl_position",pkl_position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tv_pkl_name.setText(pkl_name);
        tv_pkl_position.setText(pkl_position);
        tv_show_pkl_remain.setText("剩余"+remain_num+"个");
        View rootview = LayoutInflater.from(MainPage.this).inflate(R.layout.activity_main_page,null);
        mpopwindow.setAnimationStyle(R.style.popupwindowStyle);
        mpopwindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);
        return true;
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
            if(titles.length==0||positions.length==0) {
                name.setText("暂无查询结果");
                parkinglot_position.setText("");
            }else{
                name.setText(titles[position]);
                parkinglot_position.setText(positions[position]);
            }
            return view;
        }
    }
}