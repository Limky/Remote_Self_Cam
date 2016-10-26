package com.sqisoft.remote.util;

import com.sqisoft.remote.BuildConfig;

/**
 * 로그에 Prefix 를 같이 출력 시키기 위한 Log Class
 */
public class Log {
	private static final int REAL_METHOD_POS = 2;

	/**
	 * 로그에 로그가 들어 있는 함수명과 라인을 출력 해 준다.
	 * 
	 * @return 함수명과 라인 String
	 */
	private static String getPrefix() {
		StringBuilder sb = new StringBuilder(1024);

		try {
			StackTraceElement[] ste = new Throwable().getStackTrace();
			StackTraceElement realMethod = ste[REAL_METHOD_POS];

			sb.append("[");
			sb.append(realMethod.getFileName());
			sb.append(":");
			sb.append(realMethod.getLineNumber());
			sb.append(":");
			sb.append(realMethod.getMethodName());
			sb.append("()] ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static void v(String tag, String msg) {
		String m = getPrefix() + msg;

		if (BuildConfig.DEBUG) {
			android.util.Log.v(tag, m);
		}
	}

	public static void i(String tag, String msg) {
		String m = getPrefix() + msg;

		if (BuildConfig.DEBUG) {
			android.util.Log.i(tag, m);
		}
	}

	public static void d(String tag, String msg) {
		String m = getPrefix() + msg;

		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, m);
	}

	public static void w(String tag, String msg) {
		String m = getPrefix() + msg;

		android.util.Log.w(tag, m);
	}

	public static void e(String tag, String msg) {
		String m = getPrefix() + msg;

		android.util.Log.e(tag, m);
	}


}
