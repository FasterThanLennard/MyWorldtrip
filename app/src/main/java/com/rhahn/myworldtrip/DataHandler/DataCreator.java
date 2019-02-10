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

/**
 * Class to add new information and attributes to a country
 *
 * @author Robin Hahn
 */
public class DataCreator {
    public DataCreator() {
    }

    /**
     * Returns {@link CountryData} with all attributes
     *
     * @param newCountry  country data from request
     * @param countryData country to add information to
     * @param context     current context
     * @return CountryData with information filled
     */
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

    /**
     * Set general information to the country
     *
     * @param newCountry  country data from request
     * @param countryData country to add information to
     * @return country
     */
    private CountryData setGeneralData(JSONObject newCountry, CountryData countryData) {
        try {
            countryData.setEngName(newCountry.getString("name"));
            countryData.setFlagURL(newCountry.getString("flag"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryData;
    }

    /**
     * Create {@link AttributeData} traveldates
     *
     * @param countryData country to add information to
     * @param context     context for resources
     * @return traveldates as {@link AttributeData}
     */
    private AttributeData createTraveldates(CountryData countryData, Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        values.put(context.getResources().getResourceEntryName(R.string.from), sdf.format(countryData.getDateFrom()));
        values.put(context.getResources().getResourceEntryName(R.string.to), sdf.format(countryData.getDateTo()));
        return new AttributeData(AttributeType.TEXTLIST, context.getResources().getResourceEntryName(R.string.traveldates), values, false);
    }

    /**
     * Create {@link AttributeData} general information
     *
     * @param country country data from request
     * @param context context for resources
     * @return general information as {@link AttributeData}
     */
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

            values.put(context.getResources().getResourceEntryName(R.string.capital), capital);
            values.put(context.getResources().getResourceEntryName(R.string.subregion), subregion);
            values.put(context.getResources().getResourceEntryName(R.string.curreny), currency);
            values.put(context.getResources().getResourceEntryName(R.string.population), population);
            values.put(context.getResources().getResourceEntryName(R.string.languages), language);
            values.put(context.getResources().getResourceEntryName(R.string.timezones), timezone);
            values.put(context.getResources().getResourceEntryName(R.string.source), "https://restcountries.eu/");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new AttributeData(AttributeType.TEXTLIST, context.getResources().getResourceEntryName(R.string.generalinformation), values, false);
    }

    /**
     * Create {@link AttributeData} visa information
     *
     * @param country country data from request
     * @param context context for resources
     * @return visa information as {@link AttributeData}
     */
    private AttributeData createVisum(JSONObject country, Context context) {
        String visa = Datarequest.getFileAsString(context, R.raw.visa);
        String result = context.getString(R.string.noInformation);
        String searchFor = "Tourist: ";
        //search in datasource
        try {
            String germanName = country.getJSONObject("translations").getString("de");
            String tmp;
            int start = visa.indexOf(germanName);
            if (start >= 0) {
                tmp = visa.substring(start);
                String lines[] = tmp.split("\n");
                for (String line : lines) {
                    start = line.indexOf(searchFor);
                    if (start >= 0) {
                        result = line.substring(start + searchFor.length());
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        //add source
        String source = "https://visumcentrale.de/visa-quick-check";
        values.put(result, "-");
        values.put(context.getString(R.string.source) + ": " + source, "-");
        return new AttributeData(AttributeType.TEXTVIEW, context.getResources().getResourceEntryName(R.string.visum), values, false);
    }

    /**
     * Create {@link AttributeData} general information
     *
     * @param country country data from request
     * @param context context for resources
     * @return general information as {@link AttributeData}
     */
    private AttributeData createWeather(JSONObject country, final Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(context.getResources().getResourceEntryName(R.string.mintemp), "");
        values.put(context.getResources().getResourceEntryName(R.string.maxtemp), "");
        values.put(context.getResources().getResourceEntryName(R.string.humidity), "");
        return new AttributeData(AttributeType.TEXTLIST, context.getResources().getResourceEntryName(R.string.weather), values, false);
    }

    /**
     * Create {@link AttributeData} vaccination information
     *
     * @param country country data from request
     * @param context context for resources
     * @return vaccination information as {@link AttributeData}
     */
    private AttributeData createVaccination(JSONObject country, Context context) {
        String info = Datarequest.getFileAsString(context, R.raw.vaccinations);
        String tmpString = "";
        //search in source
        try {
            tmpString = country.getString("name").toUpperCase();
            int pos = info.indexOf(tmpString);
            int end = 0;
            if (pos >= 0) {
                tmpString = info.substring(pos);
                String[] substrings = tmpString.split("\\r?\\n");
                for (int i = 1; i < substrings.length; i++) {
                    if (Util.isUpperCase(substrings[i])) {
                        end = tmpString.indexOf(substrings[i]);
                        break;
                    }
                }
                if (end >= 1)
                    tmpString = tmpString.substring(0, end - 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        String source = "https://www.who.int/ith/2016-ith-county-list.pdf";
        values.put(tmpString, "-");
        values.put(context.getString(R.string.source) + ": " + source, "-");
        return new AttributeData(AttributeType.TEXTVIEW, context.getResources().getResourceEntryName(R.string.vaccinations), values, false);
    }

    /**
     * Create {@link AttributeData} attractions
     *
     * @param context country data from request
     * @return attractions information as {@link AttributeData}
     */
    private AttributeData createAttractions(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(AttributeType.MULTILINE.toString(), "");
        return new AttributeData(AttributeType.MULTILINE, context.getResources().getResourceEntryName(R.string.attractions), values, true);
    }

    /**
     * Create {@link AttributeData} notes
     *
     * @param context context for ressoucres
     * @return notes information as {@link AttributeData}
     */
    private AttributeData createNotes(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(AttributeType.MULTILINE.toString(), "");
        return new AttributeData(AttributeType.MULTILINE, context.getResources().getResourceEntryName(R.string.notes), values, true);
    }

    /**
     * Create {@link AttributeData} notes
     *
     * @param context country data from request
     * @return notes information as {@link AttributeData}
     */
    private AttributeData createTravelcosts(Context context) {
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        values.put(context.getResources().getResourceEntryName(R.string.flightcost), "");
        values.put(context.getResources().getResourceEntryName(R.string.dailyNightcost), "");
        values.put(context.getResources().getResourceEntryName(R.string.dailycost), "");
        return new AttributeData(AttributeType.NUMBERINPUT, context.getResources().getResourceEntryName(R.string.travelcosts), values, true);
    }
}
