package com.test.sqisoft.remote.util;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.sqisoft.remote.Demo;
import com.test.sqisoft.remote.data.AbstractRequest;
import com.test.sqisoft.remote.data.ResponseListener;
import com.test.sqisoft.remote.data.ZoneData;

import java.io.File;
import java.util.HashMap;

public class CommandUtil {
	//TODO : url 수정
	public static final String URL_ZONE = "/remote/api/zone";

	public static void getZone(final ResponseListener<ZoneData[]> listener) {
		String url = (Demo.MODE_TEST) ? "file:///android_asset/zone.json" : Demo.SERVER_URL + URL_ZONE + File.separator;
		getArray(url, ZoneData[].class, listener);
	}


	private static <U, V> void getArray(final String url, final Class<V> clazz, final ResponseListener<U> listener) {
//		 Log.d("test", url);
		 //TODO : 소스 정리.
		 try {
			VolleyUtil.getInstance().get(url, new Listener<String>() {
					@Override
					public void onResponse(String data) {
						if (listener != null) {
							try {
//								Log.d("test", data);
								U[] userCountList = (U[]) new Gson().fromJson(data, clazz);
								if (userCountList!= null)
									listener.response(true, (U)userCountList);
								else {
									listener.response(false, null);
								}
							} catch (Exception e) {
								e.printStackTrace();
								// Log.e(SFCore.TAG, "error " + e.getMessage());
								listener.response(false, null);
							}
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// Log.d(SFCore.TAG, arg0.getMessage());
						if (listener != null) {
							listener.response(false, null);
						}
					}
				});
		} catch (Exception e) {
			e.printStackTrace();
			if (listener != null) {
				listener.response(false, null);
			}
		}
	}
	
	private static <U> void get(final String url, final ResponseListener<U> listener) {
//		 Log.d("test", url);
		 VolleyUtil.getInstance().get(url, new Listener<String>() {
				@Override
				public void onResponse(String data) {
					if (listener != null) {
						try {
//							Log.d("test", data);
							U userCountList = new Gson().fromJson(data, new TypeToken<U>(){}.getType());
							if (userCountList!= null)
								listener.response(true, userCountList);
							else {
								listener.response(false, null);
							}
						} catch (Exception e) {
							e.printStackTrace();
							// Log.e(SFCore.TAG, "error " + e.getMessage());
							listener.response(false, null);
						}
					}
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// Log.d(SFCore.TAG, arg0.getMessage());
					if (listener != null) {
						listener.response(false, null);
					}
				}
			});
	}

	private static <U, V extends AbstractRequest<?>> void get(final String url, final Class<V> clazz, final ResponseListener<U> listener) {
		get(url, new Listener<String>() {
			@Override
			public void onResponse(String data) {
				if (listener != null) {
					try {
//						Log.d("test", data);
						V userCountList = (V) new Gson().fromJson(data, new TypeToken<V>(){}.getType());
						if (userCountList.isSuccess())
							listener.response(true, (U) userCountList.getData());
						else {
							listener.response(false, (U) userCountList.getData());
						}
					} catch (Exception e) {
						e.printStackTrace();
						// Log.e(SFCore.TAG, "error " + e.getMessage());
						listener.response(false, null);
					}
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// Log.d(SFCore.TAG, arg0.getMessage());
				if (listener != null) {
					listener.response(false, null);
				}
			}
		});
	}

	private static <U, V extends AbstractRequest<?>> void get(final String url, final Listener<String> listener, final ErrorListener errorListener) {
		VolleyUtil.getInstance().get(url, listener, errorListener);
	}

	private static <U, V extends AbstractRequest<?>> void post(final String url, HashMap<String, String> param, final Class<V> clazz,
			final ResponseListener<U> listener) {
		// Log.d("test", url);
		//
		// for (String key : param.keySet()) {
		// Log.d("test", key+ ":"+param.get(key));
		// }

		VolleyUtil.getInstance().post(url, param, new Listener<String>() {
			@Override
			public void onResponse(String data) {
				if (listener != null) {
					try {
						// Log.d("test", data);
						V userCountList = (V) new Gson().fromJson(data, clazz);
						if (userCountList.isSuccess())
							listener.response(true, (U) userCountList.getData());
						else {
							listener.response(false, (U) userCountList.getData());
						}
					} catch (Exception e) {
						e.printStackTrace();
						// Log.e(SFCore.TAG, "error " + e.getMessage());
						listener.response(false, null);
					}
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// Log.d(SFCore.TAG, arg0.getMessage());
				if (listener != null) {
					listener.response(false, null);
				}
			}
		});
	}
}
