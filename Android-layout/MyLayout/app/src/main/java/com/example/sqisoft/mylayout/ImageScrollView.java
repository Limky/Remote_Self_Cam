package com.example.sqisoft.mylayout;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class ImageScrollView extends Activity {

    ScrollView scrollView01;
    ImageView imageView01;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        scrollView01 = (ScrollView) findViewById(R.id.scrollView01);
        imageView01 = (ImageView) findViewById(R.id.imageView01);
        Button button01 = (Button) findViewById(R.id.button01);

        scrollView01.setHorizontalScrollBarEnabled(true); //수평바도 가능한 기능.

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.backimgedit);
        int bitmapWidth  = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView01.setImageDrawable(bitmap);
        imageView01.getLayoutParams().width = bitmapWidth;
        imageView01.getLayoutParams().height = bitmapHeight;

    }

    public void onButton1Clicked(View v){

        changeImage();
    }

    public void changeImage(){

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.meetingback);
        int bitmapWidth  = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView01.setImageDrawable(bitmap);
        imageView01.getLayoutParams().width = bitmapWidth;
        imageView01.getLayoutParams().height = bitmapHeight;

    }
}
