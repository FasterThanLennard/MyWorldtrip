package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rhahn.myworldtrip.DataHandler.Datarequest;
import com.rhahn.myworldtrip.R;

import java.util.Objects;

/**
 * Activity to show information about the used license
 *
 * @author Robin Hahn
 */
public class LicenseActivity extends AppCompatActivity {
    TextView tvLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        //set toolbar color and text
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(R.string.license);

        //set text from .txt
        tvLicense = findViewById(R.id.tvLicense);
        tvLicense.setText(Datarequest.getFileAsString(this, R.raw.license));
    }
}
