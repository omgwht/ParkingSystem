package com.abc.parkingsystem;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Dialog;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class jiaofei extends Dialog implements View.OnClickListener {
    private Button btn1,btn2,btn12,btn13,btn21,btn22,btn23;
    private Button btn11;
    MyHelper myhelper=new MyHelper(this.getContext());
    public EditText et;
    public jiaofei(@NonNull Context context){
        super(context);

    }
    @SuppressLint("MissingInflatedId")
    public void onBackPressed(){
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext())//this.getContext()
                        .setTitle("success")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("缴费成功!")
                        .setPositiveButton("确定",new OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog,int which){
                                        dialog.dismiss();
                                        //MainActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog=builder.create();
                dialog.show();
    }
    public void onBackPressed1(int i){
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext())//this.getContext()
                .setTitle("提示")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("是否确认缴费")
                .setPositiveButton("确定",new OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        // Toast.makeText(this.getContext(),"交费成功,积分+"+String.valueOf(i),Toast.LENGTH_LONG).show();
                        set1(i);
                        dialog.dismiss();
                        onBackPressed();
                        cancel();

                    }
                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       // cancel();
                    }
                });
        dialog=builder.create();
        dialog.show();

    }
    public void set1(int i){
        //MainActivity.t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));
        //int a= Integer.parseInt(et.getText().toString());
        int e=Integer.parseInt(String.valueOf(Mycode.t.getText()));
        //不提出来的话,点第二次任意按钮都会执行else里的语句
        //不知道为啥 //因为会拼接
        if(e+i<=999){
            myhelper.insert("交费", String.valueOf(e+i));
            Mycode.t.setText(String.valueOf(Integer.parseInt(String.valueOf(Mycode.t.getText()))+i));
        }else{
            onBackPressed0();
            Mycode.t.setText("999");
        }
    }
    public void g(){
        int a= Integer.parseInt(et.getText().toString());
        if(a<=999)
        {
            myhelper.insert("交费",String.valueOf(a));
            Toast.makeText(this.getContext(),"交费成功,积分+"+String.valueOf(a),Toast.LENGTH_LONG).show();}
//        else{
//            myhelper.insert("交费","+"+String.valueOf(a));
//            onBackPressed0();
//        }

    }
    public void onBackPressed0(){
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext())//this.getContext()
                .setTitle("Fault")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("积分无法增加,积分上限为999,你当前的积分为:999")
                .setPositiveButton("确定",new OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                        //MainActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog=builder.create();
        dialog.show();
    }
    public void aa(){
        Toast.makeText(this.getContext(),"无效操作",Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle saveInstanceState){
        //MainActivity.t=findViewById(R.id.ling);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_zhifu);

        btn1=(Button) findViewById(R.id.cancel);
        btn2=(Button) findViewById(R.id.sure);
        et=(EditText) findViewById(R.id.shuru);
        btn11=(Button)findViewById(R.id.btn11);
        btn12=(Button)findViewById(R.id.btn12);
        btn13=(Button)findViewById(R.id.btn13);
        btn21=(Button)findViewById(R.id.btn21);
        btn22=(Button)findViewById(R.id.btn22);
        btn23=(Button)findViewById(R.id.btn23);
        btn11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(20);
            }

        });
        btn12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(30);
            }

        });
        btn13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(50);
            }

        });
        btn21.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(100);
            }

        });
        btn22.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(200);
            }

        });
        btn23.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed1(500);
            }

        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int a= Integer.parseInt(et.getText().toString());
                if(a>0){
                cancel();
                onBackPressed();
                g();//方法在外面可以this.getContext()得到环境
                //int a2= Integer.parseInt(et.getText().toString());
                set1(a);}else{
                    aa();

                }

                //Toast.makeText(this.getContext(),"邀请成功,积分+"+String.valueOf(a),Toast.LENGTH_LONG).show();
                //Toast.makeText(p.this,"邀请成功,积分+"+String.valueOf(a),Toast.LENGTH_LONG).show();
                //t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               cancel();
            }

        });

        //内部类
//        class p extends AppCompatActivity {
//            @Override
//            public void onCreate(Bundle saveInstanceState){
//
//                super.onCreate(saveInstanceState);
//                et.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view){
//                        //int a= Integer.parseInt(et.getText().toString());
//                       // Toast.makeText(p.this,"邀请成功,积分+"+String.valueOf(a),Toast.LENGTH_LONG).show();
//                        // t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));
//
//                    }
//                });
//                btn2.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view){
//                        int a= Integer.parseInt(et.getText().toString());
//                        cancel();
//                        Toast.makeText(p.this,"邀请成功,积分+"+String.valueOf(a),Toast.LENGTH_LONG).show();
//                        //t.setText(String.valueOf(Integer.parseInt(String.valueOf(t.getText()))+2));
//                    }
//                });
//            }
//
//        }

    }
    @Override
    public void onClick(View view){

    }
    public void setDialogSize(){
        //获取当前窗口对象
        Window window=getWindow();
        //获取对象的参数
        WindowManager.LayoutParams wlp=window.getAttributes();
        //获取屏幕宽度
        Display d=window.getWindowManager().getDefaultDisplay();
        wlp.width=(int)(d.getWidth());//对话框窗口为屏幕窗口
        wlp.gravity= Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
