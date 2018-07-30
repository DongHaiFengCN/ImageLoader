package com.example.dong.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.imageload.ImageLoader;
import com.example.imageload.MemoryAndDishCache;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


        final ImageView imageView = findViewById(R.id.image_ig);

        //创建一个图片加载器
        final ImageLoader imageLoader =  ImageLoader.getInstance();

        //选择使用两级缓存方法
        MemoryAndDishCache memoryAndDishCache = new MemoryAndDishCache();

        //设置图片质量
        memoryAndDishCache.setQuality(100);

        //设置图片所要保存的文件夹的名称
        memoryAndDishCache.setCacheName("test");

        imageLoader.setImageCache(memoryAndDishCache);

      /*
       //只使用sd卡缓存
       DiskCache diskCache = DiskCache.getInstance();

        imageLoader.setImageCache(diskCache);*/

      /*
      //只使用内存缓存
       MemoryCache memoryCache = MemoryCache.getInstance();
        imageLoader.setImageCache(memoryCache);*/

        findViewById(R.id.button_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageLoader.displayImage("18030312320337431.jpg",imageView);

            }
        });
    }
}
