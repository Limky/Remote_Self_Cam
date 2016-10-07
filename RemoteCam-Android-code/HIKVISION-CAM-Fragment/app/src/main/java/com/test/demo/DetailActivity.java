package com.test.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class DetailActivity extends AppCompatActivity implements Fragment_Gallery.OnFragmentInteractionListener {

    PhotoViewAttacher mAttacher;
    RelativeLayout DetailLayout;

 //   BackPressCloseHandler backPressCloseHandler;
    ViewPager pager;
    int currentIndex;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailLayout = (RelativeLayout)findViewById(R.id.DetailLayout);
   //     backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        ArrayList<ImageObject> dataItems = intent.getParcelableArrayListExtra("imagesList");
     //   Log.i("테스트","테스트 = "+dataItems.get(1).getImagePath());
        currentIndex = (int)intent.getIntExtra("currentIndex",777);


        pager= (ViewPager)findViewById(R.id.pager);
        MyPageAdapter adapter= new MyPageAdapter(getLayoutInflater(),dataItems);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentIndex);

    /*    mAttacher = new PhotoViewAttacher(pager);*/

    }


    @Override
    public void onBackPressed() {


/*        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.DetailLayout, new Fragment_Gallery()).addToBackStack("tag").commit();*/

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("go","go");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
