package com.example.dong.test;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.imageload.ImageLoader;
import com.example.imageload.MemoryAndDishCache;

public class MainActivity extends AppCompatActivity {

    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ImageView imageView = findViewById(R.id.image_ig);

        //创建一个图片加载器，设置图片访问根路径
        imageLoader =  ImageLoader.getInstance("https://cdn.pixabay.com/photo/2018/07/06/19/48/");


        //选择使用两级缓存方法
        MemoryAndDishCache memoryAndDishCache = new MemoryAndDishCache();

        //设置图片质量，默认为100
        memoryAndDishCache.setQuality(90);

        //设置图片所要保存的文件夹的名称，不设置就是我的网名 DOAING
        memoryAndDishCache.setCacheName("test");

        imageLoader.setImageCache(memoryAndDishCache);

        findViewById(R.id.button_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //图片名称
                imageLoader.displayImage("charles-chaplin-3521070_1280.jpg",imageView);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageLoader.close();
        Log.e("DOAING","onDestroy");

    }
}
