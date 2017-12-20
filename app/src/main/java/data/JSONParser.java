package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.CurrentCondition;
import model.Hourly;
import model.Weather;
import model.uPlace;

public class JSONParser {

    public static uPlace getLocationGeo(String data) {
        uPlace uPlace = new uPlace();

        try {
            JSONObject locObject  = new JSONObject(data);
            JSONObject geoObj = getObject("GeoPosition", locObject);
            JSONObject countryObj = getObject("Country", locObject);
            JSONObject stateObj = getObject("AdministrativeArea", locObject);
            uPlace.setLat(getFloat("Latitude", geoObj));
            uPlace.setLon(getFloat("Longitude", geoObj));
            uPlace.setCountry(getString("EnglishName", countryObj));
            uPlace.setState(getString("EnglishName", stateObj));
            uPlace.setStateSymbol(getString("ID", stateObj));
            uPlace.setCity(getString("EnglishName", locObject));
            uPlace.setZip(getString("PrimaryPostalCode", locObject));
            uPlace.setCode(getString("Key", locObject));

            return uPlace;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static uPlace getLocationZip(String data) {
        uPlace uPlace = new uPlace();

        try {
            JSONArray locArray  = new JSONArray(data);
            JSONObject locObj = locArray.getJSONObject(0);
            JSONObject countryObj = getObject("Country", locObj);
            JSONObject stateObj = getObject("AdministrativeArea", locObj);
            JSONObject geoObj = getObject("GeoPosition", locObj);
            uPlace.setLat(getFloat("Latitude", geoObj));
            uPlace.setLon(getFloat("Longitude", geoObj));
            uPlace.setCountry(getString("EnglishName", countryObj));
            uPlace.setState(getString("EnglishName", stateObj));
            uPlace.setStateSymbol(getString("ID", stateObj));
            uPlace.setCity(getString("EnglishName", locObj));
            uPlace.setZip(getString("PrimaryPostalCode", locObj));
            uPlace.setCode(getString("Key", locObj));

            return uPlace;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static uPlace getLocationCity(String data) {
        uPlace uPlace = new uPlace();

        try {
            JSONArray locArray  = new JSONArray(data);
            JSONObject locObj = locArray.getJSONObject(0);
            JSONObject countryObj = getObject("Country", locObj);
            JSONObject stateObj = getObject("AdministrativeArea", locObj);
            JSONObject geoObj = getObject("GeoPosition", locObj);
            uPlace.setLat(getFloat("Latitude", geoObj));
            uPlace.setLon(getFloat("Longitude", geoObj));
            uPlace.setCountry(getString("EnglishName", countryObj));
            uPlace.setState(getString("EnglishName", stateObj));
            uPlace.setStateSymbol(getString("ID", stateObj));
            uPlace.setCity(getString("EnglishName", locObj));
            uPlace.setZip(getString("PrimaryPostalCode", locObj));
            uPlace.setCode(getString("Key", locObj));

            return uPlace;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getAccuWeather(String currData, String hourlyData) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            // get curr object
            // Log.v("TESTTTTTTTTTTTTTTTTTTTTT:::", "reached here!!!!");
            JSONArray jsonArray = new JSONArray(currData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            currentCondition.setTime(getInt("EpochTime", jsonObject));
            currentCondition.setDescription(getString("WeatherText", jsonObject));
            JSONObject currTempObject = getObject("Temperature", jsonObject);
            JSONObject currImperialObject = getObject("Imperial", currTempObject);
            currentCondition.setTemperature(getDouble("Value", currImperialObject));

            JSONObject currWindObject = getObject("Wind", jsonObject);
            JSONObject currSpeedObject = getObject("Speed", currWindObject);
            JSONObject currwindSpeedImperial = getObject("Imperial", currSpeedObject);
            currentCondition.setWindSpeed(getDouble("Value", currwindSpeedImperial));

            JSONObject currPrecipSumObject = getObject("PrecipitationSummary", jsonObject);
            JSONObject currPrecipObject = getObject("Precipitation", currPrecipSumObject);
            JSONObject currPrecipImperialObject = getObject("Imperial", currPrecipObject);
            currentCondition.setPercipitation(getDouble("Value", currPrecipImperialObject));
            weather.currentCondition = currentCondition;


            JSONObject arr[] = new JSONObject[12];
            JSONArray jsonArray2 = new JSONArray(hourlyData);

            for (int i = 0; i < 12; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray2.getJSONObject(i);
                hourly.setTime(getInt("EpochDateTime", arr[i]));
                JSONObject tempObj = getObject("Temperature", arr[i]);
                hourly.setTemperature(getDouble("Value", tempObj));
                hourly.setPercipitation(getDouble("PrecipitationProbability", arr[i]));
                JSONObject windObj = getObject("Wind", arr[i]);
                JSONObject speedObj = getObject("Speed", windObj);
                hourly.setWind(getDouble("Value", speedObj));
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
            currentCondition.setTemperature(getDouble("temperature", currObj));
            currentCondition.setPercipitation(getDouble("precipProbability", currObj));
            currentCondition.setWindSpeed(getDouble("windSpeed", currObj));
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            JSONObject jsonHourly = getObject("hourly", jsonObject);
            JSONArray jsonArray = jsonHourly.getJSONArray("data");

            JSONObject arr[] = new JSONObject[24];

            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray.getJSONObject(i + 1); // Skip first one
                hourly.setTime(getInt("time", arr[i]));
                hourly.setTemperature(getDouble("temperature", arr[i]));
                hourly.setPercipitation(getDouble("precipProbability", arr[i]));
                hourly.setWind(getDouble("windSpeed",arr[i]));
                obj.add(i, hourly);
            }
            weather.hourly = obj;

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getWUWeather(String currData, String hourlyData) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            JSONObject jsonObject = new JSONObject(currData);
            JSONObject otherObject = new JSONObject(hourlyData);

            CurrentCondition currentCondition = new CurrentCondition();
            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            JSONObject forecastObj = getObject("forecast", jsonObject);
            JSONObject simpleForecastObject = getObject("simpleforecast", forecastObj);
            JSONArray forecastDayObject = simpleForecastObject.getJSONArray("forecastday");
            JSONObject forecastObject = forecastDayObject.getJSONObject(0);
            JSONObject dateObject = getObject("date", forecastObject);
            currentCondition.setTime(getInt("epoch", dateObject));


            currentCondition.setDescription(getString("conditions", forecastObject));

            JSONObject highObject = getObject("high", forecastObject);
            JSONObject lowObject = getObject("low", forecastObject);
            double high = getDouble("fahrenheit", highObject);
            double low = getDouble("fahrenheit", lowObject);
            currentCondition.setTemperature((high + low) / 2);

            currentCondition.setPercipitation(getDouble("pop", forecastObject));
            JSONObject windObject = getObject("avewind", forecastObject);
            currentCondition.setWindSpeed(getDouble("mph", windObject));
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            JSONArray jsonArray = otherObject.getJSONArray("hourly_forecast");

            JSONObject arr[] = new JSONObject[24];
            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonArray.getJSONObject(i);
                JSONObject fctObject = getObject("FCTTIME", arr[i]);
                hourly.setTime(getInt("epoch", fctObject));
                JSONObject tempObject = getObject("temp", arr[i]);
                hourly.setTemperature(getFloat("english", tempObject));
                hourly.setPercipitation(getDouble("pop", arr[i]));
                JSONObject windSpeedObject = getObject("wspd", arr[i]);
                hourly.setWind(getDouble("english",windSpeedObject));

                obj.add(i, hourly);
            }
            weather.hourly = obj;

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weather getNOAAWeather(String currData, String hourlyData) {
        Weather weather = new Weather();
        // create JsonObject from data

        try {
            JSONObject currObject = new JSONObject(currData);
            JSONObject otherObject = new JSONObject(hourlyData);
            CurrentCondition currentCondition = new CurrentCondition();

            ArrayList<Hourly> obj = new ArrayList<Hourly>();

            JSONObject propertiesObject = getObject("properties", currObject);
            JSONArray jsonArray = propertiesObject.getJSONArray("periods");
            JSONObject forecastObject = jsonArray.getJSONObject(0);
            currentCondition.setDescription(getString("shortForecast", forecastObject));
            currentCondition.setTemperature(getDouble("temperature", forecastObject));
            String speedString = getString("windSpeed", forecastObject);
            double speedDouble = Double.parseDouble(String.valueOf(speedString.charAt(0)));
            currentCondition.setWindSpeed(speedDouble);
            weather.currentCondition = currentCondition;

            //get weather info // FROM AN ARRAY
            JSONObject propertiesHourlyObject = getObject("properties", otherObject);
            JSONArray jsonHourlyArray = propertiesHourlyObject.getJSONArray("periods");
            JSONObject arr[] = new JSONObject[24];

            for (int i = 0; i < 24; i++) {
                Hourly hourly = new Hourly();

                arr[i] = jsonHourlyArray.getJSONObject(i);
                //JSONObject forecastObject = jsonArray.getJSONObject(0);
                hourly.setTemperature(getDouble("temperature", arr[i]));
                String speedHourlyString = getString("windSpeed", arr[i]);
                double speedHourlyDouble = Double.parseDouble(String.valueOf(speedHourlyString.charAt(0)));
                hourly.setWind(speedHourlyDouble);
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

    private static double  getDouble(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getDouble(tagName);
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

    /*
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
    */

}
