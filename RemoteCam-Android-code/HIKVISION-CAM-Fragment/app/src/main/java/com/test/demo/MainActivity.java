package com.test.demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Fragment_Main.OnFragmentInteractionListener,Fragment_Camera.OnFragmentInteractionListener{


    private Button ip_cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.replacedLayout, new Fragment_Main()).commit();




   /*     ip_cameraBtn = (Button)findViewById(R.id.IP_CAMERABTN);
        ip_cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"카메라 버튼 클릭.",Toast.LENGTH_LONG).show();

                Intent intentSubActivity = new Intent(MainActivity.this,DemoActivity.class);
                startActivity(intentSubActivity);
            }
        });*/
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
