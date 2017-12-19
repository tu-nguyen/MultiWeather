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

public class MainActivity extends AppCompatActivity {
    float lat;
    float lon;

    boolean locDone = false;
    boolean weatherDone = false;

    private TextView textView;

    uPlace uPlace = new uPlace();
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
        uPlace.setCity("Boston");
        uPlace.setState("Massachusetts");
        uPlace.setCountry("USA");
        uPlace.setStateSymbol("MA");
        uPlace.setLat((float) 42.29329301);
        uPlace.setLon((float) -71.06115946);
        uPlace.setCode(String.valueOf(348735));
*/

        try {
            getLocationData(lat, lon);
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

        textView.setText("curr Lat: " + uPlace.getLat() + "\n");
        textView.append("curr Lon: " + uPlace.getLon() + "\n");
        textView.append("curr City: " + uPlace.getCity());



/*
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
*/
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected uPlace.
                Log.i("test", "uPlace: " + place.getName());
                uPlace.setCity((String) place.getName());
                uPlace.setLat((float) place.getLatLng().latitude);
                uPlace.setLon((float) place.getLatLng().longitude);

                try {
                    getLocationData(lat, lon);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                textView.append("\n");
                textView.append("new Lat: " + uPlace.getLat() + "\n");
                textView.append("new Lon: " + uPlace.getLon() + "\n");
                textView.append("new City: " + uPlace.getCity());
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("test", "An error occurred: " + status);
            }
        });

    }

    public void getLocationData (double lat, double lon) throws ExecutionException, InterruptedException {
        LocationTask locationTask = new LocationTask();
        locationTask.execute(String.valueOf(lat), String.valueOf(lon)).get();
    }

    private class LocationTask extends AsyncTask<String, Void, uPlace> {
        @Override
        protected uPlace doInBackground(String... strings) {

            String LocationData = null;
            try {
                LocationData = ((new HTTPClient()).getLocationData(lat, lon));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            uPlace = JSONParser.getLocation(LocationData);
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
