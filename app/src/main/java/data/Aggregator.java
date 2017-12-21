package data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Hourly;
import model.Weather;

/**
 * Created by valcrune on 12/19/17.
 */

public class Aggregator {
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public static Weather getMean (Weather[] data) {
        Weather weather = new Weather();
        ArrayList<Hourly> obj = new ArrayList<Hourly>();

        weather.currentCondition.setTemperature((data[0].currentCondition.getTemperature() +
                data[1].currentCondition.getTemperature()
                + data[2].currentCondition.getTemperature() +
                data[3].currentCondition.getTemperature())/4);
        weather.currentCondition.setPercipitation((data[0].currentCondition.getPercipitation() +
                data[1].currentCondition.getPercipitation()
                + data[2].currentCondition.getPercipitation())/3);
        // NOAA does not have precipitation :(
        weather.currentCondition.setWindSpeed((data[0].currentCondition.getWindSpeed() +
                data[1].currentCondition.getWindSpeed()
                + data[2].currentCondition.getWindSpeed() +
                data[3].currentCondition.getWindSpeed())/4);

        for (int i = 0; i < 12; i++) {
            Hourly hourly = new Hourly();

            hourly.setTemperature((data[0].hourly.get(i).getTemperature() +
                    data[1].hourly.get(i).getTemperature()
                    + data[2].hourly.get(i).getTemperature() +
                    data[3].hourly.get(i).getTemperature())/4);
            hourly.setPercipitation((data[0].hourly.get(i).getPercipitation() +
                    data[1].hourly.get(i).getPercipitation()
                    + data[2].hourly.get(i).getPercipitation())/3);
            hourly.setWind((data[0].hourly.get(i).getWind() +
                    data[1].hourly.get(i).getWind()
                    + data[2].hourly.get(i).getWind() +
                    data[3].hourly.get(i).getWind())/4);
            obj.add(i, hourly);
        }
        weather.hourly = obj;

        return weather;
    }

    public static Weather getMax (Weather[] data) {
        Weather weather = new Weather();
        ArrayList<Hourly> obj = new ArrayList<Hourly>();

        //Log.v("[0]: ", "" + data[0].currentCondition.getTemperature());
        //Log.v("[1]: ", "" + data[1].currentCondition.getTemperature());
        //Log.v("[2]: ", "" + data[2].currentCondition.getTemperature());
        //Log.v("[3]: ", "" + data[3].currentCondition.getTemperature());

        double maxTemp = data[0].currentCondition.getTemperature();
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].currentCondition.getTemperature() < data[i + 1].currentCondition.getTemperature()
                    && maxTemp < data[i + 1].currentCondition.getTemperature()){
                maxTemp = data[i + 1].currentCondition.getTemperature();
            }
        }
        weather.currentCondition.setTemperature(maxTemp);

        double maxPrecipitation = data[0].currentCondition.getPercipitation();
        for (int i = 0; i < data.length - 2; i++) {
            if (data[i].currentCondition.getPercipitation() < data[i + 1].currentCondition.getPercipitation()
                    && maxPrecipitation < data[i + 1].currentCondition.getPercipitation()){
                maxPrecipitation = data[i + 1].currentCondition.getPercipitation();
            }
        }
        weather.currentCondition.setPercipitation(maxPrecipitation);

        double maxWind = data[0].currentCondition.getWindSpeed();
        for (int i = 0; i < data.length -1; i++) {
            if (data[i].currentCondition.getWindSpeed() < data[i + 1].currentCondition.getWindSpeed()
                    && maxWind < data[i + 1].currentCondition.getWindSpeed()){
                maxWind = data[i + 1].currentCondition.getWindSpeed();
            }
        }
        weather.currentCondition.setWindSpeed(maxWind);

        for (int i = 0; i < 12; i++) {
            Hourly hourly = new Hourly();

            maxTemp = 0;
            for (int j = 0; j < data.length -1; j++) {

                if (data[j].hourly.get(i).getTemperature() > data[j + 1].hourly.get(i).getTemperature()){
                    maxTemp = data[j].hourly.get(i).getTemperature();
                } else {
                    maxTemp = data[j + 1].hourly.get(i).getTemperature();
                }
            }
            maxPrecipitation = 0;
            for (int j = 0; j < data.length -2; j++) {
                if (data[j].hourly.get(i).getPercipitation() > data[j + 1].hourly.get(i).getPercipitation()){
                    maxPrecipitation = data[j].hourly.get(i).getPercipitation();
                } else {
                    maxPrecipitation = data[j + 1].hourly.get(i).getPercipitation();
                }
            }
            maxWind = 0;
            for (int j = 0; j < data.length -1; j++) {
                if (data[j].hourly.get(i).getWind() > data[j + 1].hourly.get(i).getWind()){
                    maxWind = data[j].hourly.get(i).getWind();
                } else {
                    maxWind = data[j + 1].hourly.get(i).getWind();
                }
            }

            obj.add(i, hourly);
        }
        weather.hourly = obj;

        return weather;
    }

    public static Weather getMin (Weather[] data) {
        Weather weather = new Weather();
        ArrayList<Hourly> obj = new ArrayList<Hourly>();

        double minTemp = data[0].currentCondition.getTemperature();
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].currentCondition.getTemperature() > data[i + 1].currentCondition.getTemperature()
                    && minTemp > data[i + 1].currentCondition.getTemperature()){
                minTemp = data[i + 1].currentCondition.getTemperature();
            }
        }
        weather.currentCondition.setTemperature(minTemp);

        double minPrecipitation = data[0].currentCondition.getPercipitation();
        for (int i = 0; i < data.length - 2; i++) {
            if (data[i].currentCondition.getPercipitation() > data[i + 1].currentCondition.getPercipitation()
                    && minPrecipitation > data[i + 1].currentCondition.getPercipitation()){
                minPrecipitation = data[i + 1].currentCondition.getPercipitation();
            }
        }
        weather.currentCondition.setPercipitation(minPrecipitation);

        double minWind = data[0].currentCondition.getWindSpeed();
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].currentCondition.getWindSpeed() > data[i + 1].currentCondition.getWindSpeed()
                    && minWind > data[i + 1].currentCondition.getWindSpeed()){
                minWind = data[i + 1].currentCondition.getWindSpeed();
            }
        }
        weather.currentCondition.setWindSpeed(minWind);

        for (int i = 0; i < 12; i++) {
            Hourly hourly = new Hourly();

            minTemp = 0;
            for (int j = 0; j < data.length - 1; j++) {

                if (data[j].hourly.get(i).getTemperature() < data[j + 1].hourly.get(i).getTemperature()){
                    minTemp = data[j].hourly.get(i).getTemperature();
                } else {
                    minTemp = data[j + 1].hourly.get(i).getTemperature();
                }
            }
            minPrecipitation = 0;
            for (int j = 0; j < data.length -2; j++) {
                if (data[j].hourly.get(i).getPercipitation() < data[j + 1].hourly.get(i).getPercipitation()){
                    minPrecipitation = data[j].hourly.get(i).getPercipitation();
                } else {
                    minPrecipitation = data[j + 1].hourly.get(i).getPercipitation();
                }
            }
            minWind = 0;
            for (int j = 0; j < data.length - 1; j++) {
                if (data[j].hourly.get(i).getWind() < data[j + 1].hourly.get(i).getWind()){
                    minWind = data[j].hourly.get(i).getWind();
                } else {
                    minWind = data[j + 1].hourly.get(i).getWind();
                }
            }

            obj.add(i, hourly);
        }
        weather.hourly = obj;

        return weather;
    }
}
