package com.rhahn.myworldtrip.DataHandler.ResponseListener;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Errorlistener for the all country request
 *
 * @author Robin Hahn
 */
public class AllCountryResponseErrorListener implements Response.ErrorListener {
    private Context context;

    public AllCountryResponseErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }
}
