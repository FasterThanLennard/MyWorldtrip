package com.rhahn.myworldtrip.DataHandler.ResponseListener;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.rhahn.myworldtrip.Activities.AddCountryActivity;
import com.rhahn.myworldtrip.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class CountryResponseListener implements Response.Listener<String> {
    Context context;

    LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

    public CountryResponseListener(Context context) {
        this.context = context;
    }

    public LinkedHashMap<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void onResponse(String response) {
        ArrayList<String> allCountries = new ArrayList<>();
        AutoCompleteTextView etCountry = ((AddCountryActivity)context).findViewById(R.id.etChooseCountry);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(response);

            String countryName;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentAttribute = (JSONObject) jsonArray.get(i);
                attributes.put("cioc", currentAttribute.get("cioc").toString());
                attributes.put("cioc", currentAttribute.get("cioc").toString());
                attributes.put("cioc", currentAttribute.get("cioc").toString());
            }

            ArrayAdapter<String> allCountriesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, allCountries);
            //spCountries.setAdapter(adapter);
            etCountry.setAdapter(allCountriesAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
