package com.test.demo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {


    private Button ip_cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ip_cameraBtn = (Button)findViewById(R.id.IP_CAMERABTN);
        ip_cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"카메라 버튼 클릭.",Toast.LENGTH_LONG).show();

                Intent intentSubActivity = new Intent(MainActivity.this,DemoActivity.class);
                startActivity(intentSubActivity);
            }
        });
    }

}
