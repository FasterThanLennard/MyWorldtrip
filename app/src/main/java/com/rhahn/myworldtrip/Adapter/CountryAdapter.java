package com.rhahn.myworldtrip.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.R;
import com.rhahn.myworldtrip.ViewHolder.CountryCardViewHolder;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter {
    private CountryData country;
    private ArrayList<Integer> isAdded = new ArrayList<>();

    public CountryAdapter(CountryData country) {
        this.country = country;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.countrycard_layout, viewGroup, false) ;
        return new CountryCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
       if(!isAdded.contains(i)){
           ((CountryCardViewHolder) viewHolder).setAttribute(country.getAttributes().get(i), i);
            isAdded.add(i);
       }
    }

    @Override
    public int getItemCount() {
        return country.getAttributes().size();
    }
}
