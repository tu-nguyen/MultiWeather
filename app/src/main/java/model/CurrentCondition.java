package model;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by valcrune on 12/15/17.
 */

public class CurrentCondition implements Serializable{
    private int time;
    private String description;
    private double temperature;
    private double percipitation;
    private double windSpeed;
    private String alert;

    private CurrentCondition(Parcel in) {
        time = in.readInt();
        description = in.readString();
        temperature = in.readDouble();
        percipitation = in.readDouble();
        windSpeed = in.readDouble();
        alert = in.readString();
    }

    public CurrentCondition() {

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPercipitation() {
        return percipitation;
    }

    public void setPercipitation(double percipitation) {
        this.percipitation = percipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getAlert() {
        if (alert != null) {
            return alert;
        }
        return "No alert for this location";
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

}
