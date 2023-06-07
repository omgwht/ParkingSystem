package com.abc.parkingsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context,"itcast.db",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT, cause VACHAR (50),point INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //MyHelper helper=new MyHelper(MainActivity.this);
    public void insert(String cause,String point){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("cause",cause);
        values.put("point",point);
        //values.put("price",price);
        long id=db.insert("information",null,values);
        db.close();
    }
    public int delete(long id){
        //MyHelper helper=new MyHelper(MainActivity.this);
        SQLiteDatabase db=this.getWritableDatabase();
        int number=db.delete("information","_id=?",new String[]{id+""});
        db.close();
        return number;
    }
//    public void deleteall(ArrayList o){
//
//        for(int i=0;i<o.size();i++){
//            this.delete(i);
//        }
//    }

    @SuppressLint("Range")
    public String find(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("information",null,"_id=?",new String[]{id+""},
                null,null,null);
        String _id=null;
        String cause = null;
        String point=null;
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                  _id  =cursor.getString(cursor.getColumnIndex("_id"));
                 cause=cursor.getString(cursor.getColumnIndex("cause"));
                 point=cursor.getString(cursor.getColumnIndex("point"));
            }

        }
        String s=_id+cause+point;
        cursor.close();
        db.close();
        return s;
    }
//    public int update(String cause,String point){
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("cause",cause);
//
//    }

}
