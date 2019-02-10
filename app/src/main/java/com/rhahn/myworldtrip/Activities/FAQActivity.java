package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rhahn.myworldtrip.DataHandler.Datarequest;
import com.rhahn.myworldtrip.R;

import java.util.Objects;

/**
 * Activity to show FAQ's
 *
 * @author Robin Hahn
 */
public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        //set toolbar color
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(R.string.faq);

        //load and set data from txt file
        String faqText = Datarequest.getFileAsString(this, R.raw.faq);
        TextView tvFAQ = findViewById(R.id.tvFAQ);
        tvFAQ.setTextSize(this.getResources().getInteger(R.integer.textSizeFaq));
        tvFAQ.setText(faqText);

    }
}
