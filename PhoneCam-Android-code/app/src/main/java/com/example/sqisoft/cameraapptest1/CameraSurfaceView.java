package com.example.sqisoft.cameraapptest1;


import android.content.Context;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by SQISOFT on 2016-08-29.
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera camera = null;

    public CameraSurfaceView(Context context) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder Holder) {
        //카메라 오픈
        camera = Camera.open();
        Log.d("카메라 오픈","카메라가 오픈되었습니다.");

        try{
            //카메라 오픈을 통해 참조한 카메라 객체를 제어하기 위한 홀더 셋팅
            camera.setPreviewDisplay(mHolder);
            //카메라 뷰를 세로로 보이게 하기
            camera.setDisplayOrientation(90);

        }catch(Exception e){
            Log.e("CameraSurfaceView","Failed to set camera preview.",e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //surfaceChanged 함수는 서피스뷰 크기가 변경될 때 마다 새롭게 계속 호출, 필요하다면, setParameters 이용 필요한 파라미터값 설정 가능

            camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview(); //미리보기 기능 중지
            camera.release();
            camera = null; //null 저장
    }

    //사진을 찍기위해 버튼을 눌렀을 때 실행되는 capture 메소드
    public boolean capture(Camera.PictureCallback handler){
        if(camera != null){
            //사진을 찍는 takePicture
            camera.takePicture(null,null,handler);
            return true;

        }else{
            return false;
        }
    }


}
