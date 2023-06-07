package com.abc.parkingsystem;//package com.example.wk;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.Window;
//import android.view.WindowManager;
//
//public class zhifu extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_zhifu);
//    }
//    public void setDialogSize(){
//        //获取当前窗口对象
//        Window window=getWindow();
//        //获取对象的参数
//        WindowManager.LayoutParams wlp=window.getAttributes();
//        //获取屏幕宽度
//        Display d=window.getWindowManager().getDefaultDisplay();
//        wlp.width=(int)(d.getWidth());//对话框窗口为屏幕窗口
//        wlp.gravity= Gravity.BOTTOM;
//        window.setBackgroundDrawableResource(android.R.color.transparent);
//        window.setAttributes(wlp);
//    }
//}