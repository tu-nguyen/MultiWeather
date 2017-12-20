package edu.umb.multiweather;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import data.GPSTracker;
import data.HTTPClient;
import data.JSONParser;
import model.Weather;
import model.uPlace;

import static data.Aggregator.getMax;
import static data.Aggregator.getMean;
import static data.Aggregator.getMin;


public class MainActivity extends AppCompatActivity {
    final String DEGREE  = "\u00b0";
    final int MEAN = 0;
    final int MAX = 1;
    final int MIN = 2;

    private float lat;
    private float lon;
    private int zip;
    private String city;

    private uPlace uPlace = new uPlace();
    private Weather weather[] = new Weather[4];
    private Weather meanWeather = new Weather();
    private Weather maxWeather = new Weather();
    private Weather minWeather = new Weather();

    DecimalFormat f = new DecimalFormat("#0.00");
    private TextView titleText;
    private TextView tempText;
    private TextView locationText;
    private TextView precipitationTitleText ;
    private TextView precipitationText;
    private TextView windSpeedTitle;
    private TextView windSpeed;
    private TextView modeText;

    private Button hourlyAggregated;
    private Button compareCurrent;
    private Button compareHourly;

    private Button meanButton;
    private Button maxButton;
    private Button minButton;

    private int mode = 0;

    private TextView alertText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        tempText = findViewById(R.id.tempText);
        locationText = findViewById(R.id.locationText);
        precipitationTitleText = findViewById(R.id.precipitationTitleText);
        precipitationText = findViewById(R.id.precipitationText);
        windSpeedTitle = findViewById(R.id.windSpeedTitleText);
        windSpeed = findViewById(R.id.windSpeedText);
        modeText = findViewById(R.id.modeView);

        hourlyAggregated = findViewById(R.id.hourlyAggregatedButton);
        compareCurrent = findViewById(R.id.compareCurrentButton);
        compareHourly = findViewById(R.id.comareHourlyButton);

        meanButton = findViewById(R.id.meanButton);
        maxButton = findViewById(R.id.maxButton);
        minButton = findViewById(R.id.minButton);

        meanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MEAN;
                updateData(weather);
            }
        });
        maxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MAX;
                updateData(weather);
            }
        });
        minButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = MIN;
                updateData(weather);
            }
        });

        alertText = findViewById(R.id.alertText);

        GPSTracker g = new GPSTracker(getApplicationContext());
        Location location = g.getLocation();
        if (location != null){
            lat = (float) location.getLatitude();
            lon = (float) location.getLongitude();
        }

        /*

        try {
            getLocationDataGeo(lat, lon);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        */
        uPlace.setLat((float) 42.3601);
        uPlace.setLon((float) -71.0589);
        uPlace.setCity("Boston");
        uPlace.setCountry("United States");
        uPlace.setState("Massachusetts");
        uPlace.setStateSymbol("MA");
        uPlace.setZip(String.valueOf(02122));
        uPlace.setCode(String.valueOf(000000));

        try {
            renderWeatherData(uPlace);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateData(this.weather);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected uPlace.
                Log.i("test", "uPlace: " + place.getName());
                city = (String) place.getName();

                uPlace.setLat((float) place.getLatLng().latitude);
                uPlace.setLon((float) place.getLatLng().longitude);
                uPlace.setCity((String) ((String) place.getName()).replaceAll(" ", ""));
                String[] temp = place.getAddress().toString().split(", ");
                Log.v("QQQQQQQQQQQQQQQQq::: ", place.getAddress().toString());
                uPlace.setCountry(temp[2]);
                uPlace.setState(temp[0].replaceAll(" ", ""));
                uPlace.setStateSymbol(temp[2]);
                uPlace.setZip(String.valueOf(02122));
                uPlace.setCode(String.valueOf(000000));

                /*
                try {
                    getLocationDataCity(city);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
                try {
                    renderWeatherData(uPlace);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateData(weather);
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

            /*
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
            */
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

            //weather[0] = JSONParser.getAccuWeather(AccuDataCurrent, AccuDataHourly12);
            // Cause accuWeather sets stupid 50 limit smfh
            weather[1] = JSONParser.getDarkSkyWeather(DarkSkyData);
            weather[2] = JSONParser.getWUWeather(WUDataCurrent, WUDataHourly);
            weather[3] = JSONParser.getNOAAWeather(NOAADataCurrent, NOAADataHourly);
            weather[0] = JSONParser.getDarkSkyWeather(DarkSkyData);;

            return weather;
        }

        @Override
        protected void onPostExecute(Weather[] weather) {
            super.onPostExecute(weather);

            //updateData(weather);
        }
    }

    private void updateData(Weather[] weather) {
        meanWeather = getMean(weather);
        maxWeather = getMax(weather);
        minWeather = getMin(weather);

        if (mode == MEAN) {
            modeText.setText("Mean");
            locationText.setText(uPlace.getCity() + ", " + uPlace.getState());
            tempText.setText(f.format(meanWeather.currentCondition.getTemperature()) + DEGREE + "F");
            precipitationText.setText(f.format(meanWeather.currentCondition.getPercipitation()) + "%");
            windSpeed.setText(f.format(meanWeather.currentCondition.getWindSpeed()) + "mph");
            alertText.setText(weather[1].currentCondition.getAlert());
        } else if (mode == MAX) {
            modeText.setText("Max ");
            locationText.setText(uPlace.getCity() + ", " + uPlace.getState());
            tempText.setText(f.format(maxWeather.currentCondition.getTemperature()) + DEGREE + "F");
            precipitationText.setText(f.format(maxWeather.currentCondition.getPercipitation()) + "%");
            windSpeed.setText(f.format(maxWeather.currentCondition.getWindSpeed()) + "mph");
            alertText.setText(weather[1].currentCondition.getAlert());
        } else if (mode == MIN) {
            modeText.setText("Min ");
            locationText.setText(uPlace.getCity() + ", " + uPlace.getState());
            tempText.setText(f.format(minWeather.currentCondition.getTemperature()) + DEGREE + "F");
            precipitationText.setText(f.format(minWeather.currentCondition.getPercipitation()) + "%");
            windSpeed.setText(f.format(minWeather.currentCondition.getWindSpeed()) + "mph");
            alertText.setText(weather[1].currentCondition.getAlert());
        }


    }
}
