package data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Utils.Utils;

public class HTTPClient {

    public String getLocationDataGeo(float lat, float lon) throws MalformedURLException {
        String end = "&q=" + lat + "," + lon;
        return getData(new URL(Utils.ACCU_LOCATION_GEO_BASE_URL +  Utils.ACCU_API + end));
    }

    public String getLocationDataZip(int zip) throws MalformedURLException {
        String end = "&q=" + zip;
        return getData(new URL(Utils.ACCU_LOCATION_ZIP_BASE_URL +  Utils.ACCU_API + end));
    }

    public String getLocationDataCity(String city) throws MalformedURLException {
        String end = "&q=" + city;
        return getData(new URL(Utils.ACCU_LOCATION_CITY_BASE_URL +  Utils.ACCU_API + end));
    }

    public String getAccuWeatherCurrentData(String locationCode) throws MalformedURLException {
        return getData(new URL(Utils.ACCU_BASE_URL_CURR + locationCode + Utils.ACCU_API + "&details=true"));
    }

    public String getAccuWeatherHourly12Data(String locationCode) throws MalformedURLException {
        return getData(new URL(Utils.ACCU_BASE_URL12 + locationCode + Utils.ACCU_API + "&details=true"));
    }

    public String getDarkSkyWeatherData(String place) throws MalformedURLException {
        Log.v("QQQQQQQQQQQQQQQQQQQQQQQQQ::: ", Utils.DARKSKY_BASE_URL +  Utils.DARKSKY_API + place);
            return getData(new URL(Utils.DARKSKY_BASE_URL +  Utils.DARKSKY_API + place));
    }

    public String getWUWeatherCurrentData(String place) throws MalformedURLException {
        Log.v("QQQQQQQQQQQQQQQQQQQQQQQQQ::: ", Utils.WU_BASE_URL_CURR + place + ".json");
        return getData(new URL(Utils.WU_BASE_URL_CURR + place + ".json"));
    }

    public String getWUWeatherHourly12Data(String place) throws MalformedURLException {
        return getData(new URL(Utils.WU_BASE_URL_HR + place + ".json"));
    }

    public String getNOAAWeatherCurrentData(String place) throws MalformedURLException {
        //Log.v("QQQQQQQQQQQQQQQQQQQQQQQQQ::: ", Utils.NOAA_BASE_URL + place + Utils.NOAA_BASE_URL_CURR);
        return getData(new URL(Utils.NOAA_BASE_URL + place + Utils.NOAA_BASE_URL_CURR));
    }

    public String getNOAAWeatherHourlyData(String place) throws MalformedURLException {
        //Log.v("QQQQQQQQQQQQQQQQQQQQQQQQQ::: ", Utils.NOAA_BASE_URL + place + Utils.NOAA_BASE_URL_HOURLY);
        return getData(new URL(Utils.NOAA_BASE_URL + place + Utils.NOAA_BASE_URL_HOURLY));
    }

    public String getData(URL url) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;

        try {

            connection = (HttpURLConnection) (url).openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            //connection.setDoOutput(true); // wowowowoww
            connection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }
            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}