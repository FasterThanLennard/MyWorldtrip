package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.rhahn.myworldtrip.DataHandler.ResponseListener.AllCountryResponseErrorListener;
import com.rhahn.myworldtrip.DataHandler.ResponseListener.AllCountryResponseListener;
import com.rhahn.myworldtrip.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Datarequest {

    public static void getAllCountries(Context context) {
        String url = "https://restcountries.eu/rest/v2/all?fields=name;capital;translations;alpha2Code";
        AllCountryResponseListener responseListener = new AllCountryResponseListener(context);
        AllCountryResponseErrorListener errorListener = new AllCountryResponseErrorListener(context);

        DataRequestQueue.init(context);
        sendRequest(url, responseListener, errorListener);
    }

    public static void getCountryInformation(String countryname, Context context, Response.Listener<String> responseListener) {
        String url = "https://restcountries.eu/rest/v2/name/" + countryname;
        //AllCountryResponseListener responseListener = new AllCountryResponseListener(context);
        AllCountryResponseErrorListener errorListener = new AllCountryResponseErrorListener(context);

        DataRequestQueue.init(context);
        sendRequest(url, responseListener, errorListener);
    }

    public static void sendRequest(String urlString, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString, responseListener, errorListener);
        // Add the request to the Singelton RequestQueue.
        DataRequestQueue.getInstance().add(stringRequest);
    }

    public static String getFileAsString(Context context, int ressource){
        InputStream is = context.getResources().openRawResource(ressource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        return jsonString;
    }
}