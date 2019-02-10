package com.rhahn.myworldtrip.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.R;
import com.rhahn.myworldtrip.ViewHolder.TimelineCardViewHolder;

import java.util.ArrayList;

public class TimelineDataAdapter extends RecyclerView.Adapter {
    private ArrayList<CountryData> countries;
    private int countryCnt;

    public TimelineDataAdapter(ArrayList<CountryData> countries) {
        this.countries = countries;
        countryCnt = countries.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timelinecard_layout, viewGroup, false);
        return new TimelineCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TimelineCardViewHolder) viewHolder).setCountryData(countries.get(i), i ,countryCnt);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

}
