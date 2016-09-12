package com.example.sqisoft.mylayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FrameLayout extends Activity {

    ImageView imageView01,imageView02;
    boolean b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        imageView01 = (ImageView) findViewById(R.id.Frame_imageView01);
        imageView02 = (ImageView) findViewById(R.id.Frame_imageView02);

        b = false;
    }

    public void onButton1Clicked(View v){
        changeImage(b);
        b = !b;
    }

    private void changeImage(boolean b){
        if(b){
            imageView01.setVisibility(View.GONE);
            imageView02.setVisibility(View.VISIBLE);

        }else{
            imageView01.setVisibility(View.VISIBLE);
            imageView02.setVisibility(View.GONE);

        }


    }


}
