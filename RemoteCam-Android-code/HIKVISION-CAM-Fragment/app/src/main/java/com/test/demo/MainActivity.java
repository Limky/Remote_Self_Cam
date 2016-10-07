package com.test.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Fragment_Main.OnFragmentInteractionListener,Fragment_Camera.OnFragmentInteractionListener, Fragment_Gallery.OnFragmentInteractionListener,Fragment_imageDetail.OnFragmentInteractionListener{


    private Button ip_cameraBtn;
    long backKeyPressedTime = 0;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.replacedLayout, new Fragment_Main()).commit();

    }



    @Override
    public void onBackPressed(){
        //  Toast.makeText(getApplicationContext(), "back button click", Toast.LENGTH_SHORT).show();
        int count = 0;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            count = getFragmentManager().getBackStackEntryCount();
        }
        Log.d("count","count = "+count);
        backButtonFunction();

    }

    public void backButtonFunction(){

        Activity activity = MainActivity.this;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            int count = getFragmentManager().getBackStackEntryCount();
        }

        //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis(); //backKeyPressedTime 버튼을 누른 시간을 입력
            toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
