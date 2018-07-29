package com.example.dong.tool;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * 实现内存与sd的双缓存
 */
public class MemoryAndDishCache implements ImageCache {

    private MemoryCache memoryCache = MemoryCache.getInstance();

    private DiskCache diskCache = DiskCache.getInstance();

    @Override
    public void setBitMapCache(String key, Bitmap bitmap) {

        memoryCache.setBitMapCache(key, bitmap);
        diskCache.setBitMapCache(key, bitmap);

    }

    /**
     * 首先从内存中获取，如果没有再去sd卡中去读取
     *
     * @param key 文件id
     * @return bitmap文件
     */
    @Override
    public Bitmap getBitMapFromCache(String key) {
        Bitmap bitmap;

        bitmap = memoryCache.getBitMapFromCache(key);

        if (bitmap == null) {

            bitmap = diskCache.getBitMapFromCache(key);

            if (bitmap != null) {


            }

        }

        return bitmap;
    }

    @Override
    public void deleteBiteMapFromCache(String key) {

    }

    public void setQuality(int quality){

        diskCache.setQuality(quality);
    }

    /**
     *
     * @param cacheName 文件夹名字
     */
    public void setCacheName(String cacheName) {
        diskCache.setCacheName(cacheName);
    }

}
