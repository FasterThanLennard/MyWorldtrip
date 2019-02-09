package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rhahn.myworldtrip.R;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(R.string.faq);
    }
}
