/**
 * <p>DemoActivity Class</p>
 * @author zhuzhenlei 2014-7-17
 * @version V1.0  
 * @modificationHistory
 * @modify by user: 
 * @modify by reason:
*/
package com.test.demo;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.MediaPlayer.PlayM4.Player;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hikvision.netsdk.*;

/**
 * <pre>
 *  ClassName  DemoActivity Class
 * </pre>
 * 
 * @author zhuzhenlei
 * @version V1.0
 * @modificationHistory
 */
public class DemoActivity extends Activity implements Callback
{
/*	private Button          m_oLoginBtn         	= null;
	private Button          m_oPreviewBtn           = null;
	private Button			m_oPlaybackBtn			= null;
	private Button			m_oParamCfgBtn			= null;
	private Button			m_oCaptureBtn			= null;
	private Button			m_oRecordBtn			= null;
	private	Button			m_oTalkBtn				= null;
	private	Button			m_oPTZBtn				= null;
	private Button			m_oOtherBtn			= null;
	private SurfaceView 	m_osurfaceView			= null;
	private EditText        m_oIPAddr				= null;
	private EditText		m_oPort					= null;
	private EditText		m_oUser					= null;
	private EditText		m_oPsd					= null;*/

    private SurfaceView 	m_osurfaceView			= null;
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
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("메인 들어옴 크리에이트","메인 들어옴 크리에이트");

		if (!initeSdk())
		{
			this.finish();
			return;
		}

		if (!initeActivity())
		{
			this.finish();
			return;
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
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("m_iPort", m_iPort);
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		m_iPort = savedInstanceState.getInt("m_iPort");
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState" );
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
		setListeners();
		return true;
	}
	// get controller instance
	private void findViews()
	{

		m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player);

	}
	// listen
	private void setListeners()
	{

	//	m_oCaptureBtn.setOnClickListener(Capture_Listener);

	}


	//capture listener
	private Button.OnClickListener Capture_Listener = new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			try
			{
				if(m_iPort < 0)
				{
					Log.e(TAG, "please start preview first");
					return;
				}
				Player.MPInteger stWidth = new Player.MPInteger();
				Player.MPInteger stHeight = new Player.MPInteger();
				if (!Player.getInstance().getPictureSize(m_iPort, stWidth, stHeight)){
					Log.e(TAG, "getPictureSize failed with error code:" + Player.getInstance().getLastError(m_iPort));
					return;
				}
				int nSize = 5 * stWidth.value * stHeight.value;
				byte[] picBuf = new byte[nSize];
				Player.MPInteger stSize = new Player.MPInteger();
				if(!Player.getInstance().getBMP(m_iPort, picBuf, nSize, stSize))
				{
					Log.e(TAG, "getBMP failed with error code:" + Player.getInstance().getLastError(m_iPort));
					return ;
				}

				SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
				String   date   =   sDateFormat.format(new   java.util.Date());
				FileOutputStream file = new FileOutputStream("/mnt/sdcard/" + date + ".bmp");
				file.write(picBuf, 0, stSize.value);
				file.close();
			}
			catch (Exception err)
			{
				Log.e(TAG, "error: " + err.toString());
			}
		}
	};


	//login listener
	private void ipCameraLogin(){
		Toast.makeText(getApplicationContext(),"로그인 버튼 클릭.",Toast.LENGTH_LONG).show();
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
			((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
					hideSoftInputFromWindow(DemoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
						startMultiPreview();
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
	private void startMultiPreview()
	{
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int i = 0;
		for(i = 0; i < 4; i++)
		{
			if(playView[i] == null)
			{
				playView[i] = new PlaySurfaceView(this);
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
	}
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
				DemoActivity.this.processRealData(1, iDataType, pDataBuffer, iDataSize, Player.STREAM_REALTIME);
			}
		};
		return cbf;
	}


	private PlaybackCallBack getPlayerbackPlayerCbf()
	{
		PlaybackCallBack cbf = new PlaybackCallBack()
		{
			@Override
			public void fPlayDataCallBack(int iPlaybackHandle, int iDataType, byte[] pDataBuffer, int iDataSize)
			{
				// player channel 1
				DemoActivity.this.processRealData(1, iDataType, pDataBuffer, iDataSize, Player.STREAM_FILE);
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


	public void Cleanup()
	{
		// release player resource

		Player.getInstance().freePort(m_iPort);
		m_iPort = -1;

		// release net SDK resource
		HCNetSDK.getInstance().NET_DVR_Cleanup();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_BACK:

				stopSinglePlayer();
				Cleanup();
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			default:
				break;
		}

		return true;
	}
}
