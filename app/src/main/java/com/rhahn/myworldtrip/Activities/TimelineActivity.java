package com.rhahn.myworldtrip.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rhahn.myworldtrip.Adapter.TimelineDataAdapter;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;
import com.rhahn.myworldtrip.DataHandler.DataCreator;
import com.rhahn.myworldtrip.DataHandler.DataRequestQueue;
import com.rhahn.myworldtrip.DataHandler.Datapersistance;
import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.R;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Stack;

/**
 * Main Activity represents a timeline and all other activities accessible from here.
 *
 * @author Robin Hahn
 */
public class TimelineActivity extends AppCompatActivity {
    RecyclerView rvTimeline;
    RecyclerView rvCountry;
    FloatingActionButton fabChooseCountry;
    MyWorldtripData myWorldtripData;
    CountryData newCountryData;
    CountryData currentSelected;
    TimelineDataAdapter timelineAdapter;
    Toolbar tbMain;
    Toolbar tbCountry;
    ImageView imageView;
    Stack<CountryData> restoreCountry = new Stack<>();
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        myWorldtripData = (MyWorldtripData) getIntent().getSerializableExtra("worldtripdata");
        //setToolbar
        tbMain = findViewById(R.id.toolbar_main);
        tbMain.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGreen));
        setSupportActionBar(tbMain);
        imageView = findViewById(R.id.ivFlag);
        isTablet = Util.isTablet(this);

        //checks if current device is a tablet
        if (isTablet) {
            rvCountry = findViewById(R.id.rvCountry);
            tbCountry = findViewById(R.id.toolbar_country);
            tbCountry.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGreen));
        }
        //Load from filesystem
        if (myWorldtripData == null)
            myWorldtripData = Datapersistance.loadData(this);

        //if no worldtripdata is found on the filesystem create new worldtripdata
        if (myWorldtripData == null)
            myWorldtripData = new MyWorldtripData();

        //sort countries
        Collections.sort(myWorldtripData.getCountries(), new Comparator<CountryData>() {
            @Override
            public int compare(CountryData o1, CountryData o2) {
                return o1.getDateFrom().compareTo(o2.getDateFrom());
            }
        });

        timelineAdapter = new TimelineDataAdapter(myWorldtripData.getCountries());

        //get data for a new country
        newCountryData = isCountryAdded(myWorldtripData);
        if (newCountryData != null) {
            String url = "https://restcountries.eu/rest/v2/alpha/" + newCountryData.getAlpha2Code();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject newCountry = new JSONObject(response);
                        DataCreator dataCreator = new DataCreator();
                        newCountryData = dataCreator.createNewCountry(newCountry, newCountryData, TimelineActivity.this);
                        setWeatherData(newCountryData);
                        timelineAdapter.notifyDataSetChanged();

                        for (int i = 0; i < myWorldtripData.getCountries().size(); i++) {
                            if (myWorldtripData.getCountries().get(i).getId() == newCountryData.getId() && myWorldtripData.getCountries().get(i).getAttributes().size() == 0) {
                                myWorldtripData.getCountries().get(i).setAttributes(newCountryData.getAttributes());
                            }
                        }
                        Datapersistance.saveData(myWorldtripData, TimelineActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            DataRequestQueue.init(this);
            DataRequestQueue.getInstance().add(stringRequest);
        }

        //on click open AddCountryActivity
        fabChooseCountry = findViewById(R.id.fabAddCountry);
        final MyWorldtripData finalWorldtripData = myWorldtripData;
        fabChooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddCountryActivity.class);
                intent.putExtra("worldtripdata", finalWorldtripData);
                startActivity(intent);
            }
        });

        //set timelinedata
        if (myWorldtripData.getCountries() != null && myWorldtripData.getCountries().size() > 0) {
            rvTimeline = findViewById(R.id.rvTimeline);
            rvTimeline.getRecycledViewPool().setMaxRecycledViews(1, 0);
            rvTimeline.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvTimeline.setAdapter(timelineAdapter);

            //delete country on swipe to a side
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    restoreCountry.push(myWorldtripData.getCountries().get(viewHolder.getAdapterPosition()));
                    String countryName = myWorldtripData.getCountries().get(viewHolder.getAdapterPosition()).getName();

                    myWorldtripData.getCountries().remove(viewHolder.getAdapterPosition());
                    Toast.makeText(getApplicationContext(), countryName + " " + getString(R.string.gotDeleted), Toast.LENGTH_LONG).show();
                    timelineAdapter.notifyDataSetChanged();

                    Datapersistance.saveData(myWorldtripData, TimelineActivity.this);
                }
            }).attachToRecyclerView(rvTimeline);

            Datapersistance.saveData(myWorldtripData, this);
        }
    }

    /**
     * Returns Country if its added but no attributes are set
     *
     * @param myWorldtripData Complete Worldtripdata
     * @return Country with new attributes
     */
    private CountryData isCountryAdded(MyWorldtripData myWorldtripData) {
        for (CountryData country : myWorldtripData.getCountries()) {
            if (country.getAttributes().size() == 0)
                return country;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //change to TraveloverviewActivity
            case R.id.traveloverview:
                Intent intent = new Intent(this, TraveloverviewActivity.class);
                startActivity(intent);
                return true;
            //create a new worldtrip override old data
            case R.id.newTrip:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                myWorldtripData = new MyWorldtripData();
                                //restart intent to reload all variables
                                Intent intent = new Intent(TimelineActivity.this, TimelineActivity.class);
                                startActivity(intent);
                                Datapersistance.saveData(myWorldtripData, TimelineActivity.this);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //nothing to do at the moment
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(this.getString(R.string.areUsureAllDeleted)).setPositiveButton(this.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(this.getString(R.string.no), dialogClickListener).show();
                return true;
            //change to LicenseActivity
            case R.id.license:
                Intent intentLicense = new Intent(TimelineActivity.this, LicenseActivity.class);
                startActivity(intentLicense);
                return true;
            //change to FAQActivity
            case R.id.faq:
                Intent intentFaq = new Intent(TimelineActivity.this, FAQActivity.class);
                startActivity(intentFaq);
                return true;
            // restores deletes country to MyWorldtripData
            case R.id.restore:
                if (!restoreCountry.empty()) {
                    //add last deletes country
                    myWorldtripData.getCountries().add(restoreCountry.pop());
                    //sort countries
                    Collections.sort(myWorldtripData.getCountries(), new Comparator<CountryData>() {
                        @Override
                        public int compare(CountryData o1, CountryData o2) {
                            return o1.getDateFrom().compareTo(o2.getDateFrom());
                        }
                    });
                    timelineAdapter.notifyDataSetChanged();
                    Datapersistance.saveData(myWorldtripData, this);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.noCountryToRestore, Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Sets currentSelected country for tablet compatibility
     *
     * @param currentSelected Country currently selected
     */
    public void setCurrentSelected(CountryData currentSelected) {
        this.currentSelected = currentSelected;
    }

    /**
     * Adds or updates attribute values
     *
     * @param attributeIndex Index of the attribute that changes
     * @param key            Key of the attributevalue
     * @param value          value of the attributevalue
     */
    public void saveAttributeData(int attributeIndex, String key, String value) {
        int countryIndex = Util.getCountryIndex(myWorldtripData, currentSelected);
        myWorldtripData.getCountries().get(countryIndex).getAttributes().get(attributeIndex).getValues().put(key, value);
        Datapersistance.saveData(myWorldtripData, this);
    }

    /**
     * Set Weatherdata to a country. It's doing a request to openweathermap
     *
     * @param countryData country to set weatherdata
     */
    private void setWeatherData(final CountryData countryData) {
        //do request
        String city = countryData.getAttributes().get(1).getValues().get(this.getResources().getResourceEntryName(R.string.capital));
        String url = "http://api.openweathermap.org/data/2.5/weather?appid=9c6eded2603820a827316b3c6b1bd42a&units=metric&q=" + city + "," + countryData.getAlpha2Code();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject weather = new JSONObject(response);
                    JSONObject main = weather.getJSONObject("main");
                    JSONObject wind = weather.getJSONObject("wind");
                    LinkedHashMap<String, String> values = new LinkedHashMap<>();
                    String tempmin = String.valueOf(main.getLong("temp_min"));
                    String tempmax = String.valueOf(main.getLong("temp_max"));
                    String huminity = String.valueOf(main.getLong("humidity"));
                    String windspeed = String.valueOf(wind.getLong("speed"));
                    values.put(TimelineActivity.this.getResources().getResourceEntryName(R.string.mintemp), tempmin);
                    values.put(TimelineActivity.this.getResources().getResourceEntryName(R.string.maxtemp), tempmax);
                    values.put(TimelineActivity.this.getResources().getResourceEntryName(R.string.humidity), huminity);
                    values.put(TimelineActivity.this.getResources().getResourceEntryName(R.string.windspeed), windspeed);
                    values.put(TimelineActivity.this.getResources().getResourceEntryName(R.string.source), "https://openweathermap.org/api");
                    MyWorldtripData data = Datapersistance.loadData(TimelineActivity.this);
                    for (int i = 0; i < data.getCountries().size(); i++) {
                        if (data.getCountries().get(i).getId() == countryData.getId()) {
                            data.getCountries().get(i).getAttributes().get(3).setValues(values);
                            break;
                        }
                    }
                    //save data
                    Datapersistance.saveData(data, TimelineActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        DataRequestQueue.getInstance().add(stringRequest);
    }
}

