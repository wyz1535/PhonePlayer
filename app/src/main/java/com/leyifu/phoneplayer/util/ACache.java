package com.leyifu.phoneplayer.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by hahaha on 2018/12/13 0013.
 */

public class ACache {


    /**
     * 保存对象到本地
     *
     * @param context
     * @param obj
     */
    public static void savedData(Context context, Serializable obj, String fileName) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件存储到本地
     * @param context
     * @param filename 文件名
     * @param content  保存的内容
     */
    public static void writeFileData(Context context, String filename, String content) {

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取本地文件
     * @param context
     * @param fileName
     * @return
     */
    public static String readFileData(Context context, String fileName) {

        FileInputStream fis = null;

        try {
             fis = context.openFileInput(fileName);
            int lenght = fis.available();
            byte[] bytes = new byte[lenght];
            fis.read(bytes);
            String requst = new String(bytes, "utf-8");
            return requst;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
        /**
     * 读取本地对象
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Object readFileToLocal(Context context, String fileName) {

        FileInputStream fileInputStream = null;
        ObjectInputStream ois = null;

        try {
            fileInputStream = context.openFileInput(fileName);
            ois = new ObjectInputStream(fileInputStream);
            Object obj = ois.readObject();

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }

                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
