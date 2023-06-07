package com.abc.parkingsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
//    public void c(){
//        //MainActivity.this.finish();
//    }
private Button fh_btn;
    private ListView lv;
    ArrayList _id1  =new ArrayList<String>();
    ArrayList cause1=new ArrayList<String>();
    ArrayList point1=new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        MyHelper myHelper=new MyHelper(this);
        SQLiteDatabase db=myHelper.getWritableDatabase();


        Cursor cursor=db.rawQuery("select * from information",null);
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                _id1.add(cursor.getString(0));
                cause1.add(cursor.getString(1));
                point1.add(cursor.getString(2));
            }
        }

//myHelper.deleteall(_id1);
        //db.execSQL("DELETE FROM information");
        //不能写在这里,写在这里会每次点击都清空,要写在主界面实现每次打开主界面清空
        for(int i=1;i<=_id1.size();i++)
        {_id1.set(i-1, Integer.toString(i));}
        //db.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = information");

        cursor.close();
        db.close();

        lv=(ListView) findViewById(R.id.lv);
        MyBaseAdapter mba=new MyBaseAdapter();
        lv.setAdapter(mba);
//ActionBar actionBar=getSupportActionBar();
//actionBar.setDisplayHomeAsUpEnabled(true);
//        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()){
//                // android.R.id.home 这个是获取ids.xml页面的返回箭头，项目自带的，要加上android
//                case android.R.id.home:
//                    // 返回
//                    this.finish();
//                    // 结束
//                    return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }

        fh_btn=(Button) findViewById(R.id.fanhui_btn);
        fh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(MainActivity2.this,MainActivity.class);
//                startActivity(i);//用这个值会变为初始的
                finish();//返回上个页面,且不改变上个界面的任何值
            }
        });
    }
    class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return point1.size();
        }

        @Override
        public Object getItem(int position) {
            return point1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(MainActivity2.this,R.layout.item,null);
            TextView   _id0=(TextView) view.findViewById(R.id.item1);
            TextView cause0=(TextView) view.findViewById(R.id.item2);
            TextView point0=(TextView) view.findViewById(R.id.item3);
            //findviewbyid前要加view.  否则会被判为空
            //不知道为啥
            if(_id1.get(position).toString()!=null)
            {_id0.setText((CharSequence) _id1.get(position));}
            else{
                _id0.setText("000");
            }
            if(cause1.get(position).toString()!=null)
            {cause0.setText((CharSequence) cause1.get(position));}
            else{
                _id0.setText("111");
            }
            if(point1.get(position).toString()!=null)
            {point0.setText((CharSequence) point1.get(position));}
            else{
                _id0.setText("222");
            }
            return view;
        }
    }
}