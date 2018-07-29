package com.example.dong.tool;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * 内存缓存类
 */
public class MemoryCache implements ImageCache {

    private static MemoryCache memoryCache;
    private LruCache<String,Bitmap> bitmapLruCache;
    private MemoryCache(){

        long maxMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (maxMemory / 6);
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    @Override
    public void setBitMapCache(String key, Bitmap bitmap) {
        bitmapLruCache.put(key,bitmap);
    }

    @Override
    public Bitmap getBitMapFromCache(String key) {
        Bitmap bitmap = bitmapLruCache.get(key);

        if (bitmap!=null){

            Log.e("DOAING","从内存中加载");
        }

        return bitmap;
    }

    @Override
    public void deleteBiteMapFromCache(String key) {

        bitmapLruCache.remove(key);
    }

    public static MemoryCache getInstance(){
        if(memoryCache==null){

            memoryCache = new MemoryCache();
        }
        return memoryCache;
    }
}
