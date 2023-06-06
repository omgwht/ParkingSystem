package com.abc.parkingsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    //把字符串保存到指定路径的文本文件
    public static void saveText(String path,String txt){
        BufferedWriter os = null;
        try{
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (os!=null){
                try{
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
