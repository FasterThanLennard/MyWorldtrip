package com.rhahn.myworldtrip.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class MyWorldtripData implements Serializable {
    private static final long serialVersionUID = 8274991317524100610L;
    ArrayList<CountryData> countries;
    SummaryData summaryData;

    public MyWorldtripData(){
        this.countries = new ArrayList<>();
        this.summaryData = new SummaryData();
    }
    public MyWorldtripData(ArrayList<CountryData> countries, SummaryData summaryData) {
        this.countries = countries;
        this.summaryData = summaryData;
    }

    public ArrayList<CountryData> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountryData> countries) {
        this.countries = countries;
    }

    public SummaryData getSummaryData() {
        return summaryData;
    }

    public void setSummaryData(SummaryData summaryData) {
        this.summaryData = summaryData;
    }
}
