package com.example.dong.tool;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;


public interface ImageCache {

     void setBitMapCache(String key, Bitmap bitmap);
     Bitmap getBitMapFromCache(String key);
     void deleteBiteMapFromCache(String key);


}
