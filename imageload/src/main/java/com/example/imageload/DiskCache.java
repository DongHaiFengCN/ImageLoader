package com.example.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 硬盘缓存类
 */
public class DiskCache implements ImageCache {

    private static DiskCache diskCache;

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    private String cacheName = "DOAING";

    private DiskCache() {
    }


    private static String cacheDir = Environment.getExternalStorageDirectory()+ "/";
    private int quality = 100;

    @Override
    public void setBitMapCache(String key, Bitmap bitmap) {

        File file = new File(cacheDir+cacheName);

        if(!file.exists()) {

           boolean isCreate = file.mkdir();

           if(!isCreate){

               return;
           }
        }

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file.getAbsolutePath()+"/"+key);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public Bitmap getBitMapFromCache(String key) {
        Bitmap bitmap = BitmapFactory.decodeFile(cacheDir+cacheName + "/"+key);
        if (bitmap!=null){

            Log.e("DOAING","从sd中加载");
        }
        return bitmap;
    }

    @Override
    public void deleteBiteMapFromCache(String key) {

    }

    /**
     * 设置自定义压缩质量
     *
     * @param quality 压缩质量
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    public static DiskCache getInstance() {

        if (diskCache == null) {

            diskCache = new DiskCache();
        }
        return diskCache;
    }
}
