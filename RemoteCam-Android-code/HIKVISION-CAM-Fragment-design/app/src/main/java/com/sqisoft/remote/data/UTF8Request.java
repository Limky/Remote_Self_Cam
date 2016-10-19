package com.sqisoft.remote.data;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.sqisoft.remote.util.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UTF8Request extends StringRequest {
    private int mStatusCode;
    

    public int getStatusCode() {
        return mStatusCode;
    }

    public UTF8Request(int method, String url, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        //setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        //        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setRetryPolicy(new DefaultRetryPolicy(10000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public UTF8Request(String url, Listener<String> listener, ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
    	HashMap<String, String> params = new HashMap<String, String>();

        return params;
    }
    

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            mStatusCode = response.statusCode;
//            Log.d("test", "mStatusCode:"+mStatusCode);
            if(VolleyUtil.USE_SESSION) {
            	VolleyUtil.checkSessionCookie(response.headers);
            }
            String jsonString = new String(response.data, "utf-8");
            return Response.success(new String(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
        }

        return super.parseNetworkResponse(response);
    }
    
    
}