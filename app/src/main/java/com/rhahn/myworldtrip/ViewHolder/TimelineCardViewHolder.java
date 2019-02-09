package com.rhahn.myworldtrip.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhahn.myworldtrip.Activities.CountryActivity;
import com.rhahn.myworldtrip.Activities.TimelineActivity;
import com.rhahn.myworldtrip.Adapter.CountryAdapter;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;
import com.rhahn.myworldtrip.DataHandler.Datapersistance;
import com.rhahn.myworldtrip.DataHandler.ImageRequest;
import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimelineCardViewHolder extends RecyclerView.ViewHolder {
    private ImageView timeline;
    private ImageView flag;
    private TextView countryname;
    private TextView traveldate;
    private Context context;
    private ConstraintLayout clTimelinecard;
    TimelineActivity timelineActivity;
    RecyclerView rvCountry;
    private boolean isTablet;

    public TimelineCardViewHolder(@NonNull final View itemView) {
        super(itemView);
        isTablet = TimelineActivity.isTablet(itemView.getContext());
        context = itemView.getContext();
        timeline = itemView.findViewById(R.id.ivTimeline);
        flag = itemView.findViewById(R.id.ivFlag);
        countryname = itemView.findViewById(R.id.tvCountryname);
        traveldate = itemView.findViewById(R.id.tvTraveldate);
        clTimelinecard = itemView.findViewById((R.id.clTimlinecard));
        timelineActivity = ((TimelineActivity)context);
        ConstraintLayout clOuter = itemView.findViewById(R.id.clOutter);
        setTimelinecardStyle(clTimelinecard, itemView.getContext());
        setConstraintLayoutStyle(clOuter, context);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = countryname.getText().toString();
                String traveldates = traveldate.getText().toString();
                CountryData countryData = getCountry(name, traveldates, v.getContext());

                if(isTablet){
                    rvCountry = timelineActivity.findViewById(R.id.rvCountry);
                    rvCountry.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                    rvCountry.setAdapter(new CountryAdapter(countryData));
                } else {
                    Intent countyIntent = new Intent(itemView.getContext(), CountryActivity.class);
                    countyIntent.putExtra("countryData", countryData);
                    v.getContext().startActivity(countyIntent);
                }
            }
        });
    }

    public void setCountryData(CountryData countryData, int i, int countryCnt) {
        if (i == 0 && countryCnt == 1) {
            timeline.setImageResource(R.mipmap.timeline_single);
        }

        if (i == 0 && countryCnt > 1) {
            timeline.setImageResource(R.mipmap.timeline_first);
        }

        if (i == countryCnt - 1 && countryCnt > 1)
            timeline.setImageResource(R.mipmap.timeline_last);

        countryname.setText(countryData.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String travelDate = sdf.format(countryData.getDateFrom()) + " - " + sdf.format(countryData.getDateTo());
        traveldate.setText(travelDate);
        countryname.setTextSize(20);

        flag.setMaxHeight(50);
        flag.setMaxWidth(50);
        if (countryData.getFlagURL() != null)
            ImageRequest.fetchSvg(context, countryData.getFlagURL(), flag);
    }

    private CountryData getCountry(String name, String traveldates, Context context) {
        MyWorldtripData myWorldtripData = Datapersistance.loadData(context);

        int pos = traveldates.indexOf("-");
        Date dateFrom = Util.convertStringToDate(traveldates.substring(0, pos - 1));
        Date dateTo = Util.convertStringToDate(traveldates.substring(pos + 2));

        if (myWorldtripData.getCountries().size() == 0 || dateFrom == null || dateTo == null)
            return null;

        for (CountryData country : myWorldtripData.getCountries()) {
            if (country.getName().equals(name) && Util.compareDates(dateFrom, dateTo)) {
                return country;
            }
        }
        return null;
    }

    private void setConstraintLayoutStyle(ConstraintLayout layout, Context context) {
        GradientDrawable border = new GradientDrawable();
        border.setColor(ContextCompat.getColor(context, R.color.lightGrey)); // background
        //border.setStroke(2, 0xFF000000); //black border with full opacity
        border.setStroke(2, Color.LTGRAY); //black border with full opacity
        border.setCornerRadius(50.0f);
        layout.setBackground(border);
    }

    private void setTimelinecardStyle(ConstraintLayout layout, Context context) {
        GradientDrawable border = new GradientDrawable();
        border.setColor(ContextCompat.getColor(context, R.color.lightGreen));
        layout.setBackground(border);
    }
}
