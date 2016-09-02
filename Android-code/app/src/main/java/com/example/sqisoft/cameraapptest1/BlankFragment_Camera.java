package com.example.sqisoft.cameraapptest1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment_Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment_Camera extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String IMAGE_FILE = "capture.jpg";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public  Context context;
    FrameLayout preview;
    Button actionBtn,cancelBtn,saveImageBtn;
    LinearLayout saveLayout;
    CameraSurfaceView cameraView;
    Bitmap bitmap;
    String fileName;
    boolean rotate_landscape;



    private OnFragmentInteractionListener mListener;

    public BlankFragment_Camera() {
        // Required empty public constructor

        //처음에는 preview상태가 세로라고 전제
        rotate_landscape = false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_Camera.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment_Camera newInstance(String param1, String param2) {
        BlankFragment_Camera fragment = new BlankFragment_Camera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View CameraFrameView =  inflater.inflate(R.layout.fragment_camera, container, false);
        final CameraSurfaceView cameraView = new CameraSurfaceView(getActivity());
        saveLayout = (LinearLayout) CameraFrameView.findViewById(R.id.CameraSave);
        preview = (FrameLayout) CameraFrameView.findViewById(R.id.previewFrame);

        preview.addView(cameraView);//프래그먼트에서 프리뷰 시작.

        saveImageBtn = (Button) CameraFrameView.findViewById(R.id.saveImageBtn);
        cancelBtn = (Button) CameraFrameView.findViewById(R.id.cancel_Btn);
        actionBtn = (Button) CameraFrameView.findViewById(R.id.CameraAction);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"촬영 버튼 클릭.",Toast.LENGTH_SHORT).show();
                cameraView.capture(new Camera.PictureCallback(){
                    @Override
                    public void onPictureTaken(byte[] data, final Camera camera) {

                        try{

                       /*     onConfigurationChanged();*/
                  /*         int confirmRotate = getActivity().getRequestedOrientation();
                            Log.d("confirmRotate","getRequestedOrientation = "+confirmRotate);

                            Configuration config = getResources().getConfiguration();
                            Log.d("Configuration","orientation = "+config.orientation );

                            if(config.orientation == Configuration.ORIENTATION_LANDSCAPE ){
                                Log.d("현재 화면 모드:","가로 모드 입니다.");
                                rotate_landscape = true;
                            }else{
                                Log.d("현재 화면 모드:","세로 모드 입니다.");
                            }*/



                            WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
                            Display display = wm.getDefaultDisplay();
                            int orientation = display.getOrientation();
                            Log.d("현재 getOrientation:","orientation = "+orientation);

                            orientation = display.getRotation();
                            Log.d("현재 getRotation:","orientation = "+orientation);

                            changeLayout(true);

                           //JPEG 이미지를 Bitmap객체로 디코딩
                            bitmap = BitmapFactory.decodeByteArray(data,0,data.length);


                            //파일 이름 :날짜_시간
                            Date day = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
                            fileName = String.valueOf(sdf.format(day));
                            fileName +=".jpg";

                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "취소 버튼 클릭.", Toast.LENGTH_SHORT).show();
                                    camera.startPreview();
                                    changeLayout(false);
                                }
                            });

                            saveImageBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "이미지가 저장되었습니다.", Toast.LENGTH_SHORT).show();

                                    if(rotate_landscape != true) {

                                        Log.d("rotate 상태=","rotate_state="+rotate_landscape);
                                        bitmap = RotateBitmap(bitmap, 90);
                                    }

                                    createDirectoryAndSaveFile(bitmap, fileName);
                                    camera.startPreview();
                                    changeLayout(false);
                                }
                            });

                        }catch (Exception e){

                            Log.e("SampleCapture","Failed to insert image.",e);

                        }//capture 끝

                    }//onPictureTaken 끝
                });//cameraView.capture 끝

            }
        });//Button Event 끝



        return CameraFrameView;

    }//OnCreateView 끝


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) // 세로 전환시
        {
            // 배경 화면 교체 처리
            Log.d("현재 화면 모드:","세로 모드 입니다.");

        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)// 가로 전환시
        {
            // 배경 화면 교체 처리
            Log.d("현재 화면 모드:","가로 모드 입니다.");

        }
    }

    private void changeLayout(boolean b) {
        if(b) {
            actionBtn.setVisibility(View.GONE);
            saveLayout.setVisibility(View.VISIBLE);
        } else {
            actionBtn.setVisibility(View.VISIBLE);
            saveLayout.setVisibility(View.GONE);
        }
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/RemoteCamera");
        Log.d("filepath==", filepath.toString()+" "+filepath.exists());
        if (!filepath.exists()) {
            boolean ret = filepath.mkdirs();
            Log.d("mkdirs action success!!", "ret:"+ret);
        }


        File file = new File(new File("/storage/emulated/0/DCIM/RemoteCamera"), fileName);
        if (file.exists()) {
            file.delete();
        }


        try {

            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);

            galleryAddPic();

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("/storage/emulated/0/DCIM/RemoteCamera/"+fileName);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}



/*                                    String outUriStr = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,"Captured Image","Captured Image using Camera");
                                    Uri outUri = Uri.parse(outUriStr);
                                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,outUri));*/
