package data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Utils.Utils;
import model.CurrentCondition;
import model.Hourly;
import model.Percipitation;
import model.Place;
import model.Temperature;
import model.Weather;
import model.Wind;

public class JSONParser {
    public static Place getLocation (String data) {
        Place place = new Place();

        try {
            JSONObject locObject  = new JSONObject(data);
            JSONObject geoObj = getObject("GeoPosition", locObject);
            JSONObject countryObj = getObject("Country", locObject);
            JSONObject stateObj = getObject("AdministrativeArea", locObject);
            place.setLat(getFloat("Latitude", geoObj));
            place.setLon(getFloat("Longitude", geoObj));
            place.setCountry(getString("EnglishName", countryObj));
            place.setState(getString("EnglishName", stateObj));
            place.setCity(getString("EnglishName", locObject));
            place.setZip(getString("PrimaryPostalCode", locObject));
            place.setCode(getString("Key", locObject));

            return place;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getAccuWeather(String curr, String hourly) {
        Weather weather = new Weather();
        // create JsonObject from data
        String dataForCurr = curr;
        String dataForHourly12 = hourly;

        try {
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();


            // get curr object
            JSONObject jsonObject = new JSONObject(dataForCurr);
            //JSONArray jsonArray = mainObj.getJSONArray("");
            //JSONObject currObject = jsonArray.getJSONObject(0);
            //currentCondition.setTime(getInt("EpochTime", currObject));
            /*currentCondition.setDescription(getString("WeatherText", currObject));
            JSONObject currTempObject = getObject("Temperature", currObject);
            JSONObject currMetricObject = getObject("Metric", currTempObject);
            currentCondition.setTemperature(getFloat("Value", currMetricObject));
            JSONObject currWindObject = getObject("Wind", currMetricObject);
            JSONObject currSpeedObject = getObject("Speed", currWindObject);
            JSONObject currPrecipSumObject = getObject("PrecipitationSummary", currObject);
            JSONObject currPrecipObject = getObject("Precipitation", currPrecipSumObject);
            JSONObject currPrecipMetricObject = getObject("Metric", currPrecipObject);
            currentCondition.setPercipitation(getFloat("Value", currMetricObject));
            currentCondition.setWindSpeed(getFloat("Value", currSpeedObject));*/
            currentCondition.setTime(1234457);
            weather.currentCondition = currentCondition;

/*
            JSONObject arr[] = new JSONObject[24];

            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray.getJSONObject(i);
                hourly.setTime(getInt("EpochDateTime", arr[i]));
                JSONObject tempObj = getObject("Temperature", arr[i]);
                hourly.setTemperature(getFloat("Value", tempObj));
                hourly.setPercipitation(getFloat("PrecipitationProbability", arr[i]));
                JSONObject windObj = getObject("Wind", arr[i]);
                JSONObject speedObj = getObject("Speed", windObj);
                hourly.setWind(getFloat("Value", windObj));

                obj.add(i, hourly);
            }
            weather.hourly = obj;
            */

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getDarkSkyWeather(String data) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(data);
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            // get curr object
            JSONObject currObj = getObject("currently", jsonObject);
            currentCondition.setTime(getInt("time", currObj));
            currentCondition.setDescription(getString("summary", currObj));
            currentCondition.setTemperature(getFloat("temperature", currObj));
            currentCondition.setPercipitation(getFloat("precipProbability", currObj));
            currentCondition.setWindSpeed(getFloat("windSpeed", currObj));
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            JSONObject jsonHourly = getObject("hourly", jsonObject);
            JSONArray jsonArray = jsonHourly.getJSONArray("data");
            //JSONObject jsonWeather = jsonArray.getJSONObject(0);
            //weather.currentCondition.setWeatherId(getInt("id", jsonWeather));
            //weather.currentCondition.setDescription(getString("description", jsonWeather));
            //weather.currentCondition.setCondition(getString("main", jsonWeather));

            JSONObject arr[] = new JSONObject[24];

            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray.getJSONObject(i + 1);
                hourly.setTime(getInt("time", arr[i]));
                hourly.setTemperature(getFloat("temperature", arr[i]));
                hourly.setPercipitation(getFloat("precipProbability", arr[i]));
                hourly.setWind(getFloat("windSpeed",arr[i]));

                obj.add(i, hourly);
            }
            weather.hourly = obj;

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getWUWeather(String curr, String hourlyData) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(curr);
            JSONObject otherObject = new JSONObject(hourlyData);
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            JSONObject forecastObj = getObject("forecast", jsonObject);
            JSONObject simpleForecastObject = getObject("simpleforecast", forecastObj);

            JSONArray forecastDayObject = simpleForecastObject.getJSONArray("forecastday");
            JSONObject forecastObject = forecastDayObject.getJSONObject(0);

            JSONObject dateObject = getObject("date", forecastObject);

            currentCondition.setTime(getInt("epoch", dateObject));
            Log.v("test::: ", String.valueOf(currentCondition.getTime()));

            currentCondition.setDescription(getString("conditions", forecastObject));

            Log.v("testdes::: ", currentCondition.getDescription());
            JSONObject highObject = getObject("high", forecastObject);
            JSONObject lowObject = getObject("low", forecastObject);
            double high = getFloat("celsius", highObject);
            double low = getFloat("celsius", lowObject);
            currentCondition.setTemperature((high + low) / 2);
            Log.v("testtemp::: ", String.valueOf(currentCondition.getTemperature()));
            currentCondition.setPercipitation(getFloat("pop", forecastObject));
            JSONObject windObject = getObject("avewind", forecastObject);
            currentCondition.setWindSpeed(getFloat("mph", windObject));
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            //JSONObject jsonHourly = getObject("hourly_forecast", otherObject);
            JSONArray jsonArray = otherObject.getJSONArray("hourly_forecast");
            //JSONObject jsonWeather = jsonArray.getJSONObject(0);
            //weather.currentCondition.setWeatherId(getInt("id", jsonWeather));
            //weather.currentCondition.setDescription(getString("description", jsonWeather));
            //weather.currentCondition.setCondition(getString("main", jsonWeather));

            JSONObject arr[] = new JSONObject[24];

            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray.getJSONObject(i);
                JSONObject fctObject = getObject("FCTTIME", arr[i]);
                hourly.setTime(getInt("epoch", fctObject));
                JSONObject tempObject = getObject("temp", arr[i]);
                hourly.setTemperature(getFloat("metric", tempObject));
                hourly.setPercipitation(getFloat("pop", arr[i]));
                JSONObject windSpeedObject = getObject("wspd", arr[i]);
                hourly.setWind(getFloat("metric",windSpeedObject));

                obj.add(i, hourly);
            }
            weather.hourly = obj;

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }






    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static long  getLong(String tagName, JSONObject jObj) throws JSONException {
        return (long) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static boolean  getBoolean(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getBoolean(tagName);
    }

}
