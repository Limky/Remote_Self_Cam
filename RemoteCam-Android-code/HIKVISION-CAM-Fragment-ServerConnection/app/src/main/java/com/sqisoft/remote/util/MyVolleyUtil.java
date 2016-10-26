package com.sqisoft.remote.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sqisoft.remote.Demo;
import com.sqisoft.remote.domain.ServerImageDomain;

import org.json.JSONObject;

/**
 * Created by SQISOFT on 2016-10-25.
 */
public class MyVolleyUtil {

    private static MyVolleyUtil mInstance = null;
    public static final String URL_ZONE = "/remote/api/zone";


    public static MyVolleyUtil getInstance() {
        if(mInstance == null) {
            mInstance = new MyVolleyUtil();
        }
        return mInstance;
    }

    private Context getContext() {
        return Demo.getContext();
    }

    public void sendRequest(){

       // String url = (Demo.MODE_TEST) ? "file:///android_asset/zone.json" : Demo.SERVER_URL + URL_ZONE + File.separator;
        String ret = "file:///android_asset/serverimage.json";

        JSONObject parameters = new JSONObject();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ret, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new GsonBuilder().create();
                ServerImageDomain[] data = gson.fromJson(response.toString(),ServerImageDomain[].class);
                Log.i("data[0]","data[0] = "+data[0].toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonRequest);

    }





}
