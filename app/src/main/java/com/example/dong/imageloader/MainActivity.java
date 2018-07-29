package com.example.dong.imageloader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dong.tool.ImageLoader;
import com.example.dong.tool.MemoryAndDishCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


        final ImageView imageView = findViewById(R.id.image_ig);
        final ImageLoader imageLoader = new ImageLoader();
        imageLoader.setImageCache(new MemoryAndDishCache());

        findViewById(R.id.button_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("DOAING","开始加载");
                imageLoader.displayImage("https://pic.chinaz.com/2018/0303/18030312320337431.jpg",imageView);

            }
        });
    }
}
