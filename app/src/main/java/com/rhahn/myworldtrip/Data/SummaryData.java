package com.rhahn.myworldtrip.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class SummaryData implements Serializable {
    private static final long serialVersionUID = 3671194077897790143L;
    HashMap<String, Boolean> vaccinations = new HashMap<>();
    Date startDate;
    Date endDate;
    int totalCost;
    float tempFrom;
    float tempTo;

    public SummaryData(){}

    public SummaryData(HashMap<String, Boolean> vaccinations, Date startDate, Date endDate, int totalCost, float tempFrom, float tempTo) {
        this.vaccinations = vaccinations;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.tempFrom = tempFrom;
        this.tempTo = tempTo;
    }

    public HashMap<String, Boolean> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(HashMap<String, Boolean> vaccinations) {
        this.vaccinations = vaccinations;
    }

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
}
