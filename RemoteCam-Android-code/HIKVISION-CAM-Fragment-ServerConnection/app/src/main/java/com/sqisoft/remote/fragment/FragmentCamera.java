package com.sqisoft.remote.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;
import com.sqisoft.remote.Demo;
import com.sqisoft.remote.R;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.view.PlaySurfaceView;

import org.MediaPlayer.PlayM4.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCamera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FragmentCamera extends FragmentBase implements SurfaceHolder.Callback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**+++++++++++++++++++++++++++++++++++++ Area inserted  code ++++++++++++++++++++++++++++++++++++++++++++++++*/
    private SurfaceView m_osurfaceView			= null;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;


    private int				m_iLogID				= -1;				// return by NET_DVR_Login_v30
    private int 			m_iPlayID				= -1;				// return by NET_DVR_RealPlay_V30
    private int				m_iPlaybackID			= -1;				// return by NET_DVR_PlayBackByTime
    private int				m_iPort					= -1;				// play port
    private	int 			m_iStartChan 			= 0;				// start channel no
    private int				m_iChanNum				= 0;				//channel number
    private static PlaySurfaceView[] playView = new PlaySurfaceView[4];

    private final String 	TAG						= "DemoActivity_HIK";

    private boolean			m_bTalkOn				= false;
    private boolean			m_bPTZL					= false;
    private boolean			m_bMultiPlay			= false;

    private boolean			m_bNeedDecode			= true;

    /**+++++++++++++++++++++++++++++++++++++ Area inserted  code ++++++++++++++++++++++++++++++++++++++++++++++++*/


    View fragment_camera_view;
    Button cameraActionBtn, cancelBtn, saveImageBtn;
    String  fileName;
    RelativeLayout userSelectLayout, widgetPlay,timerLayout;
    ImageView captureImageView,animatedImage,live_img;
    TextView timerTextView,infoView,ingTextView,limitTimeTextView,imageTitleTextView;
    Animation animation;
    FrameLayout image_frame;

    private LimitTimeHandler mLimitTimeHandler = new LimitTimeHandler();
    private PhotoViewAttacher mAttacher;
    private Bitmap bit = null;

    private static final int CHANNEL = 1;
    private static final int MSG_LIMIT = 1;
    private static final int MSG_CAMERA = 2;
    private static final int ONE_SECOND = 1000;

    FragmentManager manager;
    FragmentTransaction transaction;


    public FragmentCamera() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Camera.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCamera newInstance(String param1, String param2) {
        FragmentCamera fragment = new FragmentCamera();
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

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment_camera_view = inflater.inflate(R.layout.fragment_camera, container, false);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        setTitle("촬영 준비");
        Log.d("카메라 프래그먼트 진입완료","카메라 프래그먼트 onCreateView");


        if (!initeSdk())
        {
            getActivity().finish();

        }

        if (!initFragment())
        {
            getActivity().finish();
        }

        ipCameraLogin();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                startPreview();
            }
        }, 100);// 0.1초 정도 딜레이를 준 후 시작

        //네트워크 카메라부터 할당된 초시간 보여주기
        mLimitTimeHandler.setLimitF(this);
        mLimitTimeHandler.sendEmptyMessageDelayed(MSG_LIMIT, ONE_SECOND);


        return fragment_camera_view;

    }

    //네트워크 카메라 초기 설정
    private boolean initeSdk()
    {
        //init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init())
        {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",true);
        return true;
    }
    // GUI init
    private boolean initFragment()
    {
        findViews();
        m_osurfaceView.getHolder().addCallback(this);
        setListeners();
        return true;
    }

    //뷰 xml 달아주기.
    private void findViews()
    {
        m_osurfaceView = (SurfaceView)fragment_camera_view.findViewById(R.id.sur_player);
        cancelBtn = (Button)fragment_camera_view.findViewById(R.id.cancel_btn);
        saveImageBtn = (Button)fragment_camera_view.findViewById(R.id.save_image_btn);
        cameraActionBtn = (Button) fragment_camera_view.findViewById(R.id.camera_action_btn);
        //  userSelectLayout = (RelativeLayout) fragment_camera_view.findViewById(R.id.user_select_layout);
        captureImageView = (ImageView) fragment_camera_view.findViewById(R.id.capture_imageview);
        timerLayout = (RelativeLayout) fragment_camera_view.findViewById(R.id.timer_layout);
        timerTextView = (TextView) fragment_camera_view.findViewById(R.id.timer);
        animatedImage = (ImageView) fragment_camera_view.findViewById(R.id.animation_circle_loading);
        infoView = (TextView) fragment_camera_view.findViewById(R.id.info_textview);
        //  ingTextView = (TextView) fragment_camera_view.findViewById(R.id.ing_textview);
        limitTimeTextView = (TextView) fragment_camera_view.findViewById(R.id.limit_time_textview);
        limitTimeTextView.setText(Html.fromHtml("<u>" + "00: 29" + "</u>"));
        widgetPlay = (RelativeLayout) fragment_camera_view.findViewById(R.id.widget_play);
        //  imageTitleTextView = (TextView) fragment_camera_view.findViewById(R.id.image_title);
        image_frame = (FrameLayout) fragment_camera_view.findViewById(R.id.image_frame);
        live_img = (ImageView) fragment_camera_view.findViewById(R.id.live_img);

    }

    //버튼 이벤트 달아주기.
    private void setListeners()
    {
        cameraActionBtn.setOnClickListener(Capture_Listener);
        cancelBtn.setOnClickListener(Cancel_Listener);
        saveImageBtn.setOnClickListener(Saveimage_Listener);
    }

    //login 함수
    private void ipCameraLogin(){
        Toast.makeText(getActivity(),"로그인 버튼 클릭.",Toast.LENGTH_LONG).show();
        try
        {
            if(m_iLogID < 0)
            {
                // login on the device loginDevice()함수를 호출하여 처음 서버 ip/id/password 등으로 접속하기
                m_iLogID = loginDevice();
                if (m_iLogID < 0)
                {
                    Log.e(TAG, "This device logins failed!");
                    return;
                }
                // get instance of exception callback and set
                ExceptionCallBack oexceptionCbf = getExceptiongCbf();
                if (oexceptionCbf == null)
                {
                    Log.e(TAG, "ExceptionCallBack object is failed!");
                    return ;
                }

                if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(oexceptionCbf))
                {
                    Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                    return;
                }

                //	m_oLoginBtn.setText("Logout");
                Log.i(TAG, "Login sucess ****************************1***************************");
            }
            else
            {
                // whether we have logout
                if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID))
                {
                    Log.e(TAG, " NET_DVR_Logout is failed!");
                    return;
                }
                //	m_oLoginBtn.setText("Login");
                m_iLogID = -1;
            }
        }
        catch (Exception err)
        {
            Log.e(TAG, "error: " + err.toString());
        }
    }


    //로그인 함수 안에서 호출되는 디바이스 로그인 함수 // 네트워크 IP/PASSWORD/ID 등으로 설정 및 접속
    private int loginDevice()
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30)
        {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }

        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(Demo.TEMP_SERVER_URL, Demo.TEMP_SERVER_PORT, Demo.USER_ID, Demo.PASSWORD, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0)
        {
            Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        if(m_oNetDvrDeviceInfoV30.byChanNum > 0)
        {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        }
        else if(m_oNetDvrDeviceInfoV30.byIPChanNum > 0)
        {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }





    //로그인 완료시 네트워크 카메라 영상을 앱에 실시간으로 보여주기.
    private void startPreview(){

        Log.d("LimkyStartPreview 작동", "LimkyStartPreview 작동");
        try
        {
            Thread.sleep(500);
            ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            if(m_iLogID < 0)
            {
                Log.e(TAG,"please login on device first");
                return ;
            }
            if(m_bNeedDecode)
            {
                if(m_iChanNum > 1)//preview more than a channel
                {
                    if(!m_bMultiPlay)
                    {
                        //     startMultiPreview();
                        m_bMultiPlay = true;
                        //	m_oPreviewBtn.setText("Stop");
                    }
                    else
                    {
                        //   stopMultiPreview();
                        m_bMultiPlay = false;
                        //		m_oPreviewBtn.setText("Preview");
                    }
                }
                else	//preivew a channel
                {
                    if(m_iPlayID < 0)
                    {
                        startSinglePreview();
                    }
                    else
                    {
                        stopSinglePreview();
                        //		m_oPreviewBtn.setText("Preview");
                    }
                }
            }
            else
            {

            }
        }
        catch (Exception err)
        {
            Log.e(TAG, "error: " + err.toString());
        }

    }


    //startPreview 에서 호출되는 preview 함수(채널 설정)
    private void startSinglePreview()
    {
        if(m_iPlaybackID >= 0)
        {
            Log.i(TAG, "Please stop palyback first");
            return ;
        }
        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
        if (fRealDataCallBack == null)
        {
            Log.e(TAG, "fRealDataCallBack object is failed!");
            return ;
        }
        Log.i(TAG, "m_iStartChan:" +m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = CHANNEL; //substream 채널 설정
        previewInfo.bBlocked = 1;
        // HCNetSDK start preview
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo, fRealDataCallBack);
        if (m_iPlayID < 0)
        {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return ;
        }

        Log.i(TAG, "NetSdk Play sucess *****************   "+CHANNEL+"채널   ***********************");
        //	m_oPreviewBtn.setText("Stop");
    }

    //프리뷰를 정지시킨다
    private void stopSinglePreview()
    {
        if ( m_iPlayID < 0)
        {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }

        //  net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID))
        {
            Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }

        m_iPlayID = -1;
        stopSinglePlayer();
    }



    //stopSinglePreview 함수 안에서 호출되는 프리뷰 정지 함수
    private void stopSinglePlayer()
    {
        Player.getInstance().stopSound();
        // player stop play
        if (!Player.getInstance().stop(m_iPort))
        {
            Log.e(TAG, "stop is failed!");
            return;
        }

        if(!Player.getInstance().closeStream(m_iPort))
        {
            Log.e(TAG, "closeStream is failed!");
            return;
        }
        if(!Player.getInstance().freePort(m_iPort))
        {
            Log.e(TAG, "freePort is failed!" + m_iPort);
            return;
        }
        m_iPort = -1;
    }


    //디바이스 관련 에러 메시지 호출해주는 함수
    private ExceptionCallBack getExceptiongCbf()
    {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack()
        {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle)
            {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    //실질적으로 영상을 실시간으로 불러와 처리하는 함수
    private RealPlayCallBack getRealPlayerCbf()
    {
        RealPlayCallBack cbf = new RealPlayCallBack()
        {
            public void fRealDataCallBack(int iRealHandle, int iDataType, byte[] pDataBuffer, int iDataSize)
            {
                // player channel 1
                processRealData(1, iDataType, pDataBuffer, iDataSize, Player.STREAM_REALTIME);
            }
        };
        return cbf;
    }

    //getRealPlayerCbf 함수 안에서 호출되는 함수 실시간 영상 데이터를 가져옴.
    public void processRealData(int iPlayViewNo, int iDataType, byte[] pDataBuffer, int iDataSize, int iStreamMode)
    {
        if(!m_bNeedDecode)
        {
            //   Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" + iDataType + ",iDataSize:" + iDataSize);
        }
        else
        {
            if(HCNetSDK.NET_DVR_SYSHEAD == iDataType)
            {
                if(m_iPort >= 0)
                {
                    return;
                }
                m_iPort = Player.getInstance().getPort();
                if(m_iPort == -1)
                {
                    Log.e(TAG, "getPort is failed with: " + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                Log.i(TAG, "getPort succ with: " + m_iPort);
                if (iDataSize > 0)
                {
                    if (!Player.getInstance().setStreamOpenMode(m_iPort, iStreamMode))  //set stream mode
                    {
                        Log.e(TAG, "setStreamOpenMode failed");
                        return;
                    }
                    if (!Player.getInstance().openStream(m_iPort, pDataBuffer, iDataSize, 2*1024*1024)) //open stream
                    {
                        Log.e(TAG, "openStream failed");
                        return;
                    }
                    if (!Player.getInstance().play(m_iPort, m_osurfaceView.getHolder()))
                    {
                        Log.e(TAG, "play failed");
                        return;
                    }
                    if(!Player.getInstance().playSound(m_iPort))
                    {
                        Log.e(TAG, "playSound failed with error code:" + Player.getInstance().getLastError(m_iPort));
                        return;
                    }
                }
            }
            else
            {
                if (!Player.getInstance().inputData(m_iPort, pDataBuffer, iDataSize))
                {
//		    		Log.e(TAG, "inputData failed with: " + Player.getInstance().getLastError(m_iPort));
                    for(int i = 0; i < 4000 && m_iPlaybackID >=0 ; i++)
                    {
                        if (!Player.getInstance().inputData(m_iPort, pDataBuffer, iDataSize))
                            Log.e(TAG, "inputData failed with: " + Player.getInstance().getLastError(m_iPort));
                        else
                            break;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                }

            }
        }

    }

    //캡처시작 버튼 이벤트
    private Button.OnClickListener Capture_Listener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            setTitle("촬영 중");
            captureModeLayout(true);
            //    mCameraTimeHandler.sendEmptyMessageDelayed(MSG_CAMERA,ONE_SECOND);

            mLimitTimeHandler.removeMessages(MSG_CAMERA);
            mLimitTimeHandler.sendEmptyMessageDelayed(MSG_CAMERA, ONE_SECOND);
            Toast.makeText(getActivity(),"카메라 캡춰링.",Toast.LENGTH_SHORT).show();

        }
    };

    public void captureEndAndInit(){

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                new WebGetImage().execute();
                changeImageviewLayout(true);
                setTitle("촬영완료");
                //한번 더 함수를 호출해 토큰을 이용 프리뷰를 멈춘다.
                startPreview();
            }
        }, 100);// 4초 정도 딜레이를 준 후 시작*/

    }

    @Override
    String getTitle() {
        return "촬영 준비";
    }




    //네트워크 카메라 웹 뷰에서 이미지를 가져온다.
    public class WebGetImage extends AsyncTask<Void, Void, Void> {

        String image_title = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
            image_title = String.valueOf(sdf.format(day));
        }

        @Override
        protected Void doInBackground(Void... params) {
            // 네트워크에 접속해서 데이터를 가져옴

            try {
                //웹사이트에 접속 (사진이 있는 주소로 접근)
                URL Url = new URL(Demo.TEMP_SERVER_CAPTURE_URL);
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

        @Override
        protected  void onPostExecute(Void nothing){
            captureImageView.setImageBitmap(bit);
            mAttacher = new PhotoViewAttacher(captureImageView);
            captureImageView.invalidate();

//            imageTitleTextView.setText(image_title);
        }


    }


    //라이브 뷰를 다시 재시작한다.
    private void restartLiveView(){

        //현재 멈춰진 프리뷰를 다시 호츨해 프리뷰를 시작한다.
        changeImageviewLayout(false);
        cameraActionBtn.setVisibility(View.VISIBLE);
        infoView.setVisibility(View.VISIBLE);

        startPreview();

    }

    //취소버튼이벤트
    private Button.OnClickListener Cancel_Listener = new Button.OnClickListener() {
        public void onClick(View v) {
            //     restartLiveView();
        }
    };

    //이미지를 저장한다. (현재 로컬)
    private Button.OnClickListener Saveimage_Listener = new Button.OnClickListener() {
        public void onClick(View v) {


            Toast.makeText(getActivity(), "image saved", Toast.LENGTH_SHORT).show();
            DataManager dataManager = DataManager.getInstance();
            dataManager.setBitmap(bit);

            transaction.replace(R.id.replacedLayout, new FragmentUserConfirm(),"FUser").commit();


            //파일 이름 :날짜_시간
            Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
            fileName = String.valueOf(sdf.format(day));
            fileName +=".jpg";


            File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/HIKVISION");
            Log.d("filepath==", filepath.toString()+" "+filepath.exists());
            if (!filepath.exists()) {
                boolean ret = filepath.mkdirs();
                Log.d("mkdirs action success!!", "ret:"+ret);
            }

            File filespace = new File(new File("/storage/emulated/0/DCIM/HIKVISION"), fileName);
            if (filespace.exists()) {
                filespace.delete();
            }

            try {

                FileOutputStream file = new FileOutputStream(filespace);
                bit.compress(Bitmap.CompressFormat.JPEG,100,file);

                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            galleryAddPic();

            //다시 레이아웃을 처음으로 돌린다.
            //    restartLiveView();

        }

    };


    //기본갤러리에서 저장 및 refresh
    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("/storage/emulated/0/DCIM/HIKVISION/"+fileName);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);

    }

    //프리뷰에서 캡춰되 이미지로 변환
    private void changeImageviewLayout(boolean b){
        if(b){
            captureImageView.setVisibility(View.VISIBLE);
            PhotoViewAttacher attacher = new PhotoViewAttacher(captureImageView);
            attacher.setScaleType(ImageView.ScaleType.FIT_XY);
            attacher.update();

            m_osurfaceView.setVisibility(View.GONE);
            live_img.setVisibility(View.GONE);

            //       userSelectLayout.setVisibility(View.VISIBLE);
            saveImageBtn.setVisibility(View.VISIBLE);
            widgetPlay.setBackgroundColor(Color.parseColor("#000000"));
            widgetPlay.invalidate();
            image_frame.setVisibility(View.GONE);
            //    image_frame.setBackgroundColor(Color.parseColor("#000000"));
            //  image_frame.setPadding(40,30,40,30);
            image_frame.invalidate();


        }else{
            captureImageView.setVisibility(View.GONE);
            m_osurfaceView.setVisibility(View.VISIBLE);
            saveImageBtn.setVisibility(View.VISIBLE);
            //      userSelectLayout.setVisibility(View.GONE);
        }

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

    //@Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_osurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.i(TAG, "surface is created" + m_iPort);
        if (-1 == m_iPort)
        {
            return;
        }
        Surface surface = holder.getSurface();
        if (true == surface.isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, holder)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }
    //@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    //@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
        if (-1 == m_iPort)
        {
            return;
        }
        if (true == holder.getSurface().isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
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


    public void captureModeLayout(boolean b){
        if(b) {

            image_frame.setBackgroundColor(Color.parseColor("#ea3986"));
            image_frame.invalidate();
            live_img.setBackgroundResource(R.drawable.live2);
            live_img.invalidate();
            cameraActionBtn.setVisibility(View.GONE);
            //      animatedImage.setVisibility(View.VISIBLE);
            //       animatedImage.startAnimation(animation);
            infoView.setVisibility(View.GONE);
            limitTimeTextView.setVisibility(View.GONE);
//           ingTextView.setVisibility(View.VISIBLE);
            timerLayout.setVisibility(View.VISIBLE);

        }else {

            //         animatedImage.setAnimation(null);
            //       animatedImage.setVisibility(View.GONE);
            //          ingTextView.setVisibility(View.GONE);
            timerLayout.setVisibility(View.GONE);
            timerTextView.setText("3");


        }
    }


    private static class LimitTimeHandler extends Handler{
        private int limitTime = 20;
        private int cameraTime = 3;
        FragmentCamera mFragment_Camera;
        public void setLimitF(FragmentCamera cameraF){
            mFragment_Camera = cameraF;
        }
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MSG_LIMIT:

                    //대기시간 업데이트
                    if(limitTime != 0) {
                        Log.i("limitTime=======", "limitTime===" + limitTime);
                        sendEmptyMessageDelayed(msg.what, ONE_SECOND);
                        String str = "00: " + limitTime;
                        if(limitTime < 10) str = "00: 0"+limitTime;
                        mFragment_Camera.limitTimeTextView.setText(Html.fromHtml("<u>" + str + "</u>"));
                        mFragment_Camera.limitTimeTextView.invalidate();
                        limitTime--;

                    }
                    break;


                case MSG_CAMERA:

                    //대기시간 업데이트
                    if(cameraTime != 0) {
                        Log.i("cameraTime=======", "cameraTime===" + cameraTime);
                        sendEmptyMessageDelayed(MSG_CAMERA, ONE_SECOND);
                        String str = Integer.toString(cameraTime);
                        mFragment_Camera.timerTextView.setText(str);
                        mFragment_Camera.timerTextView.invalidate();
                        cameraTime--;

                    }else {

                        mFragment_Camera.captureModeLayout(false);
                        mFragment_Camera.captureEndAndInit();
                        cameraTime = 3;
                    }
                    break;

            }

            super.handleMessage(msg);

        }
    }




}