package com.example.imageload;

import android.graphics.Bitmap;

import android.util.Log;
import android.util.LruCache;

/**
 * 内存缓存类
 */
public class MemoryCache implements ImageCache {

    private static MemoryCache memoryCache;
    private LruCache<String, Bitmap> bitmapLruCache;

    private MemoryCache() {

        long maxMemory = Runtime.getRuntime().maxMemory();

        //创建缓存的内存大小为分配运行内存的六分之一
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
        bitmapLruCache.put(key, bitmap);
    }

    @Override
    public Bitmap getBitMapFromCache(String key) {
        Bitmap bitmap = bitmapLruCache.get(key);

        if (bitmap != null) {

            Log.e("DOAING", "从内存中加载");
        }

        return bitmap;
    }

    @Override
    public void close() {

        if (memoryCache != null) {

            memoryCache = null;
        }


    }

    public static MemoryCache getInstance() {
        if (memoryCache == null) {

            memoryCache = new MemoryCache();
        }
        return memoryCache;
    }
}
