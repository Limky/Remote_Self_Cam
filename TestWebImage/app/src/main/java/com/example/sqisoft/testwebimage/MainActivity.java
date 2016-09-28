package com.example.sqisoft.testwebimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private Button getimage_btn;
    private ImageView image;
    private String uri ="http://192.168.2.245/Streaming/channels/3/picture";
    private Bitmap bit = null;
   // http://192.168.2.245/Streaming/channels/3/picture
//http://admin:1234qwer@192.168.2.245/Streaming/channels/3/picture
//http://cfile25.uf.tistory.com/image/156189264BF78BE538F97E
//http://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView);



        getimage_btn = (Button) findViewById(R.id.getimage_btn);
        getimage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"이미지 얻어오기 이벤트",Toast.LENGTH_SHORT).show();
                //getImage();
                new WebGetImage().execute();
                image.setImageBitmap(bit);
                image.invalidate();
            }
        });

    }

    public void getImage(){
        Toast.makeText(getApplicationContext(),"이미지 얻어오기 이벤트222",Toast.LENGTH_SHORT).show();
        new WebGetImage().execute();
        image.setImageBitmap(bit);

    }


    public class WebGetImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // 네트워크에 접속해서 데이터를 가져옴

            try {
                //웹사이트에 접속 (사진이 있는 주소로 접근)
                URL Url = new URL(uri);
                // 웹사이트에 접속 설정
                HttpURLConnection urlcon =(HttpURLConnection) Url.openConnection();
           //    Log.i("urlcon","Web-CODE ========1========= "+urlcon.getResponseCode());

                String userpass = "admin:1234qwer";
                String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes(),Base64.DEFAULT));
                urlcon.setRequestProperty("Authorization",basicAuth);

                urlcon.connect();
           //     Log.i("urlcon","Web-CODE ========2========= "+urlcon.getResponseCode());
                // 이미지 길이 불러옴
                int imagelength = urlcon.getContentLength();
                // 스트림 클래스를 이용하여 이미지를 불러옴
                BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
                // 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
                bit = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}