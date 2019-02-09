package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;

import com.rhahn.myworldtrip.Data.AttributeData;
import com.rhahn.myworldtrip.Data.CountryData;
import com.rhahn.myworldtrip.Enum.AttributeType;
import com.rhahn.myworldtrip.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class DataCreator {
    public DataCreator() {
    }

    public CountryData createNewCountry(JSONObject newCountry, CountryData countryData, Context context) {
        countryData = setGeneralData(newCountry, countryData);
        ArrayList<AttributeData> attributes = new ArrayList<>();
        attributes.add(createTraveldates(countryData, context));
        attributes.add(createGeneralInformation(newCountry, context));
        attributes.add(createVisum(newCountry, context));
        attributes.add(createWeather(newCountry, context));
        attributes.add(createVaccination(newCountry, context));
        attributes.add(createAttractions(context));
        attributes.add(createNotes(context));
        attributes.add(createTravelcosts(context));
        countryData.setAttributes(attributes);
        return countryData;
    }

    private CountryData setGeneralData(JSONObject newCountry, CountryData countryData){
        try {
            countryData.setEngName(newCountry.getString("name"));
            countryData.setFlagURL(newCountry.getString("flag"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryData;
    }

    private AttributeData createTraveldates(CountryData countryData, Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        values.put(context.getString(R.string.from), sdf.format(countryData.getDateFrom()));
        values.put(context.getString(R.string.to), sdf.format(countryData.getDateTo()));
        return new AttributeData(AttributeType.TEXTLIST, context.getString(R.string.traveldates), values, false);
    }

    private AttributeData createGeneralInformation(JSONObject country, Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        try {
            String capital = country.getString("capital");
            String subregion = country.getString("subregion");
            String currency = country.getJSONArray("currencies").getJSONObject(0).getString("name");
            currency += " (" + country.getJSONArray("currencies").getJSONObject(0).getString("symbol") + ")";
            String language = country.getJSONArray("languages").getJSONObject(0).getString("name");
            String timezone = "";
            for (int i = 0; i < country.getJSONArray("timezones").length(); i++) {
                timezone += country.getJSONArray("timezones").getString(i) + " ";
            }
            String population = country.getInt("population") + "";

            values.put(context.getString(R.string.capital), capital);
            values.put(context.getString(R.string.subregion), subregion);
            values.put(context.getString(R.string.curreny), currency);
            values.put(context.getString(R.string.population), population);
            values.put(context.getString(R.string.languages), language);
            values.put(context.getString(R.string.timezones), timezone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new AttributeData(AttributeType.TEXTLIST, context.getString(R.string.generalinformation), values, false);
    }

    private AttributeData createVisum(JSONObject country, Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(context.getString(R.string.duty), "-");
        values.put(context.getString(R.string.neededFrom), "-");
        return new AttributeData(AttributeType.TEXTLIST, context.getString(R.string.visum), values, false);
    }

    private AttributeData createWeather(JSONObject country, Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(context.getString(R.string.mintemp), "-");
        values.put(context.getString(R.string.maxtemp), "-");
        values.put(context.getString(R.string.raindays), "-");
        return new AttributeData(AttributeType.TEXTLIST, context.getString(R.string.weather), values, false);
    }

    private AttributeData createVaccination(JSONObject country, Context context) {
        String info = Datarequest.getFileAsString(context, R.raw.vaccinations);
        String tmpString = "";
        try {
            tmpString = country.getString("name").toUpperCase();
            int pos = info.indexOf(tmpString);
            int end = 0;
            if(pos >= 0){
                tmpString = info.substring(pos);
                String[] substrings = tmpString.split("\\r?\\n");
                for (int i = 1; i < substrings.length; i++) {
                    if(Util.isUpperCase(substrings[i])){
                        end = tmpString.indexOf(substrings[i]);
                        break;
                    }
                }
                tmpString = tmpString.substring(0, end -1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(tmpString, "-");
        return new AttributeData(AttributeType.TEXTVIEW, context.getString(R.string.vaccinations), values, false);
    }

    private AttributeData createAttractions(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(AttributeType.MULTILINE.toString(), "");
        return new AttributeData(AttributeType.MULTILINE, context.getString(R.string.attractions), values, true);
    }

    private AttributeData createNotes(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(AttributeType.MULTILINE.toString(),"");
        return new AttributeData(AttributeType.MULTILINE, context.getString(R.string.notes), values, true);
    }

    private AttributeData createTravelcosts(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(context.getString(R.string.flightcost), "");
        values.put(context.getString(R.string.nightcost), "");
        values.put(context.getString(R.string.dailycost), "");
        return new AttributeData(AttributeType.NUMBERINPUT, context.getString(R.string.travelcosts), values, true);
    }
}