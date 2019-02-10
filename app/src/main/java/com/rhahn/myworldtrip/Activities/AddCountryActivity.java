package com.rhahn.myworldtrip.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rhahn.myworldtrip.Data.AttributeData;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;
import com.rhahn.myworldtrip.DataHandler.Datarequest;
import com.rhahn.myworldtrip.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Activity for adding country to the timeline
 *
 * @author Robin Hahn
 */
public class AddCountryActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText etDateFrom;
    EditText etDateTo;
    EditText etCurrentSelected;
    AutoCompleteTextView etCountry;
    Button btnAddCountry;
    MyWorldtripData myWorldtripData;
    CountryData countryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));

        //load data
        myWorldtripData = (MyWorldtripData) getIntent().getSerializableExtra("worldtripdata");

        etDateFrom = findViewById(R.id.etDateFrom);
        etDateFrom.setFocusable(false);
        etDateTo = findViewById(R.id.etDateTo);
        etDateTo.setFocusable(false);
        btnAddCountry = findViewById(R.id.btnAddCountry);
        etCountry = findViewById(R.id.etChooseCountry);


        //create datepicker for adding dates to the textview
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(year, monthOfYear, dayOfMonth);
                updateLabel(etCurrentSelected);
            }
        };

        //open datepicker on click
        etDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCurrentSelected = etDateFrom;
                new DatePickerDialog(view.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //open datepicker on click
        etDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCurrentSelected = etDateTo;
                new DatePickerDialog(view.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //get all country fpr autocompleteview
        Datarequest.getAllCountries(this);

        //add country to timeline onclick
        btnAddCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    countryData = addNewCountry();
                    myWorldtripData.getCountries().add(countryData);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(view.getContext(), TimelineActivity.class);
                intent.putExtra("worldtripdata", myWorldtripData);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Returns a new {@link CountryData} object with values from the view
     *
     * @return New country as {@link CountryData}
     * @throws ParseException Date couldn't be parsed
     */
    private CountryData addNewCountry() throws ParseException {
        //parse String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String name = etCountry.getText().toString();
        Date dateFrom = sdf.parse(etDateFrom.getText().toString());
        Date dateTo = sdf.parse(etDateTo.getText().toString());

        //split countryname and alpha2code
        int startAlpha2 = name.indexOf("(");
        int endAlpha2 = name.indexOf(")");
        String alpha2 = name.substring(startAlpha2 + 1, endAlpha2);
        name = name.substring(0, startAlpha2 - 1);
        return new CountryData(name, dateFrom, dateTo, null, new ArrayList<AttributeData>(), alpha2);
    }

    /**
     * Updates {@link EditText} by convert time in string
     *
     * @param editText
     */
    private void updateLabel(EditText editText) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        editText.setText(sdf.format(myCalendar.getTime()));
    }
}