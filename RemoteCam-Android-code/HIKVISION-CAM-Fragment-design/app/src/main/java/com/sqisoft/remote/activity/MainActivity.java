package com.sqisoft.remote.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.fragment.FragmentCamera;
import com.sqisoft.remote.fragment.FragmentGallery;
import com.sqisoft.remote.fragment.FragmentImageDetail;
import com.sqisoft.remote.fragment.FragmentMain;
import com.sqisoft.remote.fragment.FragmentUserConfirm;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.sqisoft.remote.util.Log;
import com.sqisoft.remote.view.TitleView;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener,FragmentCamera.OnFragmentInteractionListener,
        FragmentGallery.OnFragmentInteractionListener,FragmentImageDetail.OnFragmentInteractionListener,FragmentUserConfirm.OnFragmentInteractionListener{


    private Button ip_cameraBtn;
    long backKeyPressedTime = 0;
    private Toast toast;
    private static TitleView mTitleView;
    private static final int ONE_SECOND = 1000;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleView = (TitleView) findViewById(R.id.remotecamera_main_title);
         manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        FragmentUtil.init(R.id.replacedLayout, getSupportFragmentManager(), mTitleView);
        FragmentMain main = new FragmentMain();
        transaction.replace(R.id.replacedLayout, main,"FMain").commit();

        DataManager dataManager = DataManager.getInstance();


        //로컬 갤러리로 이동 (내 폰에만 있는 갤러리.)
        Button photo_galleryBtn = (Button)findViewById(R.id.photo_gallery_Btn);
        photo_galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(new FragmentGallery());
            }
        });

    }



    @Override
    public void onBackPressed(){


        Fragment fragment = manager.findFragmentByTag("FUser");
        Log.d("fragment","fragment = "+fragment);
        if(fragment != null){

       //     transaction.replace(R.id.replacedLayout, new FragmentMain(),"FMain").commit();

        }

     int count = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            count = getFragmentManager().getBackStackEntryCount();
        }
        Log.d("count","count = "+count);

        backButtonFunction();

    }

    public void backButtonFunction(){
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount > 0) {
            super.onBackPressed();
            if (backStackCount == 1) {
                //Main 처리

            }
        } else {
            //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
         /*   if(System.currentTimeMillis() > backKeyPressedTime + 2000){
                backKeyPressedTime = System.currentTimeMillis(); //backKeyPressedTime 버튼을 누른 시간을 입력
                toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();

                super.onBackPressed();
                return;
            }

            if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
                finish();
                toast.cancel();
            }*/
        }
        Activity activity = MainActivity.this;



    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static TitleView getTitleView(){
        return mTitleView;
    }



    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    } // In MyActivity

}
