package edu.umb.multiweather;


import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import Utils.Utils;
import data.GPSTracker;
import data.JSONParser;
import data.HTTPClient;
import model.Place;
import model.Weather;

public class MainActivity extends AppCompatActivity {
    float lat;
    float lon;

    boolean locDone = false;
    boolean weatherDone = false;

    private TextView textView;

    Place place = new Place();
    Weather weather[] = new Weather[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GPSTracker g = new GPSTracker(getApplicationContext());
        Location location = g.getLocation();
        if (location != null){
            lat = (float) location.getLatitude();
            lon = (float) location.getLongitude();
        }

        textView = (TextView) findViewById(R.id.textView);

/*
        place.setCity("Boston");
        place.setState("Massachusetts");
        place.setCountry("USA");
        place.setStateSymbol("MA");
        place.setLat((float) 42.29329301);
        place.setLon((float) -71.06115946);
        place.setCode(String.valueOf(348735));
*/

        try {
            getLocationData(lat, lon);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            renderWeatherData(place);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        textView.append(weather[0].currentCondition.getTemperature() + "\n");
        textView.append(weather[0].currentCondition.getWindSpeed() + "\n");
        textView.append("\n\n");

        textView.append(weather[1].currentCondition.getTemperature() + "\n");
        textView.append(weather[1].currentCondition.getWindSpeed() + "\n");
        textView.append("\n\n");

        textView.append(weather[2].currentCondition.getTemperature() + "\n");
        textView.append(weather[2].currentCondition.getWindSpeed() + "\n");
        textView.append("\n\n");

        textView.append(weather[3].currentCondition.getTemperature() + "\n");
        textView.append(weather[3].currentCondition.getWindSpeed() + "\n");
        textView.append("\n\n");

    }

    public void getLocationData (double lat, double lon) throws ExecutionException, InterruptedException {
        LocationTask locationTask = new LocationTask();
        locationTask.execute(String.valueOf(lat), String.valueOf(lon)).get();
    }

    private class LocationTask extends AsyncTask<String, Void, Place> {
        @Override
        protected Place doInBackground(String... strings) {

            String LocationData = null;
            try {
                LocationData = ((new HTTPClient()).getLocationData(lat, lon));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            place = JSONParser.getLocation(LocationData);
            return place;
        }

        @Override
        protected void onPostExecute(Place place) {
            super.onPostExecute(place);
        }
    }

    public void renderWeatherData (Place place) throws ExecutionException, InterruptedException {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(place).get();
    }

    private class WeatherTask extends AsyncTask<Place, Void, Weather[]> {

        @Override
        protected Weather[] doInBackground(Place... place) {
            Place o = place[0];

            String accuString = o.getCode();
            String darkskyString = o.getLat() + "," + o.getLon();
            String wuString = "/" + o.getStateSymbol() + "/" + o.getCity();
            String noaaString = o.getLat() + "," + o.getLon();

            String AccuDataCurrent = null;
            try {
                AccuDataCurrent = ((new HTTPClient()).getAccuWeatherCurrentData((accuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String AccuDataHourly12 = null;
            try {
                AccuDataHourly12 = ((new HTTPClient()).getAccuWeatherHourly12Data((accuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String DarkSkyData = null;
            try {
                DarkSkyData = ((new HTTPClient()).getDarkSkyWeatherData(darkskyString));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String WUDataCurrent = null;
            try {
                WUDataCurrent = ((new HTTPClient()).getWUWeatherCurrentData((wuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String WUDataHourly = null;
            try {
                WUDataHourly = ((new HTTPClient()).getWUWeatherHourly12Data((wuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String NOAADataCurrent = null;
            try {
                NOAADataCurrent = ((new HTTPClient()).getNOAAWeatherCurrentData((noaaString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String NOAADataHourly = null;
            try {
                NOAADataHourly = ((new HTTPClient()).getNOAAWeatherHourlyData((noaaString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            weather[0] = JSONParser.getAccuWeather(AccuDataCurrent, AccuDataHourly12);
            weather[1] = JSONParser.getDarkSkyWeather(DarkSkyData);
            weather[2] = JSONParser.getWUWeather(WUDataCurrent, WUDataHourly);
            weather[3] = JSONParser.getNOAAWeather(NOAADataCurrent, NOAADataHourly);

            return weather;
        }

        @Override
        protected void onPostExecute(Weather[] weather) {
            super.onPostExecute(weather);
        }
    }
}
