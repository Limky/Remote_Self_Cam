package com.test.demo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Camera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Camera extends Fragment implements SurfaceHolder.Callback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

/**+++++++++++++++++++++++++++++++++++++ Area inserted DemoActivity code ++++++++++++++++++++++++++++++++++++++++++++++++*/
    private SurfaceView m_osurfaceView			= null;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;


    private int				m_iLogID				= -1;				// return by NET_DVR_Login_v30
    private int 			m_iPlayID				= -1;				// return by NET_DVR_RealPlay_V30
    private int				m_iPlaybackID			= -1;				// return by NET_DVR_PlayBackByTime

    private int				m_iPort					= -1;				// play port
    private	int 			m_iStartChan 			= 0;				// start channel no
    private int				m_iChanNum				= 0;				//channel number
    private static PlaySurfaceView [] playView = new PlaySurfaceView[4];

    private final String 	TAG						= "DemoActivity";

    private boolean			m_bTalkOn				= false;
    private boolean			m_bPTZL					= false;
    private boolean			m_bMultiPlay			= false;

    private boolean			m_bNeedDecode			= true;

    /**+++++++++++++++++++++++++++++++++++++ Area inserted DemoActivity code ++++++++++++++++++++++++++++++++++++++++++++++++*/


    View fragment_camera_view;


    public Fragment_Camera() {
        // Required empty public constructor
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
    public static Fragment_Camera newInstance(String param1, String param2) {
        Fragment_Camera fragment = new Fragment_Camera();
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
        fragment_camera_view = inflater.inflate(R.layout.fragment_camera, container, false);

        Log.d("카메라 프래그먼트 진입완료","카메라 프래그먼트 onCreateView");


        if (!initeSdk())
        {
            getActivity().finish();

        }

        if (!initeActivity())
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



        return fragment_camera_view;

    }


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
    private boolean initeActivity()
    {
        findViews();
        m_osurfaceView.getHolder().addCallback(this);
   //     setListeners();
        return true;
    }

    private void findViews()
    {

        m_osurfaceView = (SurfaceView)fragment_camera_view.findViewById(R.id.Sur_Player);

    }


    //login listener
    private void ipCameraLogin(){
        Toast.makeText(getActivity(),"로그인 버튼 클릭.",Toast.LENGTH_LONG).show();
        try
        {
            if(m_iLogID < 0)
            {
                // login on the device
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


    

  private void startPreview(){

        Log.d("LimkyStartPreview 작동", "LimkyStartPreview 작동");
        try
        {
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
                        stopMultiPreview();
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
        previewInfo.dwStreamType = 1; //substream
        previewInfo.bBlocked = 1;
        // HCNetSDK start preview
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo, fRealDataCallBack);
        if (m_iPlayID < 0)
        {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return ;
        }

        Log.i(TAG, "NetSdk Play sucess ***********************3***************************");
        //	m_oPreviewBtn.setText("Stop");
    }


    /* private void startMultiPreview()
    {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int i = 0;
        for(i = 0; i < 4; i++)
        {
            if(playView[i] == null)
            {
                playView[i] = new PlaySurfaceView(getActivity());
                playView[i].setParam(metric.widthPixels);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = playView[i].getCurHeight() - (i/2) * playView[i].getCurHeight();
                params.leftMargin = (i%2) * playView[i].getCurWidth();
                params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                addContentView(playView[i], params);

            }
            playView[i].startPreview(m_iLogID, m_iStartChan + i);
        }
        m_iPlayID = playView[0].m_iPreviewHandle;
    }*/


    private void stopMultiPreview()
    {
        int i = 0;
        for(i = 0; i < 4;i++)
        {
            playView[i].stopPreview();
        }
        m_iPlayID = -1;
    }


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

    private int loginDevice()
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30)
        {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = "192.168.2.245";
        int	nPort = 8000;
        String strUser = "admin";
        String strPsd = "1234qwer";

        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd, m_oNetDvrDeviceInfoV30);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

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
