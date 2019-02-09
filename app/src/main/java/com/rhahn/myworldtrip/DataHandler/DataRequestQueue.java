package com.rhahn.myworldtrip.DataHandler;



import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DataRequestQueue{
    private static  DataRequestQueue singletonInstance;
    private static RequestQueue requestQueue;

    private DataRequestQueue(Context context){
        requestQueue = new Volley().newRequestQueue(context);
    }

    public static void init(Context context){
        if(singletonInstance == null)
                singletonInstance =  new DataRequestQueue(context);
    }

    public static RequestQueue getInstance() {
        return requestQueue;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
