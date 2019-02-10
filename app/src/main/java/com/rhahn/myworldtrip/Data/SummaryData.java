package com.rhahn.myworldtrip.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * dataobject for travel summary information
 *
 * @author Robin Hahn
 */
public class SummaryData implements Serializable {
    private static final long serialVersionUID = 3671194077897790143L;
    //list of vaccinations and boolean for checkvalues
    private HashMap<String, Boolean> vaccinations = new HashMap<>();
    //start date of trip
    private Date startDate;
    //end date of trip
    private Date endDate;
    //total cost of trip
    private int totalCost;
    //min temp of trip
    private float tempFrom;
    //max temp of trip
    private float tempTo;

    public SummaryData(){}

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public float getTempFrom() {
        return tempFrom;
    }

    public void setTempFrom(float tempFrom) {
        this.tempFrom = tempFrom;
    }

    public float getTempTo() {
        return tempTo;
    }

    public void setTempTo(float tempTo) {
        this.tempTo = tempTo;
    }

    public HashMap<String, Boolean> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(HashMap<String, Boolean> vaccinations) {
        this.vaccinations = vaccinations;
    }
}
