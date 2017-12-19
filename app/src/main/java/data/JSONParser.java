package data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static Weather getAccuWeather(String curr, String hourl) {
        Weather weather = new Weather();
        // create JsonObject from data
        String dataForCurr = curr;
        String dataForHourly12 = hourl;

        try {
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            // get curr object
            // Log.v("TESTTTTTTTTTTTTTTTTTTTTT:::", "reached here!!!!");
            JSONArray jsonArray = new JSONArray(dataForCurr);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            currentCondition.setTime(getInt("EpochTime", jsonObject));
            currentCondition.setDescription(getString("WeatherText", jsonObject));
            JSONObject currTempObject = getObject("Temperature", jsonObject);
            JSONObject currMetricObject = getObject("Metric", currTempObject);
            currentCondition.setTemperature(getFloat("Value", currMetricObject));

            JSONObject currWindObject = getObject("Wind", jsonObject);
            JSONObject currSpeedObject = getObject("Speed", currWindObject);
            JSONObject currwindSpeedMetric = getObject("Metric", currSpeedObject);
            currentCondition.setWindSpeed(getFloat("Value", currwindSpeedMetric));

            JSONObject currPrecipSumObject = getObject("PrecipitationSummary", jsonObject);
            JSONObject currPrecipObject = getObject("Precipitation", currPrecipSumObject);
            JSONObject currPrecipMetricObject = getObject("Metric", currPrecipObject);
            currentCondition.setPercipitation(getFloat("Value", currPrecipMetricObject));

            weather.currentCondition = currentCondition;


            JSONObject arr[] = new JSONObject[12];
            JSONArray jsonArray2 = new JSONArray(hourl);

            for (int i = 0; i < 12; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray2.getJSONObject(i);
                hourly.setTime(getInt("EpochDateTime", arr[i]));
                JSONObject tempObj = getObject("Temperature", arr[i]);
                hourly.setTemperature(getFloat("Value", tempObj));
                hourly.setPercipitation(getFloat("PrecipitationProbability", arr[i]));
                JSONObject windObj = getObject("Wind", arr[i]);
                JSONObject speedObj = getObject("Speed", windObj);
                hourly.setWind(getFloat("Value", speedObj));

                obj.add(i, hourly);
            }
            weather.hourly = obj;

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
            //Log.v("test::: ", String.valueOf(currentCondition.getTime()));

            currentCondition.setDescription(getString("conditions", forecastObject));

            //Log.v("testdes::: ", currentCondition.getDescription());
            JSONObject highObject = getObject("high", forecastObject);
            JSONObject lowObject = getObject("low", forecastObject);
            double high = getFloat("celsius", highObject);
            double low = getFloat("celsius", lowObject);
            currentCondition.setTemperature((high + low) / 2);
            //Log.v("testtemp::: ", String.valueOf(currentCondition.getTemperature()));
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

    public static Weather getNOAAWeather(String data) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(data);
            //JSONObject otherObject = new JSONObject(hourlyData);
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            JSONObject propertiesObject = getObject("properties", jsonObject);
            JSONArray jsonArray = propertiesObject.getJSONArray("periods");
            JSONObject forecastObject = jsonArray.getJSONObject(0);
            //String timeString = getString("startTime", forecastObject);
            //int timeInt = tsToSec8601(timeString);
            //currentCondition.setTime(timeInt);

            currentCondition.setDescription(getString("shortForecast", forecastObject));
            //Log.v("TEST::: ", currentCondition.getDescription());
            currentCondition.setTemperature(getFloat("temperature", forecastObject));
            //currentCondition.setPercipitation(getFloat("pop", forecastObject));
            String speedString = getString("windSpeed", forecastObject);
            double speedDouble = Double.parseDouble(String.valueOf(speedString.charAt(0)));
            currentCondition.setWindSpeed(speedDouble);
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            //JSONObject jsonHourly = getObject("hourly_forecast", otherObject);
   //         JSONArray jsonArray = otherObject.getJSONArray("hourly_forecast");
            //JSONObject jsonWeather = jsonArray.getJSONObject(0);
            //weather.currentCondition.setWeatherId(getInt("id", jsonWeather));
            //weather.currentCondition.setDescription(getString("description", jsonWeather));
            //weather.currentCondition.setCondition(getString("main", jsonWeather));

        /*    JSONObject arr[] = new JSONObject[24];

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
*/
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

    private static Integer tsToSec8601(String timestamp) {
        if (timestamp == null) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt = sdf.parse(timestamp);
            long epoch = dt.getTime();
            return (int) (epoch / 1000);
        } catch (ParseException e) {
            return null;
        }
    }

}
