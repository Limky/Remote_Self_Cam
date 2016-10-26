package com.sqisoft.remote.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.fragment.FragmentCamera;
import com.sqisoft.remote.fragment.FragmentGallery;
import com.sqisoft.remote.fragment.FragmentImageDetail;
import com.sqisoft.remote.fragment.FragmentMain;
import com.sqisoft.remote.fragment.FragmentMyAlbum;
import com.sqisoft.remote.fragment.FragmentMyAlbumImageDetail;
import com.sqisoft.remote.fragment.FragmentUserConfirm;
import com.sqisoft.remote.util.FragmentUtil;
import com.sqisoft.remote.util.Log;
import com.sqisoft.remote.view.TitleView;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener,FragmentCamera.OnFragmentInteractionListener,
        FragmentGallery.OnFragmentInteractionListener,FragmentImageDetail.OnFragmentInteractionListener,FragmentUserConfirm.OnFragmentInteractionListener,FragmentMyAlbum.OnFragmentInteractionListener
,FragmentMyAlbumImageDetail.OnFragmentInteractionListener{


    private Button ip_cameraBtn;
    long backKeyPressedTime = 0;
    private Toast toast;
    private static TitleView mTitleView;
    private static final int ONE_SECOND = 1000;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FrameLayout naviLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleView = (TitleView) findViewById(R.id.remotecamera_main_title);
        naviLayout = (FrameLayout) findViewById(R.id.navi_layout);

         manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        naviLayout.setVisibility(View.VISIBLE);

        FragmentUtil.init(R.id.replacedLayout, getSupportFragmentManager(), mTitleView);

        Button mainButton = (Button) findViewById(R.id.main_link_btn);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(new FragmentMain());
           //     transaction.replace(R.id.replacedLayout, main,"FMain").commit();
                naviLayout.setVisibility(View.GONE);
            }
        });

        Button myAblumButton = (Button) findViewById(R.id.my_album_link_btn);
        myAblumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(new FragmentMyAlbum());
                naviLayout.setVisibility(View.GONE);
            }
        });

       // DataManager dataManager = DataManager.getInstance();

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

        backButtonFunction();

    }

    @Override
    public void onResume(){
        super.onResume();
        naviLayout.setVisibility(View.VISIBLE);
    }

    public void backButtonFunction(){
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.i("backStackCount","backStackCount ========== "+backStackCount);

        if (backStackCount > 0) {
            super.onBackPressed();
            if (backStackCount == 1) {
                //Main 처리
                toast = Toast.makeText(getApplicationContext(), "메인 처리", Toast.LENGTH_SHORT);
                naviLayout.setVisibility(View.VISIBLE);
           //     finish();
            }
        } else {
            finish();
            //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
          /*  if(System.currentTimeMillis() > backKeyPressedTime + 2000){
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
