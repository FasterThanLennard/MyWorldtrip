package com.rhahn.myworldtrip.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main dataobject for all information.
 *
 * @author Robin Hahn
 */
public class MyWorldtripData implements Serializable {
    private static final long serialVersionUID = 8274991317524100610L;
    //list of all countries
    private ArrayList<CountryData> countries;
    //summaryData
    private SummaryData summaryData;

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

    public SummaryData getSummaryData() {
        return summaryData;
    }
}
