package com.rhahn.myworldtrip.DataHandler.ResponseListener;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class AllCountryResponseErrorListener implements Response.ErrorListener {
    Context context;

    public AllCountryResponseErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        int i = 1;
    }
}
