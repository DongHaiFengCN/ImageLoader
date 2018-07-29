package com.example.dong.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.dong.imageloader.MainActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    static ImageLoader imageLoader;
    private ImageLoader(){

    }

    //默认实现内存缓存
    private ImageCache imageCache = MemoryCache.getInstance();

    //创建线程池为cpu的数量
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //注入缓存
    public void setImageCache(ImageCache imageCache) {

        this.imageCache = imageCache;
    }

    public void displayImage(String key, ImageView imageView) {

        Bitmap bitmap = imageCache.getBitMapFromCache(key);


        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        //如果没有缓存，使用线程池进行异步下载
        getLoadRequest(key, imageView);
    }

    private void getLoadRequest(final String url, final ImageView imageView) {


        imageView.setTag(url);
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap = downLoadImage(url);

                if (bitmap == null) {
                    return;
                }

                if(imageView.getTag().equals(url)){

                    imageCache.setBitMapCache(url,bitmap);

                    imageView.setImageBitmap(bitmap);

                }



            }
        });
    }

    //下载图片资源
    private Bitmap downLoadImage(String imageUrl) {

        Bitmap bitmap = null;
        try {
            URL url = new URL(MainActivity.uil+imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("DOAING","从网络加载");
        return bitmap;
    }
    public static ImageLoader getInstance() {

        if (imageLoader == null) {

            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }
}
