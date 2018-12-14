package com.leyifu.phoneplayer.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by hahaha on 2018/12/13 0013.
 */

public class ACache {


    public void put(String key, Serializable value) {

        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            byte[] data = bos.toByteArray();
            put(key, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, byte[] value) {
//        File file = mCache.newFile(key);
        FileOutputStream out = null;
        try {
//            out = new FileOutputStream(file);
            out.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            mCache.put(file);
        }
    }
}
