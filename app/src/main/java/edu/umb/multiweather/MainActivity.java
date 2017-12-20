package edu.umb.multiweather;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import data.GPSTracker;
import data.HTTPClient;
import data.JSONParser;
import model.Weather;
import model.uPlace;

import static data.Aggregator.getMax;
import static data.Aggregator.getMean;
import static data.Aggregator.getMin;


public class MainActivity extends AppCompatActivity implements AggregatedFragment.OnFragmentInteractionListener, SplitFragment.OnFragmentInteractionListener {
    float lat;
    float lon;
    int zip;
    String city;

    uPlace uPlace = new uPlace();
    Weather weather[] = new Weather[4];
    Weather meanWeather = new Weather();
    Weather maxWeather = new Weather();
    Weather minWeather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.textView);

        GPSTracker g = new GPSTracker(getApplicationContext());
        Location location = g.getLocation();
        if (location != null){
            lat = (float) location.getLatitude();
            lon = (float) location.getLongitude();
        }

        try {
            getLocationDataGeo(lat, lon);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            renderWeatherData(uPlace);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        meanWeather = getMean(weather);
        maxWeather = getMax(weather);
        minWeather = getMin(weather);

        textView.setText("");
        textView.append("AccuWeather Temp: " + weather[0].currentCondition.getTemperature() + "\n");
        textView.append("AccuWeather Pop: " + weather[0].currentCondition.getPercipitation() + "\n");
        textView.append("AccuWeather Wind: " + weather[0].currentCondition.getWindSpeed() + "\n");
        textView.append("DarkSky Temp: " + weather[1].currentCondition.getTemperature() + "\n");
        textView.append("DarkSky Pop: " + weather[1].currentCondition.getPercipitation() + "\n");
        textView.append("DarkSky Wind: " + weather[1].currentCondition.getWindSpeed() + "\n");
        textView.append("WU Temp: " + weather[2].currentCondition.getTemperature() + "\n");
        textView.append("WU Pop: " + weather[2].currentCondition.getPercipitation() + "\n");
        textView.append("WU Wind: " + weather[2].currentCondition.getWindSpeed() + "\n");
        textView.append("noaa Temp: " + weather[3].currentCondition.getTemperature() + "\n");
        textView.append("noaa Pop: " + " Not Available\n");
        textView.append("noaa Wind: " + weather[3].currentCondition.getWindSpeed() + "\n");

        textView.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        textView.append("Mean Temp: " + meanWeather.currentCondition.getTemperature() + "\n");
        textView.append("Mean Pop: " + meanWeather.currentCondition.getPercipitation() + "\n");
        textView.append("Mean Wind: " + meanWeather.currentCondition.getWindSpeed() + "\n");
        textView.append("Max Temp: " + maxWeather.currentCondition.getTemperature() + "\n");
        textView.append("Max Pop: " + maxWeather.currentCondition.getPercipitation() + "\n");
        textView.append("Max Wind: " + maxWeather.currentCondition.getWindSpeed() + "\n");
        textView.append("Min Temp: " + minWeather.currentCondition.getTemperature() + "\n");
        textView.append("Min Pop: " + minWeather.currentCondition.getPercipitation() + "\n");
        textView.append("Min Wind: " + minWeather.currentCondition.getWindSpeed() + "\n");


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected uPlace.
                Log.i("test", "uPlace: " + place.getName());
                city = (String) place.getName();

                try {
                    getLocationDataCity(city);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("test", "An error occurred: " + status);
            }
        });
/*
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment, new AggregatedFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        */

    }

    @Override
    public void onFragmentInteraction(Weather[] weather) {

    }

    public void getLocationDataGeo (double lat, double lon) throws ExecutionException, InterruptedException {
        LocationTaskGeo locationTask = new LocationTaskGeo();
        locationTask.execute(String.valueOf(lat), String.valueOf(lon)).get();
    }
    public void getLocationDataZip (int zip) throws ExecutionException, InterruptedException {
        LocationTaskZip locationTask = new LocationTaskZip();
        locationTask.execute(String.valueOf(zip)).get();
    }
    public void getLocationDataCity (String city) throws ExecutionException, InterruptedException {
        LocationTaskCity locationTask = new LocationTaskCity();
        locationTask.execute(city).get();
    }

    private class LocationTaskGeo extends AsyncTask<String, Void, uPlace> {
        @Override
        protected uPlace doInBackground(String... strings) {

            String LocationData = null;
            try {
                LocationData = ((new HTTPClient()).getLocationDataGeo(lat, lon));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            uPlace = JSONParser.getLocationGeo(LocationData);
            return uPlace;
        }

        @Override
        protected void onPostExecute(uPlace uPlace) {
            super.onPostExecute(uPlace);
        }
    }

    private class LocationTaskZip extends AsyncTask<String, Void, uPlace> {
        @Override
        protected uPlace doInBackground(String... strings) {

            String LocationData = null;
            try {
                LocationData = ((new HTTPClient()).getLocationDataZip(zip));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            uPlace = JSONParser.getLocationZip(LocationData);
            return uPlace;
        }

        @Override
        protected void onPostExecute(uPlace uPlace) {
            super.onPostExecute(uPlace);
        }
    }

    private class LocationTaskCity extends AsyncTask<String, Void, uPlace> {
        @Override
        protected uPlace doInBackground(String... strings) {

            String LocationData = null;
            try {
                LocationData = ((new HTTPClient()).getLocationDataCity(city));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            uPlace = JSONParser.getLocationCity(LocationData);
            return uPlace;
        }

        @Override
        protected void onPostExecute(uPlace uPlace) {
            super.onPostExecute(uPlace);
        }
    }

    public void renderWeatherData (uPlace uPlace) throws ExecutionException, InterruptedException {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(uPlace).get();
    }

    private class WeatherTask extends AsyncTask<uPlace, Void, Weather[]> {

        @Override
        protected Weather[] doInBackground(uPlace... uPlace) {
            uPlace o = uPlace[0];

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
