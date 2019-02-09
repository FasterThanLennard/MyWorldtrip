package com.rhahn.myworldtrip.Data;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class CountryData implements Serializable {

    private static final long serialVersionUID = 8796242701389734373L;
    private long id;
    private String name;
    private String engName;
    private Date dateFrom;
    private Date dateTo;
    private Drawable flagImg;
    private String alpha2Code;
    private String flagURL;
    private ArrayList<AttributeData> attributes;

    public CountryData(String name, Date dateFrom, Date dateTo, Drawable flagImg, ArrayList<AttributeData> attributes, String alpha2Code) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.flagImg = flagImg;
        this.attributes = attributes;
        this.alpha2Code = alpha2Code;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Drawable getFlagImg() {
        return flagImg;
    }

    public void setFlagImg(Drawable flagImg) {
        this.flagImg = flagImg;
    }

    public ArrayList<AttributeData> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<AttributeData> attributes) {
        this.attributes = attributes;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }



}
