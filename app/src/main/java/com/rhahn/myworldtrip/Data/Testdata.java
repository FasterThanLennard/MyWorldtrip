package com.rhahn.myworldtrip.Data;

import com.rhahn.myworldtrip.Enum.AttributeType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Testdata {
    public static MyWorldtripData getTestdata(){

        MyWorldtripData worldtripData = new MyWorldtripData(getCountries(5), getSummaryData());

        return worldtripData;
    }

    public static ArrayList<CountryData> getCountries(int NumberOfCountries){
        ArrayList<CountryData> countries = new ArrayList<>();

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        for (int i = 0; i < NumberOfCountries; i++) {
            from.set(2019 + i /12 , i%12, 1);
            to.set(2019 + i /12, i%12, 20);
            countries.add(new CountryData("Country " + i, from.getTime(), to.getTime(), null, getAttributes(), "e"));
        }
        return countries;
    }

    public static ArrayList<AttributeData> getAttributes(){
        ArrayList<AttributeData> attributes = new ArrayList<>();

        //Zeitraum
        HashMap<String, String> values = new HashMap<>();
        values.put("von", "01.01.2019");
        values.put("bis", "20.01.2019");
        attributes.add(new AttributeData(AttributeType.TEXTLIST, "Zeitraum", values, false));

        //Visum
        values = new HashMap<>();
        values.put("Pflicht", "nein");
        values.put("nötig ab:", "3 Wochen");
        attributes.add(new AttributeData(AttributeType.TEXTLIST, "Visum", values, false));

        //Impfungen
        values = new HashMap<>();
        values.put("Cholera", "Muss");
        values.put("Geldfieber", "Muss");
        values.put("Hepatitis", "Kann");
        values.put("Tollwut", "Empfohlen, aber ist nicht unbedingt nötig");
        attributes.add(new AttributeData(AttributeType.TEXTLIST, "Impfungen", values, false));

        //Wetter
        values = new HashMap<>();
        values.put("Min. Temperatur", "15°C");
        values.put("Max. Temperatur:", "25°C");
        values.put("Anzahl Regentage", "5 Tage");
        values.put("Regenmenge", "50mm");
        attributes.add(new AttributeData(AttributeType.TEXTLIST, "Wetter", values, false));

        //Sehenwürdigkeiten
        values = new HashMap<>();
        values.put("Eifelturm", "");
        values.put("Brandenburger Tor", "");
        values.put("Kölner Dom", "");
        values.put("Autobahn", "");
        attributes.add(new AttributeData(AttributeType.TEXTVIEW, "Sehenwürdigkeiten", values, true));

        //Reisekosten
        values = new HashMap<>();
        values.put("Flugkosten", "250");
        values.put("Hotelkosten", "400");
        values.put("Tägliche Kosten", "50");
        attributes.add(new AttributeData(AttributeType.NUMBERINPUT, "Reisekosten", values, true));

        //Notizen
        values = new HashMap<>();
        values.put(AttributeType.MULTILINE.toString(),"Hier steht ein langer text \n und das in mehrereren Zeilen");
        attributes.add(new AttributeData(AttributeType.MULTILINE, "Notizes", values, true));


        return attributes;
    }

    public static SummaryData getSummaryData(){

        return new SummaryData();
    }

}
