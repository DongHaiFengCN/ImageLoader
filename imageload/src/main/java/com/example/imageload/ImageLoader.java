package com.example.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    private String key = null;

    //图片的根路径
    private String url = "https://cdn.pixabay.com/photo/2018/07/06/19/48/";

    //默认实现内存缓存
    private ImageCache imageCache = MemoryCache.getInstance();

    //创建线程池为cpu的数量
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ImageLoader imageLoader;


    private ImageLoader() {

    }


    //注入缓存
    public void setImageCache(ImageCache imageCache) {

        this.imageCache = imageCache;
    }

    public void displayImage(String key, ImageView imageView) {


        if(key.equals(this.key)){

         Log.e("DOAING","重复执行了加载");
         return;
        }

        this.key = key;

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

                final Bitmap bitmap = downLoadImage(url);

                if (bitmap == null) {
                    return;
                }

                if (imageView.getTag().equals(url)) {

                    //将图片缓存到内存和sd中
                    imageCache.setBitMapCache(url, bitmap);

                    imageView.post(new Runnable() {
                        @Override
                        public void run() {

                            imageView.setImageBitmap(bitmap);
                        }
                    });


                }


            }
        });
    }

    //下载图片资源
    private Bitmap downLoadImage(String imageUrl) {

        Bitmap bitmap = null;
        try {
            URL url = new URL(this.url + imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("DOAING", "从网络加载");
        return bitmap;
    }

    /**
     * @param url 图片根路径
     */
    public static ImageLoader getInstance(String url) {

        if (imageLoader == null) {

            imageLoader = new ImageLoader();

            imageLoader.url = url;
        }
        return imageLoader;
    }


    public void close(){

        imageLoader = null;
    }

}
