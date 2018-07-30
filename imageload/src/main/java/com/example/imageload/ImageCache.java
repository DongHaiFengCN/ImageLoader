package com.example.imageload;

import android.graphics.Bitmap;


public interface ImageCache {

     void setBitMapCache(String key, Bitmap bitmap);
     Bitmap getBitMapFromCache(String key);
     void close();


}
