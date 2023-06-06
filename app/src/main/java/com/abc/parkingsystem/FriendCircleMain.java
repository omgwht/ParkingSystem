package com.abc.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendCircleMain extends AppCompatActivity {

    private ArrayList<MyPostInfo> postInfos;
    private String[] user_name_arr;
    private String[] post_detail_arr;
    private int[] post_imgs = {R.drawable.cardrop,R.drawable.myaima,R.drawable.cry,R.drawable.six};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle_main);

//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tv_username = findViewById(R.id.username);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tv_post_content = findViewById(R.id.post_content);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        ListView lv_post = findViewById(R.id.lv_post);

        connect2mysql conn = new connect2mysql();
        try {
            conn.getConnection();
            //获取到所有帖子信息
            postInfos = conn.getPostInfo();
        } catch (SQLException | ClassNotFoundException e) {
            Log.i("获取帖子的异常","获取帖子的异常"+e.toString());
            e.printStackTrace();
        }

        user_name_arr = new String[postInfos.size()];
        post_detail_arr = new String[postInfos.size()];
        // 遍历获取到的帖子信息集合
        for(int i = 0; i < postInfos.size(); i++){
            MyPostInfo postInfo_i = postInfos.get(i);
            String user_name = postInfo_i.getPost_user_name();
            String post_detail = postInfo_i.getPost_detail();
            user_name_arr[i] = user_name;
            post_detail_arr[i] = post_detail;
        }

        ListView mListView = (ListView) findViewById(R.id.lv_post);
        MyBaseAdapter_post myBaseAdapter_post = new MyBaseAdapter_post();
        mListView.setAdapter(myBaseAdapter_post);

    }

    class MyBaseAdapter_post extends BaseAdapter{

        @Override
        public int getCount() {
            return postInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return user_name_arr[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(FriendCircleMain.this,R.layout.post_n_layout,null);
            TextView tv_username = view1.findViewById(R.id.username);
            TextView tv_post_content = view1.findViewById(R.id.post_content);
            ImageView iv = view1.findViewById(R.id.iv_post_pic);
            tv_username.setText(user_name_arr[i]);
            tv_post_content.setText(post_detail_arr[i]);
            iv.setImageResource(post_imgs[i]);
            return view1;
        }
    }
}