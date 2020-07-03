package com.hbfw.kesystem.comments;

import java.io.*;

public class ObjectAsFileUtils {
    //写对象
    public static void writeObj(String filePath,Object obj) throws Exception {
        File file = new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();
    }

    //读对象
    public static Object readObj(String filePath) throws Exception {
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object object = ois.readObject();
        ois.close();
        return object;
    }
}
