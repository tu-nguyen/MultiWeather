package edu.umb.multiweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import data.JSONParser;
import data.LocationHttpClient;
import data.WeatherHttpClient;
import model.Hourly;
import model.Place;
import model.Weather;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String locationProvider = LocationManager.GPS_PROVIDER;

    private double time;
    private double temp;
    private double chanceOfRain;
    private double windSpeed;

    double lat;
    double lon;
    //String country;
    //String city;
    //String zip;
    //String code;

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
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        textView = (TextView) findViewById(R.id.textView);

        //getLocationData(lat, lon);
        place.setCity("Boston");
        place.setState("Massachusetts");
        place.setCountry("USA");
        place.setStateSymbol("MA");

        renderWeatherData(place);

    }

    public void getLocationData (double lat, double lon) {
        LocationTask locationTask = new LocationTask();
        locationTask.execute(String.valueOf(lat), String.valueOf(lon));
    }

    private class LocationTask extends AsyncTask<String, Void, Place> {
        @Override
        protected Place doInBackground(String... strings) {
            String LocationData = ((new LocationHttpClient()).getLocationData(lat, lon));

            place = JSONParser.getLocation(LocationData);
            return place;
        }

        @Override
        protected void onPostExecute(Place place) {
            super.onPostExecute(place);
            textView.setText(place.getCode());
        }
    }

    public void renderWeatherData (Place place) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(place);
    }

    private class WeatherTask extends AsyncTask<Place, Void, Weather[]> {

        @Override
        protected Weather[] doInBackground(Place... place) {
            Place o = place[0];

            String accuString = o.getCode();
            String darkskyString = o.getLat() + "," + o.getLon();
            String wuString = "/" + o.getStateSymbol() + "/" + o.getCity();

            String AccuDataCurrent = null;
            try {
                AccuDataCurrent = ((new WeatherHttpClient()).getAccuWeatherCurrentData((accuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String AccuDataHourly12 = null;
            try {
                AccuDataHourly12 = ((new WeatherHttpClient()).getAccuWeatherHourly12Data((accuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String DarkSkyData = null;
            try {
                DarkSkyData = ((new WeatherHttpClient()).getDarkSkyWeatherData(darkskyString));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String WUDataCurrent = null;
            try {
                WUDataCurrent = ((new WeatherHttpClient()).getWUWeatherCurrentData((wuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String WUDataHourly = null;
            try {
                WUDataHourly = ((new WeatherHttpClient()).getWUWeatherHourly12Data((wuString)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //weather[0] = JSONParser.getAccuWeather(AccuDataCurrent, AccuDataHourly12);
            //weather[1] = JSONParser.getDarkSkyWeather(DarkSkyData);
            weather[2] = JSONParser.getWUWeather(WUDataCurrent, WUDataHourly);

            return weather;
        }

        @Override
        protected void onPostExecute(Weather[] weather) {
            super.onPostExecute(weather);
        }
    }
}
