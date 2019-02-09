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
import java.util.Locale;

public class AllCountryResponseListener implements Response.Listener<String> {
    Context context;

    public AllCountryResponseListener(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(String response) {
        String currentLang = Locale.getDefault().getLanguage();
        ArrayList<String> allCountries = new ArrayList<>();
        AutoCompleteTextView etCountry = ((AddCountryActivity)context).findViewById(R.id.etChooseCountry);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(response);

            String countryName;
            boolean isEnglish = currentLang.equals("en");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentCountry = (JSONObject) jsonArray.get(i);
                if(currentCountry.getJSONObject("translations").has(currentLang) && isEnglish == false){
                    countryName = currentCountry.getJSONObject("translations").get(currentLang).toString();
                    allCountries.add(countryName + " (" + currentCountry.get("alpha2Code").toString() + ")");
                } else {
                    allCountries.add(currentCountry.get("name").toString() + " (" + currentCountry.get("alpha2Code").toString() + ")");
                }
            }

            ArrayAdapter<String> allCountriesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, allCountries);
            //spCountries.setAdapter(adapter);
            etCountry.setAdapter(allCountriesAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

