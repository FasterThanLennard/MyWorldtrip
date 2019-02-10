package com.rhahn.myworldtrip.Activities;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.KeyEvent;

import com.rhahn.myworldtrip.Adapter.CountryAdapter;
import com.rhahn.myworldtrip.Data.AttributeData;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;

import com.rhahn.myworldtrip.DataHandler.Datapersistance;

import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.R;

import java.util.Objects;


/**
 * Activity to show all Information about a country
 *
 * @author Robin Hahn
 */
public class CountryActivity extends AppCompatActivity {
    MyWorldtripData worldtripData;
    RecyclerView rvCountry;
    CountryData country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        country = (CountryData) getIntent().getSerializableExtra("countryData");

        //set toolbar background and title
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(country.getName());

        //load current MyWorldtripData
        worldtripData = Datapersistance.loadData(this);

        //create recyclerview
        rvCountry = findViewById(R.id.rvCountry);
        rvCountry.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGreen));
        rvCountry.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCountry.setAdapter(new CountryAdapter(country));
    }

    /**
     * Adds or updates {@link AttributeData} values
     *
     * @param attributeIndex Index of the attribute that changes
     * @param key Key of the attributevalue
     * @param value value of the attributevalue
     */
    public void saveAttributeData(int attributeIndex, String key, String value) {
        int countryIndex = Util.getCountryIndex(worldtripData, country);
        worldtripData.getCountries().get(countryIndex).getAttributes().get(attributeIndex).getValues().put(key, value);
        Datapersistance.saveData(worldtripData, this);
    }

    /**
     * Stops activity when going back to {@link TimelineActivity}
     * @param keyCode keycode
     * @param event event
     * @return true when done
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
