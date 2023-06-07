package com.abc.parkingsystem;



//import com.example.wk.jiaofei;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Mycode extends AppCompatActivity {
    //控制按钮只能被点击一次
    private boolean onlyclickone1=true;
    private boolean onlyclickone2=true;
    public void onBackPressed0(){
        android.app.AlertDialog dialog;
        android.app.AlertDialog.Builder builder=new AlertDialog.Builder(Mycode.this)//this.getContext()
                .setTitle("Fault")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("积分无法增加,积分上限为999,你当前的积分为:999")
                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                        //MainActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog=builder.create();
        dialog.show();
    }
    private Button btn1,btn2,btn3,btn4 ;
    private Button record_btn;
    private TextView t1;
    public static   TextView t;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycode);


        MyHelper myhelper=new MyHelper(Mycode.this);
        SQLiteDatabase db=myhelper.getWritableDatabase();
        db.execSQL("DELETE FROM information");
        //不能写在详情页面
        record_btn=(Button)findViewById(R.id.record);
        record_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //展示
                Intent i5=new Intent(record_btn.getContext(),MainActivity2.class);
                startActivity(i5);
            }
        });

        t1=(TextView)findViewById(R.id.back);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(t1.getContext(), Mine.class);
                startActivity(i);
            }


        });
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        t=(TextView)findViewById(R.id.ling);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(onlyclickone1){
                    myhelper.insert("签到","+2");
                    btn1.setText("已签到");
                    Toast.makeText(Mycode.this,"签到成功,积分+2",Toast.LENGTH_LONG).show();
                    t=(TextView)findViewById(R.id.ling);
                    //Toast.makeText(MainActivity.this,Integer.parseInt(String.valueOf(t.getText())),Toast.LENGTH_LONG).show();
                    t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));
                    onlyclickone1=false;
                }else{
                    Toast.makeText(Mycode.this,"今日已签到,请明日再来",Toast.LENGTH_LONG).show();
                }

            }
        });
        // btn1.performClick();
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //btn2.setText("已完成");

                myhelper.insert("邀请新用户","+80");
                //t=(TextView)findViewById(R.id.ling);
                //Toast.makeText(MainActivity.this,Integer.parseInt(String.valueOf(t.getText())),Toast.LENGTH_LONG).show();
                if(Integer.parseInt(String.valueOf(t.getText()))+80<=999)
                {t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+80));
                    Toast.makeText(Mycode.this,"邀请成功,积分+80",Toast.LENGTH_LONG).show();}
                else{
                    t.setText(String.valueOf(999));
                    onBackPressed0();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(onlyclickone2){
                    btn3.setText("已认证");
                    myhelper.insert("实名认证","+50");
                    t=(TextView)findViewById(R.id.ling);
                    //Toast.makeText(MainActivity.this,Integer.parseInt(String.valueOf(t.getText())),Toast.LENGTH_LONG).show();
                    if(Integer.parseInt(String.valueOf(t.getText()))+50<=999)
                    {t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+50));
                        Toast.makeText(Mycode.this,"认证成功,积分+50",Toast.LENGTH_LONG).show();}
                    else{
                        t.setText(String.valueOf(999));
                        onBackPressed0();
                    }onlyclickone2=false;
                }else{
                    Toast.makeText(Mycode.this,"您已认证",Toast.LENGTH_LONG).show();
                }

            }
        });
        // btn3.performClick();
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //btn1.setText("今日已签到");
//                AlertDialog dialog;
//                AlertDialog.Builder builder=new AlertDialog.Builder(this)
//                        .setTitle("请选择缴费金额:")
//                        .setIcon(R.mipmap.ic_launcher)
//                        .setMessage("是否确定退出应用")
//                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                                    @Override
//                                    public void onClick(DialogInterface dialog,int which){
//                                        dialog.dismiss();
//                                        //MainActivity.this.finish();
//                                    }
//                                })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                dialog=builder.create();
//                dialog.show();


//                Intent intent=new Intent();
//                intent.setClass(btn4.getContext(), jiaofei.class);
//                startActivity(intent);

                jiaofei j= new jiaofei(Mycode.this);
                j.show();
                j.setDialogSize();



//                Toast.makeText(MainActivity.this,"缴费成功,积分+1",Toast.LENGTH_LONG).show();
//                t=(TextView)findViewById(R.id.ling);
//                //Toast.makeText(MainActivity.this,Integer.parseInt(String.valueOf(t.getText())),Toast.LENGTH_LONG).show();
//                t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));

//                public void onClick(View view) {
//
//                    Intent i = new Intent(t1.getContext(), mybag.class);
//                    startActivity(i);
//                }
            }
        });
    }



}