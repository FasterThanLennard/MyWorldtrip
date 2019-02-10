package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rhahn.myworldtrip.Data.AttributeData;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;
import com.rhahn.myworldtrip.DataHandler.Datapersistance;
import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TraveloverviewActivity extends AppCompatActivity {
    MyWorldtripData myWorldtripData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveloverview);

        TextView tvCost = findViewById(R.id.tvCost);
        tvCost.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvCostValue = findViewById(R.id.tvCostValue);
        tvCostValue.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvDuration = findViewById(R.id.tvDuration);
        tvDuration.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvDurationValue = findViewById(R.id.tvDurationValue);
        tvDurationValue.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvWeather = findViewById(R.id.tvWeather);
        tvWeather.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvWeatherValue = findViewById(R.id.tvWeatherValue);
        tvWeatherValue.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvVaccionation = findViewById(R.id.tvVaccination);
        tvVaccionation.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        TextView tvVaccionationValue = findViewById(R.id.tvVaccinationValue);
        tvVaccionationValue.setTextSize(this.getResources().getInteger(R.integer.textSizeOverview));
        myWorldtripData = Datapersistance.loadData(this);

        String cost = String.valueOf(calculateTravelcosts())+ this.getString(R.string.euro);
        tvCostValue.setText(cost);
        String duration = String.valueOf(calculateTraveldays()) + " " + this.getString(R.string.days);
        tvDurationValue.setText(duration);
        String weather = calculateWeather();
        tvWeatherValue.setText(weather);

        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(getString(R.string.traveloverview));
    }

    /**
     * Calculates total travelcosts
     *
     * @return total travelcosts
     */
    private int calculateTravelcosts() {
        int travelcosts = 0;
        if (myWorldtripData == null)
            Datapersistance.loadData(this);

        for (CountryData country : myWorldtripData.getCountries()) {
            for (AttributeData attribute : country.getAttributes()) {
                if (attribute.getName().equals(getResources().getResourceEntryName(R.string.travelcosts))) {
                    for (String attrName : attribute.getValues().keySet()) {
                        String value = attribute.getValues().get(attrName);
                        if (value.length() > 0) {
                            try {
                                if (attrName.contains("daily")) {
                                    int days = Util.getDaysBetweenTwoDates(country.getDateFrom(), country.getDateTo());
                                    travelcosts += days * Integer.parseInt(value);
                                } else {
                                    travelcosts += Integer.parseInt(value);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return travelcosts;
    }

    private String calculateWeather() {
        String min;
        String max;
        long minTemp = Long.MAX_VALUE;
        long maxTemp = Long.MIN_VALUE;
        long tmpMin = 0;
        long tmpMax = 0;
        if (myWorldtripData == null)
            Datapersistance.loadData(this);

        for (CountryData country : myWorldtripData.getCountries()) {
            for (AttributeData attribute : country.getAttributes()) {
                if (attribute.getName().equals(getResources().getResourceEntryName(R.string.weather))) {
                    min = attribute.getValues().get(this.getResources().getResourceEntryName(R.string.mintemp));
                    max = attribute.getValues().get(this.getResources().getResourceEntryName(R.string.maxtemp));
                    if(min.length() > 0)
                        tmpMin = Long.parseLong(min);
                    if(max.length() > 0)
                        tmpMax = Long.parseLong(max);

                    if (tmpMin < minTemp)
                        minTemp = tmpMin;
                    if (tmpMax > maxTemp)
                        maxTemp = tmpMax;
                }
            }
        }
        return String.valueOf(minTemp) + this.getString(R.string.celcius) + " " + this.getString(R.string.to) + " " + String.valueOf(maxTemp) + this.getString(R.string.celcius);
    }


    /**
     * Returns days of travel.
     *
     * @return days of travel
     */
    private int calculateTraveldays() {
        if (myWorldtripData == null)
            Datapersistance.loadData(this);

        //make sure countries are sorted
        Collections.sort(myWorldtripData.getCountries(), new Comparator<CountryData>() {
            @Override
            public int compare(CountryData o1, CountryData o2) {
                return o1.getDateFrom().compareTo(o2.getDateFrom());
            }
        });

        Date dateFrom;
        Date dateTo;
        int countrySize = myWorldtripData.getCountries().size();
        if (countrySize > 0) {
            dateFrom = myWorldtripData.getCountries().get(0).getDateFrom();
            dateTo = myWorldtripData.getCountries().get(countrySize - 1).getDateTo();
            return Util.getDaysBetweenTwoDates(dateFrom, dateTo);
        }
        return 0;
    }


}
